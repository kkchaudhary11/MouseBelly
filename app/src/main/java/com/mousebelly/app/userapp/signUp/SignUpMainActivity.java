package com.mousebelly.app.userapp.signUp;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TextView;

import com.mousebelly.app.userapp.R;

@SuppressWarnings("deprecation")
public class SignUpMainActivity extends TabActivity {
    /**
     * Called when the activity is first created.
     */
    static TabHost tabHost;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // create the TabHost that will contain the Tabs

        tabHost = (TabHost) findViewById(android.R.id.tabhost);

        //tabHost.getTabWidget().getChildAt(1).setEnabled(false);


        TabHost.TabSpec tab1 = tabHost.newTabSpec("First Tab");
        TabHost.TabSpec tab2 = tabHost.newTabSpec("Second Tab");


        // Set the Tab name and Activity
        // that will be opened when particular Tab will be selected
        tab1.setIndicator("1. PROFILE");


        tab1.setContent(new Intent(this, UserPro.class));

        tab2.setIndicator("2. ADDRESS");
        tab2.setContent(new Intent(this, MapsActivity23.class));


        /** Add the tabs  to the TabHost to display. */
        tabHost.addTab(tab1);
        tabHost.addTab(tab2);

        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(Color.parseColor("#ffffff"));
            tv.setTextSize(17);
            tabHost.getTabWidget().getChildAt(i).setEnabled(false);
        }

        //tabHost.getTabWidget().getChildAt(1).setEnabled(false);
    }

    public void display() {
        tabHost.setCurrentTab(1);
    }

    public void photo() {

        tabHost.setCurrentTab(2);
    }

    public void truemap() {

        tabHost.getTabWidget().getChildAt(0).setEnabled(true);
        tabHost.getTabWidget().getChildAt(1).setEnabled(true);
    }

    public void truephoto() {

        tabHost.getTabWidget().getChildAt(0).setEnabled(true);
        tabHost.getTabWidget().getChildAt(1).setEnabled(true);
        tabHost.getTabWidget().getChildAt(2).setEnabled(true);
    }

    public void truepro() {
        tabHost.getTabWidget().getChildAt(0).setEnabled(false);
        tabHost.getTabWidget().getChildAt(1).setEnabled(false);
        tabHost.getTabWidget().getChildAt(2).setEnabled(false);
    }
}