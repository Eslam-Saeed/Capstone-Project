package com.udacity.noter.common.helpers;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.view.inputmethod.InputMethodManager;


/**
 * Created by Eslam.Mahmoud on 10/12/2017.
 */

public class Utilities {

    public static void hideSoftKeyboard(Context context) {
        if (context != null) {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) context.getSystemService(
                            Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(
                    ((Activity) context).getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return model;
        }
        return manufacturer.concat(" ").concat(model);
    }

}
