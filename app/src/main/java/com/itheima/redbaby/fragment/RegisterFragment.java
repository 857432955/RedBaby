package com.itheima.redbaby.fragment;

import android.os.Bundle;
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
public class RegisterFragment extends BaseFragment implements HttpLoader.HttpListener {


    @Bind(R.id.register_name_edit)
    EditText mRegisterNameEdit;
    @Bind(R.id.register_pwd)
    EditText mRegisterPwd;
    @Bind(R.id.register_text)
    TextView mRegisterText;
    @Bind(R.id.head_back_register)
    TextView mHeadBackRegister;

    @Override
    public View initViews() {
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_register, null);
        return view;
    }

    @OnClick(R.id.register_text)
    public void onClick(View view) {
        RBConstants.LOGINNAME = mRegisterNameEdit.getText().toString();
        String pwd = mRegisterPwd.getText().toString();
        // String pwd2 = mRegisterPwd2.getText().toString();

        HttpParams p = new HttpParams().put("username", RBConstants.LOGINNAME).put("password", pwd);
        MyApplication.HL.post(RBConstants.URL_REGISTER, p, LoginResponse.class, RBConstants.REQUEST_CODE_REGISTER, this);


        /*if (RBConstants.LOGINNAME == null){
            Toast.makeText(UIUtils.getContext(),"用户名不能为空！",Toast.LENGTH_SHORT).show();
        }else if (pwd == null){
            Toast.makeText(UIUtils.getContext(),"密码不能为空！",Toast.LENGTH_SHORT).show();
        }else if (pwd2 == null){
            Toast.makeText(UIUtils.getContext(),"确认密码不能为空！",Toast.LENGTH_SHORT).show();
        }else if (pwd != pwd2){
            Toast.makeText(UIUtils.getContext(),"两次输入的密码不一致！",Toast.LENGTH_SHORT).show();
        }*/

//        if (RBConstants.LOGINNAME == null){
//            Toast.makeText(UIUtils.getContext(),"用户名不能为空！",Toast.LENGTH_SHORT).show();
//        }else{
//            if (pwd == null){
//                Toast.makeText(UIUtils.getContext(),"密码不能为空！",Toast.LENGTH_SHORT).show();
//            }
//        }

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

    @Override
    public void onGetResponseSuccess(int requestCode, IResponse response) {
        // RegisterResponse registerResponse = (RegisterResponse) response;
        LoginResponse loginResponse = (LoginResponse) response;
        //与服务器端的数据匹配到
        if (loginResponse.response.equals("register")) {

            ALog.e("----userid---" + loginResponse.userInfo.userid);
            RBConstants.USERID = loginResponse.userInfo.userid;
            getFragmentManager().popBackStack();

            //注册完成后自动登录,登陆成功，跳转到账户中心界面
            swichToChildFragment(FragmentInstanceManager.getInstance().getFragment(UserCenterFragment.class), true);

        } else {
            //与服务器的数据无法匹配，根据错误code来弹土司
            if (loginResponse.error_code == "1534") {
                Toast.makeText(UIUtils.getContext(), "用户名或密码不能为空！", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(UIUtils.getContext(), loginResponse.error, Toast.LENGTH_SHORT).show();
            }
            ALog.e("---- 444444444---" + loginResponse.error);
        }

    }


    @Override
    public void onGetResponseError(int requestCode, VolleyError error) {

    }

    @OnClick(R.id.head_back_register)
    public void onClick() {
        getFragmentManager().popBackStack();
    }
}
