package com.itheima.redbaby.fragment;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RelativeLayout;

import com.itheima.redbaby.R;
import com.itheima.redbaby.activity.AboutActivity;
import com.itheima.redbaby.activity.BrowsingRecordActivity;
import com.itheima.redbaby.activity.HelpCenterActivity;
import com.itheima.redbaby.activity.UserFeedbackActivity;
import com.itheima.redbaby.constant.RBConstants;
import com.itheima.redbaby.manager.FragmentInstanceManager;
import com.itheima.redbaby.utils.UIUtils;

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
 * Created by 汤庆猛 on 2016/12/3.
 */
public class MoreFragment extends BaseFragment implements View.OnClickListener {

    private RelativeLayout mMy_account_rl;
    private RelativeLayout mRecent_browse_rl;
    private RelativeLayout mHelp_center;
    private RelativeLayout mUser_back;
    private RelativeLayout mAbout;


    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.fragment_more, null);
        mMy_account_rl = (RelativeLayout) view.findViewById(R.id.my_account_rl);
        mRecent_browse_rl = (RelativeLayout) view.findViewById(R.id.recent_browse_rl);
        mHelp_center = (RelativeLayout) view.findViewById(R.id.help_center);
        mUser_back = (RelativeLayout) view.findViewById(R.id.user_back);
        mAbout = (RelativeLayout) view.findViewById(R.id.about);
        mMy_account_rl.setOnClickListener(this);
        mRecent_browse_rl.setOnClickListener(this);
        mHelp_center.setOnClickListener(this);
        mUser_back.setOnClickListener(this);
        mAbout.setOnClickListener(this);
        return view;
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_account_rl:
                if (RBConstants.USERID != "") {
                   swichToChildFragment(FragmentInstanceManager.getInstance().getFragment(UserCenterFragment.class), true);
                    break;
                }
                swichToChildFragment(FragmentInstanceManager.getInstance().getFragment(LoginFragment.class), true);
                // swichToChildFragment(FragmentInstanceManager.getInstance().getFragment(UserCenterFragment.class), true);
                break;
            case R.id.recent_browse_rl:
                swichToChildFragment(FragmentInstanceManager.getInstance().getFragment(BrowseRecordFragment.class), true);
                break;
//                Intent intent = new Intent(UIUtils.getContext(), BrowsingRecordActivity.class);
//                startActivity(intent);
//                break;
            case R.id.help_center:
                startActivity(new Intent(UIUtils.getContext(), HelpCenterActivity.class));
                break;
            case R.id.user_back:
                startActivity(new Intent(UIUtils.getContext(), UserFeedbackActivity.class));
                break;
            case R.id.about:
                startActivity(new Intent(UIUtils.getContext(), AboutActivity.class));
                break;
        }
    }
}
