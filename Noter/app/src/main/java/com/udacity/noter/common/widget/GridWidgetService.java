package com.udacity.noter.common.widget;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.udacity.noter.R;
import com.udacity.noter.common.dbandprovider.ToDosDBController;
import com.udacity.noter.model.ToDo;

import java.util.ArrayList;


public class GridWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewsFactory(getApplicationContext());
    }
}


class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;
    private ArrayList<ToDo> listToDos;
    private ToDosDBController toDoDBController;

    GridRemoteViewsFactory(Context context) {
        this.mContext = context;
        toDoDBController = new ToDosDBController(mContext);

    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        listToDos = toDoDBController.getMyNotesForWidget();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (listToDos == null)
            return 0;
        else
            return listToDos.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        ToDo todo = listToDos.get(position);
        if (todo != null && !TextUtils.isEmpty(todo.getTitle()))
            remoteViews.setTextViewText(R.id.txtItemWidget, todo.getTitle());

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}

