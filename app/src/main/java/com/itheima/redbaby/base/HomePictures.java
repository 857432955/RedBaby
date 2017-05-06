package com.itheima.redbaby.base;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.itheima.redbaby.R;
import com.itheima.redbaby.bean.HomeBean;
import com.itheima.redbaby.constant.RBConstants;
import com.itheima.redbaby.utils.LogUtils;
import com.itheima.redbaby.utils.UIUtils;
import com.itheima.redbaby.view.HomeHeaderViewPager;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author 王帅峰
 * @time 2016/12/6 14:22
 * @des 轮播图
 */
public class HomePictures {
    @Bind(R.id.vp_item_home_pictures)
    HomeHeaderViewPager mVpItemHomePictures;
    @Bind(R.id.ll_item_home_pictures)
    LinearLayout mLlItemHomePictures;

    private List<HomeBean.HomeTopicBean> mHomeTopicList;
    private AutoScrollTask mAutoScrollTask;

    public HomePictures() {
    }

    /**
     * 初始化视图
     *
     * @return View
     */
    public View initView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_home_picture, null);
        ButterKnife.bind(this, view);
        return view;
    }

    /**
     * 设置数据
     * @param
     */
    public void setData(List<HomeBean.HomeTopicBean> homeTopic) {
        mHomeTopicList = homeTopic;
        //设置指示器
        for (int i = 0; i < mHomeTopicList.size(); i++) {
            ImageView imageView = new ImageView(UIUtils.getContext());
            imageView.setImageResource(R.drawable.indicator_normal);
            if (i == 0) {
                imageView.setImageResource(R.drawable.indicator_selected);
            }
            int length = UIUtils.dip2Px(8);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(length, length);
            params.leftMargin = length;
            params.bottomMargin = length;
            mLlItemHomePictures.addView(imageView, params);
        }
        //ViewPager设置适配器
        mVpItemHomePictures.setAdapter(new HomePicturesAdapter());
        mVpItemHomePictures.setOnPageChangeListener(new PicturesPageChangeListener());
        //设置当前页面,为了让指示器和轮播图一致
        int currentItem = 10000 / 2;
        int realCurrentItem = currentItem + 1000 % mHomeTopicList.size();
        mVpItemHomePictures.setCurrentItem(realCurrentItem);
        //开始轮播
        if (mAutoScrollTask == null) {
            mAutoScrollTask = new AutoScrollTask();
            mAutoScrollTask.start();
        }
        mVpItemHomePictures.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mAutoScrollTask.stop();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mAutoScrollTask.stop();
                        break;
                    case MotionEvent.ACTION_UP:
                        mAutoScrollTask.start();
                        break;
                }
                return false;
            }
        });
    }

    /**
     * ViewPager的适配器
     */
    private class HomePicturesAdapter extends PagerAdapter {
        //图片总数,为了无限轮播,总数设置一个比较大的数
        @Override
        public int getCount() {
            if (mHomeTopicList != null) {
                return 10000;
            }
            return 0;
        }

        /**
         * 当前位置要显示的VIEW
         *
         * @param container
         * @param position  当前位置
         * @return
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //为了不让索引越界
            position = position % mHomeTopicList.size();
            ImageView imageView = new ImageView(UIUtils.getContext());
            //图片填充方式
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            String pictureUrl = mHomeTopicList.get(position).pic;
            //利用Picasso来设置图片
            Picasso.with(UIUtils.getContext()).load(RBConstants.URL_SERVER + pictureUrl).into(imageView);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }


    }

    /**
     * 设置轮播图的滑动监听器
     */
    private class PicturesPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            position = position % mHomeTopicList.size();
            for (int i = 0; i < mHomeTopicList.size(); i++) {
                //获取当前的小圆点对象
                ImageView imageView = (ImageView) mLlItemHomePictures.getChildAt(i);
                //还原默认的图片
                imageView.setImageResource(R.drawable.indicator_normal);
                if (position == i) {
                    imageView.setImageResource(R.drawable.indicator_selected);
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    /**
     * 无限轮播的runnable
     */
    class AutoScrollTask implements Runnable {
        /**
         * 开始轮播
         */
        public void start() {
            stop();
            MyApplication.getmMainThreadHandler().postDelayed(this, 3000);
        }

        /**
         * 停止轮播
         */
        public void stop() {
            MyApplication.getmMainThreadHandler().removeCallbacks(this);
        }

        @Override
        public void run() {
            int currentItem = mVpItemHomePictures.getCurrentItem();
            currentItem++;
            mVpItemHomePictures.setCurrentItem(currentItem);
            start();
        }
    }
}

