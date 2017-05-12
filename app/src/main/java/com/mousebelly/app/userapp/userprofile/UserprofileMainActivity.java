package com.mousebelly.app.userapp.userprofile;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import com.mousebelly.app.userapp.R;

@SuppressWarnings("deprecation")
public class UserprofileMainActivity extends TabActivity {
    static TabHost tabHost;
    /**
     * Called when the activity is first created.
     */
    UserprofileUserBean check = new UserprofileUserBean();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userprofileactivity_main);


        // create the TabHost that will contain the Tabs

        tabHost = (TabHost) findViewById(android.R.id.tabhost);

        //tabHost.getTabWidget().getChildAt(1).setEnabled(false);


        TabHost.TabSpec tab1 = tabHost.newTabSpec("First Tab");
        TabHost.TabSpec tab2 = tabHost.newTabSpec("Second Tab");
        TabHost.TabSpec tab3 = tabHost.newTabSpec("Third tab");


        // Set the Tab name and Activity
        // that will be opened when particular Tab will be selected
        tab1.setIndicator("1. Profile");
        tab1.setContent(new Intent(this, UserprofileUserPro.class));

        tab2.setIndicator("2. Address");
        tab2.setContent(new Intent(this, UserprofileMapsActivity.class));


        tab3.setIndicator("3. Upload Picture");
        tab3.setContent(new Intent(this, UserprofileUploadphoto.class));

        /** Add the tabs  to the TabHost to display. */
        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
        tabHost.addTab(tab3);

        tabHost.getTabWidget().getChildAt(0).setEnabled(false);
        tabHost.getTabWidget().getChildAt(1).setEnabled(false);
        tabHost.getTabWidget().getChildAt(2).setEnabled(false);
        //tabHost.getTabWidget().getChildAt(1).setEnabled(false);
        // tabHost.getTabWidget().getChildAt(2).setVisibility(View.GONE);


    }

    public void display() {

        tabHost.setCurrentTab(1);
    }

    public void photo() {

        tabHost.setCurrentTab(2);
    }
}