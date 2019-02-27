package com.tahmazyan.core.fragment;

import android.os.Bundle;

import com.tahmazyan.core.activity.BaseActivityWithViewModel;
import com.tahmazyan.core.viewModel.ViewModelBase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

public abstract class BaseFragmentWithViewModel<T extends ViewDataBinding, V extends ViewModelBase> extends BaseFragment<T> {

    protected V vm;

    protected abstract Class<V> getViewModelClass();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatActivity appCompatActivity = getLifecycleOwner();
        if (appCompatActivity == null) {
            vm = ViewModelProviders.of(this, getViewModelFactory()).get(getViewModelClass());
        } else {
            vm = ViewModelProviders.of(appCompatActivity, getViewModelFactory()).get(getViewModelClass());
        }

        if (getActivity() != null && getActivity() instanceof BaseActivityWithViewModel) {
            ((BaseActivityWithViewModel<?, ?>) getActivity()).setFragmentVm(vm);
        }

        onViewModelCreated();
    }

    protected void onViewModelCreated() {

    }

    protected ViewModelProvider.Factory getViewModelFactory() {
        return null;
    }


    @Override
    public void onRefresh() {
        vm.refreshData();
    }


    @Override
    protected boolean canRefresh() {
        return false;
    }


    /**
     * Allow sub fragments to define life cycle owner
     * override method and return activity if we need
     * transfer data between fragment via view model
     * @return null or current activity
     */
    protected AppCompatActivity getLifecycleOwner() {
        return null;
    }
}
