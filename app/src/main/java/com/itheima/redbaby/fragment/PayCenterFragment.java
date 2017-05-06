package com.itheima.redbaby.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.itheima.redbaby.R;
import com.itheima.redbaby.bean.CheckoutResponse;
import com.itheima.redbaby.bean.ShoppingCarBean;
import com.itheima.redbaby.constant.RBConstants;
import com.itheima.redbaby.manager.FragmentInstanceManager;
import com.itheima.redbaby.protocol.Api;
import com.itheima.redbaby.utils.UIUtils;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.HttpParams;
import org.senydevpkg.net.resp.IResponse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 作者：程相稳 on 2016/12/4 15:26
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
public class PayCenterFragment extends BaseFragment/* implements AddressListFragment.OnDataTransmissionListener */ {
    //返回购物车按钮
    @Bind(R.id.head_back_text)
    TextView headBackText;
    //收货人信息
    @Bind(R.id.rl_receiver_info)
    RelativeLayout rlReceiverInfo;
    //支付方式
    @Bind(R.id.rl_pay_mode)
    RelativeLayout rlPayMode;
    //送货时间
    @Bind(R.id.rl_delivergoods_time)
    RelativeLayout rlDelivergoodsTime;
    //优惠券选择
    @Bind(R.id.rl_coupon)
    RelativeLayout rlCoupon;
    //发票信息
    @Bind(R.id.rl_bill)
    RelativeLayout rlBill;
    //物品列表
    @Bind(R.id.payment_product_list)
    ListView paymentProductList;
    TextView shopcarTotalMoneyText;
    //提交订单按钮
    @Bind(R.id.ordr_submit_bottom_text)
    Button ordrSubmitBottomText;
    @Bind(R.id.tv_pay_mode1)
    TextView tvPayMode1;
    @Bind(R.id.tv_delivergoods_time1)
    TextView tvDelivergoodsTime1;
    @Bind(R.id.tv_coupon1)
    TextView tvCoupon1;
    @Bind(R.id.tl_bill1)
    TextView tlBill1;
    @Bind(R.id.tv_paycenter_total_buycount)
    TextView tvPaycenterTotalBuycount;
    @Bind(R.id.tv_paycenter_first_money)
    TextView tvPaycenterFirstMoney;
    @Bind(R.id.tv_paycenter_bus_money)
    TextView tvPaycenterBusMoney;
    @Bind(R.id.tv_paycenter_delete_money)
    TextView tvPaycenterDeleteMoney;
    @Bind(R.id.tv_paycenter_total_money)
    TextView tvPaycenterTotalMoney;
    @Bind(R.id.tv_addresslist_name)
    TextView tvAddresslistName;
    @Bind(R.id.tv_addresslist_number)
    TextView tvAddresslistNumber;
    @Bind(R.id.tv_addresslist_detail)
    TextView tvAddresslistDetail;
    private Fragment mPayWayFragment;
    private CheckoutResponse mCheckoutResponse;
    private List<CheckoutResponse.PaymentListBean> mPaymentList;
    private List<CheckoutResponse.ProductListBean> mProductList;
    private int mTotalNumber = 0;
    private int mTotalMoney = 0;
    private String mAdressid="1";
    private String mPaymentTypeId="1";
    private String deliveryTypeId="1";
    private String invoiceTypeId="1";
    private String mAdressdetail="江苏传智播客济南分校区";
    private String invoiceContent = "1";
    private String mSku="";
    private int couponMoney=50;

