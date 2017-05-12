package com.mousebelly.app.userapp.favourite;

import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.mousebelly.app.userapp.Login.LoginActivity;
import com.mousebelly.app.userapp.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;


/**
 * Created by Kamal Kant on 12-05-2017.
 */

public class GetFavItems {

    public static HashMap<String, favItem> favouriteItems = new HashMap<>();

    public void getData() {

        ArrayList<String> favItems = LoginActivity.user.getFav();

        for (String favItem : favItems) {

            String resp = Server.s.get("http://mousebelly.herokuapp.com/prod_approval/prod_approval11/" + favItem);

            try {
                JSONArray jsonArray = new JSONArray(resp);
                JSONObject jsonObject = jsonArray.getJSONObject(0);

                favItem favItemobj = new favItem();
                favItemobj.setProd_name(jsonObject.getString("Prod_name"));
                favItemobj.setProd_category(jsonObject.getString("Prod_category"));
                favItemobj.setDescription(jsonObject.getString("Description"));
                favItemobj.setPrice(jsonObject.getString("Price"));
                favItemobj.setProd_id(jsonObject.getString("Prod_id"));
                favItemobj.setImage(jsonObject.getString("Image"));
                favItemobj.setStarsize(jsonObject.getString("starsize"));
                JSONObject hwfname = jsonObject.getJSONObject("HWFEmail_id");
                favItemobj.setHWF_Name(hwfname.getString("HWF_Name"));

                favouriteItems.put(favItemobj.getProd_id(), favItemobj);


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }

    public void showData() {

        Iterator it = favouriteItems.keySet().iterator();

        while (it.hasNext()) {
            String key = it.next().toString();
            favItem favItem = new favItem();
            favItem f = favouriteItems.get(key);

            RelativeLayout rl = favItem.draw(f);

            favMainActivity.favItem.addView(rl);


        }

    }


}
