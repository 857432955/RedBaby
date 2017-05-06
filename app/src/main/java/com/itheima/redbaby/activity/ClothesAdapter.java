package com.itheima.redbaby.activity;

;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.itheima.redbaby.base.MyApplication;
import com.itheima.redbaby.bean.Clothes_bean;
import com.itheima.redbaby.utils.ProductDetailContains;
import com.itheima.redbaby.constant.RBConstants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/4.
 */
public class ClothesAdapter extends PagerAdapter {
    //初始化页面集合
    private Clothes_bean chuandibean;
    private String id;
    private List<String> product_vp ;
    private ArrayList<String> chuandi_product_vp;
    private String[] imageResIds;
    //http://192.168.16.38:8080/RedBabyServer/images/product/detail/c1.jpg
    private String URL = RBConstants.URL_SERVER;
    Product_clothes product_clothes;
    public ClothesAdapter(List<String> product_vp,String id, Product_clothes product_clothes) {
        this.product_vp = product_vp;
        this.product_clothes = product_clothes;
        this.id =id;
    }

    @Override
    public int getCount() {
        return product_vp.size();
    }

    public boolean isViewFromObject(View view, Object object) {
        //固定
        return view == object;
    }

    public Object instantiateItem(ViewGroup container, final int position) {
        //类似listview,getView方法
        ImageView imageView = new ImageView(container.getContext());
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MyApplication.getContext(),"position图片被点击了"+ position,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MyApplication.getContext().getApplicationContext(),ClothesFragmentActivity2.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("pId",id);
                //chuandi_product_vp = (ArrayList<String>) product_vp;
                //intent.putStringArrayListExtra("chuandi_product_vp",chuandi_product_vp);
                //intent.putExtra("name",chuandibean.product.name);
                //intent.putExtra("markMoney",chuandibean.product.marketPrice);
                //intent.putExtra("vipMoney",chuandibean.product.limitPrice);
                //intent.putExtra("star",chuandibean.product.score);
//                MyApplication.getContext().startActivity(intent);
                product_clothes.startActivity(intent);

            }
        });

        String newURL = ProductDetailContains.URL_SERVER + product_vp.get(position);
        Picasso.with(MyApplication.getContext())
                .load(newURL)
                .into(imageView);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
