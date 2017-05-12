package com.mousebelly.app.userapp.favourite;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.mousebelly.app.userapp.R;
import com.mousebelly.app.userapp.feedback.LoadOrdersForFeedback;
import com.mousebelly.app.userapp.products.GetProductsData;

public class favMainActivity extends AppCompatActivity {

    public static LinearLayout favItem;
    public static Context favItemContext;
    ProgressBar favProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_main);

        favItemContext = favMainActivity.this;

        favItem = (LinearLayout) findViewById(R.id.favItem);

        favProgressBar = (ProgressBar) findViewById(R.id.fav_progress_bar);

        new favUnit().execute();
    }

    class favUnit extends AsyncTask<Void, Void, Void> {

        GetFavItems getFavItems;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            favProgressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected Void doInBackground(Void... voids) {

            getFavItems = new GetFavItems();
            getFavItems.getData();

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            favProgressBar.setVisibility(View.INVISIBLE);

            getFavItems.showData();
        }
    }

}
