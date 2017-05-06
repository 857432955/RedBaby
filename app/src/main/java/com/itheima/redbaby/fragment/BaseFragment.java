package com.itheima.redbaby.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itheima.redbaby.R;

/**
 * 作者：徐鹏 on 2016/12/3 20:41
 * 电话：15866972236
 */
public abstract class BaseFragment extends Fragment {
    public View mRootView;
    public Activity mActivity;
    private FragmentManager mFragmentManager;

    //fragment的创建
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取fragment所依赖的Activity对象
        mActivity = getActivity();
    }

    //初始化fragment的布局
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = initViews();
        return mRootView;
    }

    public abstract View initViews();   //静态方法,必须由子类实现


    //fragment所在的activity创建完成
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    protected void initData() {
    }

    ;//初始化数据,子类可以不实现

    //提供方法Fragment切换方法
    public void swichToChildFragment(Fragment fragment, boolean addToBackStack) {
        mFragmentManager = getFragmentManager();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if(fragment.isAdded())
        {
            transaction.show(fragment);
        }
        else
        {
            transaction.replace(R.id.fl_container, fragment);
        }
        if (addToBackStack) {
            //添加回退栈
            transaction.addToBackStack(fragment.getTag());
        }
        transaction.commit();
        //初始化
    }
}