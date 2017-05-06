package com.itheima.redbaby.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.itheima.redbaby.R;
import com.itheima.redbaby.base.MyApplication;
import com.itheima.redbaby.bean.AddAndUpdateAddressBean;
import com.itheima.redbaby.bean.AddressLinkBean;
import com.itheima.redbaby.bean.AddressListBean;
import com.itheima.redbaby.constant.RBConstants;
import com.itheima.redbaby.manager.FragmentInstanceManager;
import com.itheima.redbaby.protocol.Api;
import com.itheima.redbaby.utils.UIUtils;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.HttpParams;
import org.senydevpkg.net.resp.IResponse;
import org.senydevpkg.utils.ALog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lenovo on 2016/12/6.
 */
public class AddAddressFragment extends BaseFragment {

    @Bind(R.id.head_addaddress_back)
    TextView tv_add_back;
    @Bind(R.id.tv_saveaddres)
    TextView tv_saveaddress;
    @Bind(R.id.login_name)
    EditText loginname;
    @Bind(R.id.phonenumber_address)
    EditText phonenumber;
   // @Bind(R.id.provice_address)
   // EditText province;
    @Bind(R.id.address_detail)
    EditText detail;

    private String mName;
    private String mPhonenum;
    private String mProvinces;
    private String mDetails;
    private Spinner mAddress_sheng_sp;
    private Spinner mAddress_shi_sp;
    private AddressLinkBean mLinkBean;
    private List<String> mProvincesList;
    private List<String> mCityList;
    private List<AddressLinkBean.AreaListBean> mAreaList;
    private String mCity;


    @Override
    public View initViews() {
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_addaddress, null);
        mAddress_sheng_sp = (Spinner) view.findViewById(R.id.address_sheng_sp);
        mAddress_shi_sp = (Spinner) view.findViewById(R.id.address_shi_sp);

