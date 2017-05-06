package com.itheima.redbaby.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.itheima.redbaby.R;
import com.itheima.redbaby.utils.UIUtils;

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
 * Created by lx on 2016/12/4.
 */
public class BrowsingRecordActivity extends Activity {

    private ListView mRecordListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browsing_record);
        mRecordListView = (ListView) findViewById(R.id.Browsing_record_list_view);
        RecordAdapter adapter = new RecordAdapter();
        mRecordListView.setAdapter(adapter);
    }

    private class RecordAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return 0;
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
            RecordViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(UIUtils.getContext(),R.layout.record_item,null);
                holder = new RecordViewHolder();
                holder.record_iv = (ImageView) convertView.findViewById(R.id.record_iv);
                holder.brand_tv = (TextView) convertView.findViewById(R.id.brand_tv);
                holder.now_money_tv = (TextView) convertView.findViewById(R.id.now_money_tv);
                holder.old_money_tv = (TextView) convertView.findViewById(R.id.old_money_tv);
                holder.talk_tv = (TextView) convertView.findViewById(R.id.talk_tv);
                convertView.setTag(holder);
            }else {
                convertView.getTag();
            }
            /*--------------------将view和数据进行绑定---------------------*/

            /*--------------------将view和数据进行绑定---------------------*/
            return convertView;
        }
    }

     class RecordViewHolder {
         ImageView record_iv;
         TextView now_money_tv;
         TextView brand_tv;
         TextView old_money_tv;
         TextView talk_tv;
    }
}
