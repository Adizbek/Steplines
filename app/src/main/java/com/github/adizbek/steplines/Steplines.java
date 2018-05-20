package com.github.adizbek.steplines;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

public class Steplines extends View {

    private Paint mPaint;
    private int mTextPadding;
    private int mTextOffsetX;
    private int mOffsetY;
    private int mRadius;
    private boolean init = false;

    private int x, y = 0;
    private Paint mSidePaint;
    private Paint mSidePoint;

    private String[] labels = {"11:00", "12:00", "15:00", "21:00", "23:00"};
    private String[] items = {"Toshkent", "Buxoro", "Andijon", "Fargona", "Qarshi"};
    private Paint mSidePaint2;

    private boolean hasLabels = true;
    private int mLabelOffsetX;

    public Steplines(Context context) {
        super(context);
    }

    public Steplines(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Steplines(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private void init() {
        if (init) return;

        mTextPadding = convertDpToPx(10);
        mTextOffsetX = convertDpToPx(25);
        mOffsetY = convertDpToPx(45);
        mRadius = convertDpToPx(8);

        x = convertDpToPx(30);
        y = convertDpToPx(20);

        if (hasLabels) {
            mLabelOffsetX = convertDpToPx(80);
            x += mLabelOffsetX;
        }

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(convertDpToPx(20));

        mSidePaint = new Paint();
        mSidePaint.setAntiAlias(true);
        mSidePaint.setDither(true);
        mSidePaint.setColor(Color.parseColor("#AAAAAA"));

        mSidePaint2 = new Paint();
        mSidePaint2.setAntiAlias(true);
        mSidePaint2.setDither(true);
        mSidePaint2.setColor(Color.parseColor("#9B51E0"));

        mSidePoint = new Paint();
        mSidePoint.setAntiAlias(true);
        mSidePoint.setDither(true);
        mSidePoint.setColor(Color.parseColor("#FFFFFF"));

        init = true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        init();

        drawSideLine(canvas);
        drawSideLine(canvas, 2, 3, mSidePaint2);

        for (int i = items.length - 1; i >= 0; i--) {
            drawRegion(canvas, items[i], labels[i]);
        }
    }

    private void drawSideLine(Canvas canvas, int start, int end, Paint paint) {
        int width = mRadius + convertDpToPx(10);
        int f = convertDpToPx(5);
        int radius = width - f;
        int startY = convertDpToPx(20) + (start - 1) * mOffsetY;
        int endY = convertDpToPx(20) + (end - 1) * mOffsetY;

        canvas.drawCircle(x, startY, radius, paint);
        canvas.drawCircle(x, endY, radius, paint);
        canvas.drawRect(new Rect(x - width + f, startY, x + width - f, endY), paint);
    }

    private void drawSideLine(Canvas canvas) {
        drawSideLine(canvas, 1, items.length, mSidePaint);
    }

    private void drawRegion(Canvas canvas, String city, String label) {
        canvas.drawCircle(x, y, mRadius, mSidePoint);
        canvas.drawText(city, x + mTextOffsetX, y + mTextPadding, mPaint);

        if (hasLabels)
            canvas.drawText(label, x - mLabelOffsetX, y + mTextPadding, mPaint);

        y += mOffsetY;
    }

    public static float convertPixelsToDp(float px) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return Math.round(dp);
    }

    public static float convertDpToPixel(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
    }
//http://stackoverflow.com/questions/4605527/converting-pixels-to-dp
//The above method results accurate method compared to below methods
//http://stackoverflow.com/questions/8309354/formula-px-to-dp-dp-to-px-android


    private int convertDpToPx(int dp) {
        return Math.round(dp * (getResources().getDisplayMetrics().xdpi / DisplayMetrics.DENSITY_DEFAULT));

    }

    private int convertPxToDp(int px) {
        return Math.round(px / (Resources.getSystem().getDisplayMetrics().xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
