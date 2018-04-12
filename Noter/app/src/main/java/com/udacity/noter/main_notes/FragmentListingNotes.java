package com.udacity.noter.main_notes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.udacity.noter.R;
import com.udacity.noter.add_note.ActivityAddNote;
import com.udacity.noter.common.base.BaseFragment;
import com.udacity.noter.common.helpers.Constants;
import com.udacity.noter.common.helpers.UIUtilities;
import com.udacity.noter.model.Note;

import static android.app.Activity.RESULT_OK;

public class FragmentListingNotes extends BaseFragment implements ViewListingNotes, AdapterListingNotes.OnNoteClicked {
    private static final int ADD_NOTE_REQUEST_CODE = 12;
    private Context mContext;
    private RecyclerView rvListNotes;
    private ProgressBar progressListingNotes;
    private FloatingActionButton fabAddNote;
    private PresenterListingNotes presenterListingNotes;
    private AdapterListingNotes mAdapterListingNotes;
    private ListingNotesInteraction mListingNotesInteraction;

    public static FragmentListingNotes newInstance() {
        return new FragmentListingNotes();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListingNotesInteraction = (ListingNotesInteraction) context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getContext();
        initVariables();
        presenterListingNotes.getMyNotes();

    }

    private void initVariables() {
        presenterListingNotes = new PresenterListingNotes(mContext, this);
        mAdapterListingNotes = new AdapterListingNotes(mContext, presenterListingNotes.getListNotes(), this);
        LinearLayoutManager lnrLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rvListNotes.setLayoutManager(lnrLayoutManager);
        rvListNotes.setAdapter(mAdapterListingNotes);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listing_notes, null);
        initializeViews(view);
        setListeners();
        return view;
    }

    @Override
    protected void initializeViews(View v) {
        rvListNotes = v.findViewById(R.id.rvListNotes);
        progressListingNotes = v.findViewById(R.id.progressListing);
        fabAddNote = v.findViewById(R.id.fabAddNote);
    }

    @Override
    protected void setListeners() {
        fabAddNote.setOnClickListener(fabAddNoteCLickListener);
    }

    private View.OnClickListener fabAddNoteCLickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = ActivityAddNote.createIntent(mContext);
            startActivityForResult(intent, ADD_NOTE_REQUEST_CODE);
        }
    };

    @Override
    public void onListingNotesSuccess() {
        if (mAdapterListingNotes != null)
            mAdapterListingNotes.notifyDataSetChanged();
    }

    @Override
    public void onListingNotesFails(String errorMessage) {
        UIUtilities.showMessage(mContext, errorMessage);
    }

    @Override
    public void showProgress(boolean shouldShow) {
        progressListingNotes.setVisibility(shouldShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onNoteClicked(Note note) {
        //TODO - navigate to note details
        if (mListingNotesInteraction != null)
            mListingNotesInteraction.onNoteClicked(note);
    }

    interface ListingNotesInteraction {
        void onNoteClicked(Note note);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_NOTE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if (presenterListingNotes != null && presenterListingNotes.getListNotes() != null) {
                    presenterListingNotes.getListNotes().clear();
                    presenterListingNotes.getMyNotes();
                }
            }
        }
    }
}