        requestProvince();
        return view;
    }
    //在服务器中获取省市的方法id = 0时获取的为省
    public void requestProvince() {
        /*---------------------网络请求平拼接-----------------------*/
       String url = RBConstants.URL_SERVER + "addressarea";
        HttpParams params = new HttpParams();
        params.put("id","0");
        Class<AddressLinkBean> addressLinkBeanClass = AddressLinkBean.class;
        int requestCode = 122;
        /*---------------------网络请求平拼接-----------------------*/
        HttpLoader.getInstance(mActivity).get(url, params, addressLinkBeanClass, requestCode, new HttpLoader.HttpListener() {
            @Override
            public void onGetResponseSuccess(int requestCode, IResponse response) {
                mLinkBean = (AddressLinkBean) response;
                //设置一个集合,用来存储从网络上获取的省份
                mProvincesList = new ArrayList<String>();
                mAreaList = mLinkBean.areaList;
                /*---------------------用来打印-----------------------*/
                for (int i = 0; i < mAreaList.size(); i++) {
                    mProvincesList.add(mAreaList.get(i).value);
                    ALog.e(mAreaList.get(i).id + "省");
                    ALog.e(mAreaList.get(i).value + "省");
                }
                /*---------------------用来打印-----------------------*/
                    //第一步,设置省的适配器
                    ArrayAdapter<String> provincesAdapter = new ArrayAdapter<String>(UIUtils.getContext(),R.layout.item_address_link, mProvincesList);
                    mAddress_sheng_sp.setAdapter(provincesAdapter);
                   //provincesAdapter.setDropDownViewResource();//为适配器设置下拉列表下拉时的样式
                mAddress_sheng_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        //2.将所选的一级联动中的值带入选中的textview中
                        //mAddress_sheng_sp.setSelection(position);
                        //将int类型的position转化成String类型的
                      // String sPosition = Integer.toString(position);
                        String id1 = mAreaList.get(position).id;
                        //3.选择后进行网络访问,获取市级信息
                        String url = RBConstants.URL_SERVER + "addressarea";
                        HttpParams params = new HttpParams();
                        params.put("id",id1);
                        Class<AddressLinkBean> addressLinkBeanClass = AddressLinkBean.class;
                        int requestCode = 122;
                        HttpLoader.getInstance(mActivity).get(url, params, addressLinkBeanClass, requestCode, new HttpLoader.HttpListener() {
                            @Override
                            public void onGetResponseSuccess(int requestCode, IResponse response) {
                                AddressLinkBean linkBeanCity = (AddressLinkBean) response;
                                //设置一个集合,用来存储从网络上获取的市区
                                mCityList = new ArrayList<String>();
                                List<AddressLinkBean.AreaListBean> areaListCity = linkBeanCity.areaList;
                                 /*---------------------用来打印-----------------------*/
                                for (int i = 0; i < areaListCity.size(); i++) {
                                    mCityList.add(areaListCity.get(i).value);
                                    ALog.e(areaListCity.get(i).value+ "市");
                                }
                                 /*---------------------用来打印-----------------------*/
                                //第一步,设置市的适配器
                                ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(UIUtils.getContext(),R.layout.item_address_link, mCityList);
                                mAddress_shi_sp.setAdapter(cityAdapter);
                                mAddress_shi_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        //2.将所选的一级联动中的值带入选中的textview中
                                        mAddress_shi_sp.setSelection(position);
                                        mCity = (String) mAddress_shi_sp.getSelectedItem();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            }

                            @Override
                            public void onGetResponseError(int requestCode, VolleyError error) {

                            }
                        });
                         mProvinces = (String) mAddress_sheng_sp.getSelectedItem();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                /*----------------------------------------------------------*/

                /*mAddress_sheng_sp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //2.将所选的一级联动中的值带入选中的textview中
                             mAddress_sheng_sp.setSelection(position);
                             //3.选择后进行网络访问,获取市级信息
                            String url = RBConstants.URL_SERVER + "addressarea";
                            HttpParams params = new HttpParams();
                            params.put("id","1");
                            Class<AddressLinkBean> addressLinkBeanClass = AddressLinkBean.class;
                            int requestCode = 122;
                            HttpLoader.getInstance(mActivity).get(url, params, addressLinkBeanClass, requestCode, new HttpLoader.HttpListener() {
                                @Override
                                public void onGetResponseSuccess(int requestCode, IResponse response) {
                                    AddressLinkBean linkBeanCity = (AddressLinkBean) response;
                                    //设置一个集合,用来存储从网络上获取的市区
                                    List<String> cityList = new ArrayList<String>();
                                    List<AddressLinkBean.AreaListBean> areaListCity = mLinkBean.areaList;
                                    for (int i = 0; i < cityList.size(); i++) {
                                        cityList.add(areaListCity.get(i).value);
                                        ALog.e(areaListCity.get(i).value);
                                    }
                                    //第一步,设置市的适配器
                                    ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(UIUtils.getContext(),R.layout.item_address_link,cityList);
                                    mAddress_shi_sp.setAdapter(cityAdapter);
                                }

                                @Override
                                public void onGetResponseError(int requestCode, VolleyError error) {

                                }
                            });
                        }
                    });*/

            }
            @Override
            public void onGetResponseError(int requestCode, VolleyError error) {

            }
        });

    }
    @OnClick({R.id.head_addaddress_back, R.id.tv_saveaddres})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_addaddress_back:
                getFragmentManager().popBackStack();
                break;
            case R.id.tv_saveaddres:

                mName = loginname.getText().toString();
                mPhonenum = phonenumber.getText().toString();
                //mProvinces = province.getText().toString();
                mDetails = detail.getText().toString();

                HttpParams params = new HttpParams();
                params.put("name", mName);
                params.put("phoneNumber", mPhonenum);
                params.put("province", mProvinces);
                params.put("city", mCity);
                params.put("addressArea", "");
                params.put("addressDetail", mDetails);
                params.put("zipCode", 261000 + "");
                params.put("isDefault", 0 + "");
                params.addHeader("userid", RBConstants.USERID);

                PostAddAddress(params);

                Toast.makeText(UIUtils.getContext(), "保存地址", Toast.LENGTH_SHORT).show();
                break;
        }


    }

    private void PostAddAddress(HttpParams params) {
        Api.PostAddAddress(new HttpLoader.HttpListener() {
            @Override
            public void onGetResponseSuccess(int requestCode, IResponse response) {
                getFragmentManager().popBackStack();
                swichToChildFragment(FragmentInstanceManager.getInstance().getFragment(AddAddressFragment.class), true);

            }

            @Override
            public void onGetResponseError(int requestCode, VolleyError error) {

            }
        }, params);
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
