package com.tahmazyan.core.repository;

import com.tahmazyan.core.datasource.BaseDataSource;
import com.tahmazyan.core.interfaces.callback.DataCallback;
import com.tahmazyan.core.model.Adaptable;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRepository<A extends Adaptable, LDS extends BaseDataSource<A>, RDS extends BaseDataSource<A>>
        implements DataCallback<A> {

    protected DataCallback<A> dataCallback;
    protected LDS localDataSource;
    protected RDS remoteDataSource;
    protected boolean isCacheDirty;
    protected List<A> cachedData;

    protected abstract LDS getLocalDataSource();

    protected abstract RDS getRemoteDataSource();

    public BaseRepository(DataCallback<A> dataCallback) {
        this.dataCallback = dataCallback;
        cachedData = new ArrayList<>();
        localDataSource = getLocalDataSource();
        remoteDataSource = getRemoteDataSource();
    }

    public void cancel() {
        localDataSource.cancel();
        remoteDataSource.cancel();
    }

    public void setRefreshing(boolean refreshing) {
        this.isCacheDirty = refreshing;
    }

    @Override
    public void onDataLoadFailed(int taskId) {
        dataCallback.onDataLoadFailed(taskId);
    }

    @Override
    public void onDataLoaded(int taskId, A adaptable) {
        dataCallback.onDataLoaded(taskId, adaptable);
    }

    @Override
    public void onActionDone(int taskId, Object o) {
        dataCallback.onActionDone(taskId, o);
    }

    @Override
    public void onActionFailed(int taskId, Object o) {
        dataCallback.onActionFailed(taskId, o);
    }

    @Override
    public void onListOfDataLoaded(int taskId, List<A> adaptableList) {
        if (isCacheDirty) {
            cleanCache();
            setRefreshing(false);
        }
        cacheData(adaptableList);
        dataCallback.onListOfDataLoaded(taskId, adaptableList);
    }

    private void cacheData(List<A> adaptableList) {
        cachedData.addAll(adaptableList);
    }

    private void cleanCache() {
        cachedData.clear();
    }

    @Override
    public void onListOfDataLoadFailed(int taskId) {
        dataCallback.onListOfDataLoadFailed(taskId);
    }
}
