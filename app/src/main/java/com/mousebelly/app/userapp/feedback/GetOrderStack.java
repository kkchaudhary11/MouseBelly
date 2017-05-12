package com.mousebelly.app.userapp.feedback;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.mousebelly.app.userapp.Server;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class GetOrderStack {

    public static HashMap<String, ArrayList<FeedFoodObjectInOrderStack>> feedorderStack = new HashMap();

    ArrayList<FeedFoodObjectInOrderStack> OrderedProductsArray;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void loadData(String data) {

        try {
            JSONArray dataJsonArray = new JSONArray(data);
            for (int i = 0; i < dataJsonArray.length(); i++) {
                JSONObject jsonObject = dataJsonArray.getJSONObject(i);

          /*      FeedOrderStackObject feedOrderStackObject = new FeedOrderStackObject();

                feedOrderStackObject.set_id(jsonObject.getString("_id"));
                feedOrderStackObject.setUserId(jsonObject.getString("UserEmail_id"));
                feedOrderStackObject.setTotal_bill(jsonObject.getString("Total_bill"));
                feedOrderStackObject.setPayment_status(jsonObject.getString("Payment_status"));
                feedOrderStackObject.setOrder_date(jsonObject.getString("order_date"));
                feedOrderStackObject.setOrderId(jsonObject.getString("order_Id"));*/

                String OrderId = (jsonObject.getString("order_Id"));
                String PaymentStatus = (jsonObject.getString("Payment_status"));
                Boolean FeedbackDone = jsonObject.getBoolean("feedbackDone");

                if (PaymentStatus.equals("Success") && FeedbackDone == false) {


                    // HashMap hm = new HashMap();

                    OrderedProductsArray = new ArrayList<>();

                    try {

                        JSONObject productDetails = jsonObject.getJSONObject("Prod_details");

                        Iterator it = productDetails.keys();

                        while (it.hasNext()) {


                            String pro_id = (String) it.next();

                            JSONObject products = productDetails.getJSONObject(pro_id);

                            FeedFoodObjectInOrderStack feedFoodObjectInOrderStack = new FeedFoodObjectInOrderStack();

                            feedFoodObjectInOrderStack.setPID(products.getString("PID"));
                            feedFoodObjectInOrderStack.setProduct_Name(products.getString("Product_Name"));
                            feedFoodObjectInOrderStack.setStatus(products.getString("status"));
                            feedFoodObjectInOrderStack.setHWID(products.getString("HWID"));
                            feedFoodObjectInOrderStack.setQty(products.getString("Qty"));
                            feedFoodObjectInOrderStack.setStars(products.getString("stars"));
                            feedFoodObjectInOrderStack.setPrice_item(products.getString("Price_item"));
                            feedFoodObjectInOrderStack.setOrderid(products.getString("orderid"));
                            feedFoodObjectInOrderStack.setImage(products.getString("Image"));

                            String resp = Server.s.get("http://mousebelly.herokuapp.com/prod_approval/prod_approval_for_rating/" + feedFoodObjectInOrderStack.getPID());

                            JSONArray jsonArrayforrating = new JSONArray(resp);
                            JSONObject jsonObjectforrating = jsonArrayforrating.getJSONObject(0);
                            feedFoodObjectInOrderStack.setProductRating(Float.parseFloat(jsonObjectforrating.getString("starsize")));

                       /* hm.put(feedFoodObjectInOrderStack.getPID(), feedFoodObjectInOrderStack);

                        feedOrderStackObject.setProdDetails(hm);

                        System.out.println("Draw called**");*/


                            OrderedProductsArray.add(feedFoodObjectInOrderStack);


                        }

                        feedorderStack.put(OrderId, OrderedProductsArray);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                //OrderStackMainActivity.orderStackViewPager.addView( orderStackObject.draw() );


               /* RelativeLayout rl = new RelativeLayout(MainActivity.context);

                TextView tv = new TextView(MainActivity.context);

                tv.setText("ABC");
                rl.addView(tv);

                Dialog dialog = new Dialog(MainActivity.context);*/


                // OrderStackMainActivity.fList.add(MyFragment.newInstance(orderStackObject.draw()));

                //System.out.println("View added:");
                //System.out.println("Count: " + OrderStackMainActivity.orderStackViewPager.getChildCount());

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(feedorderStack);

    }

}
