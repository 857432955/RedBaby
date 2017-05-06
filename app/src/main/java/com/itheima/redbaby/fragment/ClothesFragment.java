package com.itheima.redbaby.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.redbaby.R;
import com.itheima.redbaby.activity.Product_clothes;
import com.itheima.redbaby.base.MyApplication;
import com.itheima.redbaby.bean.Clothes_bean;
import com.itheima.redbaby.constant.RBConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * 刘亮 商品信息的Fragment
 */
public class ClothesFragment extends Fragment {

    private List<String> data_list;
    private List<String> data_list2;

    private String product_name;
    private String product_market_money;
    private String product_vip_money;
    private float star;
    public Clothes_bean clothesBean;
    public List<String> product_vp;
    public String colorSelect;
    public String sizeSelect;
    public String product_id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initRequestData();
        ListView lv = new ListView(getContext());
        lv.setAdapter(new ClotehesFragmentAdapter());

        return lv;
    }


    private void initRequestData() {
        Product_clothes p = (Product_clothes) getActivity();
        clothesBean = p.clothesBean;
        product_name = clothesBean.product.name;
        System.out.println("product_namexinxinxin=" + product_name);
        product_market_money = clothesBean.product.marketPrice;
        System.out.println("product_market_moneyaaa=" + product_market_money);
        product_vip_money = clothesBean.product.limitPrice;
        System.out.println("product_vip_moneyaaa=" + product_vip_money);
        star = clothesBean.product.score;
        System.out.println("staraa=" + star);
        product_id = p.getId;
    }

    private class ClotehesFragmentAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return 1;
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
            final ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.item_clothes_detail, null);
                holder = new ViewHolder();
                holder.mTextView = (TextView) convertView.findViewById(R.id.tv_1);
                holder.spinner1 = (Spinner) convertView.findViewById(R.id.spinner1);
                holder.spinner2 = (Spinner) convertView.findViewById(R.id.spinner2);
                holder.rb_star = (RatingBar) convertView.findViewById(R.id.rb_star);
                holder.tv_markmoney = (TextView) convertView.findViewById(R.id.tv_markmoney);
                holder.tv_vipmoney = (TextView) convertView.findViewById(R.id.tv_vipmoney);
                holder.et_shuliang = (EditText) convertView.findViewById(R.id.et);
                holder.buy = (Button) convertView.findViewById(R.id.buy);
                holder.collect = (Button) convertView.findViewById(R.id.collect);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            //数据1
            data_list = new ArrayList<String>();
            data_list.add("红色");
            data_list.add("绿色");
            //适配器
            ArrayAdapter arr_adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, data_list);
            //设置样式
            arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //加载适配器
            holder.spinner1.setAdapter(arr_adapter);
            holder.spinner1.setBackgroundResource(R.drawable.spinner_color_normal);

            //数据2
            data_list2 = new ArrayList<String>();
            data_list2.add("M");
            data_list2.add("XXL");
            data_list2.add("XXXL");
            //适配器
            ArrayAdapter arr_adapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, data_list2);
            //设置样式
            arr_adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //加载适配器
            holder.spinner2.setAdapter(arr_adapter2);
            holder.spinner2.setBackgroundResource(R.drawable.spinner_color_normal);

            holder.mTextView.setText(product_name);
            holder.rb_star.setRating(star);
            holder.tv_vipmoney.setText(product_vip_money);
            holder.tv_markmoney.setText(product_market_money);

            holder.buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MyApplication.getContext(), "加入购物车成功", Toast.LENGTH_SHORT).show();
                    String shuliang = holder.et_shuliang.getText().toString().trim();//购买数量
                    colorSelect = holder.spinner1.getSelectedItem().toString();
                    sizeSelect = holder.spinner2.getSelectedItem().toString();
                    System.out.println("商品数量 = " + shuliang);
                    System.out.println("商品颜色 = " + colorSelect);
                    System.out.println("商品尺寸 = " + sizeSelect);
                    System.out.println("商品ID = " + product_id);

                    String colorNum = "";
                    String sizeNum = "";
                    if ("红色".equals(colorSelect)) {
                        colorNum = "1";
                    } else if ("绿色".equals(colorSelect)) {
                        colorNum = "2";
                    }
                    if ("M".equals(sizeSelect)) {
                        sizeNum = "3";
                    } else if ("XXL".equals(sizeSelect)) {
                        sizeNum = "4";
                    }
                    if ("".equals(shuliang)) {
                        shuliang = "1";
                    }
                    String sku = product_id + ":" + shuliang + ":" + colorNum + "," + sizeNum + "|";
//                    ColorAndSizeBean mColorAndSizeBean = new ColorAndSizeBean();
//                    mColorAndSizeBean.id=product_id+"";
//                    mColorAndSizeBean.count=shuliang;
//                    mColorAndSizeBean.color=colorNum;
//                    mColorAndSizeBean.size=sizeNum;
//                    System.out.println("colorNum:--"+colorNum+"sizeNum:---"+sizeNum+"--shuliang"+shuliang);
//                    EventBus.getDefault().postSticky(mColorAndSizeBean);

                    //--------根据RBConstants.listPosition集合是否为空来判断是从购物车或是显示抢购进入商品详情
                    if (RBConstants.listPosition.isEmpty()) {
                        RBConstants.listCar.add(0, sku); //从显示抢购进入添加购物车
                    } else {
                        int position = RBConstants.listPosition.get(0);//从购物车进入修改商品的信息
                        RBConstants.listCar.set(position, sku);
                        RBConstants.listPosition.clear();
                    }
                }
            });
            holder.collect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MyApplication.getContext(), "收藏成功", Toast.LENGTH_SHORT).show();
                }
            });
            return convertView;
        }
    }

    class ViewHolder {
        public TextView mTextView;
        public Spinner spinner1;
        public Spinner spinner2;
        public RatingBar rb_star;
        public TextView tv_vipmoney;
        public TextView tv_markmoney;
        public EditText et_shuliang;
        public Button buy;
        public Button collect;
    }
}