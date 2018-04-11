package com.udacity.noter.login;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.udacity.noter.R;
import com.udacity.noter.common.base.BasePresenter;
import com.udacity.noter.common.helpers.Constants;
import com.udacity.noter.common.helpers.MyApplication;
import com.udacity.noter.common.network.ConnectToDB;
import com.udacity.noter.common.network.Urls;
import com.udacity.noter.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class PresenterLogin extends BasePresenter {
    private Context mContext;
    private ViewLogin mViewLogin;
    private boolean isLoggedInUser;
    private User currentUser;

    public PresenterLogin(Context context, ViewLogin viewLogin) {
        mContext = context;
        mViewLogin = viewLogin;
    }

    public boolean validUser(String username, String password) {
        boolean isValidUsername = true, isValidPassword = true;
        if (TextUtils.isEmpty(username))
            isValidUsername = false;

        if (TextUtils.isEmpty(password))
            isValidPassword = false;
        mViewLogin.showLoginErrors(isValidUsername, isValidPassword);

        return isValidUsername && isValidPassword;
    }

    void login(User user, boolean isLoggedInUser) {
        this.isLoggedInUser = isLoggedInUser;
        this.currentUser = user;
        mViewLogin.showProgress(true);
        new TaskAddUser().execute(user);
    }

    public class TaskAddUser extends AsyncTask<User, Void, String> {
        String url;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            url = isLoggedInUser ? Urls.LOGIN : Urls.LOGIN;
        }

        @Override
        protected String doInBackground(User... users) {
            String response = "";
            ConnectToDB connectToDB = new ConnectToDB();
            try {
                response = connectToDB.post(url, MyApplication.getmGson().toJson(users[0]));
                //return response;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            mViewLogin.showProgress(false);
            try {
                if (!TextUtils.isEmpty(response) && !response.contains(Constants.GeneralKeys.FAILED_MESSAGE)) {
                    User user = MyApplication.getmGson().fromJson(response, User.class);
                    currentUser.setId(user.getId());
                    currentUser.setUserName(user.getUserName());
                    currentUser.setPassword(user.getPassword());
                    mViewLogin.onLoginSuccess();
                } else
                    mViewLogin.onLoginFail(mContext.getString(R.string.user_already_exists));
            } catch (Exception e) {
                e.printStackTrace();
                mViewLogin.onLoginFail(mContext.getString(R.string.something_went_wrong));
            }
        }
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
