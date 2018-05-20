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

import java.util.ArrayList;

public class SteplinesView extends View {

    private Paint mPaint;
    private int mTextPadding;
    private int mTextOffsetX;
    private int mOffsetY;
    private int mRadius;
    private boolean init = false;

    private int x, y = 0, startY = 0;
    private Paint mSidePaint;
    private Paint mSidePoint;

    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<SidebarLayer> sideLayers = new ArrayList<>();
    private Paint mSidePaint2;

    private boolean hasLabels = false;
    private int mLabelOffsetX;

    public SteplinesView(Context context) {
        super(context);

        init();
    }

    public SteplinesView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public SteplinesView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }


    private void init() {
        mTextPadding = convertDpToPx(10);
        mTextOffsetX = convertDpToPx(25);
        mOffsetY = convertDpToPx(45);
        mRadius = convertDpToPx(8);

        x = convertDpToPx(30);
        startY = convertDpToPx(20);
        y = startY;

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

        if (isInEditMode()) {
            items.add(new Item("A"));
            items.add(new Item("B"));
            items.add(new Item("C"));
            items.add(new Item("D"));
            items.add(new Item("E"));
            items.add(new Item("F"));
        }

        init = true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        y = startY;

        drawSideLine(canvas);

        for (SidebarLayer l : sideLayers) {
            drawSideLine(canvas, l.start, l.end, l.paint);
        }

        for (int i = 0; i < items.size(); i++) {
            drawRegion(canvas, items.get(i));
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
        drawSideLine(canvas, 1, items.size(), mSidePaint);
    }

    private void drawRegion(Canvas canvas, Item item) {
        canvas.drawCircle(x, y, mRadius, mSidePoint);
        canvas.drawText(item.name, x + mTextOffsetX, y + mTextPadding, mPaint);

        if (hasLabels && item.label != null)
            canvas.drawText(item.label, x - mLabelOffsetX, y + mTextPadding, mPaint);

        y += mOffsetY;
    }

    public void hasLabel(boolean has) {
        hasLabels = has;
    }

    private int convertDpToPx(int dp) {
        return Math.round(dp * (getResources().getDisplayMetrics().xdpi / DisplayMetrics.DENSITY_DEFAULT));

    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void addLayer(SidebarLayer layer) {
        sideLayers.add(layer);
    }

    public static class Item {
        public String label, name;

        public Item(String label, String name) {
            this.label = label;
            this.name = name;
        }

        public Item(String name) {
            this.name = name;
        }
    }

    public static class SidebarLayer {
        public int start, end;
        private final Paint paint;
        public String color;

        public SidebarLayer(String color, int start, int end) {
            this.color = color;
            this.start = start;
            this.end = end;

            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setDither(true);
            paint.setColor(Color.parseColor(color));

        }
    }
}
