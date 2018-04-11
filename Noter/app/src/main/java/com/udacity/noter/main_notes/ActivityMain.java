package com.udacity.noter.main_notes;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.udacity.noter.R;
import com.udacity.noter.common.base.BaseActivity;
import com.udacity.noter.common.helpers.Utilities;
import com.udacity.noter.model.Note;
import com.udacity.noter.note_details.FragmentNoteDetails;
import com.udacity.noter.todo.ActivityToDo;

public class ActivityMain extends BaseActivity implements FragmentListingNotes.ListingNotesInteraction {
    private Toolbar toolbarMainNotes;
    private FirebaseAnalytics mFirebaseAnalytics;
    private AdView mAdView;
    private boolean isTablet;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ActivityMain.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isTablet = Utilities.isTablet(this);
        if (isTablet)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        initializeViews();
        setListeners();
        setToolbar(toolbarMainNotes, getString(R.string.app_name), true, true);
        if (savedInstanceState == null)
            replaceFragment(FragmentListingNotes.newInstance(), R.id.frameListingNotes, false);
    }

    @Override
    protected void initializeViews() {
        toolbarMainNotes = findViewById(R.id.toolbarMain);
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(getString(R.string.banner_ad_unit_id)).build();
        mAdView.loadAd(adRequest);
    }

    @Override
    protected void setListeners() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_todo:
                ActivityToDo.startActivity(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onNoteClicked(Note note) {
        if (isTablet) {
            replaceFragment(FragmentNoteDetails.newInstance(note), R.id.frameNoteDetails, false);
        } else {
            replaceFragment(FragmentNoteDetails.newInstance(note), R.id.frameListingNotes, true);
        }
    }
}
