package com.itheima.redbaby.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.itheima.redbaby.R;
import com.itheima.redbaby.bean.AddressListBean;
import com.itheima.redbaby.bean.AddressListInfoBean;
import com.itheima.redbaby.protocol.Api;
import com.itheima.redbaby.utils.UIUtils;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.resp.IResponse;
import org.senydevpkg.utils.ALog;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * 作者：程相稳 on 2016/12/7 14:27
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
public class AddressListFragment extends BaseFragment {

    @Bind(R.id.head_back_paycenter)
    TextView headBackPaycenter;
    @Bind(R.id.address_list)
    ListView addressList;
    private View mItemView;
    private ListView mListView;
    private View mViewViewById;
    private static boolean IsVisible = false;
    private List<AddressListBean.AddressList> mAddressLists;
    private AddressListBean mAddressListBean;
    private AddressListInfoBean mAddressListInfoBean;
    private SharedPreferences.Editor mEditor;
    /*private OnDataTransmissionListener mListener;*/

    /*//MenuFragment.java文件中
    public interface OnDataTransmissionListener {
        public void dataTransmission(AddressListInfoBean data);
    }
    public void setOnDataTransmissionListener(OnDataTransmissionListener mListener) {
        this.mListener = mListener;
    }*/

    @Override
    public View initViews() {
        getAddressList();
        mItemView = View.inflate(UIUtils.getContext(), R.layout.pager_addresslist, null);
        mListView = (ListView) mItemView.findViewById(R.id.address_list);
        mAddressListInfoBean = new AddressListInfoBean();
        return mItemView;
    }

    @Override
    protected void initData() {

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(UIUtils.getContext(), "选择了" + position, Toast.LENGTH_SHORT).show();
                /*if (!IsVisible){*/
                    final SharedPreferences sharedPreferences = UIUtils.getContext().getSharedPreferences("oderdetail", Context.MODE_PRIVATE);
                    //获取编辑器
                    mEditor = sharedPreferences.edit();
                    view.findViewById(R.id.tv_addresslist_icon).setVisibility(View.VISIBLE);
                    //Toast.makeText(UIUtils.getContext(), "选择了" + mAddressListBean.addressList.get(position), Toast.LENGTH_SHORT).show();
                    //holder.tvAddresslistDetail.getText().toString();
                    //mAddressListBean.addressList.get(position);
                    mEditor.putString("adressdetail", mAddressListBean.addressList.get(position).province+
                            mAddressListBean.addressList.get(position).city+
                                    mAddressListBean.addressList.get(position).addressArea+
                            mAddressListBean.addressList.get(position).addressDetail);
                    System.out.println(mAddressListBean.addressList.get(position).addressDetail+"11111111111111111111");
                    mEditor.putString("adressname", mAddressListBean.addressList.get(position).name);
                    mEditor.putString("adressnumber", mAddressListBean.addressList.get(position).phoneNumber);
                    mEditor.putString("adressid", mAddressListBean.addressList.get(position).id+"");
                    mEditor.commit();
                    /**
                     * 方法二：采取接口回调的方式进行数据传递*/
                    /*if (mListener != null) {
                        mListener.dataTransmission(mAddressListInfoBean);
                    }*/
                    //EventBus.getDefault().postSticky(mAddressListInfoBean);//------------------发布粘性事件：
                    IsVisible= true;
                /*}else{
                    view.findViewById(R.id.tv_addresslist_icon).setVisibility(View.INVISIBLE);
                    IsVisible= false;
                }*/
                SystemClock.sleep(200);
                //view.findViewById(R.id.tv_addresslist_icon).setVisibility(View.INVISIBLE);
                //swichToChildFragment(FragmentInstanceManager.getInstance().getFragment(UpdateAddressFragment.class), true);
                getFragmentManager().popBackStack();
            }
        });
    }





    private class MyListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mAddressLists.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(mActivity, R.layout.item_addresslist, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvAddresslistDetail.setText(mAddressLists.get(position).province +
                    mAddressLists.get(position).city + mAddressLists.get(position).addressArea
                    + mAddressLists.get(position).addressDetail);
            holder.tvAddresslistName.setText(mAddressLists.get(position).name);
            holder.tvAddresslistNumber.setText(mAddressLists.get(position).phoneNumber);
            return convertView;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
    }

    class ViewHolder {
        @Bind(R.id.tv_addresslist_icon)
        TextView tvAddresslistIcon;
        @Bind(R.id.tv_addresslist_name)
        TextView tvAddresslistName;
        @Bind(R.id.tv_addresslist_number)
        TextView tvAddresslistNumber;
        @Bind(R.id.tv_addresslist_detail)
        TextView tvAddresslistDetail;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
            /*view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!IsVisible){
                        tvAddresslistIcon.setVisibility(View.VISIBLE);
                        IsVisible= true;
                    }else{
                        tvAddresslistIcon.setVisibility(View.INVISIBLE);
                        IsVisible= false;
                    }
                }
            });*/
        }

    }

    @OnClick({R.id.head_back_paycenter})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_back_paycenter:
                getFragmentManager().popBackStack();
                break;
        }
    }

    private void getAddressList() {
        Api.getAddressList(new HttpLoader.HttpListener() {
            @Override
            public void onGetResponseSuccess(int requestCode, IResponse response) {
                mAddressListBean = (AddressListBean) response;
                mAddressLists = mAddressListBean.addressList;
                ALog.i("请求成功_____________________");
                mListView.setAdapter(new MyListAdapter()); //为listView建立适配
            }
            @Override
            public void onGetResponseError(int requestCode, VolleyError error) {

            }
        });
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
