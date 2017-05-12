package com.mousebelly.app.userapp.userprofile;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

/**
 * Created by adity on 05-Apr-17.
 */
public class UserprofileUserBean {

    private String userName;
    private String email;
    private String phone;
    private String addressStreet;
    private String addressCity;
    private String addressState;
    private String addressZipcode;
    private String latitude;
    private String longitude;
    private String password;
    private String cnfrmPassword;
    private String addressCountry;

    //getters and setters

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;

    }

    public String validateUsername(String userName) {

        if (userName == null || userName.length() == 0)
            return "Username Cannot be empty";


        if (!Pattern.compile("^[A-Za-z]+.*$").matcher(userName).matches())
            return "Username must begin with character";

        //For Special Characters

        if (!Pattern.compile("^[A-Za-z0-9\\s]+$").matcher(userName).matches())
            return "Username cannot contain special characters";

        //[^A-Za-z0-9]
        this.userName = userName;

        return "Ok";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;

    }

    public String validateEmail(String email) {
        if (email == null || email.length() == 0)
            return "Email cannot be empty";

        if (!Pattern.compile("^\\S+@[A-Za-z]+\\.[A-Za-z]+\\.*[A-Za-z]+$").matcher(email).matches())
            return "Enter valid email";

        this.email = email;

        return "Ok";
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String validatePhone(String phone) {
        if (phone == null || phone.length() == 0)
            return "Phone no. cannot be empty";

        if (!Pattern.compile("^[789][0-9]{9,9}$").matcher(phone).matches())
            return "Enter a Valid Indian Mobile Number.";

        this.phone = phone;

        return "Ok";
    }

    public String getAddressStreet() {
        return addressStreet;
    }


    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    public String validateAddressStreet(String addressStreet) {
        if (addressStreet == null || addressStreet.length() == 0)
            return "Street cannot be empty";

        if (Pattern.compile("[^A-Za-z0-9]").matcher(addressStreet).matches())
            return "Enter valid Street";

        this.addressStreet = addressStreet;

        return "Ok";
    }


    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String validateAddressCity(String addressCity) {

        System.out.println(addressCity.length());

        if (addressCity == null || addressCity.length() == 0) {
            System.out.println("city empty");
            return "City cannot be empty";
        }


        if (!Pattern.compile("[A-Za-z\\s]+").matcher(addressCity).matches())
            return "Enter valid City";

        this.addressCity = addressCity;

        return "Ok";
    }

    public String getAddressState() {
        return addressState;
    }

    public void setAddressState(String addressState) {
        this.addressState = addressState;
    }

    public String validateAddressState(String addressState) {
        if (addressState == null || addressState.length() == 0)
            return "State cannot be empty";

        if (!Pattern.compile("[A-Za-z\\s]{3,30}+").matcher(addressState).matches())
            return "Enter valid State";

        this.addressState = addressState;

        return "Ok";
    }


    public String getAddressZipcode() {
        return addressZipcode;
    }

    public void setAddressZipcode(String addressZipcode) {
        this.addressZipcode = addressZipcode;
    }

    public String validateAddressZipcode(String addressZipcode) {
        if (addressZipcode == null || addressZipcode.length() == 0)
            return "Zipcode cannot be empty";

        if (!Pattern.compile("^[0-9]{6,6}$").matcher(addressZipcode).matches())
            return "Enter valid Zipcode";

        if (Pattern.compile("^[0]{6,6}$").matcher(addressZipcode).matches())
            return "Enter valid Zipcode";

        this.addressZipcode = addressZipcode;

        return "Ok";
    }


    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String validatePassword(String password) {
        if (password == null || password.length() == 0)
            return "Password cannot be empty";

        if (!Pattern.compile("^.{8,}$").matcher(password).matches())
            return "Minimum Password length should be 8 characters";

        if (!Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*?&])[A-Za-z\\d$@$!%*?&]{8,}").matcher(password).matches())
            return "Enter valid password";

        this.password = password;

        return "Ok";
    }

    public String getCnfrmPassword() {
        return cnfrmPassword;
    }

    public void setCnfrmPassword(String cnfrmPassword) {
        this.cnfrmPassword = cnfrmPassword;
    }

    public String validateCnfrmPassword(String password) {
        if (password == null || password.length() == 0)
            return "Password cannot be empty";


        try {

            if (this.password.equals(password)) {
                this.cnfrmPassword = password;
                return "Ok";
            } else {
                return "Password Does't Match";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getAddressCountry() {
        return addressCountry;
    }

    public void setAddressCountry(String addressCountry) {
        this.addressCountry = addressCountry;
    }

    public String validateAddressCountry(String addressCountry) {
        if (addressCountry == null || addressCountry.length() == 0)
            return "Country cannot be empty";

        if (!Pattern.compile("[A-Za-z\\s]{3,30}+").matcher(addressCountry).matches())
            return "Enter valid Country Name";

        this.addressCountry = addressCountry;

        return "Ok";
    }

    JSONObject tojson() throws JSONException {
        JSONObject jobj = new JSONObject();
        jobj.put("userName", this.getUserName());
        jobj.put("email", this.getEmail());
        jobj.put("pwd", this.getPassword());
        jobj.put("phn", this.getPhone());
        jobj.put("srtn", this.getAddressStreet());
        jobj.put("city", this.getAddressCity());
        jobj.put("zipcode", this.getAddressZipcode());
        jobj.put("state", this.getAddressState());
        jobj.put("country", this.getAddressCountry());
        jobj.put("lat", this.getLatitude());
        jobj.put("lng", this.getLongitude());

        return jobj;
    }

}

