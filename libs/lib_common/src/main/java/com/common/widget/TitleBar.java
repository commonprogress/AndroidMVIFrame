package com.common.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.FragmentActivity;

import com.common.R;


/**
 * @Author: Robert.yang
 * @CreateDate: 2019-09-18
 * @Description: 通用的act title bar
 */
public class TitleBar extends FrameLayout {
    private Context mContext;

    /**
     * 标题
     */
    private AppCompatTextView mTitle;

    /**
     * 左边图标按钮
     */
    private AppCompatImageView mTitleLeftImage;
    /**
     * 左边文字
     */
    private AppCompatTextView mTitleLeftTextView;

    /**
     * 右边文字
     */
    private AppCompatTextView mTitleRightTextView;
    /**
     * 右边图标按钮
     */
    private AppCompatImageView mTitleRightImage;

    /**
     * 底部的那根线 。默认不显示
     */
    private View mTitleBottomLine;

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        LayoutInflater.from(mContext).inflate(R.layout.view_common_title_bar_layout, this, true);
        mTitle = findViewById(R.id.title_bar_title_tv);


        mTitleLeftImage = findViewById(R.id.title_bar_title_left_iv);
        mTitleLeftTextView = findViewById(R.id.title_bar_title_left_tv);

        mTitleRightTextView = findViewById(R.id.title_bar_title_right_tv);

