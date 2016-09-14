package org.kjb.lei.sign.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.kjb.lei.sign.R;

/**
 * Created by YuShuLinFeng on 2016/9/15.
 */
public class ClassHolder extends RecyclerView.ViewHolder {
    public TextView tv;
    public TextView bg;
    private View rootView;

    public ClassHolder(View itemView) {
        super(itemView);
        tv = (TextView) itemView.findViewById(R.id.class_item_tv);
        bg = (TextView) itemView.findViewById(R.id.class_item_tv_bg);
        rootView = itemView;
    }

    public View getRootView() {
        return rootView;
    }

}
