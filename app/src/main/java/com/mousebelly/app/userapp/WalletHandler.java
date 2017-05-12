package com.mousebelly.app.userapp;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.mousebelly.app.userapp.Login.LoginActivity.USERID;

/**
 * Created by Kamal Kant on 22-04-2017.
 */

public class WalletHandler {

    public static int WalletAmount;

    public static void setWalletTotalAmount() {
        String resp = Server.s.get("http://mousebelly.herokuapp.com/sign/sign/" + USERID);

        try {

            JSONArray jsonArray = new JSONArray(resp);

            JSONObject jsonObject = jsonArray.getJSONObject(0);

            WalletAmount = jsonObject.getInt("wallet");

            System.out.println("Total Wallet Amount: " + WalletAmount);

        } catch (Exception e) {
            System.out.println("Exception in Loading Total Wallet Amount");
        }
    }

    public static void cutFromWallet(int amount) {

        Server.s.put("http://mousebelly.herokuapp.com/sign/walletRemainbal/" + USERID + "/" + amount);

        setWalletTotalAmount();
    }

    public static void addToWallet(int amount) {

        Server.s.put("http://mousebelly.herokuapp.com/sign/walletmoneyupdate/" + USERID + "/" + amount);

        setWalletTotalAmount();

    }


}