package com.tahmazyan.core.viewModel;

import android.os.Parcelable;

import com.tahmazyan.core.model.Adaptable;
import com.tahmazyan.core.repository.BaseRepository;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public abstract class ViewModelBaseList<R extends BaseRepository, D extends Adaptable> extends ViewModelBase<R, D> {

    private Parcelable listState; // list current scrolled state
    private final List<Adaptable> cachedData;
    private final MutableLiveData<Integer> listDataChangedLiveData;
    private ObservableField<Boolean> empty = new ObservableField<>(false);

    public ViewModelBaseList() {
        this.listDataChangedLiveData = new MutableLiveData<>();
        this.cachedData = new ArrayList<>();
    }

    public Parcelable getListState() {
        return listState;
    }

    public void setListState(Parcelable listState) {
        this.listState = listState;
    }

    public void loadMore(int page) {

    }

    @Override
    public void refreshData() {
        super.refreshData();
        loadMore(1);
    }

    public List<Adaptable> getCachedData() {
        return cachedData;
    }

    /**
     * NOT this method must be handled only in BaseListFragmentWithViewModel fragment
     * if you need do some work when data is received don't manipulate with data
     *
     * @return new data size livedata
     */
    public LiveData<Integer> getListDataChangedLiveData() {
        return listDataChangedLiveData;
    }

    protected MutableLiveData<Integer> getListDataChangedMutableLiveData() {
        return listDataChangedLiveData;
    }

    public ObservableField<Boolean> getEmpty() {
        return empty;
    }

    @Override
    public void onListOfDataLoaded(int taskId, List<D> adaptable) {
        if (isRefreshing()) {
            cachedData.clear();
        }
        cachedData.addAll(adaptable);
        /*
        post -1 value for refreshed to notify adapter update all data
        not only last added
        */
        empty.set(cachedData.isEmpty());
        empty.notifyChange();
        listDataChangedLiveData.setValue(isRefreshing() ? -1 : adaptable.size());
        super.onListOfDataLoaded(taskId, adaptable);
    }

    @Override
    public void onDataLoadFailed(int taskId) {
        super.onDataLoadFailed(taskId);
    }

    @Override
    public void onListOfDataLoadFailed(int taskId) {
        listDataChangedLiveData.setValue(null);
    }
}
