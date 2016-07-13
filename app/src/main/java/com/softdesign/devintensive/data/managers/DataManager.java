package com.softdesign.devintensive.data.managers;

import android.content.SharedPreferences;

import com.softdesign.devintensive.utils.ConstantManager;
import com.softdesign.devintensive.utils.DevintensiveApplication;

import java.util.ArrayList;
import java.util.List;

import com.softdesign.devintensive.data.network.RestService;
import com.softdesign.devintensive.data.network.ServiceGenerator;
import com.softdesign.devintensive.data.network.req.UserLoginReq;
import com.softdesign.devintensive.data.network.res.UserModelRes;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;



public class DataManager {
    private static DataManager INSTANSE = null;
    private PreferencesManager mPreferencesManager;
    private RestService mRestService;

    private DataManager() {
        this.mPreferencesManager = new PreferencesManager();
        this.mRestService = ServiceGenerator.createService(RestService.class);
    }

    public static DataManager getInstanse() {
        if (INSTANSE == null) INSTANSE = new DataManager();
        return INSTANSE;
    }

    public PreferencesManager getPreferencesManager() {
        return mPreferencesManager;
    }

    public Call<UserModelRes> loginUser(UserLoginReq userLoginReq){
        return mRestService.loginUser(userLoginReq);
    }

    public Call<ResponseBody> savePhoto(RequestBody body, MultipartBody.Part file){
        return mRestService.savePhoto(body, file);
    }
}