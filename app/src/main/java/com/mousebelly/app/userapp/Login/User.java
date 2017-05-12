package com.mousebelly.app.userapp.Login;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by adity on 07-Feb-17.
 */


public class User {

    private String _id;
    private String UserEmail_id;
    private String U_name;
    private String Pwd;
    private String Phone_No;
    private String Image;
    private String isActivated;
    private String __v;

    private String lat;
    private String longitude;

    private String street_name;
    private String zip_code;
    private String state_name;
    private String wallet;
    private String country;
    private String city_name;

    private ArrayList<String> fav = new ArrayList<>();

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public String getStreet_name() {
        return street_name;
    }

    public void setStreet_name(String street_name) {
        this.street_name = street_name;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public ArrayList<String> getFav() {
        return fav;
    }

    public void setFav(String food) {
        this.fav.add(food);
    }

    //    List<Coordinates> coord = new ArrayList<>();
//    List<Address> add = new ArrayList<>();
//
//    public List<Address> getAdd() {
//        return add;
//    }
//
//    public void setAdd(List<Address> add) {
//        this.add = add;
//    }
//
//
//
//    public List<Coordinates> getCoord() {
//        return coord;
//    }
//
//    public void setCoord(List<Coordinates> coord) {
//        this.coord = coord;
//    }

    public String get__v() {
        return __v;
    }

    public void set__v(String __v) {
        this.__v = __v;
    }

    public String getIsActivated() {
        return isActivated;
    }

    public void setIsActivated(String isActivated) {
        this.isActivated = isActivated;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getPhone_No() {
        return Phone_No;
    }

    public void setPhone_No(String phone_No) {
        Phone_No = phone_No;
    }

    public String getPwd() {
        return Pwd;
    }

    public void setPwd(String pwd) {
        Pwd = pwd;
    }

    public String getU_name() {
        return U_name;
    }

    public void setU_name(String u_name) {
        U_name = u_name;
    }

    public String getUserEmail_id() {
        return UserEmail_id;
    }

    public void setUserEmail_id(String userEmail_id) {
        UserEmail_id = userEmail_id;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;

    }

    JSONObject tojson() {

        JSONObject jobj = new JSONObject();


        //---------------------------------------------------
//        var useremail = req.body.email;
//        var uname = req.body.userName;
//        var pass = createHash(req.body.pwd);
//        var phone = req.body.phn;
//        var street = req.body.srtn;
//        var zip = req.body.zipcode;
//        var statename = req.body.state;
//        var cityname = req.body.city;
//        var cnt = req.body.country;
//        var lt = req.body.lat;
//        var lg = req.body.lng;

        //---------------------------------------------

        try {
            jobj.put("email", this.getUserEmail_id());
            jobj.put("userName", this.getU_name());
            jobj.put("pwd", this.getPwd());
            jobj.put("phn", this.getPhone_No());
            jobj.put("city", this.getCity_name());
            jobj.put("srtn", this.getStreet_name());
            jobj.put("state", this.getState_name());
            jobj.put("zipcode", this.getZip_code());
            jobj.put("country", this.getCountry());
            jobj.put("lat", this.getLat());
            jobj.put("lng", this.getLongitude());

//            jobj.put("srtn", this.getSt);
//            jobj.put("zipcode", this.getIsActivated());

//------------------Creating json Array to send data in Array--------------//
//            JSONArray jarr = new JSONArray();
//
//            for( Coordinates c : this.getCoord() )
//            {
//                jarr.put(c.tojson());
//            }
//
//            jobj.put("Coordinates", jarr);
//            jarr = new JSONArray();
//
//
////------  For Each Object( Address) that belong to getAdd. Start loop, putting in JSON Array object by converting in json-----
//            for( Address a : this.getAdd() )
//            {
//                jarr.put(a.tojson());
//            }
//
//            jobj.put("Address", jarr);
//
//       //     jarr = new JSONArray();
//-------------------------------------------------------------------------------------------------//


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jobj;
    }


}


