package com.itheima.redbaby.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author 王帅峰
 * @time 2016/12/6  11:23
 * @des ListView头布局中的ViewPager
 */
public class HomeHeaderViewPager extends ViewPager {
    public HomeHeaderViewPager(Context context) {
        super(context);
    }

    public HomeHeaderViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        int startX = 0;
        int startY = 0;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) ev.getRawX();
                startY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int currentX = (int) ev.getRawX();
                int currentY = (int) ev.getRawY();
                int dx = currentX - startX;
                int dy = currentY - startY;
                if (Math.abs(dx) <= Math.abs(dy)) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

/*    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }*/

   /* @Override
    public boolean onTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        int startX = 0;
        int startY = 0;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) ev.getRawX();
                startY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int currentX = (int) ev.getRawX();
                int currentY = (int) ev.getRawY();
                int dx = currentX - startX;
                int dy = currentY - startY;
                if (Math.abs(dx) <= Math.abs(dy)) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(ev);
    }*/
}
