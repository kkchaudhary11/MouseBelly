package com.mousebelly.app.userapp.Advertisment;


import android.os.AsyncTask;

import com.mousebelly.app.userapp.Server;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Kamal Kant on 19-04-2017.
 */

public class LoadAd extends AsyncTask<Void, Void, Void> {

    public static ArrayList<Advertisment> Ad = new ArrayList<>();

    public boolean status = false;

    @Override
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


        status = true;


        return null;
    }


}
