package com.atguigu.p2pinvest.ui;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

import com.atguigu.p2pinvest.utils.UIUtils;

/**
 * Created by 颜银 on 2016/11/14.
 * QQ:443098360
 * 微信：y443098360
 * 作用： 自定义滑动类
 */
public class MyScrollView extends ScrollView {

    private View childView;

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 获取内部子视图
     * ScrollView只能有个子视图，此方法在渲染完毕后可以调用此子视图
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 0) {
            childView = getChildAt(0);
        }
    }

    private int lastY;//记录上一次y坐标的位置
    private Rect normal = new Rect();//用于记录临界状态时的left,top,right,bottom的坐标
    private boolean isFinishAnimation = true;//判断是否结束了动画

    private int lastX, downX, downY;//记录上一次x坐标的位置--记录down事件时，x轴和y轴的坐标位置


    /**
     * 事件拦截
     * @param ev
     * @return  如果返回值为true，表示拦截子视图的处理。如果返回false，表示不拦截
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean isIntercept = false;
        int eventX = (int) ev.getX();
        int eventY = (int) ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = lastX = eventX;
                downY = lastY = eventY;
                break;
            case MotionEvent.ACTION_MOVE:
                //记录差值距离
                int totalX = Math.abs(downX - eventX);
                int totalY = Math.abs(downY - eventY);

                if (totalX < totalY && totalY > UIUtils.dp2px(10)) {
                    isIntercept = true;
                }

                //重新赋值
                lastX = eventX;
                lastY = eventY;
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return isIntercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (childView == null || !isFinishAnimation) {
            return super.onTouchEvent(ev);
        }

        //获取当前y轴方向的坐标
        int eventY = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastY = eventY;
                break;
            case MotionEvent.ACTION_MOVE:
                //获取移动量
                int dy = eventY - lastY;

                //
                if (isNeedMove()) {
                    if (normal.isEmpty()) {//如果没有记录过left,top,right,bottom，返回true.
                        //记录临界状态的left,top,right,bottom
                        normal.set(childView.getLeft(), childView.getTop(), childView.getRight(), childView.getBottom());//设置后就有对象啦
                    }
                    //给视图重新布局
                    childView.layout(childView.getLeft(), childView.getTop() + dy / 2, childView.getRight(), childView.getBottom() + dy / 2);
                }

                //重新赋值
                lastY = eventY;
                break;
            case MotionEvent.ACTION_UP:
                if (isNeedAnimation()) {
                    final TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0, normal.bottom - childView.getBottom());
                    translateAnimation.setDuration(300);
//                    translateAnimation.setFillAfter(true);//下面监听写childView的位置重置 因此不需要

                    childView.startAnimation(translateAnimation);

                    //动画设置监听
                    translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            isFinishAnimation = false;
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            isFinishAnimation = true;
                            childView.clearAnimation();//清除动画
                            childView.layout(normal.left, normal.top, normal.right, normal.bottom);//重新布局
                            normal.setEmpty();//清空normal中left、top、right、bottom数据
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                }
                break;
        }

        return super.onTouchEvent(ev);

    }

    /**
     * 是否需要移动
     * 判断是否需要按照我们自定义的方式重新布局
     *
     * @return
     */
    private boolean isNeedMove() {
        //获取姿视图的高度
        int childViewHeight = childView.getMeasuredHeight();
        //获取当前屏幕的高度
        int displayHeight = this.getHeight();
        Log.e("TAG", "childViewHeight = " + childViewHeight + ",displayHeight = " + displayHeight);

        //获取两者的距离差
        int distanceHeight = childViewHeight - displayHeight;

        //获取当前视图在y轴方向上移动的位移 (最初：0.上移：+，下移：-)
        int scrollY = this.getScrollY();

        //比较判断
        if (scrollY <= 0 || scrollY >= displayHeight) {
            return true;//下拉小于0 拉过头部  上啦大于差距  拉出下边界  因此返回true  需要滑动
        }

        return false;
    }

    /**
     * 是否需要动画
     *
     * @return
     */
    public boolean isNeedAnimation() {
        //onTouch事件中。up事件制空normal，则上面移动事件触发（就是滑动超过边界的滑动事件触发，松手需要动画回弹），才会创建normal，因此可以用于判断是否需要动画！
        return !normal.isEmpty();
    }
}
