package com.mousebelly.app.userapp.Advertisment;

import android.app.Dialog;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.mousebelly.app.userapp.CurrentContext;
import com.mousebelly.app.userapp.R;

import java.util.Timer;
import java.util.TimerTask;

public class AdMainActivity {

    Dialog AdDialog;
    ViewPager mViewPager;
    Timer adTimer;
    Timer pageTimer;
    int page = 0;

    // public static ArrayList<Advertisment> Ad = new ArrayList<>();

    public void startAd() {
        System.out.println("AdMain Activity Called");
        //super.onCreate(savedInstanceState);
        // setContentView(R.layout.ad_activity_main);


        LoadAd loadAd = new LoadAd();
        loadAd.execute();

        int gpdWaitCount = 0;
        while (gpdWaitCount < 50) {
            if (loadAd.status) break;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            gpdWaitCount++;
        }

       /* if(gpdWaitCount == 10) {
            Toast.makeText(AdMainActivity.this, "Unable to Fetch Products Data. Try again later.", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(AdMainActivity.this, "Fetched", Toast.LENGTH_LONG).show();
        }*/


        //show dialog
        AdDialog = new Dialog(CurrentContext.context);
        AdDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        AdDialog.setContentView(R.layout.ad_dialog_box);


        //AdDialog.setTitle("Ad");

        AdDialog.setCancelable(false);


        Button close = (Button) AdDialog.findViewById(R.id.close);

        //add viewpager to dialog

        mViewPager = (ViewPager) AdDialog.findViewById(R.id.AdviewPageAndroid);
        final DisplayAd adapterView = new DisplayAd(CurrentContext.context);
        mViewPager.setAdapter(adapterView);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdDialog.dismiss();
                pageTimer.cancel();
                pageTimer.purge();
                LoadAd.Ad.clear();
                LoadAd loadAd = new LoadAd();
                loadAd.execute();
                showAd(60);
            }
        });

        AdDialog.show();
        pageSwitcher(3);

    }

    public void pageSwitcher(int seconds) {
        pageTimer = new Timer(); // At this line a new Thread will be created
        pageTimer.scheduleAtFixedRate(new changeTask(), 0, seconds * 1000); // delay
        // in
        // milliseconds
    }

    public void showAd(int seconds) {
        adTimer = new Timer(); // At this line a new Thread will be created
        // adTimer.scheduleAtFixedRate(new AdRemindTask(), 0, seconds * 1000); // delay
        adTimer.schedule(new AdRemindTask(), seconds * 1000);

        // in
        // milliseconds
    }

   /* @Override
    protected Void doInBackground(Void... voids) {

        try {

            String resp = Server.s.get("http://mousebelly.herokuapp.com/ad/getadvertise");


            JSONArray jsonArray = new JSONArray(resp);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject adData = jsonArray.getJSONObject(i);

                Advertisment advertisment = new Advertisment();

                String Image = adData.getString("Image");

                advertisment.setImageUrl(Image);
                advertisment.setImgBmp(Image);

                Ad.add(advertisment);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Ad Array : " + Ad);


        return null;
    }


    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);


        AdDialog = new Dialog(CurrentContext.context);
        AdDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        AdDialog.setContentView(R.layout.ad_dialog_box);


        //AdDialog.setTitle("Ad");

        AdDialog.setCancelable(false);

        mViewPager = (ViewPager) AdDialog.findViewById(R.id.AdviewPageAndroid);
        final DisplayAd adapterView = new DisplayAd(CurrentContext.context);
        mViewPager.setAdapter(adapterView);

        Button close = (Button) AdDialog.findViewById(R.id.close);

        //add viewpager to dialog


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdDialog.dismiss();
                pageTimer.cancel();
                pageTimer.purge();
                LoadAd.Ad.clear();
                LoadAd loadAd = new LoadAd();
                loadAd.execute();
                showAd(60);
            }
        });

        AdDialog.show();
        pageSwitcher(3);
    }

*/


    class changeTask extends TimerTask {

        @Override
        public void run() {
            // As the TimerTask run on a seprate thread from UI thread we have
            // to call runOnUiThread to do work on UI thread.
            try {

                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (page > LoadAd.Ad.size()) { // In my case the number of pages are 5
                            page = 0;
                            mViewPager.setCurrentItem(page);
                            // Showing a toast for just testing purpose
                            // Toast.makeText(getApplicationContext(), "Timer stoped",Toast.LENGTH_LONG).show();
                        } else {
                            mViewPager.setCurrentItem(page++);
                        }
                    }
                }, 100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class AdRemindTask extends TimerTask {

        @Override
        public void run() {
            // As the TimerTask run on a seprate thread from UI thread we have
            // to call runOnUiThread to do work on UI thread.
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    AdDialog.show();
                    pageSwitcher(5);

                }
            }, 100);

        }
    }


}
