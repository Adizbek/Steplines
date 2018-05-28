package com.github.adizbek.steplinesview;

import android.content.Context;
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

        updateLabelOffset();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(convertDpToPx(20));

        mSidePaint = new Paint();
        mSidePaint.setAntiAlias(true);
        mSidePaint.setDither(true);
        mSidePaint.setColor(Color.parseColor("#AAAAAA"));

        mSidePoint = new Paint();
        mSidePoint.setAntiAlias(true);
        mSidePoint.setDither(true);
        mSidePoint.setColor(Color.parseColor("#FFFFFF"));

        if (isInEditMode()) {
            items.add(new Item("A", "25"));
            items.add(new Item("B", "30"));
            items.add(new Item("C", "35"));
            items.add(new Item("D", "40"));
            items.add(new Item("E", "45"));
            items.add(new Item("F", "50"));

            hasLabel(true);

            setMinimumHeight(startY + items.size() * mOffsetY);
        }

        init = true;
    }

    private void updateLabelOffset() {
        mLabelOffsetX = hasLabels ? convertDpToPx(80) : 0;
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

        setMinimumHeight(y);
    }

    private void drawSideLine(Canvas canvas, int start, int end, Paint paint) {
        int width = mRadius + convertDpToPx(10);
        int f = convertDpToPx(5);
        int radius = width - f;

        int startY = convertDpToPx(20) + (start - 1) * mOffsetY;
        int endY = convertDpToPx(20) + (end - 1) * mOffsetY;
        int startX = hasLabels ? x + mLabelOffsetX : x;

        canvas.drawCircle(startX, startY, radius, paint);
        canvas.drawCircle(startX, endY, radius, paint);
        canvas.drawRect(new Rect(startX - width + f, startY, startX + width - f, endY), paint);
    }

    private void drawSideLine(Canvas canvas) {
        drawSideLine(canvas, 1, items.size(), mSidePaint);
    }

    private void drawRegion(Canvas canvas, Item item) {
        int startX = hasLabels ? x + mLabelOffsetX : x;

        canvas.drawCircle(startX, y, mRadius, mSidePoint);
        canvas.drawText(item.name, startX + mTextOffsetX, y + mTextPadding, mPaint);

        if (hasLabels && item.label != null)
            canvas.drawText(item.label, startX - mLabelOffsetX, y + mTextPadding, mPaint);

        y += mOffsetY;
    }

    public void hasLabel(boolean has) {
        hasLabels = has;

        updateLabelOffset();
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

        public Item(String name, String label) {
            this.name = name;
            this.label = label;
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
