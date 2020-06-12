package com.gakm.flowlayoutlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DemoExample
 * @date 2020/6/11 0011 10:42
 */
public class XFlowLayout extends ViewGroup {
    private final XFlowViewDataObserver mObserver = new XFlowViewDataObserver();

    private List<RectF> mRectFs;
    private FlowAdapter mAdapter;

    private boolean itemLongClick = true;
    private boolean itemClick = true;

    public XFlowLayout(Context context) {
        super(context);
        initAttr(context, null);
    }

    public XFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs);
    }

    public XFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
    }

    public XFlowLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttr(context, attrs);
    }

    private void initAttr(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.XFlowLayout);
        itemClick = typedArray.getBoolean(R.styleable.XFlowLayout_itemClick, itemClick);
        itemLongClick = typedArray.getBoolean(R.styleable.XFlowLayout_itemLongClick, itemLongClick);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        obtainList(getChildCount());

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
            RectF rectF = mRectFs.get(i);
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

    private void obtainList(int childCount) {
        if (mRectFs == null) {
            mRectFs = new ArrayList<>();
        } else {
            if (!mRectFs.isEmpty()) {
                for (RectF rectF : mRectFs) {
                    rectF.set(0, 0, 0, 0);
                }
            }
        }
        addRect(childCount - mRectFs.size());
    }

    private void addRect(int childCount) {
        if (childCount < 0) {
            mRectFs = mRectFs.subList(0, mRectFs.size() - Math.abs(childCount));
        } else {
            for (int i = 0; i < childCount; i++) {
                mRectFs.add(new RectF());
            }
        }

    }

    private void setOnItemClick(final View view, final int position) {
        if (view == null || view.getVisibility() == View.GONE) {
            return;
        }
        if (itemClick) {
            if (!view.isClickable()) {
                view.setClickable(true);
            }
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mAdapter != null) {
                        mAdapter.setOnItemClick(v, position);
                    }
                }
            });
        }
        if (itemLongClick) {
            if (!view.isLongClickable()) {
                view.setLongClickable(true);
            }
            view.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mAdapter != null) {
                        mAdapter.setOnItemLongClick(view, position);
                    }
                    return false;
                }
            });
        }


    }

    @Override
    protected void onDetachedFromWindow() {
        if (mAdapter != null) {
            mAdapter.unregisterAdapterDataObserver(mObserver);
        }
        super.onDetachedFromWindow();

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            RectF rectF = mRectFs.get(i);
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

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    public void setAdapter(@NonNull FlowAdapter adapter) {
        if (mAdapter != null) {
            mAdapter.unregisterAdapterDataObserver(mObserver);
        }
        mAdapter = adapter;
        mAdapter.registerAdapterDataObserver(mObserver);
        mObserver.onChanged();
    }

    public abstract static class XAdapterDataObserver {
        public void onChanged() {
            // Do nothin
        }

        public void onItemRangeInserted(int positionStart, int itemCount) {
            // do nothing
        }

        public void onItemRangeRemoved(int positionStart, int itemCount) {
            // do nothing
        }

    }

    private class XFlowViewDataObserver extends XAdapterDataObserver {
        XFlowViewDataObserver() {
        }

        @Override
        public void onChanged() {
            if (getChildCount() > 0) {
                removeAllViews();
            }
            obtainList(mAdapter.getCount());
            for (int i = 0; i < mAdapter.getCount(); i++) {
                View itemView = mAdapter.getItemView(i);
                setOnItemClick(itemView, i);
                addView(itemView);
            }
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            obtainList(mAdapter.getCount());
            int handleCount = positionStart + itemCount;
            for (int i = positionStart; i < handleCount; i++) {
                View itemView = mAdapter.getItemView(i);
                setOnItemClick(itemView, i);
                addView(itemView, i);
            }

        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            obtainList(mAdapter.getCount());
            if (getChildCount() - 1 >= positionStart) {
                int handleCount = positionStart + itemCount;
                for (int i = positionStart; i < handleCount; i++) {
                    removeViewAt(i);
                }

            }
        }
    }
}
