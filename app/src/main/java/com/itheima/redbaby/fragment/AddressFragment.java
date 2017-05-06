package com.itheima.redbaby.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.itheima.redbaby.R;
import com.itheima.redbaby.bean.AddressEventBusBean;
import com.itheima.redbaby.bean.AddressListBean;
import com.itheima.redbaby.manager.FragmentInstanceManager;
import com.itheima.redbaby.protocol.Api;
import com.itheima.redbaby.utils.UIUtils;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.resp.IResponse;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by lenovo on 2016/12/5.
 */
public class AddressFragment extends BaseFragment {

    public static final int STATE_EMPTY = 0;
    public static final int STATE_FULL = 1;
    public int mCurState = STATE_FULL;//默认是空数据的情况

    @Bind(R.id.tv_addaddress_full)
    TextView mTvAddaddressFull;
    @Bind(R.id.head_back_usercenter)
    TextView tv_back_usercenter;

    private View mFullView;
    private View mEmptyView;

    private ListView mListView;

    private List<AddressListBean.AddressList> mAddressLists;

    @Override
    public View initViews() {
        getAddressList();
        mFullView = View.inflate(UIUtils.getContext(), R.layout.pager_address_full, null);
        mEmptyView = View.inflate(UIUtils.getContext(), R.layout.pager_address_empty, null);
        mListView = (ListView) mFullView.findViewById(R.id.address_list);

        mListView.setAdapter(new AddressListAdapter()); //为listView建立适配器
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //position = position - mListView.getHeaderViewsCount();
                //Toast.makeText(UIUtils.getContext(), mLvDes[position], Toast.LENGTH_SHORT).show();
                AddressEventBusBean addressEventBusbean = new AddressEventBusBean();
                addressEventBusbean.id = mAddressLists.get(position).id + "";
                addressEventBusbean.name = mAddressLists.get(position).name;
                addressEventBusbean.phone = mAddressLists.get(position).phoneNumber;
                addressEventBusbean.address = mAddressLists.get(position).province;
                addressEventBusbean.addressdetail= mAddressLists.get(position).addressDetail;

                swichToChildFragment(FragmentInstanceManager.getInstance().getFragment(UpdateAddressFragment.class), true);
                EventBus.getDefault().postSticky(addressEventBusbean);
            }
        });

//        if (mCurState == STATE_EMPTY) {
//            return mEmptyView;
//        }
        return mFullView;
    }

    @Override
    protected void initData() {
//        mListView.setAdapter(new AddressListAdapter()); //为listView建立适配器
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                //position = position - mListView.getHeaderViewsCount();
//                //Toast.makeText(UIUtils.getContext(), mLvDes[position], Toast.LENGTH_SHORT).show();
//                swichToChildFragment(FragmentInstanceManager.getInstance().getFragment(UpdateAddressFragment.class), true);
//            }
//        });
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

    @OnClick({R.id.tv_addaddress_full, R.id.head_back_usercenter})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_addaddress_full:
                swichToChildFragment(FragmentInstanceManager.getInstance().getFragment(AddAddressFragment.class), true);
                break;

            case R.id.head_back_usercenter:
                getFragmentManager().popBackStack();
                break;
           /* case R.id.tv_addaddress_empty:
                swichToChildFragment(FragmentInstanceManager.getInstance().getFragment(AddAddressFragment.class), true);
                break;*/

        }
    }

    //-----------------listView的适配器-------------------------------------------
    class AddressListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            if (mAddressLists != null) {
                return mAddressLists.size();
            }
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(UIUtils.getContext(), R.layout.item_addresslist, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final AddressListBean.AddressList addressList = mAddressLists.get(position);

            holder.mTvAddresslistName.setText(addressList.name);
            holder.mTvAddresslistNumber.setText(addressList.phoneNumber);
            holder.mTvAddresslistDetail.setText(addressList.province + addressList.city + addressList.addressArea + addressList.addressDetail);

            return convertView;
        }

        class ViewHolder {
            @Bind(R.id.tv_addresslist_name)
            TextView mTvAddresslistName;
            @Bind(R.id.tv_addresslist_icon)
            TextView mTvAddresslistIcon;
            @Bind(R.id.tv_addresslist_number)
            TextView mTvAddresslistNumber;
            @Bind(R.id.tv_addresslist_detail)
            TextView mTvAddresslistDetail;
            @Bind(R.id.linLine)
            LinearLayout mLinLine;
            @Bind(R.id.ll_address_list)
            LinearLayout mLlAddressList;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
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


    private void getAddressList() {
        Api.getAddressList(new HttpLoader.HttpListener() {
            @Override
            public void onGetResponseSuccess(int requestCode, IResponse response) {
                AddressListBean addressListBean = (AddressListBean) response;
                mAddressLists = addressListBean.addressList;

            }

            @Override
            public void onGetResponseError(int requestCode, VolleyError error) {
                Toast.makeText(UIUtils.getContext(), "请求失败", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getAddressList();
    }
}
