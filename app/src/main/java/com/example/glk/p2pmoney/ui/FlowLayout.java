package com.example.glk.p2pmoney.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zgqdg on 2016/10/6.
 */

public class FlowLayout extends ViewGroup {

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * 求取布局的宽高
     *
     * @param widthMeasureSpec  ----宽度的测量规格
     * @param heightMeasureSpec ----高度的测量规格
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获取测量模式、测量值
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        //存储AtMost模式下当前最大宽度和最大高度
        int width = 0;
        int height = 0;

        //此次循环中的行宽和行高
        int lineWidth = 0;
        int lineHeight = 0;

        //求取atMost模式下的布局宽、高值
        int cCount = getChildCount();
        for(int i = 0;i < cCount;i++){
            View child = getChildAt(i);
            //如果这边直接用child.getHeight()是错误的，结果肯定是0。因为这边还未经测量
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            //这边获取布局的Margin,因为ViewGroup没有Margin，所以在下面一个方法我们制定了MarginLayoutParams
            MarginLayoutParams mp = ((MarginLayoutParams) child.getLayoutParams());
            if(lineWidth + childWidth + mp.leftMargin + mp.rightMargin > widthSize){
                //换行:宽度----对比获得
                width = Math.max(width,lineWidth);
                height += lineWidth;
                //重置一下行宽、行高
                //这边出现一个问题，就是如果最后一次是换行,可以肯定的是高度并没有加上，并且宽度如果很大的话,width应该为此次的宽度
                lineWidth  = childWidth  + mp.leftMargin + mp.rightMargin;
                lineHeight = childHeight + mp.topMargin  + mp.bottomMargin;
            }else {
                //不换行:行高---对比获得
                lineWidth += childWidth + mp.leftMargin + mp.rightMargin;
                lineHeight = Math.max(lineHeight,childHeight + mp.topMargin + mp.bottomMargin);
            }
            //最后一次还要再参与计算一次
            if(i == cCount - 1){
                width = Math.max(width,lineWidth);
                height += lineHeight;
            }
        }
        //Log.e("glk","width:" + width + "----height:" + height);
        //根据模式来设置
        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize:width,
                heightMode == MeasureSpec.EXACTLY ? heightSize : height);
    }

    //每一行所包含的所有子View
    private List<List<View>> allViews = new ArrayList<>();

    private List<Integer>  allHeights = new ArrayList<>();

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int width = getWidth();
        int cCount = getChildCount();


        int lineWidth = 0;
        int lineHeight = 0;

        List<View> lineViews = new ArrayList<>();

        for(int i = 0 ;i < cCount;i++){
            View child = getChildAt(i);
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            MarginLayoutParams mp= (MarginLayoutParams)child.getLayoutParams();
            if(lineWidth + childWidth + mp.leftMargin + mp.rightMargin > width){
                //换行
                allViews.add(lineViews);
                allHeights.add(lineHeight);
                //重置一下变量
                lineViews = new ArrayList<>();
                lineWidth = childWidth + mp.rightMargin + mp.leftMargin;
                lineHeight = childHeight + mp.bottomMargin + mp.topMargin;
            }else {
                //不换行
                lineWidth += childWidth + mp.leftMargin + mp.rightMargin;
                lineHeight = Math.max(lineHeight,childHeight + mp.topMargin + mp.bottomMargin);
            }
            lineViews.add(child);


            if(i == cCount - 1){
                allViews.add(lineViews);
                allHeights.add(lineHeight);
            }
        }

        //摆放每一行,每一个子View的位置----------通过计算每一行的每一个子view的left,top,right,bottom
        int left = 0;
        int top = 0;

        for (int i = 0; i < allViews.size(); i++) {
            int currentHeight = allHeights.get(i);
            //当前行的所有子view
            List<View> views = allViews.get(i);
            for (View view : views) {
                int viewWidth = view.getMeasuredWidth();
                int viewHeight = view.getMeasuredHeight();
                MarginLayoutParams mp = (MarginLayoutParams) view.getLayoutParams();
                int lc = left + mp.leftMargin;
                int tc = top + mp.topMargin;
                int rc = lc + viewWidth;
                int bc = tc + viewHeight;
                view.layout(lc,tc,rc,bc);
                left += mp.leftMargin + viewWidth + mp.rightMargin;
            }
            left = 0;
            top += currentHeight;
        }

    }


    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        MarginLayoutParams mp = new MarginLayoutParams(getContext(), attrs);
        return mp;
    }
}
