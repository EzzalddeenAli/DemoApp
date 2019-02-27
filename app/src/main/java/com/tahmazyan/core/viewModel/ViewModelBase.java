package com.tahmazyan.core.viewModel;

import com.tahmazyan.core.constant.LoadingState;
import com.tahmazyan.core.event.Event;
import com.tahmazyan.core.interfaces.callback.DataCallback;
import com.tahmazyan.core.model.Adaptable;
import com.tahmazyan.core.repository.BaseRepository;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public abstract class ViewModelBase<R extends BaseRepository, D extends Adaptable>
        extends ViewModel implements DataCallback<D> {

    protected final R repository;
    protected final MutableLiveData<Integer> loading = new MutableLiveData<>();

    private boolean isRefreshing;
    private int pageId;

    protected abstract R createRepository(DataCallback<D> dataCallback);

    public ViewModelBase() {
        repository = createRepository(this);
        EventBus.getDefault().register(this);
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public MutableLiveData<Integer> getLoading() {
        return loading;
    }

    public void refreshData() {
        setRefreshing(true);
    }

    @Override
    protected void onCleared() {
        EventBus.getDefault().unregister(this);
        repository.cancel();
        super.onCleared();
    }

    @Subscribe
    public void defaultSubscription(Event event) {

    }

    public boolean isRefreshing() {
        return isRefreshing;
    }

    public void setRefreshing(boolean isRefreshing) {
        this.isRefreshing = isRefreshing;
        repository.setRefreshing(isRefreshing);
    }

    @Override
    public void onListOfDataLoaded(int taskId, List<D> adaptable) {
        if (isRefreshing) {
            onRefreshEnd();
        }
    }

    @Override
    public void onDataLoadFailed(int taskId) {
        if (isRefreshing) {
            onRefreshEnd();
        }
    }

    private void onRefreshEnd() {
        setRefreshing(false);
        loading.setValue(LoadingState.HIDE_LOADING);
    }

    @Override
    public void onListOfDataLoadFailed(int taskId) {
        if (isRefreshing) {
            onRefreshEnd();
        }
    }
}
