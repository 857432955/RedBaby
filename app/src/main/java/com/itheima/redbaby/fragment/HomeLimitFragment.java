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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.itheima.redbaby.R;
import com.itheima.redbaby.activity.Product_clothes;
import com.itheima.redbaby.bean.LimitBean;
import com.itheima.redbaby.constant.RBConstants;
import com.itheima.redbaby.manager.FragmentInstanceManager;
import com.itheima.redbaby.protocol.Api;
import com.itheima.redbaby.utils.UIUtils;
import com.squareup.picasso.Picasso;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.resp.IResponse;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 王帅峰
 * @time 2016/12/6  21:38
 * @des 限时抢购页面
 */
public class HomeLimitFragment extends BaseFragment {
    @Bind(R.id.base_title_btn_left)
    TextView mBaseTitleBtnLeft;
    @Bind(R.id.base_title_ibtn_tv)
    TextView mBaseTitleIbtnTv;
    @Bind(R.id.base_title_btn_right)
    TextView mBaseTitleBtnRight;
    @Bind(R.id.limit_lv)
    ListView mLimitLv;
   /* @Bind(R.id.result_empty)
    RelativeLayout mResultEmpty;
    @Bind(R.id.btn_refresh)
    Button mBtnRefresh;
    @Bind(R.id.refresh_view)
    RelativeLayout mRefreshView;
    @Bind(R.id.loading)
    FrameLayout mLoading;*/
    private List<LimitBean.ProductListBean> mProductList;
    private LimitAdapter mLimitAdapter;

    /**
     * 初始化视图
     *
     * @return
     */
    @Override
    public View initViews() {
        getLimitData();
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_limit, null);
        ButterKnife.bind(this, view);
        return view;
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {

        //将标题栏右侧按钮设置隐藏
        mBaseTitleBtnRight.setVisibility(View.GONE);
        //设置标题
        mBaseTitleIbtnTv.setText("限时抢购");
        /*mBtnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLimitData();
            }
        });*/
    }

    /**
     * 当页面销毁时,解绑释放资源
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * 返回按钮的点击事件,点击后返回上一页面
     */
    @OnClick(R.id.base_title_btn_left)
    public void onClick() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragment = FragmentInstanceManager.getInstance().getFragment(HomeFragment.class);
        transaction.replace(R.id.fl_container, fragment);
        transaction.commit();
    }


    /**
     * 适配器
     */
    class LimitAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            if (mProductList != null) {
                return mProductList.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(UIUtils.getContext(), R.layout.item_limit_listview, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final LimitBean.ProductListBean product = mProductList.get(position);
            String pictureUrl = product.pic;
            Picasso.with(UIUtils.getContext()).load(RBConstants.URL_SERVER + pictureUrl).into(holder.mIvItemLimit);
            holder.mTvLimitProductLimitprice.setText(product.limitPrice + "0");
            holder.mTvLimitProductName.setText(product.name);
            holder.mTvLimitProductPrice.setText(product.price + "0");
           /* if (product.leftTime!=0){
                MyApplication.getmMainThreadHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        product.setLeftTime( -- product.leftTime);
                        mLimitAdapter.notifyDataSetChanged();
                    }
                }, 1000);
            }*/
            holder.mBtnBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), Product_clothes.class);
                    intent.putExtra("pId", product.id + "");
                    startActivity(intent);
                }
            });
            int second = product.leftTime % 60;
            int minute = product.leftTime / 60 % 60;
            int hour = product.leftTime / 60 / 60;
            holder.mTvLimitProductTime.setText(hour + " : " + minute + " : " + second);
            return convertView;
        }

        class ViewHolder {
            @Bind(R.id.iv_item_limit)
            ImageView mIvItemLimit;
            @Bind(R.id.tv_limit_product_name)
            TextView mTvLimitProductName;
            @Bind(R.id.tv_limit_product_price)
            TextView mTvLimitProductPrice;
            @Bind(R.id.tv_limit_product_limitprice)
            TextView mTvLimitProductLimitprice;
            @Bind(R.id.tv_limit_product_time)
            TextView mTvLimitProductTime;
            @Bind(R.id.btn_buy)
            Button mBtnBuy;
            @Bind(R.id.ll_tiaozhuan)
            LinearLayout mLlTiaozhuan;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    /**
     * 从网络中获取到数据
     */
    private void getLimitData() {
        Api.getLimitData(new HttpLoader.HttpListener() {
            @Override
            public void onGetResponseSuccess(int requestCode, IResponse response) {
                LimitBean limitBean = (LimitBean) response;
                mProductList = limitBean.productList;
                if (mProductList != null) {
                    /*mLimitLv.setVisibility(View.VISIBLE);
                    mLoading.setVisibility(View.GONE);
                    mRefreshView.setVisibility(View.GONE);
                    mResultEmpty.setVisibility(View.GONE);*/
                    mLimitAdapter = new LimitAdapter();
                    //设置适配器
                    mLimitLv.setAdapter(mLimitAdapter);
                    mLimitLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(getContext(), Product_clothes.class);
                            intent.putExtra("pId", mProductList.get(position).id + "");
                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onGetResponseError(int requestCode, VolleyError error) {
               /* mRefreshView.setVisibility(View.VISIBLE);
                mLoading.setVisibility(View.GONE);
                mResultEmpty.setVisibility(View.GONE);
                mLimitLv.setVisibility(View.GONE);*/
                Toast.makeText(UIUtils.getContext(), "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
