package com.udacity.noter.add_note;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.udacity.noter.R;
import com.udacity.noter.common.base.BasePresenter;
import com.udacity.noter.common.helpers.Constants;
import com.udacity.noter.common.helpers.MyApplication;
import com.udacity.noter.common.network.ConnectToDB;
import com.udacity.noter.common.network.Urls;
import com.udacity.noter.model.Note;
import com.udacity.noter.model.User;

import java.io.IOException;

public class PresenterAddNote extends BasePresenter {
    private Context mContext;
    private ViewAddNote mViewAddNote;

    public PresenterAddNote(Context context, ViewAddNote viewAddNote) {
        mContext = context;
        mViewAddNote = viewAddNote;
    }

    void addNote(Note note) {
        mViewAddNote.showProgress(true);
        note.setNoteUserId(User.getLoggedInUser(mContext).getId());
        new TaskAddNote().execute(note);
    }


    public class TaskAddNote extends AsyncTask<Note, Void, String> {
        String response = "";

        @Override
        protected String doInBackground(Note... notes) {
            ConnectToDB connectToDB = new ConnectToDB();
            try {
                response = connectToDB.post(Urls.ADD_NOTE, MyApplication.getmGson().toJson(notes[0]));
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            try {
                mViewAddNote.showProgress(false);
                if (!TextUtils.isEmpty(response) && !response.contains(Constants.GeneralKeys.FAILED_MESSAGE)) {
                    mViewAddNote.onAddNoteSuccess();
                } else
                    mViewAddNote.onAddNoteFails(mContext.getString(R.string.something_went_wrong));

            } catch (Exception e) {
                e.printStackTrace();
                mViewAddNote.onAddNoteFails(mContext.getString(R.string.something_went_wrong));
            }
        }
    }
}
