package com.tahmazyan.core.interfaces.callback;

import com.tahmazyan.core.model.Adaptable;

import java.util.List;

public interface DataCallback<D extends Adaptable> {

    default void onDataLoaded(int taskId, D adaptable) {
    }

    default void onDataLoadFailed(int taskId) {

    }

    default void onListOfDataLoaded(int taskId, List<D> adaptableList) {
    }

    default void onListOfDataLoadFailed(int taskId) {
    }

    default void onActionDone(int taskId, Object o) {

    }

    default void onActionFailed(int taskId, Object o) {

    }

}