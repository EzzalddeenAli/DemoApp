package com.tahmazyan.sample.dataSource;

import com.tahmazyan.core.datasource.BaseDataSource;
import com.tahmazyan.core.interfaces.callback.DataCallback;
import com.tahmazyan.sample.model.User;

public class UserRemoteDataSource extends BaseDataSource<User> {

    public UserRemoteDataSource(DataCallback<User> dataCallback) {
        super(dataCallback);
    }

    //TODO must be implemented how to retrieve data from local db

}
