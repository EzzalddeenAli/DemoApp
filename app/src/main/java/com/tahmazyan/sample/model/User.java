package com.tahmazyan.sample.model;

import com.google.gson.annotations.SerializedName;
import com.tahmazyan.R;
import com.tahmazyan.core.model.Adaptable;

public class User implements Adaptable {

    @SerializedName("userName")
    private String userName;

    @SerializedName("avatar")
    private String avatar;

    @SerializedName("fullName")
    private String fullName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public int getItemType(int pageId) {
        return R.layout.list_item_user;
    }
}
