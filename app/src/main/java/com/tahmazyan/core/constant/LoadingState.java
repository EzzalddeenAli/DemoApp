package com.tahmazyan.core.constant;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

@Retention(RetentionPolicy.SOURCE)
@IntDef({LoadingState.SHOW_LOADING, LoadingState.HIDE_LOADING, LoadingState.KEEP_LOADING})
public @interface LoadingState {
    int SHOW_LOADING = 1;
    int HIDE_LOADING = 2;
    int KEEP_LOADING = 3;
}
