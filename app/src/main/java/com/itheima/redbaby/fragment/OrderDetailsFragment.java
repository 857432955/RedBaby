package com.itheima.redbaby.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.itheima.redbaby.R;
import com.itheima.redbaby.bean.IndentEventBusbean;
import com.itheima.redbaby.bean.OrderDetailBean;
import com.itheima.redbaby.constant.RBConstants;
import com.itheima.redbaby.manager.FragmentInstanceManager;
import com.itheima.redbaby.protocol.Api;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.HttpParams;
import org.senydevpkg.net.resp.IResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

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
 * Created by lx on 2016/12/6.
 */
public class OrderDetailsFragment extends BaseFragment implements View.OnClickListener {

    //订单号
    @Bind(R.id.tv_indent_number)
    TextView mTvIndentNumber;
    //收货人手机号
    @Bind(R.id.tv_phone)
    TextView mTvPhone;
    //收货人地址
    @Bind(R.id.tv_address)
    TextView mTvAddress;
    //订单送达状态
    @Bind(R.id.tv_indent_state)
    TextView mTvIndentState;
    //送货方式
    @Bind(R.id.tv_indent_delivery)
    TextView mTvIndentDelivery;
    //支付方式
    @Bind(R.id.tv_indent_pay)
    TextView mTvIndentPay;
    //订单生成时间
    @Bind(R.id.tv_indent_time)
    TextView mTvIndentTime;
    //发货时间
    @Bind(R.id.tv_indent_delivery_time)
    TextView mTvIndentDeliveryTime;
    //是否开具发票
    @Bind(R.id.tv_indent_bill)
    TextView mTvIndentBill;
    //发票抬头
    @Bind(R.id.tv_indent_bill_head)
    TextView mTvIndentBillHead;
    //送货要求
    @Bind(R.id.tv_indent_delivery_requirement)
    TextView mTvIndentDeliveryRequirement;
    //商品名称
    @Bind(R.id.trade_name)
    TextView mTradeName;
    //颜色
    @Bind(R.id.tv_indent_color)
    TextView mTvIndentColor;
    //尺码
    @Bind(R.id.tv_indent_size)
    TextView mTvIndentSize;
    //数量
    @Bind(R.id.tv_indent_quantity)
    TextView mTvIndentQuantity;
    //价格
    @Bind(R.id.tv_indent_price)
    TextView mTvIndentPrice;
    //数量总计
    @Bind(R.id.indent_detail_totality)
    TextView mIndentDetailTotality;
    //原始金额
    @Bind(R.id.indent_detail_original_cost)
    TextView mIndentDetailOriginalCost;
    //运费
    @Bind(R.id.indent_detail_carriage)
    TextView mIndentDetailCarriage;
    //促销优惠金额
    @Bind(R.id.indent_detail_discount_amount)
    TextView mIndentDetailDiscountAmount;
    //应支付的金额
    @Bind(R.id.indent_detail_amount_paid)
    TextView mIndentDetailAmountPaid;
    //取消订单
    @Bind(R.id.btn_cancel_order)
    Button mBtnCancelOrder;

    private String status;
    private String address;
    private String addressArea;
    private String addressDetail;
    private String invoiceTitle;
    private String name;
    private int price;
    private String color;
    private String size;
    private int count;
    private int freight;
    private int totalprice;
    private HttpParams params;


    private Button mBut_more;
    private RelativeLayout mOrderdetails_rl;
    private String mOrderID;

    public void onEventMainThread(IndentEventBusbean event) {
        mOrderID = event.orderId;

    }

    @Override
    public View initViews() {
        EventBus.getDefault().registerSticky(this);

        params = new HttpParams();
        params.put("orderId", mOrderID);
        System.out.println("+++++++++" + mOrderID);
        params.addHeader("userid", RBConstants.USERID);

        View view = View.inflate(mActivity, R.layout.fragment_orderdetails, null);
        mBut_more = (Button) view.findViewById(R.id.but_more);
        mOrderdetails_rl = (RelativeLayout) view.findViewById(R.id.orderdetails_rl);
        mBtnCancelOrder = (Button) view.findViewById(R.id.btn_cancel_order);
        mBut_more.setOnClickListener(this);
        mOrderdetails_rl.setOnClickListener(this);
        mBtnCancelOrder.setOnClickListener(this);

        getOrderDetail(params);

        return view;

    }


