package com.itheima.redbaby.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.itheima.redbaby.R;
import com.itheima.redbaby.base.MyApplication;
import com.itheima.redbaby.bean.LimitBean;
import com.itheima.redbaby.bean.LoginResponse;
import com.itheima.redbaby.bean.UserInfoBean;
import com.itheima.redbaby.constant.RBConstants;
import com.itheima.redbaby.manager.FragmentInstanceManager;
import com.itheima.redbaby.protocol.Api;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.HttpParams;
import org.senydevpkg.net.resp.IResponse;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lenovo on 2016/12/4.
 */
public class UserCenterFragment extends BaseFragment implements HttpLoader.HttpListener {


    @Bind(R.id.ll_account_myorder)
    LinearLayout mLlAccountMyorder;
    @Bind(R.id.ll_account_address_manage)
    LinearLayout mLlAccountAddressManage;
    @Bind(R.id.ll_account_giftcard)
    LinearLayout mLlAccountGiftcard;
    @Bind(R.id.ll_account_favorite)
    LinearLayout mLlAccountFavorite;
    @Bind(R.id.head_back_usercenter)
    TextView tv_back_user;
    @Bind(R.id.login_quit)
    TextView tv_login_quit;
    @Bind(R.id.my_name_text)
    TextView tv_name;
    @Bind(R.id.my_bonus_text)
    TextView tv_bonus;
    @Bind(R.id.my_level_text)
    TextView tv_level;
    @Bind(R.id.order_count)
    TextView tv_order_count;
    @Bind(R.id.favourite_count)
    TextView tv_favourite;

    private UserInfoBean mUserInfoBean;


    @Override
    public View initViews() {
        getUserInfo();
        View view = View.inflate(mActivity, R.layout.fragment_usercenter, null);
        return view;
    }

    @Override
    protected void initData() {
        //getUserInfo();
        tv_name.setText("用户名：" + RBConstants.LOGINNAME);
        if (mUserInfoBean == null) {
            tv_bonus.setText("总积分：1000");
            tv_level.setText("会员等级: 钻石会员");
            tv_order_count.setText("我的订单:");
        } else {
            tv_bonus.setText("总积分：" + mUserInfoBean.userInfo.bonus);
            tv_level.setText("会员等级: " + mUserInfoBean.userInfo.level);
            tv_order_count.setText("我的订单:(" + mUserInfoBean.userInfo.orderCount + ")");
        }

        tv_favourite.setText("收藏夹");
        //tv_favourite.setText("收藏夹:(" + mUserInfoBean.userInfo.favoritesCount + ")");
        super.initData();
    }

    private void getUserInfo() {
        Api.getUserInfo(this);
    }


    @Override
    public void onGetResponseSuccess(int requestCode, IResponse response) {
        mUserInfoBean = (UserInfoBean) response;
    }

    @Override
    public void onGetResponseError(int requestCode, VolleyError error) {

    }

    @OnClick({R.id.ll_account_myorder, R.id.ll_account_giftcard, R.id.ll_account_favorite, R.id.ll_account_address_manage, R.id.head_back_usercenter, R.id.login_quit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_account_myorder:
                swichToChildFragment(FragmentInstanceManager.getInstance().getFragment(IndentFragment.class), true);
                break;
            case R.id.ll_account_address_manage:
                swichToChildFragment(FragmentInstanceManager.getInstance().getFragment(AddressFragment.class), true);
                break;
            case R.id.ll_account_favorite:
                if (MyApplication.mCurState == 0) {
                    swichToChildFragment(FragmentInstanceManager.getInstance().getFragment(EmptyFavoriteFragment.class), true);
                } else {
                    swichToChildFragment(FragmentInstanceManager.getInstance().getFragment(FavoriteFragment.class), true);
                }
                break;
            case R.id.ll_account_giftcard:
                swichToChildFragment(FragmentInstanceManager.getInstance().getFragment(CouponMoreFragment.class), true);
                break;

            case R.id.head_back_usercenter:
                //getFragmentManager().popBackStack();
                //通过清空回退栈实现
                MoreFragment moreFragment = new MoreFragment();
                FragmentManager fragmentManager = getFragmentManager();
                // android.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                //循环的的pop回退栈
                int backStackEntryCount = fragmentManager.getBackStackEntryCount();
                while (backStackEntryCount > 0) {
                    fragmentManager.popBackStack();
                    backStackEntryCount--;
                }
                fragmentTransaction.replace(R.id.fl_container, moreFragment);
                fragmentTransaction.commit();
                break;
            case R.id.login_quit:
                new AlertDialog.Builder(getActivity()).setTitle("退出登录").setMessage("是否退出登录").setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HttpParams p = new HttpParams().addHeader("userid", RBConstants.USERID);
                        MyApplication.HL.post(RBConstants.URL_LOGOUT, p, LoginResponse.class, RBConstants.REQUEST_CODE_LOGOUT, new HttpLoader.HttpListener() {
                            @Override
                            public void onGetResponseSuccess(int requestCode, IResponse response) {
                                RBConstants.USERID = "";
                                swichToChildFragment(FragmentInstanceManager.getInstance().getFragment(LoginFragment.class), true);
                            }

                            @Override
                            public void onGetResponseError(int requestCode, VolleyError error) {

                            }
                        });

                    }
                }).show();
                break;
        }
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


}
