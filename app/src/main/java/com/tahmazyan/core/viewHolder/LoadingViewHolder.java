package com.tahmazyan.core.viewHolder;

import com.tahmazyan.core.interfaces.itemAction.IBaseActionHandler;
import com.tahmazyan.core.interfaces.itemAction.ILoadingActionHandler;
import com.tahmazyan.core.model.Loading;
import com.tahmazyan.databinding.ListItemLoadingBinding;

import androidx.databinding.ViewDataBinding;

public class LoadingViewHolder extends BaseViewHolder<ListItemLoadingBinding, ILoadingActionHandler, Loading> {

    public LoadingViewHolder(ViewDataBinding binding, IBaseActionHandler actionHandler) {
        super((ListItemLoadingBinding) binding, (ILoadingActionHandler) actionHandler);
    }

    @Override
    public void setItem(Loading item) {
        super.setItem(item);
        actionHandler.loadMore(item.getPage());
    }
}
