package com.tahmazyan.sample.repository;

import com.tahmazyan.core.interfaces.callback.DataCallback;
import com.tahmazyan.core.repository.BaseRepository;
import com.tahmazyan.sample.dataSource.UserLocalDataSource;
import com.tahmazyan.sample.dataSource.UserRemoteDataSource;
import com.tahmazyan.sample.model.User;

public class UserRepository extends BaseRepository<User, UserLocalDataSource, UserRemoteDataSource> {

    public UserRepository(DataCallback dataCallback) {
        super(dataCallback);
    }

    @Override
    protected UserLocalDataSource getLocalDataSource() {
        return new UserLocalDataSource(dataCallback);
    }

    @Override
    protected UserRemoteDataSource getRemoteDataSource() {
        return new UserRemoteDataSource(dataCallback);
    }
}
