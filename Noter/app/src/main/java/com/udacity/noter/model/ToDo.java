package com.udacity.noter.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.udacity.noter.common.helpers.DateTimeHelper;

import java.util.Date;

public class ToDo implements Parcelable {
    @SerializedName("todo_id")
    @Expose
    private String id;
    @SerializedName("todo_title")
    @Expose
    private String title;
    @SerializedName("todo_created_at")
    @Expose
    private String createdAt ;

    public ToDo(String title) {
        this.title = title;
        createdAt = DateTimeHelper.convertDateToStringForServer(new Date(), DateTimeHelper.SERVER_FORMAT);
    }

    protected ToDo(Parcel in) {
        id = in.readString();
        title = in.readString();
        createdAt = in.readString();
    }

    public ToDo() {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(createdAt);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ToDo> CREATOR = new Creator<ToDo>() {
        @Override
        public ToDo createFromParcel(Parcel in) {
            return new ToDo(in);
        }

        @Override
        public ToDo[] newArray(int size) {
            return new ToDo[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
