package com.gakm.flowlayoutlib;

import android.database.Observable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author DemoExample
 * @date 2020/6/12 0012 13:38
 */
abstract public class FlowAdapter<K> {

    private final AdapterDataObservable mObservable = new AdapterDataObservable();
    private List<K> datas;

    private OnViewClickListener onViewClickListener;
    private OnViewLongClickListener onViewLongClickListener;

    public FlowAdapter(@NonNull List<K> list, int res) {
        this.datas = list;
    }

    public FlowAdapter(@NonNull K[] data) {
        datas = new ArrayList<K>(Arrays.asList(data));
    }
    /*    *//**
     * 默认实在尾部
     *
     * @param list
     *//*
    public void addData(@NonNull List<T> list) {
        datas.addAll(list);
    }
    */

    /**
     * 默认实在尾部
     *
     * @param list
     *//*
    public void addHeadData(@NonNull List<T> list) {
        datas.add;
    }*/
    public void setNewData(@NonNull List<K> list) {
        datas = list;
        notifyDataSetChanged();
    }

    /**
     * 默认实在尾部
     *
     * @param
     */

    public void addData(@NonNull List<K> list) {
        datas.addAll(list);
        notifyItemRangeInserted(0, list.size());
    }

    /**
     * 默认是添加在最前边
     *
     * @param data
     */
    public void addData(@NonNull K data) {
        datas.add(0, data);
        notifyItemRangeInserted(0, 1);
    }

    public void removeItem(int position) {
        removeItem(0, 1);
    }

    public void removeItem(int position, int count) {
        if (count > datas.size()) {
            return;
        }
        int handleCount = position + count;
        for (int i = position; i < handleCount; i++) {
            datas.remove(position);
        }
        notifyItemRangeRemoved(position, count);
    }

    @NonNull
    protected abstract View getItemView(int position);

    public int getCount() {
        return datas.size();
    }

    public List<K> getItems() {
        return datas;
    }

    @Nullable
    public K getSafeData(int position) {
        if (position <= getCount()) {
            return getItems().get(position);
        }
        return null;
    }

    public final void addItemClickListener(@NonNull OnViewClickListener onViewClickListener) {
        this.onViewClickListener = onViewClickListener;
    }

    void setOnItemClick(View v, int position) {
        if (getOnViewClickListener() != null) {
            getOnViewClickListener().onViewClick(v, position);
        }
    }

    void setOnItemLongClick(View v, int position) {
        if (getOnViewLongClickListener() != null) {
            getOnViewLongClickListener().onViewLongClick(v, position);
        }
    }

    public final OnViewClickListener getOnViewClickListener() {
        return onViewClickListener;
    }

    public final void addItemLongClickListener(@NonNull OnViewLongClickListener onViewClickListener) {
        this.onViewLongClickListener = onViewClickListener;
    }

    public final OnViewLongClickListener getOnViewLongClickListener() {
        return onViewLongClickListener;
    }

    public void notifyDataSetChanged() {
        mObservable.notifyChanged();
    }

    public final void notifyItemRangeInserted(int positionStart, int itemCount) {
        mObservable.notifyItemRangeInserted(positionStart, itemCount);
    }

    public final void notifyItemRangeRemoved(int positionStart, int itemCount) {
        mObservable.notifyItemRangeInserted(positionStart, itemCount);
    }

    void registerAdapterDataObserver(@NonNull XFlowLayout.XAdapterDataObserver observer) {
        mObservable.registerObserver(observer);
    }

    void unregisterAdapterDataObserver(@NonNull XFlowLayout.XAdapterDataObserver observer) {
        mObservable.unregisterObserver(observer);
    }

    /**
     * listener;
     */
    public interface OnViewClickListener {
        void onViewClick(View v, int position);
    }

    /**
     * listener;
     */
    public interface OnViewLongClickListener {
        void onViewLongClick(View v, int position);
    }

    static class AdapterDataObservable extends Observable<XFlowLayout.XAdapterDataObserver> {
        public boolean hasObservers() {
            return !mObservers.isEmpty();
        }

        void notifyChanged() {
            // since onChanged() is implemented by the app, it could do anything, including
            // removing itself from {@link mObservers} - and that could cause problems if
            // an iterator is used on the ArrayList {@link mObservers}.
            // to avoid such problems, just march thru the list in the reverse order.
            for (int i = mObservers.size() - 1; i >= 0; i--) {
                mObservers.get(i).onChanged();
            }
        }

        void notifyItemRangeChanged(int positionStart, int itemCount) {
            notifyItemRangeChanged(positionStart, itemCount, null);
        }

        void notifyItemRangeChanged(int positionStart, int itemCount,
                                    @Nullable Object payload) {

        }

        void notifyItemRangeInserted(int positionStart, int itemCount) {
            // since onItemRangeInserted() is implemented by the app, it could do anything,
            // including removing itself from {@link mObservers} - and that could cause problems if
            // an iterator is used on the ArrayList {@link mObservers}.
            // to avoid such problems, just march thru the list in the reverse order.
            for (int i = mObservers.size() - 1; i >= 0; i--) {
                mObservers.get(i).onItemRangeInserted(positionStart, itemCount);
            }
        }

        void notifyItemRangeRemoved(int positionStart, int itemCount) {
            // since onItemRangeRemoved() is implemented by the app, it could do anything, including
            // removing itself from {@link mObservers} - and that could cause problems if
            // an iterator is used on the ArrayList {@link mObservers}.
            // to avoid such problems, just march thru the list in the reverse order.
            for (int i = mObservers.size() - 1; i >= 0; i--) {
                mObservers.get(i).onItemRangeRemoved(positionStart, itemCount);
            }
        }
    }

}