    @Override
    public View initViews() {
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_payment_center, null);
        /*AddressListFragment addressListFragment = new AddressListFragment();
        addressListFragment.setOnDataTransmissionListener(this);*/
        /*try {
            File cacheDir = mActivity.getCacheDir();
            File file = new File(cacheDir,"sku.text");
            BufferedReader br = new BufferedReader(new FileReader(file));
            mSku = br.readLine();
            br.close();
            Log.i("sku", mSku);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        //------------------------------读取集合
        if (mSku==""){
            for(String s:RBConstants.listCar){
                mSku = mSku+s;
            }
        }

        Api.getCheckoutData(new HttpLoader.HttpListener() {
            @Override
            public void onGetResponseSuccess(int requestCode, IResponse response) {
                mCheckoutResponse = (CheckoutResponse) response;
                System.out.println(mCheckoutResponse.toString());
                mProductList = mCheckoutResponse.productList;
                initPayInfo();
                paymentProductList.setFocusableInTouchMode(true);
                paymentProductList.setFocusable(false);
                paymentProductList.setAdapter(new MyListAdapter());
                setListViewHeightBasedOnChildren(paymentProductList);
                //initSp();
            }

            @Override
            public void onGetResponseError(int requestCode, VolleyError error) {
                System.out.println("1111111111111111111111111111111111");
            }
        }, /*"1:20:1,3|2:30:1,5""7:3:2,3|10:2:2,3|5:5:4,2|7:3:1,4"*/mSku, RBConstants.USERID);
        return view;
    }

    @Override
    protected void initData() {
        initSp();
        //super.initData();
    }

    private void initPayInfo() {

        mTotalNumber = 0;
        mTotalMoney = 0;
        for (int i = 0; i < mProductList.size(); i++) {
            mTotalNumber = mTotalNumber + mCheckoutResponse.productList.get(i).prodNum;
            mTotalMoney = mTotalMoney + mCheckoutResponse.productList.get(i).prodNum * mCheckoutResponse.productList.get(i).product.price;
        }
        tvPaycenterTotalBuycount.setText(mTotalNumber + "");
        tvPaycenterFirstMoney.setText(mTotalMoney + "");
        tvPaycenterBusMoney.setText(10.0 + "");
        tvPaycenterDeleteMoney.setText(couponMoney + "");
        tvPaycenterTotalMoney.setText(mTotalMoney - 10 - couponMoney + ".0");
        mTotalNumber = 0;
        mTotalMoney = 0;
    }

    private void initSp() {
        SharedPreferences sharedPreferences = UIUtils.getContext().getSharedPreferences("oderdetail", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        String paytype = sharedPreferences.getString("paytype", "");
        String paytime = sharedPreferences.getString("paytime", "");
        String whopay = sharedPreferences.getString("whopay", "");
        String billcontent = sharedPreferences.getString("billcontent", "");
        String coupontype = sharedPreferences.getString("coupontype", "");
        String adressname = sharedPreferences.getString("adressname", "");
        String adressnumber = sharedPreferences.getString("adressnumber", "");
        mAdressdetail = sharedPreferences.getString("adressdetail", "");
        mAdressid = sharedPreferences.getString("adressid", "");
        /*if (adressname.equals("null") || adressname.equals("")) {
            editor.putString("adressname", "孙德超");
            editor.commit();//提交修改
        }
        if (adressnumber.equals("null") || adressnumber.equals("")) {
            editor.putString("adressnumber", "123");
            editor.commit();//提交修改
        }
        if (adressdetail.equals("null") || adressdetail.equals("")) {
            editor.putString("adressdetail", "火星");
            editor.commit();//提交修改
        }*/
        if (paytype.equals("null") || paytype.equals("")) {
            editor.putString("paytype", "到付-现金");
            editor.commit();//提交修改
        }else if(paytype.equals("到付-现金")){
            mPaymentTypeId = 1+"";
        }else if (paytype.equals("到付-pos")){
            mPaymentTypeId = 2+"";
        }else if (paytype.equals("支付宝")){
            mPaymentTypeId = 3+"";
        }
        if (paytime.equals("null") || paytime.equals("")) {
            editor.putString("paytime", "双休日 、工作日与节假日均可送货");
            editor.commit();//提交修改
        }else if(paytime.equals("双休日 、工作日与节假日均可送货")){
            deliveryTypeId = 1+"";
        }else if (paytime.equals("只双休日与节假日可以送货")){
            deliveryTypeId = 2+"";
        }else if (paytime.equals("只工作日送货")){
            deliveryTypeId = 3+"";
        }
        if (whopay.equals("null") || whopay.equals("")) {
            editor.putString("whopay", "个人");
            editor.commit();//提交修改
        }else if(paytime.equals("个人")){
            invoiceTypeId = 1+"";
        }else if (paytime.equals("单位")){
            invoiceTypeId = 2+"";
        }

        if (billcontent.equals("null") || billcontent.equals("")) {
            editor.putString("billcontent", "日用品");
            editor.commit();//提交修改
        }else if(paytime.equals("日用品")){
            invoiceContent = 1+"";
        }else if (paytime.equals("服饰")){
            invoiceContent = 2+"";
        }else if (paytime.equals("其他")){
            invoiceContent = 3+"";
        }

        if (coupontype.equals("null") || coupontype.equals("")) {
            editor.putString("coupontype", "9月惊喜50元礼券");
            editor.commit();//提交修改
        }else if(coupontype.equals("9月惊喜50元礼券")){
            couponMoney = 50;
        }else if (coupontype.equals("国庆80元礼券")){
            couponMoney = 80;
        }else if (coupontype.equals("圣诞节大放送80元礼券")){
            couponMoney = 80;
        }
        tvPayMode1.setText(paytype);
        tvDelivergoodsTime1.setText(paytime);
        tlBill1.setText(whopay + "(" + billcontent + ")");
        tvCoupon1.setText(coupontype);
        tvAddresslistName.setText(adressname);
        tvAddresslistNumber.setText(adressnumber);
        tvAddresslistDetail.setText(mAdressdetail);
        editor.putString("totalmoney", mTotalMoney - 10 - couponMoney + ".0");

        editor.commit();//提交修改
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }
    /*@Subscribe
    public void onEvent(PayTypeEvent payTypeEvent) {
       // onCreateView(inflater,);
        //tvPayMode1.setText("2222");
        System.out.println("payTypeEvent"+payTypeEvent.payType);
    }*/


    public void onEvent(ShoppingCarBean mShoppingCarBean) {
        Log.i("PayCenterFragment","商品总数为"+mShoppingCarBean.totalCount);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        //mPayWayFragment = new PayWayFragment();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //EventBus.getDefault().unregister(this);
        mTotalNumber = 0;
        mTotalMoney = 0;
        //ButterKnife.unbind(this);
        //ButterKnife.unbind(this);
    }

    @OnClick({R.id.rl_pay_mode, R.id.rl_delivergoods_time, R.id.rl_coupon, R.id.rl_bill,
            R.id.ordr_submit_bottom_text, R.id.head_back_text, R.id.rl_receiver_info})
    public void onClick(View view) {

        switch (view.getId()) {
            //跳转到收货人界面
            case R.id.rl_receiver_info:
                swichToChildFragment(FragmentInstanceManager.getInstance().getFragment(AddressListFragment.class), true);
                break;
            //跳转到支付方式选择界面
            case R.id.rl_pay_mode:
                //getFragmentManager().beginTransaction().hide(this).show(mPayWayFragment).commit();
                swichToChildFragment(FragmentInstanceManager.getInstance().getFragment(PayWayFragment.class), true);
                break;
            //跳转到支付时间选择界面
            case R.id.rl_delivergoods_time:
                swichToChildFragment(FragmentInstanceManager.getInstance().getFragment(ExpressTimeWayFragment.class), true);
                break;
            //跳转到优惠券选择界面
            case R.id.rl_coupon:
                swichToChildFragment(FragmentInstanceManager.getInstance().getFragment(CouponFragment.class), true);
                break;
            //跳转到发票选择界面
            case R.id.rl_bill:
                swichToChildFragment(FragmentInstanceManager.getInstance().getFragment(BillFragment.class), true);
                break;
            case R.id.ordr_submit_bottom_text:
               //view.animate().rotation(360).setDuration(1000).start();
                HttpParams params = new HttpParams();
                params.put("sku", "1:3:1,2|2:2:2,3");
                params.put("addressId",mAdressid);
                params.put("paymentType", mPaymentTypeId);

                params.put("deliveryType", deliveryTypeId);

                params.put("invoiceType", invoiceTypeId);
                params.put("invoiceTitle", mAdressdetail);

                params.put("invoiceContent", invoiceContent);
                params.addHeader("userid", RBConstants.USERID);
                RBConstants.listCar.clear();
                PostAddAddress(params);
                swichToChildFragment(FragmentInstanceManager.getInstance().getFragment(CommitSuccessFragment.class), true);
                break;
            //回退到购物车
            case R.id.head_back_text:
                getFragmentManager().popBackStack();
                /*AddressListFragment addressListFragment = new AddressListFragment();
                addressListFragment.setOnDataTransmissionListener(new AddressListFragment.OnDataTransmissionListener() {
                    @Override
                    public void dataTransmission(AddressListInfoBean data) {
                        AddressListInfoBean addressListInfoBean = data;
                        System.out.println(addressListInfoBean.toString()+"555555555555555555");
                    }
                });*/
                break;
        }
    }

    private void PostAddAddress(HttpParams params) {
        Api.PostAddOrder(new HttpLoader.HttpListener() {
            @Override
            public void onGetResponseSuccess(int requestCode, IResponse response) {
                System.out.println("添加订单成功11111111111111111111111111111111111111");
                /*getFragmentManager().popBackStack();
                swichToChildFragment(FragmentInstanceManager.getInstance().getFragment(CommitSuccessFragment.class), true);*/
            }

            @Override
            public void onGetResponseError(int requestCode, VolleyError error) {
            }
        }, params);
    }

   /* @Override
    public void dataTransmission(AddressListInfoBean data) {
        AddressListInfoBean addressListInfoBean = data;
        System.out.println(addressListInfoBean.toString()+"555555555555555");
    }*/


    //-----------------listView的适配器-------------------------------------------
    class MyListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mProductList.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(mActivity, R.layout.item_paycenter, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            //holder.ivPaycenterItemProdImage.setImageDrawable(Drawable.createFromPath(mCheckoutResponse.productList.get(position).product.pic));
            HttpLoader.getInstance(UIUtils.getContext()).display(holder.ivPaycenterItemProdImage, RBConstants.URL_SERVER + mCheckoutResponse.productList.get(position).product.pic);
            holder.tvPaycenterItemName.setText(mCheckoutResponse.productList.get(position).product.name);
            //System.out.println(mCheckoutResponse.productList.get(position).prodNum);
            holder.tvPaycenterItemNumber.setText(mCheckoutResponse.productList.get(position).prodNum + "");
            holder.tvPaycenterItemSize.setText(mCheckoutResponse.productList.get(position).product.productProperty.get(1).v);
            holder.tvPaycenterItemColor.setText(mCheckoutResponse.productList.get(position).product.productProperty.get(0).v);
            holder.tvPaycenterItemPrice.setText(mCheckoutResponse.productList.get(position).product.price + ".0");
            holder.tvPaycenterItemCount.setText(mCheckoutResponse.productList.get(position).product.price + ".0");
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

    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    static class ViewHolder {
        @Bind(R.id.iv_paycenter_item_prodImage)
        ImageView ivPaycenterItemProdImage;
        @Bind(R.id.tv_paycenter_item_name)
        TextView tvPaycenterItemName;
        @Bind(R.id.tv_paycenter_item_number)
        TextView tvPaycenterItemNumber;
        @Bind(R.id.tv_paycenter_item_size)
        TextView tvPaycenterItemSize;
        @Bind(R.id.tv_paycenter_item_color)
        TextView tvPaycenterItemColor;
        @Bind(R.id.tv_paycenter_item_price)
        TextView tvPaycenterItemPrice;
        @Bind(R.id.tv_paycenter_item_count)
        TextView tvPaycenterItemCount;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


    /*//提供方法Fragment切换方法
    public void swichToChildFragment(Fragment fragment) {
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.animator_enter,R.anim.animator_exit,R.anim.animator_enter,R.anim.animator_exit)
                .addToBackStack(fragment.getTag())
                .replace(R.id.fl_container, fragment)
                .commit();
    }*/
}