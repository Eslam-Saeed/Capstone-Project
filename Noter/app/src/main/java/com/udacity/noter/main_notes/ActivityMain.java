package com.udacity.noter.main_notes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.udacity.noter.R;
import com.udacity.noter.common.base.BaseActivity;

public class ActivityMain extends BaseActivity {
    private Toolbar toolbarMainNotes;
    private FirebaseAnalytics mFirebaseAnalytics;
    private AdView mAdView;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ActivityMain.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        initializeViews();
        setListeners();
        setToolbar(toolbarMainNotes, getString(R.string.app_name), true, true);
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
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
