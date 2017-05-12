package com.mousebelly.app.userapp.preOrderStack;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mousebelly.app.userapp.CurrentContext;
import com.mousebelly.app.userapp.R;
import com.mousebelly.app.userapp.Server;
import com.mousebelly.app.userapp.SocketAccess;
import com.mousebelly.app.userapp.ZoomOutPageTransformer;

import java.util.ArrayList;
import java.util.List;

import static com.mousebelly.app.userapp.Login.LoginActivity.USERID;

public class PreOrderMainActivity extends ActionBarActivity {

    public static LinearLayout orderStackProductsDataLayout;
    public static HorizontalScrollView orderStackHorizontalScrollView;
    public static ViewPager orderStackViewPager;
    public static int displayWidth;
    public static int currX = 0;
    public static int beginX;
    public static List<Fragment> fList = new ArrayList<>();
    static Context context;
    MyPageAdapter myPageAdapter;

    ProgressBar PreOrderProgressBar;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pre_order_activity_main);

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        PreOrderProgressBar = (ProgressBar) findViewById(R.id.Pre_Order_Progress_Bar);

        CurrentContext.context = PreOrderMainActivity.this;
        /////////////////////


        // Update the action bar title with the TypefaceSpan instance
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(actionBar.getDisplayOptions()
                | ActionBar.DISPLAY_SHOW_CUSTOM);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT, Gravity.LEFT
                | Gravity.CENTER_VERTICAL);
//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        ColorDrawable white = new ColorDrawable(this.getResources().getColor(R.color.white));
//        actionBar.setBackgroundDrawable(white);
//        actionBar.setTitle(s);

        actionBar.setTitle("");

        ActionBar.LayoutParams titleLayout = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.WRAP_CONTENT);

        Typeface trumpitFaceHeader = Typeface.createFromAsset(getAssets(), "fonts/Trumpit.ttf");

        LinearLayout mainTitleLayout = new LinearLayout(PreOrderMainActivity.this);
        mainTitleLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        mainTitleLayout.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout maintitle = new LinearLayout(PreOrderMainActivity.this);
        maintitle.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            maintitle.setClipToOutline(true);
        }
        maintitle.setBackgroundResource(R.drawable.rounded_layer);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            maintitle.setElevation(2.0f);
        }
        mainTitleLayout.addView(maintitle);

        maintitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go yo home activity
                navigateUpTo(getIntent());
            }
        });


        TextView actionBarText = new TextView(PreOrderMainActivity.this);
        actionBarText.setTextColor(Color.parseColor("#AAC706"));
        actionBarText.setPadding(20, 5, 0, 6);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            actionBarText.setElevation(4.0f);
        }
        actionBarText.setText("Mouse");
        //actionBarText.setBackgroundColor(Color.parseColor("#ffffff"));
        actionBarText.setTextSize(25);
        actionBarText.setTypeface(trumpitFaceHeader, Typeface.BOLD);

        actionBarText.setLayoutParams(titleLayout);
        maintitle.addView(actionBarText);

        TextView actionBarText1 = new TextView(PreOrderMainActivity.this);
        actionBarText1.setTextColor(Color.parseColor("#800000"));
        actionBarText1.setPadding(0, 5, 20, 5);
        actionBarText1.setText(" - Belly");
        //actionBarText1.setBackgroundColor(Color.parseColor("#ffffff"));
        actionBarText1.setTextSize(25);
        actionBarText1.setTypeface(trumpitFaceHeader, Typeface.BOLD);
        maintitle.addView(actionBarText1);


        LinearLayout nevLayout = new LinearLayout(PreOrderMainActivity.this);
        nevLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        nevLayout.setGravity(Gravity.RIGHT);
        //nevLayout.setOrientation(LinearLayout.HORIZONTAL);

        mainTitleLayout.addView(nevLayout);

        ImageView leftButton = new ImageView(PreOrderMainActivity.this);
        leftButton.setImageResource(R.drawable.left_arrow);
        leftButton.setOnClickListener(new View.OnClickListener() {
            public Context getActivity() {
                return PreOrderMainActivity.this;
            }

            @Override
            public void onClick(View view) {
                orderStackViewPager.setCurrentItem(getItem(-1), true); //getItem(-1) for previous
            }
        });

        ImageView rightButton = new ImageView(PreOrderMainActivity.this);
        rightButton.setImageResource(R.drawable.right_arrow);
        rightButton.setOnClickListener(new View.OnClickListener() {
            public Context getActivity() {
                return PreOrderMainActivity.this;
            }

            @Override
            public void onClick(View view) {
                orderStackViewPager.setCurrentItem(getItem(+1), true); //getItem(-1) for previous
            }
        });


        nevLayout.addView(leftButton);
        nevLayout.addView(rightButton);


        actionBar.setCustomView(mainTitleLayout);


        ////////////////////


        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        displayWidth = width;

        PreOrderMainActivity.context = PreOrderMainActivity.this;


        LoadPreOrders loadPreOrders = new LoadPreOrders();
        loadPreOrders.execute();

       /* String respOrderStack = Server.s.get("http://mousebelly.herokuapp.com/preorder/preorderbymail/" + USERID);
        System.out.println("Order Stack : ");
        System.out.println(respOrderStack);

        PreOrderStack preOrderStack = new PreOrderStack();

        preOrderStack.loadData(respOrderStack);

        while (SocketAccess.connected == false) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/

        /*
        UserLoginTask userLoginTask = new UserLoginTask();
        userLoginTask.execute();
*/


     /*   System.out.println("FLIST:");
        System.out.println(PreOrderMainActivity.fList);

        myPageAdapter = new MyPageAdapter(getSupportFragmentManager(), PreOrderMainActivity.fList);

        orderStackViewPager = (ViewPager) findViewById(R.id.OrderStackViewPager);
        orderStackViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        orderStackViewPager.setAdapter(myPageAdapter);*/

    }


    public class LoadPreOrders extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            PreOrderProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            String respOrderStack = Server.s.get("http://mousebelly.herokuapp.com/preorder/preorderbymail/" + USERID);
            System.out.println("Order Stack : ");
            System.out.println(respOrderStack);

            PreOrderStack preOrderStack = new PreOrderStack();

            preOrderStack.loadData(respOrderStack);

            while (SocketAccess.connected == false) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            System.out.println("FLIST:");
            System.out.println(PreOrderMainActivity.fList);

            myPageAdapter = new MyPageAdapter(getSupportFragmentManager(), PreOrderMainActivity.fList);

            orderStackViewPager = (ViewPager) findViewById(R.id.OrderStackViewPager);
            orderStackViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
            orderStackViewPager.setAdapter(myPageAdapter);

            PreOrderProgressBar.setVisibility(View.GONE);


        }

    }

    private int getItem(int i) {
        return orderStackViewPager.getCurrentItem() + i;
    }

    private class MyPageAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments;

        public MyPageAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return this.fragments.get(position);
        }

        @Override
        public int getCount() {
            return this.fragments.size();
        }
    }
}
