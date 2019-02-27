package com.tahmazyan.core.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tahmazyan.R;
import com.tahmazyan.core.interfaces.itemAction.IBaseActionHandler;
import com.tahmazyan.core.model.Adaptable;
import com.tahmazyan.core.model.Loading;
import com.tahmazyan.core.viewHolder.BaseViewHolder;
import com.tahmazyan.core.viewHolder.LoadingViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<Adaptable> data;
    private IBaseActionHandler actionHandler;
    private Context context;
    private int pageId;

    public RecyclerViewAdapter(Context context, IBaseActionHandler actionHandler) {
        this(context, -1, actionHandler, false);
    }

    public RecyclerViewAdapter(Context context, int pageId, IBaseActionHandler actionHandler) {
        this(context, pageId, actionHandler, false);
    }

    public RecyclerViewAdapter(Context context, int pageId, IBaseActionHandler actionHandler, boolean mustLoadInStart) {
        this.context = context;
        this.pageId = pageId;
        this.actionHandler = actionHandler;
        this.data = new ArrayList<>();
        if (mustLoadInStart) {
            data.add(new Loading(1));
        }
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), viewType, viewGroup, false);
        switch (viewType) {
            case R.layout.list_item_loading:
                return new LoadingViewHolder(viewDataBinding, actionHandler);
            //TODO create UserViewHolder
//            case R.layout.list_item_user:
//                break;
            default:
                return new LoadingViewHolder(viewDataBinding, actionHandler);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder viewHolder, int position) {
        viewHolder.setItem(data.get(position));
    }

    public void setItems(List<Adaptable> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void addItems(List<Adaptable> items) {
        int dataOldSize = data.size();
        data.addAll(items);
        notifyItemRangeInserted(dataOldSize, items.size());
    }

    public void addItemAtFirst(Adaptable item) {
        data.add(0, item);
        notifyItemInserted(0);
    }

    public boolean hasItems() {
        return !data.isEmpty();
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getItemType(pageId);
    }

    public Adaptable getItemAt(int position) {
        return data.get(position);
    }

    public int indexOf(Adaptable item) {
        return data.indexOf(item);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void addItem(Adaptable item) {
        data.add(item);
        notifyItemInserted(data.size() - 1);
    }

    public void addItemAt(int position, Adaptable item) {
        data.add(position, item);
        notifyDataSetChanged();
    }

    public void addItemsAt(int index, List<? extends Adaptable> items) {
        data.addAll(index, items);
        notifyItemRangeInserted(index, items.size());
    }

    public void updateItem(Adaptable item) {
        int position = data.indexOf(item);
        if (position != -1) {
            data.set(position, item);
            notifyItemChanged(position);
        }
    }

    public void removeItem(Adaptable item) {
        int position = data.indexOf(item);
        removeItem(position);
    }

    public void setItem(Adaptable item) {
        int position = data.indexOf(item);
        if (position == -1) {
            return;
        }
        data.set(position, item);
    }

    public void removeItem(int position) {
        if (position != -1) {
            data.remove(position);
            notifyItemRemoved(position);
        }
    }

    private void addLoading(int pageToLoad) {
        Loading loading = new Loading(pageToLoad);
        data.add(loading);
        notifyItemInserted(data.size());
    }

    public void removeLoading() {
        int last = data.size() - 1;
        if (!data.isEmpty() && data.get(last) instanceof Loading) {
            data.remove(last);
            notifyItemRemoved(last);
        }
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull BaseViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.onAttached();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull BaseViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.onDetached();
    }

    @Override
    public void onViewRecycled(@NonNull BaseViewHolder holder) {
        super.onViewRecycled(holder);
        holder.onRecycled();
    }

    @Override
    public boolean onFailedToRecycleView(@NonNull BaseViewHolder holder) {
        return true;
    }

}
