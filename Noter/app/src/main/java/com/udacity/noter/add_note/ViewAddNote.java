package com.udacity.noter.add_note;

import com.udacity.noter.common.base.BaseView;

public interface ViewAddNote extends BaseView {

    void onAddNoteSuccess();

    void onAddNoteFails(String errorMessage);

}
