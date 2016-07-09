package com.softdesign.devintensive.data.managers;

import android.content.SharedPreferences;

import com.softdesign.devintensive.utils.ConstantManager;
import com.softdesign.devintensive.utils.DevintensiveApplication;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static DataManager INSTANSE = null;
    private PreferencesManager mPreferencesManager;

    private DataManager() {
        this.mPreferencesManager = new PreferencesManager();
    }

    public static DataManager getInstanse() {
        if (INSTANSE == null) INSTANSE = new DataManager();
        return INSTANSE;
    }

    public PreferencesManager getPreferencesManager() {
        return mPreferencesManager;
    }
}