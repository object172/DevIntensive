package com.softdesign.devintensive.data.managers;
import android.content.SharedPreferences;
import android.net.Uri;

import com.softdesign.devintensive.utils.ConstantManager;
import com.softdesign.devintensive.utils.DevintensiveApplication;

import java.util.ArrayList;
import java.util.List;

public class PreferencesManager {

    private SharedPreferences mSharedPreferences;
   // private static final String[] USER_FIELDS = {ConstantManager.USER_PHONE_KEY, ConstantManager.USER_MAIL_KEY,
   //         ConstantManager.USER_VK_KEY, ConstantManager.USER_GITHUB_KEY, ConstantManager.EDIT_MODE_KEY};

    private static final String[] USER_FIELDS = {
        ConstantManager.USER_PHONE_KEY,
        ConstantManager.USER_MAIL_KEY,
        ConstantManager.USER_VK_KEY,
        ConstantManager.USER_GITHUB_KEY,
        ConstantManager.USER_BIO_KEY
    };
    private static final String[] USER_VALUES = {
        ConstantManager.USER_RATING_VALUE,
        ConstantManager.USER_CODE_LINES_VALUE,
        ConstantManager.USER_PROJECTS_VALUE
    };
    private static final String[] CONTENT_VALUES = {
        ConstantManager.CONTENT_PHONE_VALUES,
        ConstantManager.CONTENT_EMAIL_VALUES,
        ConstantManager.CONTENT_GIT_VALUES,
        ConstantManager.CONTENT_VK_VALUES,
        ConstantManager.CONTENT_BIO_VALUES
    };
    private static final String[] NAV_VALUES = {
        ConstantManager.NAV_FIRST_NAME_VALUES,
        ConstantManager.NAV_SECOND_NAME_VALUES,
        ConstantManager.NAV_AVATAR_VALUES
    };



    public PreferencesManager() {
        this.mSharedPreferences = DevintensiveApplication.getSharedPreferences();
    }

    public void saveUserProfileData(List<String> userFields){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        for (int i = 0; i < USER_FIELDS.length; i++) {
            editor.putString(USER_FIELDS[i], userFields.get(i));
        }
        editor.apply();
    }

    public List<String> loadUserProfileData(){
        List<String> userFields = new ArrayList<>();
        for (int i = 0; i < USER_FIELDS.length; i++) {
            userFields.add(mSharedPreferences.getString(USER_FIELDS[i], "null"));
        }
        return userFields;
    }



    public void saveUserProfileValue(int[] userValues){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        for (int i = 0; i < USER_VALUES.length; i++) {
            editor.putString(USER_VALUES[i], String.valueOf(userValues[i]));
        }
        editor.apply();
    }

    public List<String> loadUserProfileValue(){
        List<String> userValues = new ArrayList<>();
        for (int i = 0; i < USER_VALUES.length; i++) {
            userValues.add(mSharedPreferences.getString(USER_VALUES[i], "0"));
        }
        return userValues;
    }

    public void saveContentValue(String[] contentValues){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        for (int i = 0; i < CONTENT_VALUES.length; i++) {
            if (CONTENT_VALUES[i].equals(ConstantManager.CONTENT_VK_VALUES) ||
                CONTENT_VALUES[i].equals(ConstantManager.CONTENT_GIT_VALUES)) {
                    editor.putString(CONTENT_VALUES[i], contentValues[i].substring(7));
                }else{
                    editor.putString(CONTENT_VALUES[i], contentValues[i]);
                }
            }
        editor.apply();
    }

    public List<String> loadContentValue(){
        List<String> userValues = new ArrayList<>();
        for (int i = 0; i < CONTENT_VALUES.length; i++) {
            userValues.add(mSharedPreferences.getString(CONTENT_VALUES[i], "null"));
        }
        return userValues;
    }

    public void saveNavValue(String[] navValues){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        for (int i = 0; i < NAV_VALUES.length; i++) {
            editor.putString(NAV_VALUES[i], navValues[i]);
        }
        editor.apply();
    }

    public List<String> loadNavValue(){
        List<String> userValues = new ArrayList<>();
        for (int i = 0; i < NAV_VALUES.length; i++) {
            userValues.add(mSharedPreferences.getString(NAV_VALUES[i], "null"));
        }
        return userValues;
    }



    public void saveUserPhoto(Uri uri) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(ConstantManager.USER_PHOTO_KEY, uri.toString());
        editor.apply();
    }

    public Uri loadUserPhoto() {
        return Uri.parse(mSharedPreferences.getString(ConstantManager.USER_PHOTO_KEY, "android.resource://com.softdesign.devintensive/drawable/userphoto"));
    }



    public void saveAuthToken(String authToken){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(ConstantManager.AUTH_TOKEN_KEY, authToken);
        editor.apply();
    }

    public String getAuthToken(){
        return mSharedPreferences.getString(ConstantManager.AUTH_TOKEN_KEY, "null");
    }

    public void saveUserId(String userId){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(ConstantManager.USER_ID_KEY, userId);
        editor.apply();
    }

    public String getUserId(){
        return mSharedPreferences.getString(ConstantManager.USER_ID_KEY, "null");
    }

    public String getEmail(){
        return mSharedPreferences.getString(ConstantManager.CONTENT_EMAIL_VALUES, "null");
    }
}
