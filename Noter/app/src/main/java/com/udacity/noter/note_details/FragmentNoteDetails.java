package com.udacity.noter.note_details;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.noter.R;
import com.udacity.noter.common.base.BaseFragment;
import com.udacity.noter.common.helpers.Constants;
import com.udacity.noter.common.helpers.DateTimeHelper;
import com.udacity.noter.model.Note;

import java.util.Locale;

public class FragmentNoteDetails extends BaseFragment {
    private TextView txtTitle, txtDescription, txtDate;
    private Note currentNote;
    private Context mContext;

    public static FragmentNoteDetails newInstance(Note note) {
        FragmentNoteDetails fragment = new FragmentNoteDetails();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.BundleKeys.SELECTED_NOTE, note);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        if(getArguments() != null)
            currentNote = getArguments().getParcelable(Constants.BundleKeys.SELECTED_NOTE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_details, null);
        initializeViews(view);
        setListeners();
        loadDataToViews();
        return view;
    }

    private void loadDataToViews() {
        if (currentNote != null) {
            if (TextUtils.isEmpty(currentNote.getNoteTitle()))
                txtTitle.setText("");
            else
                txtTitle.setText(currentNote.getNoteTitle());

            if (TextUtils.isEmpty(currentNote.getNoteDescription()))
                txtDescription.setText("");
            else
                txtDescription.setText(currentNote.getNoteDescription());

            if (TextUtils.isEmpty(currentNote.getNoteCreatedAt()))
                txtDate.setText("");
            else
                txtDate.setText(DateTimeHelper.getRelativeTime(mContext, currentNote.getNoteCreatedAt(), DateTimeHelper.SERVER_FORMAT,
                        Locale.ENGLISH, DateTimeHelper.YYYY_MMM_DD));

        }
    }

    @Override
    protected void initializeViews(View v) {
        txtTitle = v.findViewById(R.id.txtTitle);
        txtDescription = v.findViewById(R.id.txtDescription);
        txtDate = v.findViewById(R.id.txtDate);
    }

    @Override
    protected void setListeners() {

    }
}
