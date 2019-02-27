package com.tahmazyan.sample.viewModel;

import com.tahmazyan.core.interfaces.callback.DataCallback;
import com.tahmazyan.core.repository.BaseRepository;
import com.tahmazyan.core.viewModel.ViewModelBaseList;
import com.tahmazyan.sample.model.User;
import com.tahmazyan.sample.repository.UserRepository;

public class UsersListViewModel extends ViewModelBaseList<UserRepository, User> {
    @Override
    protected UserRepository createRepository(DataCallback dataCallback) {
        return new UserRepository(dataCallback);
    }
}
