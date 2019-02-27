package com.tahmazyan.core.activity;


import android.os.Bundle;
import android.view.View;

import com.tahmazyan.core.constant.LoadingState;
import com.tahmazyan.core.viewModel.ViewModelBase;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProviders;

public abstract class BaseActivityWithViewModel<T extends ViewDataBinding, V extends ViewModelBase<?, ?>> extends BaseActivity<T> {

    protected V vm;

    protected abstract Class<V> getViewModelClass();

    private int loadingCurrentState;

    @Override
    protected void onCreated(@Nullable Bundle savedInstanceState) {
        super.onCreated(savedInstanceState);
        vm = ViewModelProviders.of(this).get(getViewModelClass());

        vm.getLoading().observe(this, this::updateLoadingState);

    }

    private void updateLoadingState(int loadingState) {
        if (loadingState == loadingCurrentState) {
            return;
        }

        switch (loadingState) {
            case LoadingState.SHOW_LOADING:
            case LoadingState.KEEP_LOADING:
                baseBinding.loadingView.setVisibility(View.VISIBLE);
                break;
            case LoadingState.HIDE_LOADING:
                baseBinding.loadingView.setVisibility(View.GONE);
                break;
        }

        loadingCurrentState = loadingState;
    }

    public void setFragmentVm(ViewModelBase<?, ?> vm) {
        vm.getLoading().observe(this, this::updateLoadingState);
    }
}

