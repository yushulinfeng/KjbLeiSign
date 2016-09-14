package org.kjb.lei.sign.fragment;

import android.util.Log;

import org.kjb.lei.sign.R;
import org.kjb.lei.sign.utils.base.BaseFragment;
import org.kjb.lei.sign.utils.tools.PositionTool;
import org.kjb.lei.sign.utils.tools.TimerTool;

/**
 * 签到界面
 */
public class SignFragment extends BaseFragment implements TimerTool.TimerListener {
    private PositionTool position;
    private PositionTool.Point point;

    @Override
    protected int getLayoutId() {
        return R.layout.sign_main_sign;
    }

    @Override
    protected void afterCreate() {
        position = new PositionTool(getActivity());

    }

    @Override
    public void onTimerAction() {
        Log.e("EEEEE", "TIMER");

        //时间范围判定
        //地点获取

        point = position.getPosition();

    }

}
