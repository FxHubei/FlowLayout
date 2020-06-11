package com.gakm.demoexample;

import android.content.Context;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * @author DemoExample
 * @company 广安科贸
 * @date 2020/6/11 0011 10:42
 */
public class FTabLayoutExample extends ViewGroup {
    private ArrayList<RectF> rectFS;

    public FTabLayoutExample(Context context) {
        super(context);
    }

    public FTabLayoutExample(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FTabLayoutExample(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FTabLayoutExample(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        initList(getChildCount());

        int lineWidth = 0;
        int lineHeight = 0;
        int height = 0;
        int width = 0;
        int parentPaddingTB = getPaddingBottom() + getPaddingTop();
        int parentPaddingLF = getPaddingLeft() + getPaddingRight();
        L.d("parentHeight2=" + getMeasuredHeight());
        int maxParentWidthLine = MeasureSpec.getSize(widthMeasureSpec) - parentPaddingLF;
        int childLeft = getPaddingLeft();
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            measureChild(childAt, widthMeasureSpec, heightMeasureSpec);
            int measuredWidth = childAt.getMeasuredWidth();
            int measuredHeight = childAt.getMeasuredHeight();

            MarginLayoutParams layoutParams = (MarginLayoutParams) childAt.getLayoutParams();
            int leftMargin = layoutParams.leftMargin;
            int topMargin = layoutParams.topMargin;
            int bottomMargin = layoutParams.bottomMargin;
            int rightMargin = layoutParams.rightMargin;
            int childHeight = measuredHeight + topMargin + bottomMargin;
            int childWidth = measuredWidth + leftMargin + rightMargin;
            RectF rectF = rectFS.get(i);
            if (lineWidth + childWidth > maxParentWidthLine) {
                width = Math.max(width, lineWidth);
                lineWidth = childWidth;
                height += lineHeight;
                lineHeight = childHeight;
                childLeft = getPaddingLeft();
            } else {
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }
            childLeft += leftMargin;
            L.d("==height=" + height);
            int childTop = (topMargin + getPaddingTop() + height);
            setChildFrame(rectF, childLeft, childTop, measuredWidth, measuredHeight);
            childLeft += (measuredWidth + rightMargin);

            if (i == getChildCount() - 1) {
                width = Math.max(lineWidth, width);
                height += lineHeight;
            }
        }
        setMeasuredDimension(
                View.resolveSize(width + parentPaddingLF, widthMeasureSpec),
                View.resolveSize(height + parentPaddingTB, heightMeasureSpec)
        );

    }

    private void initList(int childCount) {
        if (rectFS == null) {
            rectFS = new ArrayList<>(childCount);
            for (int i = 0; i < childCount; i++) {
                rectFS.add(new RectF());
            }
        }
        if (!rectFS.isEmpty()) {
            for (RectF rectF : rectFS) {
                rectF.set(0, 0, 0, 0);
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            RectF rectF = rectFS.get(i);
            View childAt = getChildAt(i);

            childAt
                    .layout((int) (rectF.left), (int) rectF.top, (int) rectF.right, (int) rectF.bottom);
        }
    }

    private void setChildFrame(
            RectF rectF,
            int left,
            int top,
            int width,
            int height
    ) {
        L.d("onLayout=" + "==left==" + left + " ==top===" + top + "==width==" + (+left + width) + "===bottom==" + (top + height));
        rectF.set(left, top, left + width, top + height);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
