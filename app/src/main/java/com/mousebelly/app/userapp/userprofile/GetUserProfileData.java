package com.mousebelly.app.userapp.userprofile;

import android.content.Context;
import android.os.AsyncTask;

import com.mousebelly.app.userapp.Login.LoginActivity;
import com.mousebelly.app.userapp.Server;
import com.roger.catloadinglibrary.CatLoadingView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by adity on 10-Apr-17.
 */

public class GetUserProfileData extends AsyncTask<Void, Void, Void> {
    static UserprofileUserBean userprofileUserBean = new UserprofileUserBean();
    static ArrayList<UserprofileUserBean> userprofileUserBeanArray = new ArrayList<>();
    public Boolean status = false;
    Context mcontext;
    CatLoadingView mView;
    String Email = LoginActivity.USERID;
    String jsonstr;
    String uname;

    GetUserProfileData(Context context, CatLoadingView mView) {
        mcontext = context;
        this.mView = mView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {


        jsonstr = Server.s.get("http://mousebelly.herokuapp.com/sign/sign/" + Email);
        System.out.println("This is Json str::::::::::::" + jsonstr);

        if (jsonstr != null) {

            try {
                status = false;
                JSONArray jaar = new JSONArray(jsonstr);
                for (int i = 0; i < jaar.length(); i++) {

                    JSONObject jsonObject = jaar.getJSONObject(i);
                    userprofileUserBean.validateUsername(jsonObject.getString("U_name"));
                    userprofileUserBean.validateEmail(jsonObject.getString("UserEmail_id"));
                    userprofileUserBean.validatePassword(jsonObject.getString("Pwd"));
                    userprofileUserBean.validatePhone(jsonObject.getString("Phone_No"));

                    JSONArray jsonArray = jsonObject.getJSONArray("Address");
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    userprofileUserBean.setAddressStreet(jsonObject1.getString("street_name"));
                    userprofileUserBean.setAddressCity(jsonObject1.getString("city_name"));
                    userprofileUserBean.setAddressState(jsonObject1.getString("state_name"));
                    userprofileUserBean.setAddressZipcode(jsonObject1.getString("zip_code"));
                    userprofileUserBean.setAddressCountry(jsonObject1.getString("country"));

                    JSONArray jsonArray2 = jsonObject.getJSONArray("Cordinates");
                    JSONObject jsonObject2 = jsonArray2.getJSONObject(i);
                    userprofileUserBean.setLatitude(jsonObject2.getString("lat"));
                    userprofileUserBean.setLongitude(jsonObject2.getString("long"));

                    userprofileUserBeanArray.add(userprofileUserBean);

                    System.out.println("THis is BEan:::::::::::::::::::::::" + userprofileUserBean);
                }

                status = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        try {
            super.onPreExecute();
            //  mView.dismiss();

            String user = userprofileUserBean.getUserName();
            String eml = userprofileUserBean.getEmail();
            String phn = userprofileUserBean.getPhone();

            System.out.println("This user details::::::::::::" + user + ":::::::::" + eml + ":::::::::" + phn + "::::::" + uname);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
