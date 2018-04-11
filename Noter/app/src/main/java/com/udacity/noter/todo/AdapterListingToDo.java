package com.udacity.noter.todo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.noter.R;
import com.udacity.noter.common.helpers.DateTimeHelper;
import com.udacity.noter.model.ToDo;

import java.util.ArrayList;
import java.util.Locale;

public class AdapterListingToDo extends RecyclerView.Adapter<AdapterListingToDo.MyViewHolder> {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<ToDo> listToDos;

    public AdapterListingToDo(Context context, ArrayList<ToDo> listToDos) {
        mContext = context;
        this.mLayoutInflater = LayoutInflater.from(mContext);
        this.listToDos = listToDos;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.row_list_todo, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ToDo toDo = listToDos.get(position);
        if (toDo != null) {
            if (TextUtils.isEmpty(toDo.getTitle()))
                holder.txtTitle.setText("");
            else
                holder.txtTitle.setText(toDo.getTitle());

            if (TextUtils.isEmpty(toDo.getCreatedAt()))
                holder.txtDate.setText("");
            else
                holder.txtDate.setText(DateTimeHelper.getRelativeTime(mContext, toDo.getCreatedAt(), DateTimeHelper.SERVER_FORMAT,
                        Locale.ENGLISH, DateTimeHelper.YYYY_MMM_DD));
        }
    }

    @Override
    public int getItemCount() {
        if (listToDos == null)
            return 0;
        return listToDos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle, txtDate;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDate = itemView.findViewById(R.id.txtDate);
        }
    }
}
