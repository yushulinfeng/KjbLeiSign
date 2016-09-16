package org.kjb.lei.sign.fragment;

import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.kjb.lei.sign.R;
import org.kjb.lei.sign.main.SignMain;
import org.kjb.lei.sign.model.AnClass;
import org.kjb.lei.sign.model.AnClassInfo;
import org.kjb.lei.sign.utils.all.StaticMethod;
import org.kjb.lei.sign.utils.base.BaseFragment;
import org.kjb.lei.sign.utils.connect.ConnectList;
import org.kjb.lei.sign.utils.connect.ConnectListener;
import org.kjb.lei.sign.utils.connect.ServerURL;
import org.kjb.lei.sign.utils.tools.PositionTool;
import org.kjb.lei.sign.utils.tools.TestTool;
import org.kjb.lei.sign.utils.tools.TimerTool;
import org.kjb.lei.sign.utils.tools.UserTool;

import butterknife.Bind;

/**
 * 签到界面
 */
public class SignFragment extends BaseFragment implements TimerTool.TimerListener {
    @Bind(R.id.sign_tv_show)
    TextView signTvShow;

    private boolean init_finish = false;
    private PositionTool position;
    private PositionTool.Point point;
    private String userid = "";
    private int index = 0, week = 0, last_time = 0;
    private int sign_state = 0;//0未签到，class_time该节次已签到
    //取国庆之前的时间--上课前10分钟可签到
    private static final int[] time_start = {750, 1000, 1350, 1550, 1850};
    private static final int[] time_end = {800, 1010, 1400, 1600, 1900};

    @Override
    protected int getLayoutId() {
        return R.layout.sign_main_sign;
    }

    @Override
    protected void afterCreate() {
        position = new PositionTool(getActivity());
        userid = UserTool.getUserInfo(getActivity())[0];
        AnClassInfo.initTableColor(SignMain.class_table);
        if (TestTool.isTest()) signTvShow.setText("已禁用签到\n长按头像进行设置");
        init_finish = true;
    }

    @Override
    public void onTimerAction() {
        Log.e("EEEEE", "TIMER");
        if (!init_finish) return;
        if (TestTool.isTest()) return;
        try {
            if (SignMain.is_teacher)
                teaSeeSign();
            else
                stuSign();
        }catch(Exception e){
            signTvShow.setText("-签到-");
        }
    }

    private void teaSeeSign() {
        if (TextUtils.isEmpty(SignMain.real_name)) {
            signTvShow.setText("教师信息获取失败");
            return;
        }
        @SuppressWarnings("deprecation")
        Time time = new Time();
        time.setToNow();
        int time_int = time.hour * 100 + time.minute;
        //周次
        week = time.weekDay + 1;//1-7
        index = 0;
        //节次
        for (int i = 0; i < 5; i++) {
            index = i;
            if (time_int < time_start[i])
                break;
        }
        if (time_int > time_start[4])
            index++;//那天的课结束了
        if (index == 0) {//保留1-5
            signTvShow.setText("签到尚未开始");
            return;
        }
        //显示信息的处理
        String class_show = "";
        last_time = (week - 1) * 5 + index;
        //时间判断，仅在开始签到后，到下一节上课前可看上一节课的信息（不保留历史）
        boolean needSee = false;
        for (int i = 0; i < SignMain.class_table.size(); i++) {//获取下一个节次
            AnClass cls = SignMain.class_table.get(i);
            int cls_time = cls.getTime();
            if (cls_time == last_time) {
                needSee = true;
            }
        }
        if (!needSee) {
            class_show = "非学生签到时间";
            int last_sign_time = 36;
            AnClass last_cls = null;
            for (int i = 0; i < SignMain.class_table.size(); i++) {//获取下一个节次
                AnClass cls = SignMain.class_table.get(i);
                int cls_time = cls.getTime();
                if (cls_time >= last_time && cls_time < last_sign_time) {
                    last_sign_time = cls_time;
                    last_cls = cls;
                }
            }
            if (last_cls != null) {
                class_show += "\n----------\n下次签到："
                        + last_cls.getName() + "\n地点：" + last_cls.getPlace();
            } else {
                class_show += "\n----------\n本周签到已结束";
            }
            signTvShow.setText(class_show);
            return;
        }
        StaticMethod.POST(ServerURL.SIGN, new ConnectListener() {
            @Override
            public ConnectList setParam(ConnectList list) {
                list.put(ServerURL.TYPE_WATCH);
                list.put("tea", SignMain.real_name);
                list.put("time", last_time + "");
                return list;
            }

            @Override
            public void onResponse(String response) {
                StaticMethod.showLog(response);////////////////////////
                if (TextUtils.isEmpty(response) || "-2".equals(response)) {//获取失败，不用管，30秒后重新
                } else {//获取成功，解析
                    try {
                        JSONObject json = new JSONObject(response);
                        String cls = json.getString("name");
                        int all = json.getInt("all");
                        int curr = json.getInt("curr");
                        signTvShow.setText("----" + cls + "----\n" + "签到人数：" + curr
                                + "，总人数：" + all + "\n\n总人数是指所有登录本平台的学生人数");
                    } catch (JSONException e) {
                    }
                }
            }
        });
    }

