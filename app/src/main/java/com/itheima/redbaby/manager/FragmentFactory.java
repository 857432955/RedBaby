package com.itheima.redbaby.manager;

import com.itheima.redbaby.fragment.BaseFragment;
import com.itheima.redbaby.fragment.ClassFragment;
import com.itheima.redbaby.fragment.HomeFragment;
import com.itheima.redbaby.fragment.MoreFragment;
import com.itheima.redbaby.fragment.PayWayFragment;
import com.itheima.redbaby.fragment.SearchFragment;
import com.itheima.redbaby.fragment.ShoppingCarFragment;
import com.itheima.redbaby.utils.LogUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 王帅峰
 * @time 2016/12/4  8:49
 * @des 用于创建fragment的工厂,可以获取到fragment
 */
public class FragmentFactory {
    public static final int FRAGMENT_HOME = 0;
    public static final int FRAGMENT_SEARCH = 1;
    public static final int FRAGMENT_CLASS = 2;
    public static final int FRAGMENT_SHOPPING = 3;
    public static final int FRAGMENT_MORE = 4;
    /**
     * 用于缓存fragment的实例
     */
    public static Map<Integer, BaseFragment> mCacheFragemnts = new HashMap<>();

    /**
     * 创建fragment对象
     *
     * @param position
     * @return
     */
    public static BaseFragment createFragment(int position) {
        BaseFragment fragment = null;

        if (mCacheFragemnts.containsKey(position)) {
            fragment = mCacheFragemnts.get(position);
            return fragment;
        }
        switch (position) {
            case FRAGMENT_HOME:
                fragment = new HomeFragment();
                break;
            case FRAGMENT_SEARCH:
                fragment = new SearchFragment();
                break;
            case FRAGMENT_CLASS:
                fragment = new ClassFragment();
                break;
            case FRAGMENT_SHOPPING:
                fragment = new ShoppingCarFragment();
                break;
            case FRAGMENT_MORE:
                fragment = new MoreFragment();
                break;

        }
        mCacheFragemnts.put(position, fragment);
        return fragment;
    }
}
