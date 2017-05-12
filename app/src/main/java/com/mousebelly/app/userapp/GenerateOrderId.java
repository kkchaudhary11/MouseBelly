package com.mousebelly.app.userapp;

/**
 * Created by Kamal Kant on 12-04-2017.
 */

public class GenerateOrderId {

    public static String getOrderId() {
        String random;
        double order = Math.floor(Math.random() * 89999 + 10000);
        random = "O_id" + (int) order;


        return random;
    }
}
