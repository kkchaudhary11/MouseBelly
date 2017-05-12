package com.mousebelly.app.userapp.preOrderStack;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mousebelly.app.userapp.SocketAccess;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;


public class PreOrderStack extends SocketAccess {


    public static HashMap<String, PreOrderStackObject> preorderStack = new HashMap();
    private Socket s;


    public PreOrderStack() {
        s = SocketAccess.socket;

        s.on("preorderstacked", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                receive(args[0]);
                //System.out.println(args[0]);

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void loadData(String data) {

        try {
            JSONArray dataJsonArray = new JSONArray(data);
            for (int i = 0; i < dataJsonArray.length(); i++) {
                JSONObject jsonObject = dataJsonArray.getJSONObject(i);

                PreOrderStackObject preOrderStackObject = new PreOrderStackObject();

                preOrderStackObject.set_id(jsonObject.getString("_id"));
                preOrderStackObject.setUserId(jsonObject.getString("UserEmail_id"));
                preOrderStackObject.setTotal_bill(jsonObject.getString("Total_bill"));
                preOrderStackObject.setPayment_status(jsonObject.getString("Payment_status"));
                preOrderStackObject.setOrder_date(jsonObject.getString("order_date"));
                preOrderStackObject.setOrderId(jsonObject.getString("order_Id"));

                HashMap hm = new HashMap();

                try {
                    JSONObject productDetails = jsonObject.getJSONObject("Prod_details");

                    Iterator it = productDetails.keys();

                    while (it.hasNext()) {
                        String pro_date = (String) it.next();

                        JSONArray productsArray = productDetails.getJSONArray(pro_date);


                        for (int j = 0; j < productsArray.length(); j++) {
                            JSONObject products = productsArray.getJSONObject(j);


                            FoodObjectInPreOrderStack foodObjectInPreOrderStack = new FoodObjectInPreOrderStack();

                            foodObjectInPreOrderStack.setPID(products.getString("PID"));
                            foodObjectInPreOrderStack.setProduct_Name(products.getString("Product_Name"));
                            foodObjectInPreOrderStack.setStatus(products.getString("status"));
                            foodObjectInPreOrderStack.setHWID(products.getString("HWID"));
                            foodObjectInPreOrderStack.setQty(products.getString("Qty"));
                            foodObjectInPreOrderStack.setStars(products.getString("stars"));
                            foodObjectInPreOrderStack.setPrice_item(products.getString("Price_item"));
                            foodObjectInPreOrderStack.setOrderid(products.getString("orderid"));
                            foodObjectInPreOrderStack.setDelivery_date(products.getString("delivery_date"));
                            foodObjectInPreOrderStack.setImage(products.getString("Image"));


                            hm.put(foodObjectInPreOrderStack.getDelivery_date() + "_" + foodObjectInPreOrderStack.getPID(), foodObjectInPreOrderStack);

                            preOrderStackObject.setProdDetails(hm);

                            System.out.println("Draw called**");


                            preorderStack.put(foodObjectInPreOrderStack.getOrderid(), preOrderStackObject);

                        }


                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                //PreOrderMainActivity.orderStackViewPager.addView( preOrderStackObject.draw() );


                RelativeLayout rl = new RelativeLayout(PreOrderMainActivity.context);

                TextView tv = new TextView(PreOrderMainActivity.context);

                tv.setText("ABC");
                rl.addView(tv);

                PreOrderMainActivity.fList.add(MyFragment.newInstance(preOrderStackObject.draw()));

                //System.out.println("View added:");
                //System.out.println("Count: " + PreOrderMainActivity.orderStackViewPager.getChildCount());

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(preorderStack);

    }

    @Override
    public void receive(Object o) {
        System.out.println("Message in PreOrderStack : " + o);

        try {
            JSONObject jsonObject = new JSONObject(o.toString());
            String userId = jsonObject.getString("Uemail");
            String message = jsonObject.getString("message");
            if (message.contains("preorderstackupdate")) {
                String[] orderDetails = message.split(";");
                String OrderID = orderDetails[1];
                String ProductID = orderDetails[2];
                String DeliveryDate = orderDetails[3];
                String status = orderDetails[4];

                if (preorderStack.containsKey(OrderID)) {
                    PreOrderStackObject preOrderStackObject = preorderStack.get(OrderID);

                    preOrderStackObject.updateOrderIdColor(status);

                    HashMap<String, FoodObjectInPreOrderStack> hm1 = preOrderStackObject.getProdDetails();

                    if (hm1.containsKey(DeliveryDate + "_" + ProductID)) {
                        FoodObjectInPreOrderStack foodObjectInPreOrderStack = hm1.get(DeliveryDate + "_" + ProductID);

                        FoodObjectInPreOrderStack.updateOrderStatus(foodObjectInPreOrderStack, status);

                        //foodObjectInPreOrderStack.orderDraw();
                    }
                }


                System.out.println("Updated order stack  : " + preorderStack);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
