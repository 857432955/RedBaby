package com.itheima.redbaby.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.itheima.redbaby.R;
import com.itheima.redbaby.activity.Product_clothes;
import com.itheima.redbaby.bean.SearchListBean;
import com.itheima.redbaby.constant.RBConstants;
import com.itheima.redbaby.protocol.Api;
import com.itheima.redbaby.utils.LogUtils;
import com.itheima.redbaby.utils.PrefUtils;
import com.itheima.redbaby.utils.UIUtils;
import com.squareup.picasso.Picasso;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.resp.IResponse;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author
 * @time 2016/12/4  16:38
 * @des
 */
public class CommodityFragment extends BaseFragment {

    private List<SearchListBean.ProductListBean> mSearchDataList;

    @Bind(R.id.lv_home)
    ListView mLvHome;
    @Bind(R.id.iv_back)
    TextView mIvBack;
    @Bind(R.id.iv_filtrate)
    TextView mIvFiltrate;
    @Bind(R.id.bt_sales)
    RadioButton mBtSales;
    @Bind(R.id.bt_price)
    RadioButton mBtPrice;
    @Bind(R.id.bt_like)
    RadioButton mBtLike;
    @Bind(R.id.bt_putaway)
    RadioButton mBtPutaway;
    @Bind(R.id.rg_order)
    RadioGroup mRgOrder;
    @Bind(R.id.shopping_title)
    TextView mShoppingTitle;
//    @Bind(R.id.empty_prod_img)
//    ImageView mEmptyProdImg;
    private boolean isPriceDown = false;
    private String mData;
    private int mPriceStatus = 0;


    /**
     * 初始化视图
     *
     * @return
     */
    @Override
    public View initViews() {
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_commodity, null);
        ButterKnife.bind(this, view);
        //四种排序方式
        mRgOrder.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.bt_sales:
                        getSearchDataList(mData, "saleDown");
                        break;
                    case R.id.bt_price:
                        mBtPrice.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                LogUtils.s("现在状态是：" + mPriceStatus);
                                if (mPriceStatus == 0) {
                                    mBtPrice.setBackgroundResource(R.drawable.price_center_down_selected);
                                    getSearchDataList(mData, "priceDown");
                                    mPriceStatus = 1;
                                } else {
                                    mBtPrice.setBackgroundResource(R.drawable.price_center_up_selected);
                                    getSearchDataList(mData, "priceUp");
                                    mPriceStatus = 0;
                                }
                            }
                        });

                        break;
                    case R.id.bt_like:
                        //getSearchDataList(mData, "commentDown");
                        break;
                    case R.id.bt_putaway:
                        getSearchDataList(mData, "shelvesDown");
                        break;
                }
            }
        });
        return view;

    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        mData = PrefUtils.getString(mActivity, "searchData", "");
        LogUtils.s("接收数据----->" + mData);
        getSearchDataList(mData, null);

    }


    /**
     * 获取搜索网络数据据
     */
    public void getSearchDataList(String keyword, String orderby) {
        LogUtils.s("数据刷新了");
        RequestQueue queue = Volley.newRequestQueue(UIUtils.getContext());
        queue.add(Api.getSearchDataList(new HttpLoader.HttpListener() {
            @Override
            public void onGetResponseSuccess(int requestCode, IResponse response) {
                SearchListBean searchListBean = (SearchListBean) response;
                mSearchDataList = searchListBean.productList;

                mLvHome.setAdapter(new PregnantListViewAdapter());
                mLvHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getContext(), Product_clothes.class);
                        intent.putExtra("pId", mSearchDataList.get(position).id + "");
                        System.out.println("传递数据---------》"+ mSearchDataList.get(position).id);
                        startActivity(intent);
                    }
                });
                mIvFiltrate.setVisibility(View.GONE);
                mShoppingTitle.setText("搜索结果（" + mSearchDataList.size() + "）条");
                mShoppingTitle.setTextSize(17);
            }

            @Override
            public void onGetResponseError(int requestCode, VolleyError error) {
                Toast.makeText(mActivity, "获取搜索数据失败", Toast.LENGTH_SHORT).show();
            }
        }, keyword, orderby));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    //页面跳转
    @OnClick({R.id.iv_back, R.id.iv_filtrate})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back://返回上一页妈妈个人护理
                getFragmentManager().popBackStack();
                /*MomNurseFragment fragment = new MomNurseFragment();
                swichToChildFragment(fragment, true);*/
                break;
            case R.id.iv_filtrate://进入下一个筛选
                FiltrateFragment filtrateFragment = new FiltrateFragment();
                swichToChildFragment(filtrateFragment, true);
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.rg_order)
    public void onClick() {
    }


    /**
     * ListView的适配器
     */
    class PregnantListViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mSearchDataList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(UIUtils.getContext(), R.layout.item_commodity_listview, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.mTvBrandname.setText(mSearchDataList.get(position).name);
            holder.mTvPrice.setText("￥" + mSearchDataList.get(position).price);
            holder.mTvOriginal.setText("￥" + mSearchDataList.get(position).marketPrice);
            holder.mTvEvaluate.setText("已有0人评价");
            Picasso.with(mActivity).load(RBConstants.URL_SERVER + mSearchDataList.get(position).pic).into(holder.mIvCommodity);
            /*if (mSearchDataList.size() != 0) {
                holder.mTvBrandname.setText(mSearchDataList.get(position).name);
                holder.mTvPrice.setText("￥" + mSearchDataList.get(position).price);
                holder.mTvOriginal.setText("￥" + mSearchDataList.get(position).marketPrice);
                holder.mTvEvaluate.setText("已有0人评价");
                Picasso.with(mActivity).load(RBConstants.URL_SERVER + mSearchDataList.get(position).pic).into(holder.mIvCommodity);
            } else {
                mEmptyProdImg.setVisibility(View.VISIBLE);
            }*/
            return convertView;
        }


        class ViewHolder {
            @Bind(R.id.iv_commodity)
            ImageView mIvCommodity;
            @Bind(R.id.tv_brandname)
            TextView mTvBrandname;
            @Bind(R.id.tv_price)
            TextView mTvPrice;
            @Bind(R.id.tv_original)
            TextView mTvOriginal;
            @Bind(R.id.tv_evaluate)
            TextView mTvEvaluate;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    //碎片切换方法
    public void swichToChildFragment(Fragment fragment, boolean addToBackStack) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (addToBackStack) {
            //添加回退栈
            transaction.addToBackStack(fragment.getTag());
        }
        transaction.replace(R.id.fl_container, fragment);
        transaction.commit();
    }

    /**
     * 当页面销毁时取消绑定
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}