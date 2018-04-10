package com.udacity.noter.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by eslam on 4/7/18.
 */

public class Note implements Parcelable{
    @SerializedName("note_id")
    @Expose
    private String noteId;
    @SerializedName("note_title")
    @Expose
    private String noteTitle;
    @SerializedName("note_description")
    @Expose
    private String noteDescription;
    @SerializedName("note_user_id")
    @Expose
    private String noteUserId;
    @SerializedName("note_created_at")
    @Expose
    private String noteCreatedAt;

    public Note(String noteTitle, String noteDescription) {
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
    }

    protected Note(Parcel in) {
        noteId = in.readString();
        noteTitle = in.readString();
        noteDescription = in.readString();
        noteUserId = in.readString();
        noteCreatedAt = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(noteId);
        dest.writeString(noteTitle);
        dest.writeString(noteDescription);
        dest.writeString(noteUserId);
        dest.writeString(noteCreatedAt);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public String getNoteUserId() {
        return noteUserId;
    }

    public void setNoteUserId(String noteUserId) {
        this.noteUserId = noteUserId;
    }

    public String getNoteCreatedAt() {
        return noteCreatedAt;
    }

    public void setNoteCreatedAt(String noteCreatedAt) {
        this.noteCreatedAt = noteCreatedAt;
    }
}
