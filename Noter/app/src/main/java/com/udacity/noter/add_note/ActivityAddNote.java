package com.udacity.noter.add_note;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.udacity.noter.R;
import com.udacity.noter.common.base.BaseActivity;
import com.udacity.noter.common.helpers.UIUtilities;
import com.udacity.noter.common.helpers.Utilities;
import com.udacity.noter.main_notes.ActivityMain;
import com.udacity.noter.model.Note;

public class ActivityAddNote extends BaseActivity implements ViewAddNote {
    private Toolbar mToolbar;
    private ProgressBar mProgressBar;
    private EditText edtTitle, edtDescription;
    private Button btnAdd;
    private PresenterAddNote presenterAddNote;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ActivityAddNote.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Utilities.isTablet(this))
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_add_note);
        initializeViews();
        setListeners();
        setToolbar(mToolbar, getString(R.string.add_note), true, true);
        presenterAddNote = new PresenterAddNote(this, this);
    }

    @Override
    protected void initializeViews() {
        mToolbar = findViewById(R.id.toolbarAddNote);
        mProgressBar = findViewById(R.id.progressAddNote);
        edtTitle = findViewById(R.id.edtTitle);
        edtDescription = findViewById(R.id.edtDescription);
        btnAdd = findViewById(R.id.btnAddNote);
    }

    @Override
    protected void setListeners() {
        btnAdd.setOnClickListener(btnAddCLickListener);
    }

    private View.OnClickListener btnAddCLickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Utilities.hideSoftKeyboard(ActivityAddNote.this);
            if (TextUtils.isEmpty(edtTitle.getText().toString()))
                edtTitle.setError(getString(R.string.title_empty));
            else
                presenterAddNote.addNote(new Note(edtTitle.getText().toString(), edtDescription.getText().toString()));
        }
    };

    @Override
    public void onAddNoteSuccess() {
        UIUtilities.showMessage(this, getString(R.string.add_note_success));
        finish();
    }

    @Override
    public void onAddNoteFails(String errorMessage) {
        UIUtilities.showMessage(this, errorMessage);
    }

    @Override
    public void showProgress(boolean shouldShow) {
        mProgressBar.setVisibility(shouldShow ? View.VISIBLE : View.GONE);
    }
}