        mTitleRightImage = findViewById(R.id.title_bar_title_right_iv);
        mTitleBottomLine = findViewById(R.id.title_bar_bottom_line);
    }

    /**
     * 设置TitleBar背景颜色
     */
    public void setTitleBarBackground(int color) {
        this.setBackgroundColor(color);
    }

    /**
     * 设置TitleBar背景颜色
     */
    public void setTitleBarBackground(Drawable background) {
        this.setBackground(background);
    }

    /**
     * 显示那根线。默认不显示
     *
     * @param visibility
     */
    public void setLineVisibility(int visibility) {
        mTitleBottomLine.setVisibility(visibility);
    }

    public void setTitle(String title) {
        if (mTitle.getVisibility() != VISIBLE) {
            mTitle.setVisibility(VISIBLE);
        }
        mTitle.setText(title);
    }


    public AppCompatTextView getTitleView() {
        return mTitle;
    }


    public void setTitleColor(int color) {
        if (mTitle.getVisibility() != VISIBLE) {
            mTitle.setVisibility(VISIBLE);
        }
        mTitle.setTextColor(color);
    }


    public void setTitleLeftImageVisibility(int visible) {
        mTitleLeftImage.setVisibility(visible);
    }

    public void setLeftImage(Drawable d) {
        mTitleLeftImage.setImageDrawable(d);
        mTitleLeftImage.setVisibility(VISIBLE);
    }

    public void setLeftImageVisible(boolean visible) {
        mTitleLeftImage.setVisibility(visible ? VISIBLE : GONE);
    }

    public void setRightTextVisibility(int visible) {
        mTitleRightTextView.setVisibility(visible);
    }

    /**
     * 设置左侧文字
     *
     * @param text
     */
    public void setLeftText(String text) {
        mTitleLeftTextView.setVisibility(VISIBLE);
        mTitleLeftTextView.setText(text);
    }

    public AppCompatTextView getLeftText() {
        return mTitleLeftTextView;
    }

    /**
     * 设置右侧文本
     */
    public void setRightText(String text) {
        setRightTextVisibility(VISIBLE);
        mTitleRightTextView.setText(text);
    }

    public AppCompatTextView getRightText() {
        return mTitleRightTextView;
    }

    /**
     * 设置右侧文本颜色
     */
    public void setRightTextColor(int color) {
        mTitleRightTextView.setTextColor(color);
    }

    /**
     * 设置右侧第一个按钮
     */
    public void setRightImage(Drawable d) {
        mTitleRightImage.setImageDrawable(d);
        mTitleRightImage.setVisibility(VISIBLE);
    }

    public AppCompatImageView getTitleRightImage() {
        return mTitleRightImage;
    }


    public void setLeftOnClickListener(OnClickListener listener) {
        setTitleLeftImageVisibility(View.VISIBLE);
        mTitleLeftImage.setOnClickListener(listener);
    }

    public void setRightTextOnClickListener(OnClickListener listener) {
        mTitleRightTextView.setOnClickListener(listener);
    }

    /**
     * 设置右侧第一个按钮的点击事件
     *
     * @param clickListener
     */
    public void setRightImageOnClickListener(OnClickListener clickListener) {
        mTitleRightImage.setOnClickListener(clickListener);
    }


    public void setHeaderTitle(String title) {
        setUpMainTitle(title);
        mOnLeftClick = null;
        setTitleLeftImageVisibility(View.GONE);
        setRightTextVisibility(View.GONE);
    }


    public void setTitleBarWithLeftImage(String title) {
        this.setTitleBarWithLeftImage(title, null);
    }

    public void setTitleBarWithLeftImage(String title, OnLeftClick listener) {

        setUpMainTitle(title);

        setUpLeftImage(listener);

        setRightTextVisibility(View.GONE);
    }

    public void setTitleBarWithLeftAndRight(String title, String rightTitle, OnLeftClick leftListener, OnClickListener rightListener) {

        setUpMainTitle(title);

        setUpLeftImage(leftListener);

        setUpRightText(rightTitle, rightListener);
    }

    /**
     * 设置标题，左侧按钮，右侧图片按钮与点击事件
     *
     * @param title         标题
     * @param drawable      右侧图片
     * @param leftListener  左侧按钮点击事件
     * @param rightListener 右侧按钮点击事件
     */
    public void setTitleBarWithLeftAndRightImage(String title, Drawable drawable, OnLeftClick leftListener, OnClickListener rightListener) {

        setUpMainTitle(title);

        setUpLeftImage(leftListener);

        setUpRightImage(drawable, rightListener);
    }

    public void setTitleBarWithRight(String title, String rightTitle, OnClickListener rightListener) {

        setUpMainTitle(title);

        setUpRightText(rightTitle, rightListener);
    }


    /**
     * 设置主标题
     *
     * @param title
     */
    public void setUpMainTitle(String title) {
        setVisibility(View.VISIBLE);
        setTitle(title);
    }

    /**
     * 设置左侧图片，默认点击返回
     *
     * @param listener
     */
    public void setUpLeftImage(OnLeftClick listener) {
        setTitleLeftImageVisibility(View.VISIBLE);

        mOnLeftClick = listener;
        setLeftOnClickListener(new TitleBarLeftDefaultListener());
    }


    /**
     * 设置右侧文本和点击事件
     *
     * @param rightTitle
     * @param rightListener
     */
    private void setUpRightText(String rightTitle, OnClickListener rightListener) {
        setRightText(rightTitle);
        if (rightListener != null) {
            setRightTextOnClickListener(rightListener);
        }
    }

    /**
     * 设置右侧图片和点击事件
     *
     * @param drawable
     * @param rightListener
     */
    public void setUpRightImage(Drawable drawable, OnClickListener rightListener) {
        setRightImage(drawable);
        if (rightListener != null) {
            setRightImageOnClickListener(rightListener);
        }
    }


    public View getTitleBottomLine() {
        return mTitleBottomLine;
    }

    private class TitleBarLeftDefaultListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    }

    public ImageView getLeftImageBtn(){
        return mTitleLeftImage;
    }

    public void onBackPressed() {
        if (mOnLeftClick == null || !mOnLeftClick.onLeftClick()) {
            FragmentActivity activity = ((FragmentActivity) mContext);
            if (null != activity) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    if (!activity.isDestroyed()) {
                        activity.onBackPressed();
                    }
                } else {
                    activity.onBackPressed();
                }
            }
        }
    }

    private OnLeftClick mOnLeftClick;

    public interface OnLeftClick {
        /**
         * 顶部导航栏左侧返回按钮点击事件
         *
         * @return true 为事件已被处理； false 事件为处理。
         * 如果返回false，默认会调用onBackPress方法，不需要手动调用，否则会引起无限递归
         */
        boolean onLeftClick();
    }

}
