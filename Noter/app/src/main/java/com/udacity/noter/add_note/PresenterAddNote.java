package com.udacity.noter.add_note;

import android.content.Context;

import com.udacity.noter.common.base.BasePresenter;
import com.udacity.noter.model.Note;

public class PresenterAddNote extends BasePresenter {
    private Context mContext;
    private ViewAddNote mViewAddNote;

    public PresenterAddNote(Context context, ViewAddNote viewAddNote) {
        mContext = context;
        mViewAddNote = viewAddNote;
    }

    void addNote(Note note){
        mViewAddNote.showProgress(true);
    }
}
