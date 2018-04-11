package com.udacity.noter.todo;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.udacity.noter.R;
import com.udacity.noter.add_todo.ActivityAddToDo;
import com.udacity.noter.common.base.BaseActivity;
import com.udacity.noter.common.dbandprovider.ToDosDBController;
import com.udacity.noter.common.helpers.Utilities;
import com.udacity.noter.model.ToDo;

import java.util.ArrayList;

public class ActivityToDo extends BaseActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private Toolbar mToolbarToDo;
    private RecyclerView rvTodo;
    private FloatingActionButton fabAddToDo;
    private AdapterListingToDo adapterListingToDo;
    private ArrayList<ToDo> listToDos = new ArrayList<>();
    private ToDosDBController toDoDbController;
    private Context mContext;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ActivityToDo.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Utilities.isTablet(this))
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_to_do);
        mContext = this;
        initializeViews();
        setListeners();
        setToolbar(mToolbarToDo, getString(R.string.todo), true, true);
        toDoDbController = new ToDosDBController(this);
        getLoaderManager().initLoader(0, null, this);
    }

    private void initializeVariables() {
        adapterListingToDo = new AdapterListingToDo(this, listToDos);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvTodo.setAdapter(adapterListingToDo);
        rvTodo.setLayoutManager(layoutManager);
    }

    @Override
    protected void initializeViews() {
        mToolbarToDo = findViewById(R.id.toolbarToDo);
        rvTodo = findViewById(R.id.rvToDo);
        fabAddToDo = findViewById(R.id.fabAddToDo);
    }

    @Override
    protected void setListeners() {
        fabAddToDo.setOnClickListener(fabAddToDoClickListener);
    }

    private View.OnClickListener fabAddToDoClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ActivityAddToDo.startActivity(mContext);
        }
    };

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return toDoDbController.getMyToDos();
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor != null) {
            ToDo tempToDo;
            ArrayList<ToDo> tempListToDos = new ArrayList<>();
            while (cursor.moveToNext()) {
                tempToDo = new ToDo();
                tempToDo.setId(cursor.getString(0));
                tempToDo.setTitle(cursor.getString(1));
                tempToDo.setCreatedAt(cursor.getString(2));
                tempListToDos.add(tempToDo);
            }
            listToDos.addAll(tempListToDos);
            cursor.close();
            initializeVariables();
            adapterListingToDo.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        rvTodo.setAdapter(null);
    }
}
