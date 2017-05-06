package com.itheima.redbaby.activity;

import android.app.Application;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.itheima.redbaby.R;
import com.itheima.redbaby.base.MyApplication;

/**
 * Created by Administrator on 2016/12/5.
 */
public class ClothesDetailAdapter extends BaseAdapter {
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
        View view = View.inflate(MyApplication.getContext(), R.layout.item_clothes_detail,null);
        return view;
    }
}
