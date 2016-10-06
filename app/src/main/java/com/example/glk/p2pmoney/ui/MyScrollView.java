package com.example.glk.p2pmoney.ui;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;


/**
 * Created by zgqdg on 2016/9/24.
 */

public class MyScrollView extends ScrollView {

    //要操纵的子布局
    private View innerView;
    private float y;
    private Rect normal = new Rect();
    //判断回滚事件是否在发生，如果在发生过程中，则不允许向下拖动
    private boolean animationFinish = true;

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        int childCount = getChildCount();
        if(childCount > 0){
            innerView = getChildAt(0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(innerView == null){
            return super.onTouchEvent(ev);
        }else{
            commonTouchEvent(ev);
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 自定义touch事件处理
     * @param ev
     */
    private void commonTouchEvent(MotionEvent ev) {
        if(animationFinish) {
            int action = ev.getAction();
            switch (action){
                case MotionEvent.ACTION_DOWN:
                    y = ev.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    float preY = y == 0 ? ev.getY() : y;
                    float nowY = ev.getY();
                    int detailY = (int)(preY - nowY);
                    y = nowY;
                    //操纵view进行拖动detailY的一半，也就是只有当空间ScrollView包含的内容可以完全显示
                    //或者scrollView不能完全显示，但是已经拖动到了顶部
                    if(isNeedMove()){
                        //布局该变位置之前，记录一下正常状态的位置，使用Rect类
                        if(normal.isEmpty()){
                            normal.set(innerView.getLeft(),innerView.getTop(),
                                    innerView.getRight(),innerView.getBottom());
                        }
                        //进行移动
                        innerView.layout(innerView.getLeft(),innerView.getTop() - detailY/2,
                                innerView.getRight(),innerView.getBottom() - detailY/2);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    y = 0;
                    //放手之后，布局需要回滚到原来的位置,这边伴随着一个动画，慢慢移动回去
                    if(isNeedAnimation()){
                        animation();
                    }
                    break;
            }
        }
    }

    private void animation() {
        //这边有第四个参数注意，如果反过来写，会导致松手的时候,先回往下一倍，才会往上回滚
        TranslateAnimation ta = new TranslateAnimation(0,0,0,normal.top - innerView.getTop());
        ta.setDuration(200);
        ta.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                animationFinish = false;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //执行完毕后清除View动画
                innerView.clearAnimation();
                innerView.layout(normal.left,normal.top,normal.right,normal.bottom);
                //清空normal，避免下次误移动
                normal.setEmpty();
                animationFinish = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        innerView.startAnimation(ta);
    }

    /**
     * 判断一下是否需要回滚
     * @return
     */
    private boolean isNeedAnimation() {
        return !normal.isEmpty();
    }

    /**
     * 判断是否需要移动
     * 两种情况需要移动
     * 一、ScrollView视图完全能够显示里面的内容
     * 二、虽然不能显示，但是已经拖动到了顶部
     * @return
     */
    private boolean isNeedMove() {
        int offset = innerView.getMeasuredHeight() - getHeight();
        int scrollY = getScrollY();
        //Log.e("zoubo","getMeasuredHeight" + innerView.getMeasuredHeight() + "----getHeight():" + getHeight());
        //Log.e("zoubo","offset:" + offset + "----scrollY:" + scrollY);
        //第一种，内容已经被屏幕包含了业就是scrollY = 0
        //第二种，内容超过屏幕， 偏移值等于offset
        if(scrollY == 0 || scrollY == offset){
            return true;
        }

        return false;
    }
}
