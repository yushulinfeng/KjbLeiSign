package org.kjb.lei.sign.fragment;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.kjb.lei.sign.R;
import org.kjb.lei.sign.model.AnClass;
import org.kjb.lei.sign.model.AnClassInfo;

import java.util.List;

/**
 * 表格适配器
 */
public class ClassAdapter extends RecyclerView.Adapter<ClassHolder> {
    private static final String[] weeks = {"星期一", "星期二",
            "星期三", "星期四", "星期五", "星期六", "星期日"};
    private List<AnClass> items;

    public ClassAdapter(List<AnClass> items) {
        this.items = items;
    }

    @Override
    public ClassHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sign_main_class_item, parent, false);
        ClassHolder holder = new ClassHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ClassHolder holder, int position) {
        //课程表格式处理
        holder.tv.setVisibility(View.VISIBLE);
        holder.tv.setGravity(Gravity.CENTER);
        holder.bg.setVisibility(View.GONE);
        holder.getRootView().setBackgroundResource(0);
        if (position == 0) {
            holder.tv.setVisibility(View.GONE);
            holder.bg.setVisibility(View.GONE);
            holder.getRootView().setBackgroundColor(Color.rgb(238, 238, 238));
            return;
        } else if (position < 6) {
            holder.tv.setVisibility(View.GONE);
            holder.bg.setVisibility(View.VISIBLE);
            holder.bg.setText(position + "");
            holder.getRootView().setBackgroundColor(Color.rgb(238, 238, 238));
            return;
        } else if (position % 6 == 0) {
            holder.tv.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
            holder.tv.setText(weeks[position / 6 - 1]);
            holder.getRootView().setBackgroundColor(Color.rgb(238, 238, 238));
            return;
        }
        //意外处理
        if (items == null) {
            holder.tv.setText("");
            return;
        }
        //加载课程
        position = position - position / 6 - 5;//换算到1-35
        String show = "";
        for (int i = 0; i < items.size(); i++) {
            AnClass cls = items.get(i);
            if (cls.getTime() == position) {
                if (!"".equals(show)) show += "\n";
                show += cls.getName() + "\n" + cls.getPlace();
                holder.getRootView().setBackgroundColor(AnClassInfo.colors[cls.getColor()]);
            }
        }
        holder.tv.setText(show);
    }

    @Override
    public int getItemCount() {
        return 48;
    }

}
