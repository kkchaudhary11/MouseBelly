package com.mousebelly.app.userapp.mealPlanner;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mousebelly.app.userapp.CustomToast;
import com.mousebelly.app.userapp.Server;
import com.mousebelly.app.userapp.WalletHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Aaru on 07/04/2017.
 */

public class Cart {

    public static HashMap<String, FoodObject> cartItems = new HashMap<>();

    public static int cartAmount = 0;

    //public static int walletTotalAmount = 0;

    public TextView tv = new TextView(MealPlannerMainActivity.context);

 /*   public static void setWalletTotalAmount() {
        String resp = Server.s.get("http://mousebelly.herokuapp.com/sign/sign/" + USERID);

        try {

            JSONArray jsonArray = new JSONArray(resp);

            JSONObject jsonObject = jsonArray.getJSONObject(0);

            walletTotalAmount = jsonObject.getInt("wallet");

            System.out.println("Total Wallet Amount: " + walletTotalAmount);

        } catch (Exception e) {
            System.out.println("Exception in Loading Total Wallet Amount");
        }
    }*/

    public static boolean updateCart(FoodObject foodObject, int value) {
        int qtyUpdate = foodObject.getQty() - value;

        Cart.cartAmount += (Integer.parseInt(foodObject.getPrice()) * qtyUpdate);


        if (WalletHandler.WalletAmount < Cart.cartAmount) {
            CustomToast.Toast(MealPlannerMainActivity.context, "Not sufficient amount");
            foodObject.setQty(value);
            Cart.cartAmount -= (Integer.parseInt(foodObject.getPrice()) * qtyUpdate);
            return false;
        }

        System.out.println("Update Cart : " + Cart.cartAmount);

        MealPlannerMainActivity.totalAmount.setText(String.valueOf(Cart.cartAmount));

        //foodObject.setQty(value);

        cartItems.put(foodObject.getProd_id() + "_" + foodObject.getDate(), foodObject);

        return true;
    }

    public static void addToCart(FoodObject foodObject) {

        cartItems.put(foodObject.getProd_id() + "_" + foodObject.getDate(), foodObject);

        System.out.println("FoodObject price: " + Integer.parseInt(foodObject.getPrice()));
        System.out.println("FoodObject qty: " + foodObject.getQty());

        Cart.cartAmount += (Integer.parseInt(foodObject.getPrice()) * foodObject.getQty());

        System.out.println("This is get price  and quantity::::::" + Integer.parseInt(foodObject.getPrice()) * foodObject.getQty());
        System.out.println("cart Amount::::: " + Cart.cartAmount);

        CustomToast.Toast(MealPlannerMainActivity.context, "Added to cart");

    }

    public static void removeFromCart(FoodObject foodObject) {

        boolean flag = cartItems.get(foodObject.getProd_id() + "_" + foodObject.getDate()) != null;

        cartItems.remove(foodObject.getProd_id() + "_" + foodObject.getDate());


        LinearLayout l = MealPlannerMainActivity.staticCartLinearLayout;

        if (l != null)
            for (int i = 0; i < l.getChildCount(); i++) {
                RelativeLayout rl = (RelativeLayout) l.getChildAt(i);

                if (rl.getTag().equals(foodObject.getDate() + "_" + foodObject.getProd_id())) {
                    l.removeView(rl);
                    break;
                }
            }

        System.out.println("Remove Flag : " + flag);
        if (flag) {
            cartAmount -= Integer.parseInt(foodObject.getPrice()) * foodObject.getQty();

            System.out.println("Cart Amount Post Remove: " + cartAmount);
            foodObject.setQty(1);
            if (MealPlannerMainActivity.totalAmount != null)
                MealPlannerMainActivity.totalAmount.setText(String.valueOf(Cart.cartAmount));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static void addCartToOrderPlan(String orderId) {
        ArrayList<FoodObject> al = new ArrayList<FoodObject>();

        Iterator iterator = Cart.cartItems.keySet().iterator();

        while (iterator.hasNext()) {
            String key = iterator.next().toString();

            FoodObject foodObject = Cart.cartItems.get(key);

            al.add(foodObject);
        }

        System.out.println("Old Order: ");
        System.out.println(OrderPlan.orderPlan.keySet().size());
        System.out.println(OrderPlan.orderPlan.keySet());
        System.out.println(OrderPlan.toJSON());

        for (FoodObject foodObject : al) {
            foodObject.setOrderId(orderId);
            //foodObject.setSelected(true);

            RelativeLayout rl = DateLayout.dateLayout.get(foodObject.getDate());

            if (rl != null)
                for (int i = 1; i < rl.getChildCount(); i++) {
                    RelativeLayout rl1 = (RelativeLayout) rl.getChildAt(i);

                    if (rl1.getTag().equals(foodObject.getDate() + "_" + foodObject.getProd_id())) {
                        GradientDrawable shape = new GradientDrawable();
                        shape.setCornerRadius(8);
                        shape.setStroke(5, Color.GREEN);
                        rl1.setBackground(shape);
                        break;
                    }
                }

            OrderPlan.addToOrderPlan(foodObject);
        }

        System.out.println("New Order: ");
        System.out.println(OrderPlan.orderPlan.keySet().size());
        System.out.println(OrderPlan.orderPlan.keySet());
        System.out.println(OrderPlan.toJSON());

        LinearLayout l = MealPlannerMainActivity.staticCartLinearLayout;

        while (l.getChildCount() != 0) {
            RelativeLayout rl = (RelativeLayout) l.getChildAt(0);
            l.removeView(rl);
        }

        //
        System.out.println("NEW ORDER JSON: ");
        System.out.println(OrderPlan.getOrderJSON(orderId));

        int satus = Server.s.post("http://mousebelly.herokuapp.com/preorder/walletOrder", OrderPlan.getOrderJSON(orderId));
        Cart.cartAmount = 0;
        Cart.cartItems = new HashMap<>();
    }


    public static boolean inCart(FoodObject foodObject) {

        FoodObject foodObject1 = cartItems.get(foodObject.getProd_id() + "_" + foodObject.getDate());

        return foodObject1 != null;
    }

    public static ArrayList<FoodObject> getAllItemsInCart() {
        ArrayList<FoodObject> al = new ArrayList<>();

        al = (ArrayList<FoodObject>) Cart.cartItems.values();

        return al;
    }
}
