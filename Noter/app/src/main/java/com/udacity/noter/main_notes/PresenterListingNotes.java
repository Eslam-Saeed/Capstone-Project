package com.udacity.noter.main_notes;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.udacity.noter.R;
import com.udacity.noter.common.base.BasePresenter;
import com.udacity.noter.common.helpers.Constants;
import com.udacity.noter.common.helpers.MyApplication;
import com.udacity.noter.common.network.ConnectToDB;
import com.udacity.noter.common.network.Urls;
import com.udacity.noter.model.Note;
import com.udacity.noter.model.User;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class PresenterListingNotes extends BasePresenter {
    private Context mContext;
    private ViewListingNotes mViewListingNotes;
    private ArrayList<Note> listNotes;

    PresenterListingNotes(Context context, ViewListingNotes viewListingNotes) {
        mContext = context;
        mViewListingNotes = viewListingNotes;
        this.listNotes = new ArrayList<>();
    }

    void getMyNotes() {
        mViewListingNotes.showProgress(true);
        new TaskGetMyNotes().execute();
    }

    public ArrayList<Note> getListNotes() {
        return listNotes;
    }

    public class TaskGetMyNotes extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            String response = "";
            ConnectToDB connectToDB = new ConnectToDB();
            try {
                response = connectToDB.get(Urls.GET_ALL_NOTES + User.getLoggedInUser(mContext).getId());
                return response;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            mViewListingNotes.showProgress(false);
            if (!TextUtils.isEmpty(response) && !response.contains(Constants.GeneralKeys.FAILED_MESSAGE)) {
                Type type = new TypeToken<ArrayList<Note>>() {
                }.getType();
                ArrayList<Note> result = MyApplication.getmGson().fromJson(response, type);
                if (result != null) {
                    listNotes.addAll(result);
                    mViewListingNotes.onListingNotesSuccess();
                } else
                    mViewListingNotes.onListingNotesFails(mContext.getString(R.string.something_went_wrong));
            } else
                mViewListingNotes.onListingNotesFails(mContext.getString(R.string.user_already_exists));
        }
    }
}
