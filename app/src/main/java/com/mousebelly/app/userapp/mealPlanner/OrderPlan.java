package com.mousebelly.app.userapp.mealPlanner;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.mousebelly.app.userapp.CustomToast;
import com.mousebelly.app.userapp.WalletHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import static com.mousebelly.app.userapp.Login.LoginActivity.USERID;

/**
 * Created by Aaru on 07/04/2017.
 */

public class OrderPlan {
    public static HashMap<String, OrderObject> orderPlan = new HashMap();

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static void loadData(String data) {
        try {
            JSONArray dataJsonArray = new JSONArray(data);
            for (int i = 0; i < dataJsonArray.length(); i++) {
                JSONObject jsonObject = dataJsonArray.getJSONObject(i);

                OrderObject orderObject = new OrderObject();
                orderObject.set_id(jsonObject.getString("_id"));
                orderObject.setOrder_date(jsonObject.getString("order_date"));
                orderObject.setOrder_Id(jsonObject.getString("order_Id"));
                orderObject.setPayment_status(jsonObject.getString("Payment_status"));
                orderObject.setTotal_bill(jsonObject.getString("Total_bill"));
                orderObject.setUserEmailId(jsonObject.getString("UserEmail_id"));

                try {
                    JSONObject prodDetails = jsonObject.getJSONObject("Prod_details");

                    Iterator it = prodDetails.keys();

                    while (it.hasNext()) {
                        String date = (String) it.next();

                        JSONArray items = prodDetails.getJSONArray(date);

                        for (int j = 0; j < items.length(); j++) {
                            JSONObject jsonObject1 = items.getJSONObject(j);

                            FoodObjectInOrderPlan foodObject = new FoodObjectInOrderPlan();

                            foodObject.setPID(jsonObject1.getString("PID"));
                            foodObject.setProduct_Name(jsonObject1.getString("Product_Name"));
                            foodObject.setStatus(jsonObject1.getString("status"));
                            foodObject.setHWID(jsonObject1.getString("HWID"));
                            foodObject.setQty(jsonObject1.getString("Qty"));
                            foodObject.setStars(jsonObject1.getString("stars"));
                            foodObject.setPrice_item(jsonObject1.getString("Price_item"));
                            foodObject.setOrderid(jsonObject1.getString("orderid"));
                            foodObject.setDelivery_date(jsonObject1.getString("delivery_date"));
                            foodObject.setImage(jsonObject1.getString("Image"));

                            orderObject.getOrderItems().put(date + "_" + jsonObject1.getString("PID"), foodObject);

                            OrderPlan.orderPlan.put(jsonObject1.getString("orderid"), orderObject);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Order Plan : " + OrderPlan.orderPlan);

        ArrayList<FoodObjectInOrderPlan> orderPlanItems = new ArrayList<>();

        Set keys = OrderPlan.orderPlan.keySet();

        for (Object o : keys) {
            String key = o.toString();

            OrderObject orderObject = OrderPlan.orderPlan.get(key);
            HashMap<String, FoodObjectInOrderPlan> hm = orderObject.getOrderItems();

            Set newKeys = hm.keySet();
            for (Object o1 : newKeys) {
                String newkey = o1.toString();

                FoodObjectInOrderPlan foodObjectInOrderPlan = hm.get(newkey);

                orderPlanItems.add(foodObjectInOrderPlan);
            }
        }

        System.out.println("ArrayList:");
        System.out.println(orderPlanItems);

        for (FoodObjectInOrderPlan f : orderPlanItems) {
            HashMap hm1 = MealPlan.mealPlan.get(f.getHWID());

            if (hm1 != null) {
                HashMap hm2 = (HashMap) hm1.get(f.getPID());

                if (hm2 != null) {
                    FoodObject foodObject = (FoodObject) hm2.get(f.getDelivery_date());

                    if (foodObject != null) {
                        foodObject.setOrderId(f.getOrderid());
                        foodObject.setSelected(true);

                        DateLayout.AddToLayout(foodObject);

                        //FoodObject.dispFood(foodObject);
                    }
                }
            }
        }

        System.out.println("Meal Plan with check:");

        System.out.println(MealPlan.mealPlan);

        Iterator i = MealPlan.mealPlan.keySet().iterator();

        while (i.hasNext()) {
            String hwid = i.next().toString();

            HashMap hm1 = MealPlan.mealPlan.get(hwid);

            if (hm1 != null) {
                Iterator i1 = hm1.keySet().iterator();
                while (i1.hasNext()) {
                    String prodId = i1.next().toString();
                    HashMap hm2 = (HashMap) hm1.get(prodId);

                    if (hm2 != null) {
                        Iterator i2 = hm2.keySet().iterator();

                        while (i2.hasNext()) {
                            String date = i2.next().toString();

                            FoodObject foodObject = (FoodObject) hm2.get(date);

                            if (foodObject != null) {
                                MealPlan.addToUI(foodObject);
                            }
                        }
                    }
                }
            }
        }
    }

    //
    public static void addToOrderPlan(FoodObject foodObject) {

        OrderObject orderObject = orderPlan.get(foodObject.getOrderId());

        if (orderObject == null)
            orderObject = new OrderObject();

        Date dt = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, d MMMM yyyy");

        dt = cal.getTime();

        //orderObject.set_id( jsonObject.getString("_id") );
        orderObject.setOrder_date(formatter.format(dt).toString());
        orderObject.setOrder_Id(foodObject.getOrderId());
        orderObject.setPayment_status("Success");

        String total = orderObject.getTotal_bill();

        if (total == null) {
            int temp = Integer.parseInt(foodObject.getPrice()) * foodObject.getQty();

            orderObject.setTotal_bill(temp + "");
        } else {
            int temp = Integer.parseInt(total) + Integer.parseInt(foodObject.getPrice()) * foodObject.getQty();

            orderObject.setTotal_bill(temp + "");
        }

        orderObject.setUserEmailId(USERID);

        HashMap<String, FoodObjectInOrderPlan> orderItems = orderObject.getOrderItems();

        if (orderItems == null)
            orderItems = new HashMap<>();

        FoodObjectInOrderPlan foodObjectInOrderPlan = new FoodObjectInOrderPlan();

        foodObjectInOrderPlan.setPID(foodObject.getProd_id());
        foodObjectInOrderPlan.setProduct_Name(foodObject.getProd_name());
        foodObjectInOrderPlan.setStatus("ordered");
        foodObjectInOrderPlan.setHWID(foodObject.getHWFEmail_id());
        foodObjectInOrderPlan.setQty(String.valueOf(foodObject.getQty()));
        foodObjectInOrderPlan.setStars(foodObject.getStarsize());
        foodObjectInOrderPlan.setPrice_item(foodObject.getPrice());
        foodObjectInOrderPlan.setOrderid(foodObject.getOrderId());
        foodObjectInOrderPlan.setDelivery_date(foodObject.getDate());
        System.out.println("OrderPlan Image : " + foodObject.getImage());
        foodObjectInOrderPlan.setImage(foodObject.getImage());

        orderItems.put(foodObject.getDate() + "_" + foodObject.getProd_id(), foodObjectInOrderPlan);

        orderObject.setOrderItems(orderItems);

        System.out.println(foodObject.getOrderId());

        orderPlan.put(foodObject.getOrderId(), orderObject);

        System.out.println("Inside:");
        System.out.println(foodObject.getOrderId());

    }

    public static boolean removeFromOrderPlan(FoodObject foodObject) {

        System.out.println("Remove Called");

        OrderObject orderObject = orderPlan.get(foodObject.getOrderId());
        if (orderObject != null) {
            FoodObjectInOrderPlan foodObjectInOrderPlan = orderObject.getOrderItems().get(foodObject.getDate() + "_" + foodObject.getProd_id());
            if (foodObjectInOrderPlan != null) {
                orderObject.getOrderItems().remove(foodObject.getDate() + "_" + foodObject.getProd_id());

                System.out.println(OrderPlan.toJSON());

                int amount = Integer.parseInt(foodObjectInOrderPlan.getPrice_item()) * Integer.parseInt(foodObjectInOrderPlan.getQty());
                //System.out.println("Desc Wallet Amount : " + walletAmountDesc);

                // send product_details

               /* int status = Server.s.putwithdata("http://mousebelly.herokuapp.com/preorder/preorder/"+foodObjectInOrderPlan.getOrderid(),);

                if(status==200){*/

                WalletHandler.addToWallet(amount);
                //Server.s.put("http://mousebelly.herokuapp.com/sign/walletmoneyupdate/" + USERID + "/" + walletAmountDesc);

                CustomToast.Toast(MealPlannerMainActivity.context, "Amount added to Wallet");

                WalletHandler.setWalletTotalAmount();

                MealPlannerMainActivity.walletMenu.setTitle("₹" + String.valueOf(WalletHandler.WalletAmount));
                //}


                return true;
            }
        }

        return false;
    }

    public static JSONObject getOrderJSON(String orderId) {
        JSONObject jsonObject = new JSONObject();

        String oid = orderId;

        System.out.println("Order Id:" + oid);

        OrderObject orderObject = orderPlan.get(oid);

        try {
            jsonObject.put("_id", orderObject.get_id());
            jsonObject.put("order_date", orderObject.getOrder_date());
            jsonObject.put("order_Id", orderObject.getOrder_Id());
            jsonObject.put("Payment_status", orderObject.getPayment_status());
            jsonObject.put("UserEmail_id", orderObject.getUserEmailId());

            //orderObject.setTotal_bill( jsonObject.getString("Total_bill") );

        } catch (Exception e) {
            e.printStackTrace();
        }

        int total = 0;

        HashMap hm1 = new HashMap();

        HashMap hm2 = orderObject.getOrderItems();

        Iterator i1 = hm2.keySet().iterator();

        while (i1.hasNext()) {
            String key = i1.next().toString();

            String date = key.split("_")[0];

            if (hm1.get(date) == null) {
                ArrayList al = new ArrayList();

                FoodObjectInOrderPlan foodObjectInOrderPlan = (FoodObjectInOrderPlan) hm2.get(key);

                total += Integer.parseInt(foodObjectInOrderPlan.getPrice_item()) * Integer.parseInt(foodObjectInOrderPlan.getQty());

                al.add(foodObjectInOrderPlan);

                hm1.put(date, al);
            } else {
                ArrayList al = (ArrayList) hm1.get(date);

                FoodObjectInOrderPlan foodObjectInOrderPlan = (FoodObjectInOrderPlan) hm2.get(key);

                total += Integer.parseInt(foodObjectInOrderPlan.getPrice_item()) * Integer.parseInt(foodObjectInOrderPlan.getQty());

                al.add(foodObjectInOrderPlan);

                hm1.put(date, al);
            }

        }

        try {
            jsonObject.put("Total_bill", total);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //

        JSONObject prodDetails = new JSONObject();

        System.out.println("Hash Map:" + hm1);

        Iterator iterator = hm1.keySet().iterator();

        while (iterator.hasNext()) {
            String key = iterator.next().toString();

            ArrayList al = (ArrayList) hm1.get(key);

            JSONArray jsonArray = new JSONArray();

            for (Object o : al) {
                FoodObjectInOrderPlan foodObjectInOrderPlan = (FoodObjectInOrderPlan) o;

                jsonArray.put(foodObjectInOrderPlan.toJSON());
            }

            try {
                prodDetails.put(key, jsonArray);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        try {
            jsonObject.put("Prod_details", prodDetails);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(jsonObject);

        return jsonObject;
    }

    public static JSONObject getProdDetailsJSON(String orderId) {
        JSONObject jsonObject = new JSONObject();

        String oid = orderId;

        System.out.println("Order Id:" + oid);

        OrderObject orderObject = orderPlan.get(oid);

        try {
            jsonObject.put("_id", orderObject.get_id());
            jsonObject.put("order_date", orderObject.getOrder_date());
            jsonObject.put("order_Id", orderObject.getOrder_Id());
            jsonObject.put("Payment_status", orderObject.getPayment_status());
            jsonObject.put("UserEmail_id", orderObject.getUserEmailId());

            //orderObject.setTotal_bill( jsonObject.getString("Total_bill") );

        } catch (Exception e) {
            e.printStackTrace();
        }

        int total = 0;

        HashMap hm1 = new HashMap();

        HashMap hm2 = orderObject.getOrderItems();

        Iterator i1 = hm2.keySet().iterator();

        while (i1.hasNext()) {
            String key = i1.next().toString();

            String date = key.split("_")[0];

            if (hm1.get(date) == null) {
                ArrayList al = new ArrayList();

                FoodObjectInOrderPlan foodObjectInOrderPlan = (FoodObjectInOrderPlan) hm2.get(key);

                total += Integer.parseInt(foodObjectInOrderPlan.getPrice_item()) * Integer.parseInt(foodObjectInOrderPlan.getQty());

                al.add(foodObjectInOrderPlan);

                hm1.put(date, al);
            } else {
                ArrayList al = (ArrayList) hm1.get(date);

                FoodObjectInOrderPlan foodObjectInOrderPlan = (FoodObjectInOrderPlan) hm2.get(key);

                total += Integer.parseInt(foodObjectInOrderPlan.getPrice_item()) * Integer.parseInt(foodObjectInOrderPlan.getQty());

                al.add(foodObjectInOrderPlan);

                hm1.put(date, al);
            }

        }

        try {
            jsonObject.put("Total_bill", total);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //

        JSONObject prodDetails = new JSONObject();

        System.out.println("Hash Map:" + hm1);

        Iterator iterator = hm1.keySet().iterator();

        while (iterator.hasNext()) {
            String key = iterator.next().toString();

            ArrayList al = (ArrayList) hm1.get(key);

            JSONArray jsonArray = new JSONArray();

            for (Object o : al) {
                FoodObjectInOrderPlan foodObjectInOrderPlan = (FoodObjectInOrderPlan) o;

                jsonArray.put(foodObjectInOrderPlan.toJSON());
            }

            try {
                prodDetails.put(key, jsonArray);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        try {
            jsonObject.put("Prod_details", prodDetails);

            return prodDetails;
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(jsonObject);

        jsonObject = new JSONObject();

        return jsonObject;
    }

    //

    public static JSONArray toJSON() {

        JSONArray masterJSONArray = new JSONArray();

        Iterator i = orderPlan.keySet().iterator();

        System.out.println("Order Plan JSON Key Set:");
        System.out.println(orderPlan.keySet());

        while (i.hasNext()) {

            JSONObject jsonObject = new JSONObject();

            String oid = i.next().toString();

            System.out.println("Order Id:" + oid);

            OrderObject orderObject = orderPlan.get(oid);

            try {
                jsonObject.put("_id", orderObject.get_id());
                jsonObject.put("order_date", orderObject.getOrder_date());
                jsonObject.put("order_Id", orderObject.getOrder_Id());
                jsonObject.put("Payment_status", orderObject.getPayment_status());
                jsonObject.put("UserEmail_id", orderObject.getUserEmailId());

                //orderObject.setTotal_bill( jsonObject.getString("Total_bill") );

            } catch (Exception e) {
                e.printStackTrace();
            }

            int total = 0;

            HashMap hm1 = new HashMap();

            HashMap hm2 = orderObject.getOrderItems();

            Iterator i1 = hm2.keySet().iterator();

            while (i1.hasNext()) {
                String key = i1.next().toString();

                String date = key.split("_")[0];

                if (hm1.get(date) == null) {
                    ArrayList al = new ArrayList();

                    FoodObjectInOrderPlan foodObjectInOrderPlan = (FoodObjectInOrderPlan) hm2.get(key);

                    total += Integer.parseInt(foodObjectInOrderPlan.getPrice_item()) * Integer.parseInt(foodObjectInOrderPlan.getQty());

                    al.add(foodObjectInOrderPlan);

                    hm1.put(date, al);
                } else {
                    ArrayList al = (ArrayList) hm1.get(date);

                    FoodObjectInOrderPlan foodObjectInOrderPlan = (FoodObjectInOrderPlan) hm2.get(key);

                    total += Integer.parseInt(foodObjectInOrderPlan.getPrice_item()) * Integer.parseInt(foodObjectInOrderPlan.getQty());

                    al.add(foodObjectInOrderPlan);

                    hm1.put(date, al);
                }

            }

            try {
                jsonObject.put("Total_bill", total);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //

            JSONObject prodDetails = new JSONObject();

            System.out.println("Hash Map:" + hm1);

            Iterator iterator = hm1.keySet().iterator();

            while (iterator.hasNext()) {
                String key = iterator.next().toString();

                ArrayList al = (ArrayList) hm1.get(key);

                JSONArray jsonArray = new JSONArray();

                for (Object o : al) {
                    FoodObjectInOrderPlan foodObjectInOrderPlan = (FoodObjectInOrderPlan) o;

                    jsonArray.put(foodObjectInOrderPlan.toJSON());
                }

                try {
                    prodDetails.put(key, jsonArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            try {
                jsonObject.put("Prod_details", prodDetails);
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println(jsonObject);

            masterJSONArray.put(jsonObject);

        }

        return masterJSONArray;
    }


}
