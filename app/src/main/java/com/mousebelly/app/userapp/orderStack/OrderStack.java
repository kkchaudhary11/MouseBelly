package com.mousebelly.app.userapp.orderStack;

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


public class OrderStack extends SocketAccess {


    public static HashMap<String, OrderStackObject> orderStack = new HashMap();
    private Socket s;


    public OrderStack() {
        s = SocketAccess.socket;

        s.on("orderstacked", new Emitter.Listener() {
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

                OrderStackObject orderStackObject = new OrderStackObject();

                orderStackObject.set_id(jsonObject.getString("_id"));
                orderStackObject.setUserId(jsonObject.getString("UserEmail_id"));
                orderStackObject.setTotal_bill(jsonObject.getString("Total_bill"));
                orderStackObject.setPayment_status(jsonObject.getString("Payment_status"));
                orderStackObject.setOrder_date(jsonObject.getString("order_date"));
                orderStackObject.setOrderId(jsonObject.getString("order_Id"));

                HashMap hm = new HashMap();

                try {

                    JSONObject productDetails = jsonObject.getJSONObject("Prod_details");

                    Iterator it = productDetails.keys();

                    while (it.hasNext()) {
                        String pro_id = (String) it.next();

                        JSONObject products = productDetails.getJSONObject(pro_id);

                        FoodObjectInOrderStack foodObjectInOrderStack = new FoodObjectInOrderStack();

                        foodObjectInOrderStack.setPID(products.getString("PID"));
                        foodObjectInOrderStack.setProduct_Name(products.getString("Product_Name"));
                        foodObjectInOrderStack.setStatus(products.getString("status"));
                        foodObjectInOrderStack.setHWID(products.getString("HWID"));
                        foodObjectInOrderStack.setQty(products.getString("Qty"));
                        foodObjectInOrderStack.setStars(products.getString("stars"));
                        foodObjectInOrderStack.setPrice_item(products.getString("Price_item"));
                        foodObjectInOrderStack.setOrderid(products.getString("orderid"));
                        foodObjectInOrderStack.setImage(products.getString("Image"));


                        hm.put(foodObjectInOrderStack.getPID(), foodObjectInOrderStack);

                        orderStackObject.setProdDetails(hm);

                        System.out.println("Draw called**");


                        orderStack.put(foodObjectInOrderStack.getOrderid(), orderStackObject);

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                //OrderStackMainActivity.orderStackViewPager.addView( orderStackObject.draw() );
/*

                if(orderStack.isEmpty()){
                    RelativeLayout rl = new RelativeLayout(OrderStackMainActivity.context);
                    TextView tv = new TextView(OrderStackMainActivity.context);
                    tv.setText("No orders");
                    rl.addView();
                    OrderStackMainActivity.fList.add(MyFragment.newInstance(orderStackObject.draw()));
                }
*/

                OrderStackMainActivity.fList.add(MyFragment.newInstance(orderStackObject.draw()));

                //System.out.println("View added:");
                //System.out.println("Count: " + OrderStackMainActivity.orderStackViewPager.getChildCount());

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(orderStack);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void receive(Object o) {
        System.out.println("Message in GetOrderStack : " + o);

        try {
            JSONObject jsonObject = new JSONObject(o.toString());
            String userId = jsonObject.getString("Uemail");
            String message = jsonObject.getString("message");
            if (message.contains("orderstackupdate")) {
                String[] orderDetails = message.split(";");
                String OrderID = orderDetails[1];
                String ProductID = orderDetails[2];
                String status = orderDetails[3];

                if (orderStack.containsKey(OrderID)) {
                    OrderStackObject orderStackObject = orderStack.get(OrderID);


                    orderStackObject.updateOrderIdColor(status);

                    HashMap<String, FoodObjectInOrderStack> hm1 = orderStackObject.getProdDetails();


                    if (hm1.containsKey(ProductID)) {
                        FoodObjectInOrderStack foodObjectInOrderStack = hm1.get(ProductID);

                        FoodObjectInOrderStack.updateOrderStatus(foodObjectInOrderStack, status);

                        //foodObjectInOrderStack.orderDraw();
                    }
                }

                System.out.println("Updated order stack  : " + orderStack);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
