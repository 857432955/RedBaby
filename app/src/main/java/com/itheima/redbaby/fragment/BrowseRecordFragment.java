package com.itheima.redbaby.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.itheima.redbaby.R;
import com.itheima.redbaby.activity.Product_clothes;
import com.itheima.redbaby.base.MyApplication;
import com.itheima.redbaby.bean.FavoriteBean;
import com.itheima.redbaby.constant.RBConstants;
import com.itheima.redbaby.utils.UIUtils;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.HttpParams;
import org.senydevpkg.net.resp.IResponse;
import org.senydevpkg.utils.ALog;

import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lenovo on 2016/12/10.
 */
public class BrowseRecordFragment extends BaseFragment {

    @Bind(R.id.back_to_more)
    TextView mBackToMore;
    private ListView mFavorite_lv;
    private View mView;
    private TextView mBack1;
    private TextView mClean_tv;
    private FavoriteBean mFavoriteBean;

    @Override
    public View initViews() {
        mView = View.inflate(mActivity, R.layout.fragment_browserecord, null);
        return mView;
    }

    @Override
    protected void initData() {
        mFavorite_lv = (ListView) mView.findViewById(R.id.favorite_lv);
        //获取网络数据
        initNetRequest();
        //给listview的item设置点击事件,网商品详情页面跳转
        mFavorite_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mActivity, Product_clothes.class);
                intent.putExtra("pId", mFavoriteBean.productList.get(position).id);
                ALog.e("mFavoriteBean===============>" + mFavoriteBean.productList.get(position).id);
                startActivity(intent);
            }
        });
    }

    private void initNetRequest() {
        String url = RBConstants.URL_SERVER + "favorites";
        HttpParams params = new HttpParams();
        params.put("page", "1").put("pageNum", "20");
        Class<FavoriteBean> favoriteBeanClass = FavoriteBean.class;
        int requestCode = 101;
        params.addHeader("userid", RBConstants.USERID);
        HttpLoader.getInstance(mActivity).get(url, params, favoriteBeanClass, requestCode, new HttpLoader.HttpListener() {
            @Override
            public void onGetResponseSuccess(int requestCode, IResponse response) {
                mFavoriteBean = (FavoriteBean) response;
                //注意将数据通过构造参数传递,若直接用全局变量可能会获取不到数据
                final FavoriteAdapter adapter = new FavoriteAdapter(mFavoriteBean);
                MyApplication.getmMainThreadHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        mFavorite_lv.setAdapter(adapter);
                    }
                });
            }

            @Override
            public void onGetResponseError(int requestCode, VolleyError error) {
                Toast.makeText(UIUtils.getContext(), "网络请求异常", Toast.LENGTH_SHORT).show();
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

    @OnClick(R.id.back_to_more)
    public void onClick() {
        getFragmentManager().popBackStack();
    }

//    @OnClick(R.id.back_to_more)
//    public void onClick(View view){
//        getFragmentManager().popBackStack();
//    }


//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.back_to_more:
//                getFragmentManager().popBackStack();
//                break;
//        }
//    }

    private class FavoriteAdapter extends BaseAdapter {
        private FavoriteBean favoriteBean;

        public FavoriteAdapter(FavoriteBean favoriteBean) {
            this.favoriteBean = favoriteBean;
        }

        // public List<FavoriteBean.ProductListBean> mList = favoriteBean.productList;
        @Override
        public int getCount() {
            return favoriteBean.productList.size();
            //return mList.size();
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
            RecordViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(UIUtils.getContext(), R.layout.favorite_item, null);
                holder = new RecordViewHolder();
                holder.favorite_record_iv = (ImageView) convertView.findViewById(R.id.favorite_record_iv);
                holder.favorite_brand_tv = (TextView) convertView.findViewById(R.id.favorite_brand_tv);
                holder.favorite_now_money_tv = (TextView) convertView.findViewById(R.id.favorite_now_money_tv);
                holder.favorite_old_money_tv = (TextView) convertView.findViewById(R.id.favorite_old_money_tv);
                holder.favorite_talk_tv = (TextView) convertView.findViewById(R.id.favorite_talk_tv);
                convertView.setTag(holder);
            } else {
                holder = (RecordViewHolder) convertView.getTag();//注意一定将获得的view赋值给holder,产生关系
            }
            /*--------------------将view和数据进行绑定---------------------*/
            Random random = new Random();
            int x = random.nextInt(1000);
            FavoriteBean.ProductListBean productListBean = favoriteBean.productList.get(position);
            holder.favorite_brand_tv.setText(productListBean.name);
            ALog.e("-----name-----" + productListBean.name);
            holder.favorite_now_money_tv.setText(productListBean.price);
            holder.favorite_old_money_tv.setText(productListBean.marketPrice);
            holder.favorite_talk_tv.setText("已有" + x + "人评价");
            HttpLoader.getInstance(mActivity).display(holder.favorite_record_iv, RBConstants.URL_SERVER + productListBean.pic);
            /*--------------------将view和数据进行绑定---------------------*/
            return convertView;
        }
    }

    class RecordViewHolder {
        ImageView favorite_record_iv;
        TextView favorite_now_money_tv;
        TextView favorite_brand_tv;
        TextView favorite_old_money_tv;
        TextView favorite_talk_tv;
    }
}
