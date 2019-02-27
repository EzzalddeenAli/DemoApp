package com.tahmazyan.core.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.tahmazyan.BuildConfig;
import com.tahmazyan.core.model.Adaptable;

public class PrefManager {

    private static PrefManager instance;
    private SharedPreferences preferences;
    private Gson gson;

    private PrefManager() {
    }

    public void init(Context context) {
        preferences = context.getSharedPreferences(BuildConfig.APPLICATION_ID + "shared_pref", Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public static PrefManager get() {
        if (instance == null) {
            instance = new PrefManager();
        }
        return instance;
    }

    public <M extends Adaptable> void setModel(M model) {
        preferences.edit().putString(getKeyByType(model.getClass()), gson.toJson(model)).apply();
    }

    public <M extends Adaptable> M getModel(Class<M> clazz) {
        Adaptable config = gson.fromJson(preferences.getString(getKeyByType(clazz), ""), clazz);
        if (config == null) {
            try {
                config = clazz.newInstance();
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }
        return clazz.cast(config);
    }

    private String getKeyByType(Class clazz) {
        return clazz.getSimpleName();
    }
}
