package com.itheima.redbaby.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

/**
 * Created by ZhangRuxing on 2016-12-08.
 */

public class SearchExplandableListView extends ExpandableListView {
    public SearchExplandableListView(Context context) {
        this(context, null);
    }

    public SearchExplandableListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchExplandableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

           /* 隐藏默认箭头显示 */
        this.setGroupIndicator(null);

        /* 隐藏垂直滚动条 */
        this.setVerticalScrollBarEnabled(false);

        setCacheColorHint(Color.TRANSPARENT);
        setDividerHeight(0);
    }
}
