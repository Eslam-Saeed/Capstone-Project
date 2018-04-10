package com.udacity.noter.main_notes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.noter.R;
import com.udacity.noter.common.helpers.DateTimeHelper;
import com.udacity.noter.model.Note;

import java.util.ArrayList;
import java.util.Locale;

public class AdapterListingNotes extends RecyclerView.Adapter<AdapterListingNotes.MyViewHolder> {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<Note> listNotes;
    private OnNoteClicked mOnNoteClicked;

    public AdapterListingNotes(Context context, ArrayList<Note> listNotes, OnNoteClicked onNoteClicked) {
        mContext = context;
        this.mLayoutInflater = LayoutInflater.from(mContext);
        this.listNotes = listNotes;
        mOnNoteClicked = onNoteClicked;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.row_listing_note, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Note currentNote = listNotes.get(position);
        if (currentNote != null) {
            if (TextUtils.isEmpty(currentNote.getNoteTitle()))
                holder.txtTitle.setText("");
            else
                holder.txtTitle.setText(currentNote.getNoteTitle());

            if (TextUtils.isEmpty(currentNote.getNoteDescription()))
                holder.txtDescription.setText("");
            else
                holder.txtDescription.setText(currentNote.getNoteDescription());

            if (TextUtils.isEmpty(currentNote.getNoteCreatedAt()))
                holder.txtDate.setText("");
            else
                holder.txtDate.setText(DateTimeHelper.getRelativeTime(mContext, currentNote.getNoteCreatedAt(), DateTimeHelper.SERVER_FORMAT,
                        Locale.ENGLISH, DateTimeHelper.YYYY_MMM_DD));

            holder.setListeners(currentNote);
        }
    }

    @Override
    public int getItemCount() {
        if (listNotes == null)
            return 0;
        return listNotes.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle, txtDescription, txtDate;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            txtDate = itemView.findViewById(R.id.txtDate);
        }

        void setListeners(final Note note) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnNoteClicked != null)
                        mOnNoteClicked.onNoteClicked(note);
                }
            });
        }
    }

    interface OnNoteClicked {
        void onNoteClicked(Note note);
    }
}
