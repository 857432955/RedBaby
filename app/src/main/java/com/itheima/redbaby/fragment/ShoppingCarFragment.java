package com.itheima.redbaby.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.itheima.redbaby.R;
import com.itheima.redbaby.activity.Product_clothes;
import com.itheima.redbaby.bean.ColorAndSizeBean;
import com.itheima.redbaby.bean.ShoppingCarBean;
import com.itheima.redbaby.constant.RBConstants;
import com.itheima.redbaby.manager.FragmentInstanceManager;
import com.itheima.redbaby.utils.LogUtils;
import com.itheima.redbaby.utils.UIUtils;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.HttpParams;
import org.senydevpkg.net.resp.IResponse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

public class ShoppingCarFragment extends BaseFragment implements View.OnClickListener {


    @Bind(R.id.fraHead)
    FrameLayout mFraHead;
    @Bind(R.id.shopcar_total_buycount_text_1)
    TextView mShopcarTotalBuycountText1;
    @Bind(R.id.shopcar_total_bonus_text_1)
    TextView mShopcarTotalBonusText1;
    @Bind(R.id.shopcar_total_money_text_1)
    TextView mShopcarTotalMoneyText1;
    @Bind(R.id.shopcar_product_list)

    ListView mListView;
    @Bind(R.id.shopcar_bottom_toPay_text)
    TextView mTextView;
    private ShoppingCarBean mShoppingCarBean;
    private SharedPreferences sp;

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.fragment_shopping_car, null);
        Log.i("ShoppingCarFragment","onCreateView.......");
        LogUtils.s("1223323243--------------------------------......");
        //请求网络
        requestServer();

        return view;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EventBus.getDefault().registerSticky(this);//-----------------注册EventBus
        Log.i("ShoppingCarFragment","onCreate........");
    }
    @Override
    protected void initData() {
        Log.i("ShoppingCarFragment","onCreateActivity-------initData.......");
    }

    //处理
    public void onEvent(ColorAndSizeBean event){
        String sku = event.id+":"+event.count+":"+event.color+","+event.size+"|";
//        //-------------------将sku存入sp中
//        sp = mActivity.getSharedPreferences("car", mActivity.MODE_APPEND);
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putString("sku",sku);
//        editor.commit();


        //-----------------用文件输出流来存储数据------------------------------
//        try {
//            File cacheDir = mActivity.getCacheDir();
//            File file = new File(cacheDir,"sku.text");
//            FileOutputStream fos = new FileOutputStream(file,true);
//            fos.write(sku.getBytes());
//            fos.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }




        //---------------用StringBuff-----------------------------------------
//        Log.i("onEvent","hahahhahahhaha....");
//        RBConstants.sb.append(sku);     //用StingBuffer存在内存中......


        //--------------用集合存储
        //RBConstants.listCar.add(sku);
    }

    /**
     * ---------------------------请求网络---------------------------------------
     */
    private void requestServer() {
        //String sku = "7:3:2,3,4|10:2:2,3|5:5:4|7:3:1,4";
        //--------------------用流取读取---------------------------------------
//        String sku ="";
//        try {
//            File cacheDir = mActivity.getCacheDir();
//            File file = new File(cacheDir,"sku.text");
//            BufferedReader br = new BufferedReader(new FileReader(file));
//            sku = br.readLine();
//            br.close();
//            Log.i("sku",sku);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        LogUtils.s("够恶u无u好的suv-------------------------------------》");
        //------------------------------读取集合
        String sku = "";
        for(String s:RBConstants.listCar){
            sku = sku+s;
        }
        Log.i("666",sku);



        if (sku.isEmpty()){
            switchFragment(FragmentInstanceManager.getInstance()
                    .getFragment(EmptyCarFragment.class));
        }else{

            //String sku = "7:3:2,3,4|10:2:2,3|5:5:4|7:3:1,4";
            //String sku = sp.getString("sku","1");//---------------在sp中获取sku
            String url = RBConstants.URL_CART;
            HttpParams params = new HttpParams();
            params.put("sku", sku);
            Class<? extends IResponse> clazz = ShoppingCarBean.class;
            int requestCode = RBConstants.REQUEST_CODE_CART;
            Request<?> request = HttpLoader.getInstance(mActivity)
                    .get(url, params, clazz, requestCode, new HttpLoader.HttpListener() {
                        @Override
                        public void onGetResponseSuccess(int requestCode, IResponse response) {
                            mShoppingCarBean = (ShoppingCarBean) response;
                            Log.i("ShoppingCarFragment","从网络获取到数据.......");
                            initHeadData();//-----------填充头信息的三个总计
                            if (mListView.getFooterViewsCount()==0){//判断是否存在底部view
                                View footView = View.inflate(mActivity,R.layout.item_shop_car_foot,null);//添加底部view
                                mListView.addFooterView(footView,null,false);//失去点击事件
                            }

                            mListView.setAdapter(new MyListAdapter()); //为listView建立适配器
                            mTextView.setOnClickListener(ShoppingCarFragment.this);//----------为结算设置点击事件
                            //---------为listView设置条目点击事件
                            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent = new Intent(mActivity,Product_clothes.class);
                                    intent.putExtra("pId",mShoppingCarBean.cart.get(position).product.id);
                                    startActivity(intent);
                                    RBConstants.listPosition.add(position);
                                }
                            });
                        }
                        @Override
                        public void onGetResponseError(int requestCode, VolleyError error) {

                        }
                    }, true);
        }

    }

    /**
     * ------------------填充头信息的三个总计-------------------------------
     */
    private void initHeadData() {
        mShopcarTotalBuycountText1.setText(mShoppingCarBean.totalCount);
        mShopcarTotalBonusText1.setText(mShoppingCarBean.totalPoint);
        mShopcarTotalMoneyText1.setText(mShoppingCarBean.totalPrice);
    }

    //-----------------结算的点击事件-----------------------------
    @Override
    public void onClick(View v) {
        swichToChildFragment(FragmentInstanceManager.getInstance()
                .getFragment(PayCenterFragment.class), true);


        EventBus.getDefault().postSticky(mShoppingCarBean);//------------------发布粘性事件：

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        Log.i("ShoppingCarFragment","黄油刀创建......");
        return rootView;
    }



    //-----------------listView的适配器-------------------------------------------
    class MyListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mShoppingCarBean.cart.size();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = View.inflate(mActivity, R.layout.item_shopping, null);
            Button bt = (Button) view.findViewById(R.id.delete);
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RBConstants.listCar.remove(position);
                    requestServer();
                }
            });



            ViewHolder viewHolder = new ViewHolder(view);
            ShoppingCarBean.CartBean cartBean = mShoppingCarBean.cart.get(position);
            String prodNum = cartBean.prodNum;
            viewHolder.mShopcarItemProdIdText.setText(prodNum);//数量

            ShoppingCarBean.CartBean.ProductBean product = cartBean.product;
            String name = product.name;
            viewHolder.mShopcarItemProdNameText.setText(name);//商品名


            String price = product.price;
            viewHolder.mShopcarItemProdPrice.setText(price);//价格
            viewHolder.mShopcarItemCount.setText(Integer.parseInt(price)*Integer.parseInt(prodNum)+"");//小计

            String url =RBConstants.URL_SERVER+product.pic;
            Log.i("99999",url);
            HttpLoader.getInstance(mActivity).display(viewHolder.mShopcarItemProdImageImg,url);//图片

            List<ShoppingCarBean.CartBean.ProductBean.ProductPropertyBean> productProperty = product.productProperty;
            //Log.i("222222222", productProperty.get(0).id+"");
            for (ShoppingCarBean.CartBean.ProductBean.ProductPropertyBean colorAndSize:productProperty){
                switch (colorAndSize.id) {
                    case 1:
                        viewHolder.mShopcarItemColor.setText("红");
                        break;
                    case 2:
                        viewHolder.mShopcarItemColor.setText("绿");
                        break;
                    case 3:
                        viewHolder.mShopcarItemProdPriceText.setText("M");
                        break;
                    case 4:
                        viewHolder.mShopcarItemProdPriceText.setText("XXL");
                        break;
                }

            }


            return view;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


    }

    static class ViewHolder {
        @Bind(R.id.shopcar_item_prodImage_img)
        ImageView mShopcarItemProdImageImg;//图片
        @Bind(R.id.shopcar_item_prodName_text)
        TextView mShopcarItemProdNameText;//商品名
        @Bind(R.id.shopcar_item_prodId_text)
        TextView mShopcarItemProdIdText;//数量
        @Bind(R.id.shopcar_item_prodPrice_text)
        TextView mShopcarItemProdPriceText;//尺码
        @Bind(R.id.shopcar_item_color)
        TextView mShopcarItemColor;//颜色
        @Bind(R.id.shopcar_item_prodPrice)
        TextView mShopcarItemProdPrice;//价格
        @Bind(R.id.shopcar_item_count)
        TextView mShopcarItemCount;//小计

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("ShoppingCarFragment","onDestroyView.......");
        //----------注销EventBus---------------------------------
        //EventBus.getDefault().unregister(this);

      /*  //---------------取消粘性事件----------------------------
        ColorAndSizeBean stickyEvent = EventBus.getDefault().getStickyEvent(ColorAndSizeBean.class);
        // Better check that an event was actually posted before
        if(stickyEvent != null) {
            // "Consume" the sticky event
            EventBus.getDefault().removeStickyEvent(stickyEvent);
            // Now do something with it
        }*/
    }

    @Override
    public void onDestroy() {
        Log.i("ShoppingCarFragment","被销毁.......");
        super.onDestroy();
        //ButterKnife.unbind(this);

    }


    //提供方法切换Fragment，点击RadioButton的时候需要清空回退栈
    public void switchFragment(Fragment fragment) {
        android.support.v4.app.FragmentManager mFragmentManager = getFragmentManager();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        //循环的的pop回退栈
        int backStackEntryCount = mFragmentManager.getBackStackEntryCount();
        while (backStackEntryCount > 0) {
            mFragmentManager.popBackStack();
            backStackEntryCount--;
        }
        transaction.replace(R.id.fl_container, fragment);
        transaction.commit();
    }

    //获取焦点------>重新刷新网络
    @Override
    public void onResume() {
        super.onResume();
        requestServer();
    }
}
