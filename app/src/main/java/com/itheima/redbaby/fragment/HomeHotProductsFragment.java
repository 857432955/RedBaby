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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.itheima.redbaby.R;
import com.itheima.redbaby.activity.Product_clothes;
import com.itheima.redbaby.bean.NewProductBean;
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
 * @des 热门单品页面
 */
public class HomeHotProductsFragment extends BaseFragment {
    @Bind(R.id.base_title_btn_left)
    TextView mBaseTitleBtnLeft;
    @Bind(R.id.base_title_ibtn_tv)
    TextView mBaseTitleIbtnTv;
    @Bind(R.id.base_title_btn_right)
    TextView mBaseTitleBtnRight;
    @Bind(R.id.lv_product_list)
    ListView mLvProductList;
    @Bind(R.id.rb_sale)
    RadioButton mRbSale;
    @Bind(R.id.rb_price)
    RadioButton mRbPrice;
    @Bind(R.id.rb_shelves)
    RadioButton mRbShelves;
    @Bind(R.id.rg_order)
    RadioGroup mRgOrder;
    private boolean isPriceDown = false;
    private List<NewProductBean.ProductListBean> mProductList;

    /**
     * 初始化视图
     *
     * @return
     */
    @Override
    public View initViews() {
        getNewProductData("saleDown");
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_product_list, null);
        ButterKnife.bind(this, view);
        mRgOrder.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_sale:
                        getNewProductData("saleDown");
                        isPriceDown = false;
                        break;
                    case R.id.rb_price:
                        getNewProductData("priceDown");
                        mRbPrice.setBackgroundResource(R.drawable.price_center_down_selected);
                        break;
                    case R.id.rb_shelves:
                        getNewProductData("shelvesDown");
                        isPriceDown = false;
                        break;
                }
            }
        });
        mRbPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPriceDown){
                    getNewProductData("priceUp");
                    mRbPrice.setBackgroundResource(R.drawable.price_center_up_selected);
                }else {
                    getNewProductData("priceDown");
                    mRbPrice.setBackgroundResource(R.drawable.price_center_down_selected);
                }
                isPriceDown = !isPriceDown;
            }
        });
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
        mBaseTitleIbtnTv.setText("新品上架");

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
            ProductViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(UIUtils.getContext(), R.layout.item_product_list_listview, null);
                holder = new ProductViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ProductViewHolder) convertView.getTag();
            }
            NewProductBean.ProductListBean productListBean = mProductList.get(position);
            Picasso.with(UIUtils.getContext()).load(RBConstants.URL_SERVER + productListBean.pic).into(holder.mIvItemLimit);
            holder.mTvProductName.setText(productListBean.name);
            holder.mTvProductListPrice.setText(productListBean.price + ".00");
            holder.mTvProductMarketPrice.setText(productListBean.marketPrice + ".00");
            return convertView;
        }

    }

    class ProductViewHolder {
        @Bind(R.id.iv_item_limit)
        ImageView mIvItemLimit;
        @Bind(R.id.tv_product_name)
        TextView mTvProductName;
        @Bind(R.id.tv_product_marketPrice)
        TextView mTvProductMarketPrice;
        @Bind(R.id.tv_product_list_price)
        TextView mTvProductListPrice;

        ProductViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    /**
     * 从网络中获取到数据
     */
    private void getNewProductData(String orderby) {
        Api.getNewProductData(new HttpLoader.HttpListener() {
            @Override
            public void onGetResponseSuccess(int requestCode, IResponse response) {
                NewProductBean newProductBean = (NewProductBean) response;
                mProductList = newProductBean.productList;
                //设置适配器
                mLvProductList.setAdapter(new LimitAdapter());
                mLvProductList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getContext(), Product_clothes.class);
                        intent.putExtra("pId",mProductList.get(position).id + "");
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onGetResponseError(int requestCode, VolleyError error) {
                Toast.makeText(UIUtils.getContext(), "请求失败", Toast.LENGTH_SHORT).show();
            }
        }, orderby);
    }
}