    private void stuSign() {
        if (SignMain.class_table == null) return;
        boolean needSign = false;
        //时间范围判定
        //地点获取
        @SuppressWarnings("deprecation")
        Time time = new Time();
        time.setToNow();
        int time_int = time.hour * 100 + time.minute;
        //周次
        week = time.weekDay + 1;//1-7
        index = 0;
        //节次
        for (int i = 0; i < 5; i++) {
            index = i;
            if (time_int < time_end[i])
                break;
        }
        if (time_int > time_start[index])//签到时间范围内
            needSign = true;
        if (time_int > time_end[4]) {
            needSign = false;
            index++;//那天的课结束了
        }
        index++;// 1-6
        //显示信息的处理
        String class_show = "";
        int class_time = (week - 1) * 5 + index;
        if (class_time == 36) class_time = 1;
        last_time = 36;
        for (int i = 0; i < SignMain.class_table.size(); i++) {//获取下一个节次
            AnClass cls = SignMain.class_table.get(i);
            int cls_time = cls.getTime();
            if (cls_time >= class_time && cls_time < last_time) {
                last_time = cls_time;
            }
        }
        String tea_temp1 = null, tea_temp2 = "", place = null;
        for (int i = 0; i < SignMain.class_table.size(); i++) {//加载所有该节次的课程
            AnClass cls = SignMain.class_table.get(i);
            if (cls.getTime() == last_time) {
                if (!"".equals(class_show)) class_show += "\n";
                class_show += cls.getName() + "\n" + cls.getPlace();
                if (tea_temp1 == null) tea_temp1 = cls.getTeacher();
                else tea_temp2 = cls.getTeacher();
                place = cls.getPlace();
            }
        }
        class_show = "--下一节课--\n" + class_show + "\n----\n"
                + (needSign ? "签到进行中" : "尚未开始签到");
        //没有教师，不支持签到
        if (TextUtils.isEmpty(tea_temp1) && TextUtils.isEmpty(tea_temp2)) {
            needSign = false;
            class_show += "\n--本课程无需签到--\n----\n";
        } else if (sign_state == last_time) {//如果已经签到
            needSign = false;
            class_show += "\n--已签到--\n";
        }
        //获取位置信息，判断是否在位置范围内
        point = position.getPosition();//本人位置
        PositionTool.Point refer = position.getReferPosition(getActivity(), place);
        //4区： 36.666383，117.134116
        //宿舍：36.666922，117.135008
        if (point != null) {
            if (Math.abs(point.x - refer.x) > 0.0005 ||
                    Math.abs(point.y - refer.y) > 0.0005) {//不再签到范围内
                needSign = false;
            }
            class_show += "--当前坐标--\n" + point.x + "," + point.y;
        } else {
            needSign = false;
        }
        signTvShow.setText(class_show);
        if (!needSign)
            return;
        //处理教师信息
        final String tea1 = tea_temp1, tea2 = tea_temp2;
        final int post_time = last_time;
        StaticMethod.POST(ServerURL.SIGN, new ConnectListener() {
            @Override
            public ConnectList setParam(ConnectList list) {
                list.put(ServerURL.TYPE_SIGN);
                list.put("stuid", userid);
                list.put("stu", SignMain.real_name);
                list.put("tea", tea1);
                list.put("tea2", tea2);
                list.put("time", post_time + "");
                list.put("x", point.x + "");
                list.put("Y", point.y + "");
                return list;
            }

            @Override
            public void onResponse(String response) {
                StaticMethod.showLog(response);////////////////////////
                if ("1".equals(response) || "2".equals(response)) {//签到成功
                    sign_state = last_time;
                    signTvShow.setText(signTvShow.getText().toString() +
                            "----------\n签到成功");
                } else {//签到失败，不用管，30秒后自动重签
                }
            }
        });
    }

}
