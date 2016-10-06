package com.example.glk.p2pmoney.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.glk.p2pmoney.R;

/**
 * Created by zgqdg on 2016/9/26.
 */

//自定义圆环
public class RoundProgress extends View {

    private Paint paint = new Paint();
    private int roundColor;
    private int textColor;
    private int roundProgressColor;
    private float textSize;
    private float roundWidth;
    private int max;

    private int progress = 0;

    public RoundProgress(Context context) {
        this(context,null);
    }

    public RoundProgress(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RoundProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.RoundProgress);
        roundColor = typedArray.getColor(R.styleable.RoundProgress_roundColor, Color.RED);
        //圆环进度的颜色
        roundProgressColor = typedArray.getColor(R.styleable.RoundProgress_roundProgressColor, Color.GREEN);
        //中间进度百分比文字字符串的颜色
        textColor =  typedArray.getColor(R.styleable.RoundProgress_textColor, Color.GREEN);
        //中间进度百分比文字大小
        textSize = typedArray.getDimension(R.styleable.RoundProgress_textSize, 15);
        //圆环的宽度
        roundWidth = typedArray.getDimension(R.styleable.RoundProgress_roundWidth, 5);
        //最大进度
        max = typedArray.getInteger(R.styleable.RoundProgress_max, 100);
        typedArray.recycle();
    }



    //自定义绘制方法
    @Override
    protected void onDraw(Canvas canvas) {
        //第一步、绘制一个最外层的圆
        int center = getWidth()/2;
        int radius = (int) (center - roundWidth / 2);
        paint.setColor(roundColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(roundWidth);
        //抗锯齿
        paint.setAntiAlias(true);
        //这边主要问题是计算圆的半径，也就是第三个参数
        canvas.drawCircle(center,center,radius,paint);

        //第二步，绘制正中间的文本
        float textWidth =  paint.measureText(progress + "%");
        float sx = center - textWidth / 2;
        float sy = center + textSize / 2;
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        paint.setStrokeWidth(0);
        canvas.drawText(progress+"%",sx,sy,paint);

        //第三步:
        /**
         * 参数解释:
         * oval:绘制弧形圈所包含的矩形范围轮廓
         * 0:开始的角度
         * 360 * progress / max:扫描过的角度
         * false:是否包含圆心
         * paint:绘制弧形时候的画笔
         */
        RectF oval = new RectF(center - radius,center - radius,center + radius,center +radius);
        paint.setStrokeWidth(roundWidth);
        paint.setColor(roundProgressColor);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(oval, 0 , 360 * progress / max ,false,paint);
    }

    public void setProgress(int progress){
        this.progress = progress;
        if(progress > 100){
            this.progress = 100;
        }

        postInvalidate();
    }
}
