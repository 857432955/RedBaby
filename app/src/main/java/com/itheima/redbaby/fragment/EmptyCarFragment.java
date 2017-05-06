package com.itheima.redbaby.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.itheima.redbaby.R;

/**
 * 作者：徐鹏 on 2016/12/6 14:44
 * 电话：15866972236
 */
public class EmptyCarFragment extends BaseFragment {
    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.fragment_empty_car,null);
        TextView viewById = (TextView) view.findViewById(R.id.tv_goto_another);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeLimitFragment homeLimitFragment = new HomeLimitFragment();
                switchFragment(homeLimitFragment);
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("EmptyCarFragment","onDestroyView........");
    }

    //提供方法切换Fragment，点击RadioButton的时候需要清空回退栈
    public void switchFragment(Fragment fragment) {
        android.support.v4.app.FragmentManager mFragmentManager = getFragmentManager();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        //循环的的pop回退栈
        int backStackEntryCount = mFragmentManager.getBackStackEntryCount();
        while (backStackEntryCount > 0) {
            mFragmentManager.popBackStack();
            backStackEntryCount--;
        }
        transaction.replace(R.id.fl_container, fragment);
        transaction.commit();
    }



}
