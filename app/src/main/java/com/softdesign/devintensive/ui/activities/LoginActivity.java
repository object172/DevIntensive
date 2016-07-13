package com.softdesign.devintensive.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.softdesign.devintensive.R;

import android.content.Intent;
import android.net.Uri;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.softdesign.devintensive.data.managers.DataManager;
import com.softdesign.devintensive.data.network.req.UserLoginReq;
import com.softdesign.devintensive.data.network.res.UserModelRes;
import com.softdesign.devintensive.utils.NetworkStatusChecker;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.login_coordinator)
    CoordinatorLayout mLoginCoordinator;
    @BindView(R.id.login)
    EditText mLogin;
    @BindView(R.id.password)
    EditText mPassword;
    @BindView(R.id.sign_in)
    Button mSignIn;
    @BindView(R.id.remember_password)
    TextView mRememberPassword;
    private DataManager mDataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mDataManager = DataManager.getInstanse();
        mSignIn.setOnClickListener(this);
        mRememberPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_in:
                signIn();
                break;
            case R.id.remember_password:
                rememberPassword();
                break;
        }
    }

    private void showSnackbar(String message) {
        Snackbar.make(mLoginCoordinator, message, Snackbar.LENGTH_SHORT).show();
    }

    private void rememberPassword(){
        Intent rememberIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://devintensive.softdesign-apps.ru/forgotpass"));
        startActivity(rememberIntent);
    }

    private void loginSuccess(UserModelRes userModel){
        mDataManager.getPreferencesManager().saveAuthToken(userModel.getData().getToken());
        mDataManager.getPreferencesManager().saveUserId(userModel.getData().getUser().getId());
        saveUserValues(userModel);
        saveContentValues(userModel);
        saveNavValues(userModel);
        savePhoto(userModel);
        Intent loginIntent = new Intent(this, MainActivity.class);
        startActivity(loginIntent);
    }

    private void signIn(){
        if (NetworkStatusChecker.isNetworkAvailable(this)) {
            Call<UserModelRes> call = mDataManager.loginUser(new UserLoginReq(mLogin.getText().toString(),
                mPassword.getText().toString()));
            call.enqueue(new Callback<UserModelRes>() {
                @Override
                public void onResponse(Call<UserModelRes> call, Response<UserModelRes> response) {
                    if (response.code() == 200) {
                        loginSuccess(response.body());
                    } else if (response.code() == 404) {
                        showSnackbar("Неверный логин или пароль");
                    } else {
                        showSnackbar("HTTP ошибка:" + response.code());
                    }
                }
                @Override
                public void onFailure(Call<UserModelRes> call, Throwable t) {

                }
            });
        }else {
            showSnackbar("Соединение отсутствует");
        }
    }

    private void saveUserValues(UserModelRes userModel) {
        int[] userValues = {
                userModel.getData().getUser().getProfileValues().getRating(),
                userModel.getData().getUser().getProfileValues().getLinesCode(),
                userModel.getData().getUser().getProfileValues().getProjects()
        };
        mDataManager.getPreferencesManager().saveUserProfileValue(userValues);
    }

    private void saveContentValues(UserModelRes userModel){
        String[] contentValues = {
            userModel.getData().getUser().getContacts().getPhone(),
            userModel.getData().getUser().getContacts().getEmail(),
            userModel.getData().getUser().getRepositories().getRepo().get(0).getGit(),
            userModel.getData().getUser().getContacts().getVk(),
            userModel.getData().getUser().getPublicInfo().getBio()
        };
        mDataManager.getPreferencesManager().saveContentValue(contentValues);
    }

    private void saveNavValues(UserModelRes userModel){
        String[] navValues = {
            userModel.getData().getUser().getFirstName(),
            userModel.getData().getUser().getSecondName(),
            userModel.getData().getUser().getPublicInfo().getAvatar()
        };

        mDataManager.getPreferencesManager().saveNavValue(navValues);
    }

    private void savePhoto(UserModelRes userModel){
        mDataManager.getPreferencesManager().saveUserPhoto(Uri.parse(userModel.getData().getUser().getPublicInfo().getPhoto()));
    }
}
