package com.udacity.noter.common.helpers;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by Eslam.Mahmoud on 11/16/2017.
 */

public class SpacingItemDecoration extends RecyclerView.ItemDecoration {
    private int spacing;
    private Context context;

    public SpacingItemDecoration(Context context, int padding) {
        this.context = context;
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        spacing = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, padding, metrics);
    }

    @Override
    public final void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        if (position != 0) {
            outRect.left = spacing;
            outRect.right = spacing;
        } else {
            if (LocalizationHelper.getLanguage(context).equals(LocalizationHelper.LOCALE_ENGLISH))
                outRect.right = spacing;
            else
                outRect.left = spacing;
        }
    }
}