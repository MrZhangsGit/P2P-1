package com.atguigu.p2pinvest.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.atguigu.p2pinvest.R;
import com.atguigu.p2pinvest.utils.UIUtils;

/**
 * Created by 颜银 on 2016/11/12.
 * QQ:443098360
 * 微信：y443098360
 * 作用：
 */
public class RoundProgress extends View {


    //属性
//    private int roundColor = Color.GRAY;//圆环的颜色
//    private int roundProgressColor = Color.RED;//圆弧的颜色
//    private int textColor = Color.BLUE;//文本的颜色
//
//    private int roundWidth = UIUtils.dp2px(10);//圆环的宽度
//    private int textSize = UIUtils.dp2px(20);//字体的大小
//
//    //提供当前的进度和最大值
//    private int progress = 80;
//    private int max = 100;

    private int roundColor;//圆环的颜色
    private int roundProgressColor ;//圆弧的颜色
    private int textColor ;//文本的颜色

    private float roundWidth;//圆环的宽度
    private float textSize ;//字体的大小

    //提供当前的进度和最大值
    private int progress;
    private int max;



    private Paint paint;

    public RoundProgress(Context context) {
        this(context, null);
    }

    public RoundProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //在自定义View类的构造方法中, 取出布局中的自定义属性值   初始化自定义属性
        initData(context,attrs);

        paint = new Paint();
        paint.setAntiAlias(true);//设置抗毛边

    }

    private int width;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = this.getMeasuredWidth();//获取当前视图的宽
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //1.绘制圆环
        //设置圆环中心
        int dx = width / 2;
        int dy = width / 2;

        //设置圆环半径
        float radius = width / 2 - roundWidth / 2;
        //设置画笔属性
        paint.setColor(roundColor);
        paint.setStyle(Paint.Style.STROKE);//设置样式为圆环
        paint.setStrokeWidth(roundWidth);//设置圆环的宽度

        //绘制
        canvas.drawCircle(dx, dy, radius, paint);

        //2.绘制进度
        //理解为包裹圆环中心线的圆的矩形
        RectF rectf = new RectF(roundWidth / 2, roundWidth / 2, width - roundWidth / 2, width - roundWidth / 2);
        paint.setColor(roundProgressColor);//设置画笔颜色
        canvas.drawArc(rectf, 0, progress * 360 / max, false, paint);

        //3.绘制文本
        // 设置画笔(设置字体大小的操作要放在设置包裹的rect之前）
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        paint.setStrokeWidth(0);

        String text = progress * 100 / max + "%";
        Rect bound = new Rect();
        paint.getTextBounds(text, 0, text.length(), bound);//使其宽度和高度整好包裹文本内容

        float left = width / 2 - bound.width() / 2;
        float bottom = width / 2 + bound.height() / 2;

        canvas.drawText(text, left, bottom, paint);
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getProgress() {
        return progress;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    private void initData(Context context, AttributeSet attrs) {
        //1.得到所有自定义属性的数组
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundProgress);
        //2.获取自定义属性的值, 如果没有指定取默认值
        roundColor = typedArray.getColor(R.styleable.RoundProgress_roundColor, Color.RED);
        roundProgressColor = typedArray.getColor(R.styleable.RoundProgress_roundProgressColor, Color.GREEN);
        textColor = typedArray.getColor(R.styleable.RoundProgress_textColor, Color.GREEN);
        roundWidth = typedArray.getDimension(R.styleable.RoundProgress_roundWidth, UIUtils.dp2px(10));
        textSize = typedArray.getDimension(R.styleable.RoundProgress_textSize, UIUtils.dp2px(20));

        max = typedArray.getInteger(R.styleable.RoundProgress_max, 100);//默认最大100
        progress = typedArray.getInteger(R.styleable.RoundProgress_progress, 60);//默认当前为60%

        //3.释放资源数据
        typedArray.recycle();

    }
}
