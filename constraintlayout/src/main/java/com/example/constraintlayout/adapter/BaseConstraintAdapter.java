package com.example.constraintlayout.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.example.constraintlayout.Constraint;
import com.example.constraintlayout.ConstraintLayout.LayoutParams;

/**
 * Created by LiuJin on 2018-04-03:9:59
 *
 * @author wuxio
 */
public abstract class BaseConstraintAdapter {

    /**
     * 为该布局位置生成一个view
     *
     * @param position 布局位置
     * @return 该布局位置的view
     */
    public abstract View generateViewTo(int position);


    public LayoutParams generateLayoutParamsTo(int position) {

        return new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }


    /**
     * 为该布局位置生成一个约束
     *
     * @param position   布局位置
     * @param constraint 初始化的约束,即约束的四体边是parent的外围,可以通过constraint获取父布局
     * @return 一个修改后的约束, 将使用该约束约束该布局位置的view
     */
    public abstract Constraint generateConstraintTo(int position, Constraint constraint);

    /**
     * 返回该布局一共有多少view
     *
     * @return 一共有多少个view
     */
    public abstract int getChildCount();


    public void beforeMeasure(int position, View view) {

    }


    public void afterMeasure(int position, View view) {

    }


    public void beforeLayout(int position, View view) {

    }


    public void afterLayout(int position, View view) {

    }
}
