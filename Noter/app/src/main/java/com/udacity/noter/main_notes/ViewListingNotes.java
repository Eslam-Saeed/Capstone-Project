package com.udacity.noter.main_notes;

import com.udacity.noter.common.base.BaseView;

public interface ViewListingNotes extends BaseView {

    void onListingNotesSuccess();

    void onListingNotesFails(String errorMessage);
}
