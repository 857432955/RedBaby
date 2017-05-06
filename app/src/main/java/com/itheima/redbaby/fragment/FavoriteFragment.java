package com.itheima.redbaby.fragment;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.itheima.redbaby.MainActivity;
import com.itheima.redbaby.R;
import com.itheima.redbaby.activity.Product_clothes;
import com.itheima.redbaby.base.LimitBuyBean;
import com.itheima.redbaby.base.MyApplication;
import com.itheima.redbaby.bean.FavoriteBean;
import com.itheima.redbaby.constant.Constants;
import com.itheima.redbaby.constant.RBConstants;
import com.itheima.redbaby.manager.FragmentInstanceManager;
import com.itheima.redbaby.utils.UIUtils;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.HttpParams;
import org.senydevpkg.net.resp.IResponse;
import org.senydevpkg.utils.ALog;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * .::::.
 * .::::::::.
 * :::::::::::
 * ..:::::::::::'
 * '::::::::::::'
 * .::::::::::
 * '::::::::::::::..
 * ..::::::::::::.
 * ``::::::::::::::::
 * ::::``:::::::::'        .:::.
 * ::::'   ':::::'       .::::::::.
 * .::::'      ::::     .:::::::'::::.
 * .:::'       :::::  .:::::::::' ':::::.
 * .::'        :::::.:::::::::'      ':::::.
 * .::'         ::::::::::::::'         ``::::.
 * ...:::           ::::::::::::'              ``::.
 * ```` ':.          ':::::::::'                  ::::..
 * '.:::::'                    ':'````..
 * Created by lx on 2016/12/5.
 */
public class FavoriteFragment extends BaseFragment implements View.OnClickListener {

    private ListView mFavorite_lv;
    private View mView;
    private TextView mBack1;
    private TextView mClean_tv;
    private FavoriteBean mFavoriteBean;

    @Override
    public View initViews() {
        mView = View.inflate(mActivity, R.layout.fragment_favorite,null);

        return mView;
    }

    @Override
    protected void initData() {
        mBack1 = (TextView) mView.findViewById(R.id.back1);
        mClean_tv = (TextView)mView.findViewById(R.id.clean_tv);
        mBack1.setOnClickListener(this);
        mClean_tv.setOnClickListener(this);
        mFavorite_lv = (ListView) mView.findViewById(R.id.favorite_lv);
        //获取网络数据
        initNetRequest();
        //给listview的item设置点击事件,网商品详情页面跳转
        mFavorite_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent  = new Intent(mActivity, Product_clothes.class);
                intent.putExtra("pId", mFavoriteBean.productList.get(position).id);
                 ALog.e("mFavoriteBean===============>" +mFavoriteBean.productList.get(position).id );
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
        params.addHeader("userid",RBConstants.USERID);
        HttpLoader.getInstance(mActivity).get(url,params,favoriteBeanClass,requestCode, new HttpLoader.HttpListener() {
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
                Toast.makeText(UIUtils.getContext(),"网络请求异常",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back1:
               getFragmentManager().popBackStack();
            break;
            case R.id.clean_tv:
                MyApplication.mCurState =0;
             //清空收藏夹
               //swichToChildFragment(FragmentInstanceManager.getInstance().getFragment(EmptyFavoriteFragment.class),true);
            /*    MainActivity mainActivity = new MainActivity();
                EmptyFavoriteFragment emptyFavoriteFragment = new EmptyFavoriteFragment();
                mainActivity.switchFragment(emptyFavoriteFragment);*/
               //通过清空回退栈实现
                EmptyFavoriteFragment emptyFavoriteFragment = new EmptyFavoriteFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                //循环的的pop回退栈
                int backStackEntryCount = fragmentManager.getBackStackEntryCount();
                while (backStackEntryCount > 0) {
                    fragmentManager.popBackStack();
                    backStackEntryCount--;
                }
                fragmentTransaction.replace(R.id.fl_container,emptyFavoriteFragment);
                fragmentTransaction.commit();
                break;
        }
    }

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
                convertView = View.inflate(UIUtils.getContext(),R.layout.favorite_item,null);
                holder = new RecordViewHolder();
                holder.favorite_record_iv = (ImageView) convertView.findViewById(R.id.favorite_record_iv);
                holder.favorite_brand_tv = (TextView) convertView.findViewById(R.id.favorite_brand_tv);
                holder.favorite_now_money_tv = (TextView) convertView.findViewById(R.id.favorite_now_money_tv);
                holder.favorite_old_money_tv = (TextView) convertView.findViewById(R.id.favorite_old_money_tv);
                holder.favorite_talk_tv = (TextView) convertView.findViewById(R.id.favorite_talk_tv);
                convertView.setTag(holder);
            }else {
                holder = (RecordViewHolder) convertView.getTag();//注意一定将获得的view赋值给holder,产生关系
            }
            /*--------------------将view和数据进行绑定---------------------*/
            Random random = new Random();
            int x = random.nextInt(1000);
            FavoriteBean.ProductListBean productListBean = favoriteBean.productList.get(position);
            holder.favorite_brand_tv.setText(productListBean.name);
            ALog.e("-----name-----"+productListBean.name);
            holder.favorite_now_money_tv.setText(productListBean.price);
            holder.favorite_old_money_tv.setText(productListBean.marketPrice);
            holder.favorite_talk_tv.setText("已有"+x+"人评价");
            HttpLoader.getInstance(mActivity).display(holder.favorite_record_iv,RBConstants.URL_SERVER+productListBean.pic);
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
