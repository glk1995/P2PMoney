package com.example.glk.p2pmoney.util;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

import com.example.glk.p2pmoney.R;

/**
 * Created by zgqdg on 2016/10/8.
 */

public class DrawableUtil {

    /**
     * 定义一个shape资源
     * @param rgb
     * @param corneradius
     * @return
     */
    //传入一个颜色，和四周圆角半径，把边框厚度设置为固定1dp
    public static GradientDrawable getDrawable(int rgb,int corneradius){
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(rgb);
        gradientDrawable.setGradientType(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadius(corneradius);
        gradientDrawable.setStroke(UIUtils.dp2px(1),rgb);

        return gradientDrawable;
    }

    public static StateListDrawable getSelector(Drawable normalDrawable,Drawable pressDrawable){

        StateListDrawable stateListDrawable = new StateListDrawable();
        //给当前的颜色选择器添加选中图片指向状态，未选中图片指向状态
        stateListDrawable.addState(
                new int[]{android.R.attr.state_enabled,android.R.attr.state_pressed},pressDrawable);
        stateListDrawable.addState(
                new int[]{android.R.attr.state_enabled},normalDrawable);
        //设置默认状态
        stateListDrawable.addState(new int[]{},normalDrawable);


        return stateListDrawable;
    }
}
