package com.itheima.redbaby.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.itheima.redbaby.R;
import com.itheima.redbaby.bean.AddressEventBusBean;
import com.itheima.redbaby.bean.IndentEventBusbean;
import com.itheima.redbaby.constant.RBConstants;
import com.itheima.redbaby.protocol.Api;
import com.itheima.redbaby.utils.UIUtils;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.HttpParams;
import org.senydevpkg.net.resp.IResponse;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by lenovo on 2016/12/6.
 */
public class UpdateAddressFragment extends BaseFragment {

    @Bind(R.id.head_updateaddress_back)
    TextView tv_updateaddress_back;
    @Bind(R.id.tv_saveaddress_update)
    TextView tv_saveaddress_update;
    @Bind(R.id.et_name_updateaddress)
    EditText name;
    @Bind(R.id.et_phone_updateaddress)
    EditText phonenum;
    @Bind(R.id.et_add_updateaddress)
    EditText details;
    @Bind(R.id.tv_address)
    EditText address;
    @Bind(R.id.default_address)
    Button defaultsddress;
    @Bind(R.id.remove)
    Button remove;

    private String mName;
    private String mPhonenum;
    private String mProvinces;
    private String mDetails;
    private String id;

    public void onEventMainThread(AddressEventBusBean event) {
        id = event.id;
        mName = event.name;
        mPhonenum = event.phone;
        mDetails = event.addressdetail;
        mDetails = event.address;
    }

    @Override
    public View initViews() {
        EventBus.getDefault().registerSticky(this);

        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_updateaddress, null);
        return view;
    }

    @Override
    protected void initData() {
        name.setText(mName);
        phonenum.setText(mPhonenum);
        address.setText(mProvinces);
        details.setText(mDetails);
        super.initData();
    }

    @OnClick({R.id.head_updateaddress_back, R.id.tv_saveaddress_update})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_updateaddress_back:
                getFragmentManager().popBackStack();
                break;
            case R.id.tv_saveaddress_update:

                // id = "134";
                mName = name.getText().toString();
                mPhonenum = phonenum.getText().toString();
                mProvinces = address.getText().toString();
                mDetails = details.getText().toString();

                HttpParams params = new HttpParams();
                params.put("id", id);
                params.put("name", mName);
                params.put("phoneNumber", mPhonenum);
                params.put("province", mProvinces);
                params.put("city", "");
                params.put("addressArea", "");
                params.put("addressDetail", mDetails);
                params.put("zipCode", 261000 + "");
                params.put("isDefault", 0 + "");
                params.addHeader("userid", RBConstants.USERID);

                PostUpdateAddress(params);

                Toast.makeText(UIUtils.getContext(), "保存地址", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    private void PostUpdateAddress(HttpParams params) {
        Api.PostUpdateAddress(new HttpLoader.HttpListener() {
            @Override
            public void onGetResponseSuccess(int requestCode, IResponse response) {
                Toast.makeText(UIUtils.getContext(), "地址已修改完成", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onGetResponseError(int requestCode, VolleyError error) {

            }
        }, params);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
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
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}