    //    @Override
//    protected void initData() {
//
//        mTvIndentNumber.setText("10010213335171");
//        mTvPhone.setText("138001380022");
//        mTvIndentDelivery.setText("快递");
//        mTvIndentPay.setText("");
//        mTvIndentTime.setText("2016/9/6 17:23:41");
//        mTvIndentDeliveryTime.setText("2011/9/6 17:43:41");
//        mTvIndentBill.setText("是");
//        mTvIndentQuantity.setText("1");
//        mTvIndentDeliveryRequirement.setText("只工作日送货(双休日,日.....)");
//        mIndentDetailDiscountAmount.setText("¥30.00");
//        mIndentDetailAmountPaid.setText("¥108.00");
//        super.initData();
//    }


    private void getOrderDetail(HttpParams params) {
        Api.getOrderDetail(new HttpLoader.HttpListener() {
            @Override
            public void onGetResponseSuccess(int requestCode, IResponse response) {
                OrderDetailBean orderDetailBean = (OrderDetailBean) response;

                //订单状态
                if (orderDetailBean.orderInfo == null) {
                    mTvIndentState.setText("未处理");
                } else {
                    mTvIndentState.setText(orderDetailBean.orderInfo.status);
                    //   status = orderDetailBean.orderInfo.status;
                }

                //地址
                if (orderDetailBean.addressInfo == null) {
                    addressArea = "AAAA";
                } else {
                    addressArea = orderDetailBean.addressInfo.addressArea;
                }
                if (orderDetailBean.addressInfo == null) {
                    addressDetail = "BBBBB";
                } else {
                    addressDetail = orderDetailBean.addressInfo.addressDetail;
                }
                mTvAddress.setText(addressArea + addressDetail);

                //发票抬头
                if (orderDetailBean.invoiceInfo == null) {
                    mTvIndentBillHead.setText("XXX公司");
                } else {
                    mTvIndentBillHead.setText(orderDetailBean.invoiceInfo.invoiceTitle);
                }

                //商品名,颜色，尺寸，价格
                if (orderDetailBean.productList == null) {
                    mTradeName.setText("如意金箍棒");
                    mTvIndentColor.setText("金色");
                    mTvIndentSize.setText("超级大");
                    mTvIndentPrice.setText(10000 + "");
                } else {
                    mTradeName.setText(orderDetailBean.productList.get(0).product.name);
                    mTvIndentColor.setText(orderDetailBean.productList.get(0).product.productProperty.get(0).v);
                    mTvIndentSize.setText(orderDetailBean.productList.get(0).product.productProperty.get(1).v);
                    mTvIndentPrice.setText(orderDetailBean.productList.get(0).product.price + "");
                }

                //总件数，总价格，运费
                if (orderDetailBean.checkoutAddup == null) {
                    mIndentDetailTotality.setText(1 + "件");
                    mIndentDetailOriginalCost.setText(11111 + "");
                    mIndentDetailCarriage.setText(100 + "");
                } else {
                    mIndentDetailTotality.setText(orderDetailBean.checkoutAddup.totalCount + "件");
                    mIndentDetailOriginalCost.setText(orderDetailBean.checkoutAddup.freight + "");
                    mIndentDetailCarriage.setText(orderDetailBean.checkoutAddup.totalPrice + "");
                }

            }

            @Override
            public void onGetResponseError(int requestCode, VolleyError error) {

            }
        }, params);
    }

    private void postOrderCancel(HttpParams params){
        Api.postOrderCancel(new HttpLoader.HttpListener() {
            @Override
            public void onGetResponseSuccess(int requestCode, IResponse response) {
                getFragmentManager().popBackStack();
            }

            @Override
            public void onGetResponseError(int requestCode, VolleyError error) {

            }
        }, params);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but_more:
                getFragmentManager().popBackStack();
                break;
//            case R.id.orderdetails_rl:
//                swichToChildFragment(FragmentInstanceManager.getInstance().getFragment(LogisticsQueryFragment.class), true);
//                break;
            case R.id.btn_cancel_order:

            //    EventBus.getDefault().registerSticky(this);
                postOrderCancel(params);
        }
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
        //ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
