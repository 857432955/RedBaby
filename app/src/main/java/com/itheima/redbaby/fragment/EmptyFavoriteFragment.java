package com.itheima.redbaby.fragment;

import android.view.View;
import android.widget.TextView;

import com.itheima.redbaby.R;
import com.itheima.redbaby.manager.FragmentInstanceManager;

import java.util.List;

/**
 * .::::.
 * .::::::::.
 * :::::::::::
 * ..:::::::::::'
 * '::::::::::::'
 * .::::::::::
 * '::::::::::::::..
 * ..::::::::::::.
 * ``::::::::::::::::
 * ::::``:::::::::'        .:::.
 * ::::'   ':::::'       .::::::::.
 * .::::'      ::::     .:::::::'::::.
 * .:::'       :::::  .:::::::::' ':::::.
 * .::'        :::::.:::::::::'      ':::::.
 * .::'         ::::::::::::::'         ``::::.
 * ...:::           ::::::::::::'              ``::.
 * ```` ':.          ':::::::::'                  ::::..
 * '.:::::'                    ':'````..
 * Created by lx on 2016/12/5.
 */
public class EmptyFavoriteFragment extends BaseFragment implements View.OnClickListener {

    private View mView;
    private TextView mBack_account_center;

    @Override
    public View initViews() {
        mView = View.inflate(mActivity, R.layout.empty_fragment_favorite,null);
        return mView;
    }

    @Override
    protected void initData() {
        mBack_account_center = (TextView) mView.findViewById(R.id.back_account_center);
        mBack_account_center.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        swichToChildFragment(FragmentInstanceManager.getInstance().getFragment(UserCenterFragment.class),true);
    }



}
