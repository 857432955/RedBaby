package com.itheima.redbaby.fragment;

import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.itheima.redbaby.R;
import com.itheima.redbaby.manager.FragmentInstanceManager;
import com.itheima.redbaby.utils.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：程相稳 on 2016/12/5 17:23
 * /                       .::::.
 * /                     .::::::::.
 * /                    :::::::::::
 * /                 ..:::::::::::'
 * /              '::::::::::::'
 * /                .::::::::::
 * /           '::::::::::::::..
 * /                ..::::::::::::.
 * /              ``::::::::::::::::
 * /               ::::``:::::::::'        .:::.
 * /              ::::'   ':::::'       .::::::::.
 * /            .::::'      ::::     .:::::::'::::.
 * /           .:::'       :::::  .:::::::::' ':::::.
 * /          .::'        :::::.:::::::::'      ':::::.
 * /         .::'         ::::::::::::::'         ``::::.
 * /     ...:::           ::::::::::::'              ``::.
 * /    ```` ':.          ':::::::::'                  ::::..
 * /                       '.:::::'                    ':'````..
 */
public class CommitSuccessFragment extends BaseFragment {
    @Bind(R.id.ordr_submit_bottom_text)
    Button ordrSubmitBottomText;
    @Bind(R.id.tv_paysuccess_money)
    TextView tvPaysuccessMoney;
    @Bind(R.id.tv_paysuccess_way)
    TextView tvPaysuccessWay;

    @Override
    public View initViews() {
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_pay_sucess, null);
        return view;
    }

    @Override
    protected void initData() {
        SharedPreferences sharedPreferences = UIUtils.getContext().getSharedPreferences("oderdetail", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        String paytype = sharedPreferences.getString("paytype", "");
        String totalmoney = sharedPreferences.getString("totalmoney", "");
        tvPaysuccessMoney.setText("应付金额:"+paytype);
        tvPaysuccessWay.setText("支付方式:"+totalmoney);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.ordr_submit_bottom_text})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.ordr_submit_bottom_text:
                swichToChildFragment(FragmentInstanceManager.getInstance().getFragment(HomeFragment.class),false);
                switchFragment(FragmentInstanceManager.getInstance().getFragment(HomeFragment.class));
                break;
        }
    }

    //提供方法切换Fragment，点击RadioButton的时候需要清空回退栈
    public void switchFragment(Fragment fragment) {
        FragmentManager mFragmentManager = getActivity().getSupportFragmentManager();
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
