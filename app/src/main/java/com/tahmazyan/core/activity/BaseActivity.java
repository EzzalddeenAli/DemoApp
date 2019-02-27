package com.tahmazyan.core.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.google.android.material.snackbar.Snackbar;
import com.tahmazyan.R;
import com.tahmazyan.databinding.ActivityBaseBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.navigation.NavController;

public abstract class BaseActivity<T extends ViewDataBinding> extends AppCompatActivity {

    protected ActivityBaseBinding baseBinding;
    protected T binding;
    protected NavController navController;

    @LayoutRes
    protected abstract int getLayoutResId();

    protected void onCreated(@Nullable Bundle savedInstanceState) {
    }

    protected void onPreCreated(@Nullable Bundle savedInstanceState) {
    }

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onPreCreated(savedInstanceState);
        baseBinding = DataBindingUtil.setContentView(this, R.layout.activity_base);
        binding = DataBindingUtil.inflate(getLayoutInflater(), getLayoutResId(), baseBinding.containerLayout, true);
        onCreated(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    protected void hideStatusBar() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    protected void showStatusBar() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    public void hideKeyboard() {
        View view = getCurrentFocus();
        hideKeyboard(view);
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (view != null && imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void openKeyboard(View view) {
        view.post(() -> {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInputFromWindow(
                    view.getApplicationWindowToken(), InputMethodManager.SHOW_IMPLICIT, 0);
            view.requestFocus();
        });
    }

    @Subscribe
    public void defaultSubscription(Object o) {

    }

    //helper method for converting defined size to pixel from dimensions
    public float pixelOf(int resId) {
        return getResources().getDimension(resId);
    }

    public void goTo(Class<? extends Activity> screen) {
        goTo(screen, null);
    }

    public void goTo(Class<? extends Activity> screen, Bundle extras) {
        Intent intent = new Intent(this, screen);
        if (extras != null) intent.putExtras(extras);
        startActivity(intent);
        finish();
    }

    public void goTo(Class<? extends Activity> screen, Bundle extras, boolean revers) {
        Intent intent = new Intent(this, screen);
        if (extras != null) intent.putExtras(extras);
        startActivity(intent);
        if (!revers) {
            finish();
        }
    }

    public void navigateTo(int navigationId) {
        if (navController == null) {
            throw new NullPointerException("Nav controller can not be null wrong activity initialization");
        }
        navigateTo(navigationId, null);
    }

    public void navigateTo(int navigationId, Bundle bundle) {
        if (navController == null) {
            throw new NullPointerException("Nav controller can not be null wrong activity initialization");
        }
        try {
            navController.navigate(navigationId, bundle);
        } catch (IllegalArgumentException e) {
            //call navigation method in try block to prevent runtime crash
            //when missing some action for navigation
            e.printStackTrace();
        }
    }

    //get navController for current navigation
    public NavController getNavController() {
        return navController;
    }

    public void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        snackbar.show();
    }

}
