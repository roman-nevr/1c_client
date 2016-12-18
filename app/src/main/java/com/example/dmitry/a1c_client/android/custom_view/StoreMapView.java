package com.example.dmitry.a1c_client.android.custom_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.example.dmitry.a1c_client.R;
import com.example.dmitry.a1c_client.domain.entity.StoreMapObject;

import java.util.List;

public class StoreMapView extends View {
    private String warningString;
    private float warningStringSize = 0;
    private Drawable warningPicture;

    private StoreMapObject storeMapObject = null;
    private Rect paddingRect;

    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;

    public StoreMapView(Context context) {
        super(context);
        init(null, 0);
    }

    public StoreMapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public StoreMapView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        if(isInEditMode()){
            storeMapObject = new StoreMapObject(7,9, new Point(6,8), new Point(6,2));
            storeMapObject.setPlace(1,4,1,"1");
            storeMapObject.setPlace(2,4,1,"1");
            storeMapObject.setPlace(3,1,2,"2");
            storeMapObject.setPlace(3,2,2,"2");
            storeMapObject.setPlace(4,3,3,"3");
            storeMapObject.setPlace(5,3,3,"3");
            storeMapObject.setPlace(6,3,3,"3");
        }

        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.StoreMapView, defStyle, 0);

        warningString = a.getString(
                R.styleable.StoreMapView_warningString);
        if (warningString == null){
            warningString = "";
        }
        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        warningStringSize = a.getDimension(
                R.styleable.StoreMapView_warningStringSize,
                warningStringSize);

        if (a.hasValue(R.styleable.StoreMapView_warningPicture)) {
            warningPicture = a.getDrawable(
                    R.styleable.StoreMapView_warningPicture);
            warningPicture.setCallback(this);
        }

        a.recycle();

        // Set up a default TextPaint object
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements();
    }

    private void invalidateTextPaintAndMeasurements() {
        mTextPaint.setTextSize(warningStringSize);
        mTextWidth = mTextPaint.measureText(warningString);

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = fontMetrics.bottom;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredWidth = measureWidth(widthMeasureSpec);
        int measuredHeight = measureHeight(heightMeasureSpec);
        // Вы ДОЛЖНЫ сделать вызов метода setMeasuredDimension,
        // иначе получится выброс исключения при
        // размещении элемента внутри разметки.
        if(storeMapObject != null){
            float xSpace = (measuredWidth - getPaddingLeft() - getPaddingRight())/storeMapObject.getWidth();
            float ySpace = (measuredHeight - getPaddingTop() - getPaddingBottom())/storeMapObject.getHeight();
            if(xSpace > ySpace){
                measuredWidth = (int) (measuredWidth * ySpace / xSpace);
                warningStringSize = warningStringSize * ySpace / xSpace;
            }else {
                measuredHeight = (int) (measuredHeight * xSpace / ySpace);
                warningStringSize = warningStringSize * xSpace / ySpace;
            }
        }


        setMeasuredDimension(measuredWidth, measuredHeight);
        invalidateTextPaintAndMeasurements();
    }

    private int measureHeight(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        // Размер по умолчанию, если ограничения не были установлены.
        int result = specSize;
        if ((specMode == MeasureSpec.AT_MOST)||(specMode == MeasureSpec.UNSPECIFIED)) {
            //wrap content
        } else if (specMode == MeasureSpec.EXACTLY) {
            // Если ваш элемент может поместиться внутри этих границ, верните это значение.
            result = specSize;
        }
        return result;
    }
    private int measureWidth(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        // Размер по умолчанию, если ограничения не были установлены.
        int result = specSize;
        if (specMode == MeasureSpec.AT_MOST) {
            //wrap content
        } else if (specMode == MeasureSpec.EXACTLY) {
            //match parent (almost)
            //certain size
            result = specSize;
        }
        return result;
    }

    public void setStoreMapObject(StoreMapObject inStoreMapObject){
        this.storeMapObject = inStoreMapObject;
        storeMapObject.getPath();
        this.invalidate();

        /*storeMapObject = new StoreMapObject(3,3);
        storeMapObject.setPlace(1,1,1,"1");*/
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);

        // allocations per draw cycle.
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        paddingRect = new Rect(paddingLeft, paddingTop, paddingRight, paddingBottom);

        float contentWidth = getWidth() - paddingLeft - paddingRight;
        float contentHeight = getHeight() - paddingTop - paddingBottom;

        if (storeMapObject == null){
            // Draw the example drawable on top of the text.
            if (warningPicture != null) {
                warningPicture.setBounds(paddingLeft, paddingTop,
                        paddingLeft + (int)contentWidth, paddingTop + (int)contentHeight);
                warningPicture.draw(canvas);
            }

            // Draw the text.
            canvas.drawText(warningString,
                    paddingLeft + (contentWidth - mTextWidth) / 2,
                    paddingTop + (contentHeight + mTextHeight) / 2,
                    mTextPaint);
        }else {

            List<Point> pathing = storeMapObject.getPath();
            //List<Point> pathing = aStarPathing.pathing(new Point(0,0), new Point(2,2));
            float xSpace = contentWidth / storeMapObject.getWidth();
            float ySpace = contentHeight / storeMapObject.getHeight();
            drawLines(canvas, xSpace, ySpace);
            drawRects(canvas, xSpace, ySpace);
            //drawPathing(canvas, pathing, xSpace, ySpace);
            curvedDrawPathing(canvas, pathing, xSpace, ySpace);
        }
    }

    private void drawPathing(Canvas canvas, List<Point> pathing, float xSpace, float ySpace) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10.0f);
        for (int i = 1; i < pathing.size(); i ++){
            canvas.drawLine(getCenterX(pathing.get(i-1),xSpace),paddingRect.left + pathing.get(i-1).y*ySpace + ySpace/2,
                    paddingRect.left + pathing.get(i).x*xSpace + xSpace/2,paddingRect.left + pathing.get(i).y*ySpace + ySpace/2, paint);
        }
    }

    private void curvedDrawPathing(Canvas canvas, List<Point> pathing, float xSpace, float ySpace) {
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(10.0f);
        paint.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        path.moveTo(getCenterX(pathing.get(0), xSpace),getCenterY(pathing.get(0),ySpace));
        path.rLineTo((pathing.get(1).x - pathing.get(0).x)*xSpace/2, (pathing.get(1).y - pathing.get(0).y)*ySpace/2);
        for (int i = 1; i < pathing.size()-1; i++) {
            addCurve(path, pathing.get(i-1), pathing.get(i),pathing.get(i+1), xSpace, ySpace);
        }
        path.lineTo(getCenterX(pathing.get(pathing.size()-1), xSpace),getCenterY(pathing.get(pathing.size()-1),ySpace));
        canvas.drawPath(path, paint);
    }

    private void addCurve(Path path, Point previous, Point current, Point next, float xSpace, float ySpace) {
        float x3 = (next.x - previous.x)*xSpace/2;
        float y3 = (next.y - previous.y)*ySpace/2;
        float x1 = (current.x - previous.x)*xSpace/2;
        float y1 = (current.y - previous.y)*ySpace/2;
        float x2 = (next.x - current.x)*xSpace/4;
        float y2 = (next.y - current.y) * ySpace/4;
        path.rCubicTo(x1,y1, x1, y1, x3, y3);
    }

    private float getCenterX(Point point, float xSpace){
        return paddingRect.left + point.x*xSpace + xSpace/2;
    }
    private float getCenterY(Point point, float ySpace){
        return paddingRect.top + point.y*ySpace + ySpace/2;
    }

    private void drawRects(Canvas canvas, float xSpace, float ySpace){
        Paint paint = new Paint();
        paint.setColor(0);
        paint.setStrokeWidth(2.0f);
        paint.setTextAlign(Paint.Align.CENTER);
        for (int x = 0; x < storeMapObject.getWidth(); x ++){
            for (int y = 0; y < storeMapObject.getHeight(); y ++){
                if(storeMapObject.getType(x,y) != 0){
                    paint.setColor(Color.GREEN);
                }
                RectF rect = new RectF();
                rect.left = paddingRect.left + x * xSpace;
                rect.right = rect.left + xSpace;
                rect.top = paddingRect.top + y * ySpace;
                rect.bottom = rect.top + ySpace;
                canvas.drawRect(rect, paint);
                paint.setColor(Color.BLACK);
                paint.setTextSize(xSpace);
                Paint.FontMetrics fontMetrics = paint.getFontMetrics();
                float diff = (fontMetrics.ascent - fontMetrics.bottom) / 2 + fontMetrics.bottom;
                canvas.drawText(storeMapObject.getComment(x,y), rect.left + xSpace / 2,
                        rect.top + ySpace / 2 - diff, paint);
                paint.setColor(0);
            }
        }
    }

    private void drawLines(Canvas canvas, float xSpace, float ySpace){
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(2.0f);
        //vertical lines
        for (int x = 0; x < storeMapObject.getWidth() + 1; x ++){
            canvas.drawLine(paddingRect.left +x * xSpace, paddingRect.top,
                        paddingRect.left +x * xSpace, ySpace * storeMapObject.getHeight() + paddingRect.top, paint);
        }
        //horizontal lines
        for (int y = 0; y < storeMapObject.getHeight() + 1; y ++){
            canvas.drawLine(paddingRect.left, paddingRect.top +y * ySpace,
                    xSpace * storeMapObject.getWidth() + paddingRect.left, paddingRect.top +y * ySpace, paint);
        }
    }

    /**
     * Gets the example string attribute value.
     *
     * @return The example string attribute value.
     */
    public String getExampleString() {
        return warningString;
    }

    /**
     * Sets the view's example string attribute value. In the example view, this string
     * is the text to draw.
     *
     * @param exampleString The example string attribute value to use.
     */
    public void setExampleString(String exampleString) {
        warningString = exampleString;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example color attribute value.
     *
     * @return The example color attribute value.
     */

    /**
     * Sets the view's example color attribute value. In the example view, this color
     * is the font color.
     *
     * @param exampleColor The example color attribute value to use.
     */

    /**
     * Gets the example dimension attribute value.
     *
     * @return The example dimension attribute value.
     */
    public float getExampleDimension() {
        return warningStringSize;
    }

    /**
     * Sets the view's example dimension attribute value. In the example view, this dimension
     * is the font size.
     *
     * @param exampleDimension The example dimension attribute value to use.
     */
    public void setExampleDimension(float exampleDimension) {
        warningStringSize = exampleDimension;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example drawable attribute value.
     *
     * @return The example drawable attribute value.
     */
    public Drawable getExampleDrawable() {
        return warningPicture;
    }

    /**
     * Sets the view's example drawable attribute value. In the example view, this drawable is
     * drawn above the text.
     *
     * @param exampleDrawable The example drawable attribute value to use.
     */
    public void setExampleDrawable(Drawable exampleDrawable) {
        warningPicture = exampleDrawable;
    }
}
