package com.itheima.redbaby.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.itheima.redbaby.R;


/**
 * @author 王帅峰
 * @time 2016/12/6  9:00
 * @des  自定义一个ViewGroup来包裹图片,主要来解决屏幕适配的问题
 *   作用：
 *      在已知宽度的情况下,动态计算高度；
 *      在已知高度的情况下,动态计算宽度
 */
public class RatioLayout extends FrameLayout {

    public static final int RELATIVE_WIDTH = 0;//已知宽度,动态计算高度
    public static final int RELATIVE_HEIGHT = 1;//已知高度,动态计算宽度
    private float mPicRatio; // 宽高比例
    private int mRelative = RELATIVE_WIDTH; // 相对谁来计算值

    /**
     * 设置宽高比
     */
    public void setPicRatio(float picRatio) {
        mPicRatio = picRatio;
    }
    /**
     * 设置相对于宽度还是高度计算
     */
    public void setRelative(int relative) {
        mRelative = relative;
    }

    public RatioLayout(Context context) {
        this(context,null);
    }

    public RatioLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        //取出自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioLayout);
        mPicRatio = typedArray.getFloat(R.styleable.RatioLayout_picRatio,1);
        mRelative = typedArray.getInt(R.styleable.RatioLayout_relative,RELATIVE_WIDTH);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获取宽高的模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        //判断如果宽是准确值
        if (widthMode == MeasureSpec.EXACTLY && mRelative == RELATIVE_WIDTH){
            //获取宽的值
            int widthSize = MeasureSpec.getSize(widthMeasureSpec);
            //通过宽和宽高比来获取高的值
            int heightSize = (int) (widthSize/mPicRatio + .5f);
            //保存测量结果
            setMeasuredDimension(widthSize,heightSize);
            //测量孩子
            int childWidthSize = widthSize - getPaddingLeft() - getPaddingRight();
            int childHeightSize = heightSize - getPaddingTop() - getPaddingBottom();
            //设置子控件
            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize,MeasureSpec.EXACTLY);
            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeightSize,MeasureSpec.EXACTLY);
            measureChildren(childWidthMeasureSpec,childHeightMeasureSpec);
        }else if(heightMode == MeasureSpec.EXACTLY && mRelative == RELATIVE_HEIGHT) { //如果高度模式是确定的
            //获取高的值
            int heightSize = MeasureSpec.getSize(widthMeasureSpec);
            //通过高和宽高比来获取宽的值
            int widthSize = (int) (heightSize * mPicRatio + .5f);
            //保存测量结果
            setMeasuredDimension(widthSize, heightSize);
            //测量孩子
            int childWidthSize = widthSize - getPaddingLeft() - getPaddingRight();
            int childHeightSize = heightSize - getPaddingTop() - getPaddingBottom();
            //设置子控件
            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);
            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeightSize, MeasureSpec.EXACTLY);
            measureChildren(childWidthMeasureSpec, childHeightMeasureSpec);
        }else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
