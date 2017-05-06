package com.itheima.redbaby.activity;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.itheima.redbaby.base.MyApplication;
import com.itheima.redbaby.utils.ProductDetailContains;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2016/12/10.
 */

public class bigPicture extends PagerAdapter {

    private List<String> list;

    public bigPicture(List<String> imageResIds) {
        this.list = imageResIds;
    }

    public int getCount() {//类似于listview适配器
        return list.size();
    }
    public boolean isViewFromObject(View view, Object object) {
        //固定
        return view == object;
    }
    //创建界面
    public Object instantiateItem(ViewGroup container, int position) {
        //类似listview,getView方法
        ImageView imageView = new ImageView(container.getContext());
        String newURL = ProductDetailContains.URL_SERVER + list.get(position);
        Picasso.with(MyApplication.getContext())
                .load(newURL)
                .into(imageView);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        container.addView(imageView);
        return imageView;
    }
    //销毁界面
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}

