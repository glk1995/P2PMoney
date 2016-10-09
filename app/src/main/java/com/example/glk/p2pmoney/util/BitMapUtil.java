package com.example.glk.p2pmoney.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

/**
 * Created by zgqdg on 2016/10/8.
 */

public class BitMapUtil {

    /**
     *对原始图片进行缩放
     *
     * 注意:wf、wh必须不能是int
     *
     *
     * @param source 图片源
     * @param wf    指定宽度,必须是float类型！！！！！
     * @param hf    指定高度,必须是float类型！！！！！
     * @return
     */
    public static Bitmap zoom(Bitmap source,float wf , float hf){


        Matrix matrix = new Matrix();
        //参数：缩放的宽度比例、高度比例
        float sx = wf / source.getWidth();
        float sy = hf / source.getHeight();
        matrix.postScale(sx,sy);
        //2、3:从那个点开始 4、5：缩放的高度、宽度  7：是否使用图像处理
        return Bitmap.createBitmap(source,0,0,source.getWidth(),source.getHeight(),matrix,true);
    }


    /**
     * 图像圆形裁减
     * @param source
     * @return
     */

    public static Bitmap circleBitmap(Bitmap source){
        final Paint paint = new Paint();
        //抗锯齿效果
        int width = source.getWidth();
        paint.setAntiAlias(true);
        //指定大小bitmap
        //最后一个参数是生成图像质量
        Bitmap target = Bitmap.createBitmap(width,width, Bitmap.Config.ARGB_8888);
        //根据target生成一个画布
        Canvas canvas = new Canvas(target);
        //在画布上画了一个圆
        //参数Cx,Cy ---> 确定绘制圆的圆心点
        //半径参数
        //画笔
        canvas.drawCircle(width / 2,width / 2, width/2 ,paint);
        //这局话是关键:
        //分析:我们以一张图片作为画布，在上面画了一个圆---->这时候绘制的圆和预判本身就出现了一个圆形的交际团
        //setXfermode:设置当绘制的图像出现相交情况时候的处理方式，它包含的常用模式有哪几种
        //PorterDuff.Mode.SRC---显示上层图像
        //PorterDuff.Mode.CLEAR 清除画布上的图像
        //PortDuff.Mode.SRC_IN取两层图像的交集部分，只显示上层图像，注意这里是指取相交叉的部分，然后显示上层图像
        //PortDuff.Mode.DST_IN取两层图像的交集部分，只显示下层图像
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //使用设置了setXfermode方案的paint绘制图像
        canvas.drawBitmap(source,0,0,paint);

        return target;
    }

}
