package com.tahmazyan.core.model;

import com.tahmazyan.R;

public class Loading implements Adaptable {

    private boolean isLoading = false;
    private int page = 1;

    @Override
    public int getItemType(int type) {
        return R.layout.list_item_loading;
    }

    public int getPage() {
        return page;
    }

    public Loading(int page) {
        this.page = page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }
}
