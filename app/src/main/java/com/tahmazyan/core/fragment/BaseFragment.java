package com.tahmazyan.core.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tahmazyan.R;
import com.tahmazyan.core.activity.BaseActivity;
import com.tahmazyan.databinding.FragmentBaseBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public abstract class BaseFragment<T extends ViewDataBinding> extends Fragment
        implements SwipeRefreshLayout.OnRefreshListener {

    private FragmentBaseBinding baseBinding;
    protected T binding;

    @LayoutRes
    protected abstract int getLayoutResId();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (canRefresh()) {
            baseBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_base, container, false);
            binding = DataBindingUtil.inflate(inflater, getLayoutResId(), baseBinding.contentContainer, true);
            baseBinding.refreshLayout.setEnabled(canRefresh());
            baseBinding.refreshLayout.setOnRefreshListener(this);
            return baseBinding.getRoot();
        } else {
            binding = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false);
            return binding.getRoot();
        }
    }

    protected BaseActivity<?> getBaseActivity() {
        return ((BaseActivity<?>) getActivity());
    }

    @Subscribe
    public void defaultSubscription(Object o) {

    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onRefresh() {

    }

    protected abstract boolean canRefresh();

    protected void setRefreshing(boolean refreshing) {
        if (!canRefresh()) {
            return;
        }
        baseBinding.refreshLayout.setRefreshing(refreshing);
    }

    protected float pixelOf(int resId) {
        return getBaseActivity().pixelOf(resId);
    }

    protected NavController getNavController() {
        return getBaseActivity().getNavController();
    }

    private void setTitle(String title) {
        if (getBaseActivity().getSupportActionBar() != null) {
            getBaseActivity().getSupportActionBar().setTitle(title);
        }
    }

    private void setTitle(int titleResId) {
        setTitle(getString(titleResId));
    }

    public String getTitle() {
        return getClass().getSimpleName();
    }

    protected void enableRefreshing(boolean enable) {
        if (!canRefresh()) {
            return;
        }
        if (baseBinding.refreshLayout != null) {
            baseBinding.refreshLayout.setEnabled(enable);
        }
    }
}

