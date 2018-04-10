package com.udacity.noter.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.udacity.noter.common.helpers.AppPreferences;
import com.udacity.noter.common.helpers.Constants;

public class User implements Parcelable {
    @SerializedName("user_id")
    @Expose
    private String id;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("user_password")
    @Expose
    private String password;

    protected User(Parcel in) {
        id = in.readString();
        userName = in.readString();
        password = in.readString();
    }

    public User() {
    }

    public static User getLoggedInUser(Context context) {
        User user = new User();
        user.setId(AppPreferences.getString(Constants.GeneralKeys.USER_ID, context, ""));
        user.setUserName(AppPreferences.getString(Constants.GeneralKeys.USER_NAME, context, ""));
        user.setPassword(AppPreferences.getString(Constants.GeneralKeys.USER_PASSWORD, context, ""));
        return user;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(userName);
        dest.writeString(password);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public static void initUser(User user, Context context) {
        AppPreferences.setString(Constants.GeneralKeys.USER_ID, user.getId(), context);
        AppPreferences.setString(Constants.GeneralKeys.USER_NAME, user.getUserName(), context);
        AppPreferences.setString(Constants.GeneralKeys.USER_PASSWORD, user.getPassword(), context);
    }
}
