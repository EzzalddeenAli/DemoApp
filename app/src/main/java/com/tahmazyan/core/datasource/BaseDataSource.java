package com.tahmazyan.core.datasource;

import com.tahmazyan.core.interfaces.callback.DataCallback;
import com.tahmazyan.core.model.Adaptable;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BaseDataSource<A extends Adaptable> {

    protected DataCallback<A> dataCallback;
    private CompositeDisposable compositeDisposable;

    public BaseDataSource(DataCallback<A> dataCallback) {
        this.dataCallback = dataCallback;
        compositeDisposable = new CompositeDisposable();
    }

    protected void add(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    public void cancel() {
        compositeDisposable.clear();
    }
}
