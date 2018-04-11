package com.udacity.noter.add_todo;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.udacity.noter.R;
import com.udacity.noter.common.base.BaseActivity;
import com.udacity.noter.common.dbandprovider.ToDosDBController;
import com.udacity.noter.common.helpers.UIUtilities;
import com.udacity.noter.common.helpers.Utilities;
import com.udacity.noter.model.ToDo;
import com.udacity.noter.todo.ActivityToDo;

public class ActivityAddToDo extends BaseActivity {
    private EditText edtTitle;
    private Button btnAddToDo;
    private ToDosDBController toDosDBController;
    private Context mContext;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ActivityAddToDo.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Utilities.isTablet(this))
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_add_to_do);
        mContext = this;
        initializeViews();
        setListeners();
        toDosDBController = new ToDosDBController(this);
    }

    @Override
    protected void initializeViews() {
        edtTitle = findViewById(R.id.edtTitle);
        btnAddToDo = findViewById(R.id.btnAddToDo);
    }

    @Override
    protected void setListeners() {
        btnAddToDo.setOnClickListener(btnAddToDoClickListener);
    }

    private View.OnClickListener btnAddToDoClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (TextUtils.isEmpty(edtTitle.getText().toString()))
                edtTitle.setError(getString(R.string.title_empty));
            else {
                long response = toDosDBController.addNewToDo(new ToDo(edtTitle.getText().toString()));
                if (response == 1) {
                    UIUtilities.showMessage(mContext, getString(R.string.todo_added_success));
                    finish();
                } else
                    UIUtilities.showMessage(mContext, getString(R.string.something_went_wrong));
            }
        }
    };
}
