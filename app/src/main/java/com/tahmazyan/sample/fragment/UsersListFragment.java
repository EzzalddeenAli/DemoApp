package com.tahmazyan.sample.fragment;

import com.tahmazyan.R;
import com.tahmazyan.core.fragment.BaseListFragmentWithViewModel;
import com.tahmazyan.databinding.FragmentUsersListBinding;
import com.tahmazyan.sample.viewModel.UsersListViewModel;

public class UsersListFragment extends BaseListFragmentWithViewModel<FragmentUsersListBinding, UsersListViewModel> {


    @Override
    protected Class<UsersListViewModel> getViewModelClass() {
        return UsersListViewModel.class;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_users_list;
    }
}
