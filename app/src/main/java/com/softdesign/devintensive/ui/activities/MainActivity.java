package com.softdesign.devintensive.ui.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.softdesign.devintensive.R;
import com.softdesign.devintensive.data.managers.DataManager;
import com.softdesign.devintensive.utils.ConstantManager;
import com.softdesign.devintensive.utils.RoundedAvatarDrawable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int mCurrentEditMode = 0;
    private boolean[] status = {false, true};
    private DataManager mDataManager;

    private static final String TAG = ConstantManager.TAG_PREFIX + "MainActivity";
    private ImageView avatarView;
    @BindView(R.id.main_coordinator_container)
    CoordinatorLayout mMainCoordinatorContainer;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.navigation_drawer)
    DrawerLayout mNavigationDrawer;
    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;
    @BindView(R.id.fab)
    FloatingActionButton mFab;

    @BindViews({R.id.edit_phone, R.id.edit_mail, R.id.edit_vk, R.id.edit_github, R.id.edit_bio})
    List<EditText> userFieldViews;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mDataManager = DataManager.getInstanse();
        View v = mNavigationView.getHeaderView(0);
        avatarView = (ImageView) v.findViewById(R.id.avatar);

        mFab.setOnClickListener(this);

        setupToolbar();
        setupDrawer();
        createRoundedAvatar();
        loadUserInfoValue();

        if (savedInstanceState != null) {
            mCurrentEditMode = savedInstanceState.getInt(ConstantManager.EDIT_MODE_KEY, 0);
            changeEditMode(mCurrentEditMode);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ConstantManager.EDIT_MODE_KEY, mCurrentEditMode);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mNavigationDrawer.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveUserInfoValue();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    private void showSnackbar(String message) {
        Snackbar.make(mMainCoordinatorContainer, message, Snackbar.LENGTH_SHORT).show();
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Спирин Вячеслав");
        }
    }

    private void setupDrawer() {
        NavigationView mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        assert mNavigationView != null;
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                showSnackbar(item.getTitle().toString());
                item.setChecked(true);
                mNavigationDrawer.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mNavigationDrawer.isDrawerOpen(GravityCompat.START))
            mNavigationDrawer.closeDrawer(GravityCompat.START);
        else
            onBackPressed();
    }

    private void createRoundedAvatar() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = false;
        Bitmap avatar = BitmapFactory.decodeResource(getResources(), R.drawable.avatar, options);
        RoundedAvatarDrawable roundedAvatarDrawable = new RoundedAvatarDrawable(avatar);
        avatarView.setImageDrawable(roundedAvatarDrawable);
    }

    private void changeEditMode(int mode){
        if (mode == 0) {
            mFab.setImageResource(R.drawable.ic_mode_edit_black_24dp);
            saveUserInfoValue();
        } else {
            mFab.setImageResource(R.drawable.ic_done_black_24dp);
        }
        ButterKnife.apply(userFieldViews, CHANGE_EDIT_ALL);
    }

    private void loadUserInfoValue() {
        List<String> userFields = mDataManager.getPreferencesManager().loadUserProfileData();
        for (int i = 0; i < userFields.size(); i++) {
            if (!userFields.get(i).equals("null"))userFieldViews.get(i).setText(userFields.get(i));
        }
    }

    private void saveUserInfoValue() {
        List<String> userFields = new ArrayList<>();
        for (EditText userEditText  : userFieldViews) {
            userFields.add(userEditText.getText().toString());
        }
        mDataManager.getPreferencesManager().saveUserProfileData(userFields);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                if (mCurrentEditMode == 0) mCurrentEditMode = 1;
                else mCurrentEditMode = 0;
                changeEditMode(mCurrentEditMode);
                break;
        }
    }

    final ButterKnife.Action<EditText> CHANGE_EDIT_ALL = new ButterKnife.Action<EditText>() {
        @Override
        public void apply(@NonNull EditText view, int index) {
            view.setEnabled(status[mCurrentEditMode]);
            view.setFocusable(status[mCurrentEditMode]);
            view.setFocusableInTouchMode(status[mCurrentEditMode]);
        }
    };
}
