package com.tahmazyan.core.viewHolder;

import android.content.Context;
import android.view.View;

import com.tahmazyan.core.interfaces.itemAction.IBaseActionHandler;
import com.tahmazyan.core.model.Adaptable;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseViewHolder<T extends ViewDataBinding,
        H extends IBaseActionHandler,
        A extends Adaptable> extends RecyclerView.ViewHolder {

    protected A item;
    protected T binding;
    protected H actionHandler;
    protected int displayWidth;
    protected Context context;

    public BaseViewHolder(T binding, H actionHandler) {
        super(binding.getRoot());
        this.binding = binding;
        context = binding.getRoot().getContext();
        this.actionHandler = actionHandler;
        if (displayWidth == 0) {
            displayWidth = binding.getRoot().getResources().getDisplayMetrics().widthPixels;
        }
    }

    public void setItem(A item) {
        this.item = item;
    }

    public void onDetached() {
    }

    public void onRecycled() {
    }

    public void onAttached() {

    }

    public void onClick(View view) {

    }

    protected float pixelOf(int resId) {
        return binding.getRoot().getContext().getResources().getDimension(resId);
    }
}
