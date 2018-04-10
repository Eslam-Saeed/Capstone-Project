package com.udacity.noter.common.helpers;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by eslam on 2/15/18.
 */

public class UIUtilities {

    public static void showMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
