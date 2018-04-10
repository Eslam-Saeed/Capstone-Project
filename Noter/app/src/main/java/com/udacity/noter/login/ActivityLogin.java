package com.udacity.noter.login;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.udacity.noter.R;
import com.udacity.noter.add_note.ActivityAddNote;
import com.udacity.noter.common.base.BaseActivity;
import com.udacity.noter.common.helpers.UIUtilities;
import com.udacity.noter.common.helpers.Utilities;
import com.udacity.noter.main_notes.ActivityMain;
import com.udacity.noter.model.User;

import okhttp3.OkHttpClient;

public class ActivityLogin extends BaseActivity implements ViewLogin {
    private EditText edtUsername, edtPassword;
    private Button btnLogin;
    private Toolbar mToolbarLogin;
    private ProgressBar mProgressBarLogin;
    private PresenterLogin mPresenterLogin;
    private boolean isLoggedInUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeViews();
        setListeners();
        setToolbar(mToolbarLogin, getString(R.string.login), false, true);
        mPresenterLogin = new PresenterLogin(this, this);
        checkUserExists();
    }

    private void checkUserExists() {
        if (!TextUtils.isEmpty(User.getLoggedInUser(this).getId())) {
            isLoggedInUser = true;
            mPresenterLogin.login(User.getLoggedInUser(this), true);
            return;
        }
        isLoggedInUser = false;
    }

    @Override
    protected void initializeViews() {
        mProgressBarLogin = findViewById(R.id.progressLogin);
        btnLogin = findViewById(R.id.btnLogin);
        mToolbarLogin = findViewById(R.id.toolbarLogin);
        edtUsername = findViewById(R.id.edtUserName);
        edtPassword = findViewById(R.id.edtPassword);
    }

    @Override
    protected void setListeners() {
        btnLogin.setOnClickListener(btnLoginClickListener);
    }

    private View.OnClickListener btnLoginClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Utilities.hideSoftKeyboard(ActivityLogin.this);
            isLoggedInUser = false;
            if (mPresenterLogin.validUser(edtUsername.getText().toString(), edtPassword.getText().toString()))
                mPresenterLogin.login(new User(edtUsername.getText().toString(), edtPassword.getText().toString()), isLoggedInUser);
        }
    };


    @Override
    public void onLoginSuccess() {
        User.initUser(mPresenterLogin.getCurrentUser(), this);
        ActivityMain.startActivity(this);
    }

    @Override
    public void onLoginFail(String errorMessage) {
        UIUtilities.showMessage(this, errorMessage);
    }

    @Override
    public void showLoginErrors(boolean isValidUsername, boolean isValidPassword) {
        if (!isValidUsername)
            edtUsername.setError(getString(R.string.username_empty));
        if (!isValidPassword)
            edtPassword.setError(getString(R.string.password_empty));
    }

    @Override
    public void showProgress(boolean shouldShow) {
        mProgressBarLogin.setVisibility(shouldShow ? View.VISIBLE : View.GONE);
    }
}
