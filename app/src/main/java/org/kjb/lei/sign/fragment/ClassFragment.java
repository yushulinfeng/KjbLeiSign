package org.kjb.lei.sign.fragment;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.kjb.lei.sign.R;
import org.kjb.lei.sign.main.SignMain;
import org.kjb.lei.sign.model.AnClass;
import org.kjb.lei.sign.utils.base.BaseFragment;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * 课程表界面
 */
public class ClassFragment extends BaseFragment {

    @Bind(R.id.sign_class_recycler)
    RecyclerView signClassRecycler;

    @Override
    protected int getLayoutId() {
        return R.layout.sign_main_class;
    }

    @Override
    protected void afterCreate() {
        signClassRecycler.setItemAnimator(new DefaultItemAnimator());
        signClassRecycler.setHasFixedSize(true);
        GridLayoutManager girdLayoutManager = new GridLayoutManager(getActivity(), 6);
        girdLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
        signClassRecycler.setLayoutManager(girdLayoutManager);
        signClassRecycler.setAdapter(new ClassAdapter(SignMain.class_table));
        signClassRecycler.setHasFixedSize(true);
        signClassRecycler.addItemDecoration(new ClassDivider(getActivity()));
    }

}
