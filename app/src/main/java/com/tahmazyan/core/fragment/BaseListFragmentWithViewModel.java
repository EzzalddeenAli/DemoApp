package com.tahmazyan.core.fragment;

import android.os.Bundle;
import android.view.View;

import com.tahmazyan.R;
import com.tahmazyan.core.adapter.RecyclerViewAdapter;
import com.tahmazyan.core.interfaces.itemAction.ILoadingActionHandler;
import com.tahmazyan.core.model.Adaptable;
import com.tahmazyan.core.view.SpaceItemDecorator;
import com.tahmazyan.core.viewModel.ViewModelBaseList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public abstract class BaseListFragmentWithViewModel<T extends ViewDataBinding, V extends ViewModelBaseList>
        extends BaseFragmentWithViewModel<T, V>
        implements ILoadingActionHandler {

    protected RecyclerViewAdapter adapter;
    private RecyclerView recyclerView;

    protected Observer<Adaptable> adaptableObserver = new Observer<Adaptable>() {
        @Override
        public void onChanged(@Nullable Adaptable adaptable) {
            adapter.addItemAt(0, adaptable);
//            vm.getAdaptableLiveData().removeObserver(adaptableObserver);
        }
    };

    @Override
    protected void onViewModelCreated() {
        super.onViewModelCreated();
        vm.getListDataChangedLiveData().observe(this, (Observer<Integer>) integer -> {
            setRefreshing(false);
            if (integer != null) {
                if (integer == -1) {
                    adapter.notifyDataSetChanged();
                } else {
                    adapter.notifyItemRangeInserted(adapter.getItemCount() == 0 ? 0
                            : adapter.getItemCount() - integer, integer);
                }
            } else {
                setRefreshing(false);
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_view);
        setRecyclerViewObservable();
        initRecyclerView();
    }

    private void initRecyclerView() {
        if (recyclerView == null) {
            return;
        }
        adapter = getAdapter();
        adapter.setItems(vm.getCachedData());
        recyclerView.setLayoutManager(getLayoutManager());
        recyclerView.addItemDecoration(getItemDecoration());
        recyclerView.setAdapter(adapter);
        if (vm.getCachedData().size() == 0) {
            vm.loadMore(1);
        }
    }

    protected RecyclerViewAdapter getAdapter() {
        return new RecyclerViewAdapter(getContext(), vm.getPageId(), this, true);
    }

    protected RecyclerView.ItemDecoration getItemDecoration() {
        return new SpaceItemDecorator((int) pixelOf(R.dimen.list_item_spacing_default), SpaceItemDecorator.VERTICAL);
    }

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
    }

    private void setRecyclerViewObservable() {
        //TODO implement recycler view dat change observer logic
    }

    @Override
    public void onRefresh() {
        vm.setRefreshing(true);
        vm.loadMore(1);
    }

    @Override
    protected void setRefreshing(boolean refreshing) {
        super.setRefreshing(refreshing);
        vm.setRefreshing(refreshing);
    }

    @Override
    public void onStop() {
        if (recyclerView.getLayoutManager() != null) {
            vm.setListState(recyclerView.getLayoutManager().onSaveInstanceState());
        }
        super.onStop();
    }

    @Override
    protected boolean canRefresh() {
        return true;
    }

    public void loadMore(int page) {
        enableRefreshing(false);
        vm.loadMore(page);
    }
}

