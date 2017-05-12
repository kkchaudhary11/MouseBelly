package com.mousebelly.app.userapp.payment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mousebelly.app.userapp.R;
import com.mousebelly.app.userapp.Server;
import com.mousebelly.app.userapp.orderStack.OrderStackMainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Successfull extends AppCompatActivity {

    List<String> OrderIDArray;
    List<String> OrderUserArray;
    List<String> OrderQuantityArray;
    List<String> OrderHwfNameArray;
    List<String> OrderProductNameArray;
    List<String> OrderProductIDArray;
    List<String> OrderStatusArray;
    List<String> OrderTotalBillArray;
    List<String> OrderPaymentStatusArray;
    List<String> OrderOrderDateArray;
    List<String> OrderSystemIDArray;

    String OrderID;

    String OrderPaymentStatus;
    String OrderTotalBill;

    ProgressBar pb;
    String jsonstr;
    TextView payorderid, paystatus, paybill, thankyou, transation;
    ImageView paid, fail;
    Button cont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successfull);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            payorderid = (TextView) findViewById(R.id.orderidofpaiditem);
            paystatus = (TextView) findViewById(R.id.statusofpaiditem);
            paybill = (TextView) findViewById(R.id.totalbillofpaiditem);
            cont = (Button) findViewById(R.id.cont);
            cont.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Successfull.this, OrderStackMainActivity.class);
                    startActivity(intent);
                }
            });

            thankyou = (TextView) findViewById(R.id.thankyou);
            transation = (TextView) findViewById(R.id.transaction);
            fail = (ImageView) findViewById(R.id.fail);
            paid = (ImageView) findViewById(R.id.paid);
            pb = (ProgressBar) findViewById(R.id.order_progress);
            new PaySuc().execute();
            // ORDERID = cart.getOrderId();
            System.out.println("This is order id of User :::::::::" + PaymentDetails.orderID);
        }
    }

    public class PaySuc extends AsyncTask<Void, Void, Void> {


        protected void onPreExecute() {
            super.onPreExecute();
            pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            jsonstr = Server.s.get("http://mousebelly.herokuapp.com/order/order3/" + PaymentDetails.orderID);
            if (jsonstr != null) {
                try {
                    JSONArray jaar = new JSONArray(jsonstr);

                    OrderIDArray = new ArrayList<>();
                    OrderUserArray = new ArrayList<>();
                    OrderQuantityArray = new ArrayList<>();
                    OrderHwfNameArray = new ArrayList<>();
                    OrderProductNameArray = new ArrayList<>();
                    OrderProductIDArray = new ArrayList<>();
                    OrderStatusArray = new ArrayList<>();
                    OrderTotalBillArray = new ArrayList<>();
                    OrderPaymentStatusArray = new ArrayList<>();
                    OrderOrderDateArray = new ArrayList<>();
                    OrderSystemIDArray = new ArrayList<>();


                    for (int i = 0; i < jaar.length(); i++) {
                        JSONObject jsonObject = jaar.getJSONObject(i);
                        String OrderSystemID = jsonObject.getString("_id");
                        String OrderUserEmail = jsonObject.getString("UserEmail_id");
                        OrderTotalBill = jsonObject.getString("Total_bill");
                        OrderPaymentStatus = jsonObject.getString("Payment_status");
                        String OrderOrderDate = jsonObject.getString("order_date");
                        String Order_ID = jsonObject.getString("order_Id");

                        JSONObject object = new JSONObject(jsonObject.getString("Prod_details"));
                        Iterator it = object.keys();
                        while (it.hasNext()) {
                            String f = it.next().toString();
                            JSONObject object1 = new JSONObject(object.getString(f));
                            OrderID = object1.getString("orderid");
                            String OrderQuantity = object1.getString("Qty");
                            String OrderHWFName = object1.getString("HWID");
                            String OrderProductName = object1.getString("Product_Name");
                            String OrderProductID = object1.getString("PID");
                            String OrderStatus = object1.getString("status");

                            /*if((OrderStatus.equals("delivered") || OrderStatus.equals("paid")) && (showHistoryStatus==false)) {
                                // do not include paid and delivered items in list until show History is true

                            }else{*/
                            OrderUserArray.add(OrderUserEmail);
                            OrderIDArray.add(OrderID);
                            OrderQuantityArray.add(OrderQuantity);
                            OrderHwfNameArray.add(OrderHWFName);
                            OrderProductNameArray.add(OrderProductName);
                            OrderProductIDArray.add(OrderProductID);
                            OrderStatusArray.add(OrderStatus);
                            OrderSystemIDArray.add(OrderSystemID);
                            OrderTotalBillArray.add(OrderTotalBill);
                            OrderPaymentStatusArray.add(OrderPaymentStatus);
                            OrderOrderDateArray.add(OrderOrderDate);
                        }
                        System.out.println("UBE = " + OrderIDArray);
                        System.out.println("UBN = " + OrderQuantityArray);
                        System.out.println("UBP = " + OrderHwfNameArray);
                        System.out.println("UBA = " + OrderProductNameArray);
                        System.out.println("UBE = " + OrderProductIDArray);
                        System.out.println("UBN = " + OrderStatusArray);

                        //}
                        System.out.println("_id   :" + OrderSystemID);
                        System.out.println("UserEmail_id" + OrderUserEmail);

                        System.out.println("Bill = " + OrderTotalBill);
                        System.out.println("Order Payment = " + OrderPaymentStatus);
                        System.out.println("Order Date = " + OrderOrderDate);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        protected void onPostExecute(Void result) {
            super.onPreExecute();
            if (OrderPaymentStatus.equals("Success")) {
                paid.setVisibility(View.VISIBLE);
                thankyou.setVisibility(View.VISIBLE);
                transation.setVisibility(View.VISIBLE);
                payorderid.setText(OrderID);
                paystatus.setText(OrderPaymentStatus);
                paybill.setText(OrderTotalBill);
            } else {

                // fail.setVisibility(View.VISIBLE);
                payorderid.setText(OrderID);
                paystatus.setText(OrderPaymentStatus);
                paybill.setText(OrderTotalBill);
            }

            pb.setVisibility(View.GONE);

            if (jsonstr == null) {
                Toast.makeText(getApplicationContext(), "Unable to load data.Check your Internet Connection", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }


}
