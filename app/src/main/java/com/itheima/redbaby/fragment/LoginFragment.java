package com.itheima.redbaby.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.itheima.redbaby.R;
import com.itheima.redbaby.base.MyApplication;
import com.itheima.redbaby.bean.LoginResponse;
import com.itheima.redbaby.constant.RBConstants;
import com.itheima.redbaby.manager.FragmentInstanceManager;
import com.itheima.redbaby.utils.UIUtils;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.HttpParams;
import org.senydevpkg.net.resp.IResponse;
import org.senydevpkg.utils.ALog;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by lenovo on 2016/12/6.
 */
public class LoginFragment extends BaseFragment implements HttpLoader.HttpListener {

    @Bind(R.id.login_text)
    TextView mLoginText;
    @Bind(R.id.register_txt)
    TextView mRegisterTxt;
    @Bind(R.id.login_name_edit)
    EditText login_name;
    @Bind(R.id.login_pwd_edit)
    EditText login_pwd;

    public String mUsername;
    @Bind(R.id.head_back_login)
    TextView mHeadBackLogin;
    private String mPwd;

    @Override
    public View initViews() {
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_login, null);
        return view;
    }


    @Nullable
    @OnClick({R.id.login_text, R.id.register_txt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_text:
                RBConstants.LOGINNAME = login_name.getText().toString();
                mPwd = login_pwd.getText().toString();
//                RBConstants.LOGINNAME = "test";
//                mPwd = "test";
                HttpParams p = new HttpParams().put("username", RBConstants.LOGINNAME).put("password", mPwd);
                MyApplication.HL.post(RBConstants.URL_LOGIN, p, LoginResponse.class, RBConstants.REQUEST_CODE_LOGIN, this);

                break;
            case R.id.register_txt:
                getFragmentManager().popBackStack();
                swichToChildFragment(FragmentInstanceManager.getInstance().getFragment(RegisterFragment.class), true);
                break;
        }

    }


    @Override
    public void onGetResponseSuccess(int requestCode, IResponse response) {
        if (requestCode == RBConstants.REQUEST_CODE_LOGIN) {
            LoginResponse loginResponse = (LoginResponse) response;
            //与服务器端的数据匹配到
            if (loginResponse.response.equals("login")) {

                ALog.e("----userid---" + loginResponse.userInfo.userid);
                RBConstants.USERID = loginResponse.userInfo.userid;
                getFragmentManager().popBackStack();

                //登陆成功，跳转到账户中心界面
                swichToChildFragment(FragmentInstanceManager.getInstance().getFragment(UserCenterFragment.class), false);
            } else {
                //与服务器的数据无法匹配，根据错误code来弹土司
                if (loginResponse.error_code.equals(1534)) {
                    Toast.makeText(UIUtils.getContext(), "用户名或密码不能为空！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UIUtils.getContext(), loginResponse.error, Toast.LENGTH_SHORT).show();
                }
                ALog.e("---- 444444444---" + loginResponse.error);
            }
        }
    }

    @Override
    public void onGetResponseError(int requestCode, VolleyError error) {

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

    @OnClick(R.id.head_back_login)
    public void onClick() {
        getFragmentManager().popBackStack();
    }

}
