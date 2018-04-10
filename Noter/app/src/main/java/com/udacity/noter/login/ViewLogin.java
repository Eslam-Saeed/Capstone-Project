package com.udacity.noter.login;

import com.udacity.noter.common.base.BaseView;

public interface ViewLogin extends BaseView {
    void onLoginSuccess();

    void onLoginFail(String errorMessage);

    void showLoginErrors(boolean isValidUsername, boolean isValidPassword);
}
