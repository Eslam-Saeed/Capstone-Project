package com.udacity.noter.common.base;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


import com.udacity.noter.R;
import com.udacity.noter.common.helpers.LocalizationHelper;

import java.util.HashMap;


public abstract class BaseActivity extends AppCompatActivity {

    private Toolbar myToolbar;

    protected void setToolbar(Toolbar toolbar, String title, boolean showUpButton, boolean withElevation) {
        myToolbar = toolbar;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && withElevation) {
            toolbar.setElevation(getResources().getDimension(R.dimen.padding_small));
        }
        if (myToolbar != null) {
            myToolbar.setTitle(title);
            setSupportActionBar(myToolbar);
        }
        if (showUpButton) {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    protected void setToolbarColor(Toolbar toolbar, int color) {
        toolbar.setBackgroundColor(color);
    }

    public void setToolbarTitle(String title) {
        if (myToolbar != null)
            myToolbar.setTitle(title);
    }

    public void setToolbarSubTitle(String subTitle) {
        if (myToolbar != null) {
            myToolbar.setSubtitle(subTitle);
        }

    }

    public void navigateToThisActivity(Class activity, HashMap<String, String> putExtras, boolean finishIt) {
        Intent intent = new Intent(this, activity);
        if (putExtras != null && putExtras.size() > 0) {
            for (HashMap.Entry<String, String> entry : putExtras.entrySet()) {
                intent.putExtra(entry.getKey(), entry.getValue());
            }
        }
        startActivity(intent);
        if (finishIt)
            finish();
    }

    protected void setToolbarNavigationIcon(Toolbar toolbar, int iconId) {
        toolbar.setNavigationIcon(iconId);
    }

    protected void replaceFragment(BaseFragment fragment, int containerId, boolean addToBackStack) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (addToBackStack)
            fragmentTransaction.replace(containerId, fragment).addToBackStack("").commit();
        else
            fragmentTransaction.replace(containerId, fragment).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalizationHelper.changeAppLanguage(LocalizationHelper.getLanguage(this), this);
    }

    protected abstract void initializeViews();

    protected abstract void setListeners();

}
