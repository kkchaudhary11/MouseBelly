package com.mousebelly.app.userapp.products;


import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mousebelly.app.userapp.CustomToast;
import com.mousebelly.app.userapp.Server;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.mousebelly.app.userapp.Login.LoginActivity.USERID;


public class Cart {

    public static HashMap<String, ProductUnit> cartItems = new HashMap<>();

    public static int cartAmount = 0;

    public static int walletTotalAmount = 0;

    public TextView tv = new TextView(MainActivity.context);

    public static void setWalletTotalAmount() {
        String resp = Server.s.get("http://mousebelly.herokuapp.com/sign/sign/" + USERID);

        try {

            JSONArray jsonArray = new JSONArray(resp);

            JSONObject jsonObject = jsonArray.getJSONObject(0);

            walletTotalAmount = jsonObject.getInt("wallet");

            System.out.println("Total Wallet Amount: " + walletTotalAmount);

        } catch (Exception e) {
            System.out.println("Exception in Loading Total Wallet Amount");
        }
    }

    public static boolean updateCart(ProductUnit productUnit, int value) {
        int qtyUpdate = productUnit.getQty() - value;

        Cart.cartAmount += (Integer.parseInt(productUnit.getPrice()) * qtyUpdate);

        System.out.println("Update Cart : " + Cart.cartAmount);

        if (MainActivity.totalAmount != null)

            MainActivity.totalAmount.setText(String.valueOf(Cart.cartAmount));

        //ProductUnit.setQty(value);

        cartItems.put(productUnit.getProd_id(), productUnit);

        return true;
    }

    public static void addToCart(ProductUnit productUnit) {

        cartItems.put(productUnit.getProd_id(), productUnit);

        System.out.println("ProductUnit price: " + Integer.parseInt(productUnit.getPrice()));
        System.out.println("ProductUnit qty: " + productUnit.getQty());

        Cart.cartAmount += (Integer.parseInt(productUnit.getPrice()) * productUnit.getQty());

        System.out.println("This is get price  and quantity:::" + Integer.parseInt(productUnit.getPrice()) * productUnit.getQty());
        System.out.println("cart Amount:::" + Cart.cartAmount);

        CustomToast.Toast(MainActivity.context, "Added to cart");

    }

    public static void removeFromCart(ProductUnit productUnit) {

        boolean flag = cartItems.get(productUnit.getProd_id()) != null;

        cartItems.remove(productUnit.getProd_id());

        LinearLayout l = MainActivity.staticCartLinearLayout;

        if (l != null)
            for (int i = 0; i < l.getChildCount(); i++) {
                RelativeLayout rl = (RelativeLayout) l.getChildAt(i);

                if (rl.getTag().equals(productUnit.getProd_id())) {
                    l.removeView(rl);
                    break;
                }
            }

        if (flag) {
            cartAmount -= Integer.parseInt(productUnit.getPrice()) * productUnit.getQty();

            System.out.println("Cart Amount Post Remove: " + cartAmount);

            if (MainActivity.totalAmount != null)
                MainActivity.totalAmount.setText(String.valueOf(Cart.cartAmount));
        }
    }


    public static boolean inCart(ProductUnit productUnit) {

        ProductUnit productUnit1 = cartItems.get(productUnit.getProd_id());

        return productUnit1 != null;
    }

    public static ArrayList<ProductUnit> getAllItemsInCart() {
        ArrayList<ProductUnit> al = new ArrayList<>();

        al = (ArrayList<ProductUnit>) Cart.cartItems.values();

        //ProductUnit p= (Pr)Cart.cartItems.values();

        return al;
    }
}
