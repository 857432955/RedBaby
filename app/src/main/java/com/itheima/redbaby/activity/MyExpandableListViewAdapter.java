package com.itheima.redbaby.activity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.itheima.redbaby.R;
import com.itheima.redbaby.base.MyApplication;

import java.util.List;

/**
 * Created by Administrator on 2016/12/6.
 */
public class MyExpandableListViewAdapter extends BaseExpandableListAdapter {
    private List<List<String>> body_news;
    private List<List<Integer>> body_image;
    private List<Integer> head_image;
    private List<String> head_news;
    private List<List<String>> body_names;

    public MyExpandableListViewAdapter(List<String> head_news, List<List<String>> body_news, List<List<Integer>> body_image, List<List<String>> body_names) {
        this.head_news = head_news;
        this.body_image = body_image;
        this.body_news = body_news;
        this.body_names = body_names;
    }

    @Override
    public int getGroupCount() {
        return head_news.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 3;
    }//2

    @Override
    public Object getGroup(int groupPosition) {
        return head_news.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return body_news.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        HeadHolder headHolder = null;
        if (convertView == null) {
            convertView = View.inflate(MyApplication.getContext(), R.layout.expendlist_group,null);
            headHolder = new HeadHolder();
            headHolder.head_tv = (TextView) convertView.findViewById(R.id.head_tv);
            headHolder.head_iv = (ImageView) convertView.findViewById(R.id.head_iv);
            convertView.setTag(headHolder);
        } else {
            headHolder = (HeadHolder) convertView.getTag();
        }
        //headHolder.head_tv.setText(head_news.get(groupPosition));
        if(isExpanded){
            headHolder.head_iv.setImageResource(R.drawable.arrowdown);
        }else{
            headHolder.head_iv.setImageResource(R.drawable.arrowright);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        BodyHolder bodyHolder = null;
        if (convertView == null) {
            convertView = View.inflate(MyApplication.getContext(),R.layout.expendlist_item,null);
            bodyHolder = new BodyHolder();
            bodyHolder.body_tv = (TextView) convertView.findViewById(R.id.body_tv);
            bodyHolder.body_iv = (ImageView) convertView.findViewById(R.id.body_iv);
            bodyHolder.body_tv2 = (TextView) convertView.findViewById(R.id.body_tv2);
            convertView.setTag(bodyHolder);
        } else {
            bodyHolder = (BodyHolder) convertView.getTag();
        }
        bodyHolder.body_tv.setText(body_names.get(groupPosition).get(childPosition));
        bodyHolder.body_tv2.setText(body_news.get(groupPosition).get(childPosition));
        bodyHolder.body_iv.setBackgroundResource(body_image.get(groupPosition).get(childPosition));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    class HeadHolder {
        public TextView head_tv;
        public ImageView head_iv;
    }

    class BodyHolder {
        public ImageView body_iv;
        public TextView body_tv;
        public TextView body_tv2;
    }
}
