package com.example.constraintlayout.simple;

import android.view.View;

import com.example.constraintlayout.Constraint;
import com.example.constraintlayout.ConstraintLayout;

/**
 * Created by LiuJin on 2018-04-03:16:43
 *
 * @author wuxio
 *
 * 记录对view的操作,如何约束,如何生成
 */
public interface ConstraintOperator < T extends View > {


    /**
     * 该布局位置的view
     *
     * @param position 布局位置
     * @return view
     */
    T onGenerateView(int position);

    /**
     * 该布局位置的view
     *
     * @param position 布局位置
     * @return view
     */
    ConstraintLayout.LayoutParams onGenerateLayoutParams(int position, T t);

    /**
     * 生成一个约束,用于对应位置的view
     *
     * @param position   该布局位置的view
     * @param constraint 一个空白约束,用来记录对view的约束
     * @return 对该布局位置的约束
     */
    Constraint onGenerateConstraint(int position, Constraint constraint, T child);

    /**
     * view measure之前回调
     *
     * @param position 布局位置
     * @param v        该位置的view
     */
    default void onBeforeMeasure(int position, T v) {

    }

    /**
     * view measure之后回调
     *
     * @param position 布局位置
     * @param v        该位置的view
     */
    default void onAfterMeasure(int position, T v) {

    }

    /**
     * view layout之前回调
     *
     * @param position 布局位置
     * @param v        该位置的view
     */
    default void onBeforeLayout(int position, T v) {

    }

    /**
     * view layout之后回调
     *
     * @param position 布局位置
     * @param v        该位置的view
     */
    default void onAfterLayout(int position, T v) {

    }
}
