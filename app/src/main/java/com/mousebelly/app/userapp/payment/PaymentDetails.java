package com.mousebelly.app.userapp.payment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mousebelly.app.userapp.GenerateOrderId;
import com.mousebelly.app.userapp.Login.LoginActivity;
import com.mousebelly.app.userapp.R;
import com.mousebelly.app.userapp.Server;
import com.mousebelly.app.userapp.WalletHandler;
import com.mousebelly.app.userapp.orderStack.OrderStackMainActivity;
import com.mousebelly.app.userapp.products.Cart;
import com.mousebelly.app.userapp.products.ProductUnit;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

public class PaymentDetails extends AppCompatActivity {

    public static String orderID;
    TextView TotalBill, CustomerId, OrderId;
    EditText PromoCode;
    Button useWallet, Pay;
    WebView webView;
    ProgressBar WebProgresesBar;
    JSONObject jobj;
    LinearLayout paymentDetailsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        OrderId = (TextView) findViewById(R.id.order_id);
        CustomerId = (TextView) findViewById(R.id.customer_id);
        TotalBill = (TextView) findViewById(R.id.total_price);
        PromoCode = (EditText) findViewById(R.id.promo_code);
        useWallet = (Button) findViewById(R.id.usewallet);
        Pay = (Button) findViewById(R.id.pay);
        paymentDetailsLayout = (LinearLayout) findViewById(R.id.payment_details_layout);
        WebProgresesBar = (ProgressBar) findViewById(R.id.web_progress);

        webView = (WebView) findViewById(R.id.WebView);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setInitialScale(100);
        webView.getSettings().setDomStorageEnabled(true);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webSettings.setAllowContentAccess(true);
        webView.getProgress();

        orderID = GenerateOrderId.getOrderId();

        OrderId.setText(orderID);
        TotalBill.setText(String.valueOf(Cart.cartAmount));
        CustomerId.setText(LoginActivity.USERID);


        // Order JSON

        jobj = new JSONObject();


        System.out.println("ORDER ID ::::: " + orderID);


        // pay with bank

        Pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //hide layout when click on payment button

                paymentDetailsLayout.setVisibility(View.GONE);

                try {
                    jobj.put("UserEmail_id", LoginActivity.USERID);

                    JSONObject productid = new JSONObject();

                    Iterator it = Cart.cartItems.keySet().iterator();
                    while (it.hasNext()) {
                        ProductUnit p = Cart.cartItems.get(it.next());

                        JSONObject product = new JSONObject();
                        product.put("PID", p.getProd_id());
                        product.put("Product_Name", p.getProd_name());
                        product.put("status", "ordered");
                        product.put("HWID", p.getHWFEmail_id());
                        product.put("Qty", p.getQty());
                        product.put("stars", 0);
                        product.put("Price_item", p.getPrice());
                        product.put("orderid", orderID);
                        product.put("Image", p.getImage());

                        productid.put(p.getProd_id(), product);

                    }

                    jobj.put("Product_Details", productid);
                    jobj.put("Total_bill", Cart.cartAmount);

                    jobj.put("order_Id", orderID);

                    Date myDate = new Date();
                    String date = (new SimpleDateFormat("d-M-yyyy / HH:mm").format(myDate));

                    jobj.put("order_date", date);
                    jobj.put("feedbackDone", false);

                    System.out.println("ORDER OBJECT : " + jobj);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                String dataSet[] =
                        {
                                "merchant_id", "124827"
                                ,
                                "order_id", orderID
                                ,
                                "currency", "INR"
                                ,
                                "amount", String.valueOf(Cart.cartAmount)
                                ,
                                "redirect_url", "https://mousebelly.herokuapp.com"
                                ,
                                "cancel_url", "https://mousebelly.herokuapp.com"
                                ,
                                "language", "EN"
                                ,
                                "billing_name", LoginActivity.user.getU_name()
                                ,
                                "billing_address", LoginActivity.user.getStreet_name()
                                ,
                                "billing_city", LoginActivity.user.getCity_name()
                                ,
                                "billing_state", LoginActivity.user.getState_name()
                                ,
                                "billing_zip", LoginActivity.user.getZip_code()
                                ,
                                "billing_country", "India"
                                ,
                                "billing_tel", LoginActivity.user.getPhone_No()
                                ,
                                "billing_email", LoginActivity.user.getUserEmail_id()
                                ,
                                "promo_code", ""
                                ,
                                "customer_identifier", LoginActivity.user.getUserEmail_id()
                        };

                String data = "<form method='post' action='http://mousebelly.herokuapp.com/ccavRequestHandler'>";

                for (int i = 0; i < dataSet.length; i += 2) {
                    data += "<input type='hidden' name='" + dataSet[i] + "' value='" + dataSet[i + 1] + "' />";
                }

                data += "<center><input type='submit' style=' font-size: 60px;color:#FFFFFF; margin-top:100px; color: #FFFFFF; background-color: #3F51B5;border: 3pt #1A237E; padding:10px; font-weight: bold;' value='CHECK OUT'></center>";
                // data += "<input type='submit' value='CheckOut'>";

                data += "</form>";

                int status = Server.s.post("http://mousebelly.herokuapp.com/order/order", jobj);

                webView.setWebViewClient(new WebViewClient() {
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        //view.loadData(finalData, "text/html; charset=utf-8", "UTF-8");

                        System.out.println("URL: " + url);
                        if (url.equals("https://mousebelly.herokuapp.com/")) {
                            Intent webviewIntent = new Intent(PaymentDetails.this, Successfull.class);
                            startActivity(webviewIntent);
                        } else {
                    /*Intent webViewfailIntent = new Intent(PaymentDetails.this, Successfull.class);
                    startActivity(webViewfailIntent);*/
                        }

                        return false;
                    }
                });

                webView.loadData(data, "text/html; charset=utf-8", "UTF-8");


            }
        });


        // Pay with Wallet

        useWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try {
                    jobj.put("UserEmail_id", LoginActivity.USERID);

                    JSONObject productid = new JSONObject();

                    Iterator it = Cart.cartItems.keySet().iterator();
                    while (it.hasNext()) {
                        ProductUnit p = Cart.cartItems.get(it.next());

                        JSONObject product = new JSONObject();
                        product.put("PID", p.getProd_id());
                        product.put("Product_Name", p.getProd_name());
                        product.put("status", "ordered");
                        product.put("HWID", p.getHWFEmail_id());
                        product.put("Qty", p.getQty());
                        product.put("stars", 0);
                        product.put("Price_item", p.getPrice());
                        product.put("orderid", orderID);
                        product.put("Image", p.getImage());

                        productid.put(p.getProd_id(), product);

                    }

                    jobj.put("Prod_details", productid);
                    jobj.put("Total_bill", Cart.cartAmount);

                    jobj.put("order_Id", orderID);

                    Date myDate = new Date();
                    String date = (new SimpleDateFormat("d-M-yyyy / HH:mm").format(myDate));

                    jobj.put("order_date", date);
                    jobj.put("feedbackDone", false);

                    System.out.println("ORDER OBJECT : " + jobj);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                System.out.println("Cart ITEMS : " + Cart.cartItems);

                // check balance
                if (WalletHandler.WalletAmount < Cart.cartAmount) {
                    Toast.makeText(PaymentDetails.this, "Not Sufficient Amount", Toast.LENGTH_SHORT).show();
                    return;
                }

                int amount = WalletHandler.WalletAmount - Cart.cartAmount;

                //WalletHandler.cutFromWallet(amount);

                //WalletHandler.setWalletTotalAmount();

                final Dialog walletDialog = new Dialog(PaymentDetails.this);

                walletDialog.setTitle("Order Details");
                walletDialog.setContentView(R.layout.wallet_payment_dialog_box);


                TextView OrderID = (TextView) walletDialog.findViewById(R.id.order_id);
                TextView RemainingBal = (TextView) walletDialog.findViewById(R.id.remaining_balance);

                OrderID.setText("Order ID : " + orderID);

                int status = Server.s.post("http://mousebelly.herokuapp.com/order/walletOrder", jobj);

                if (status == 200) {

                    WalletHandler.cutFromWallet(amount);

                    //Server.s.put("http://mousebelly.herokuapp.com/sign/walletRemainbal/" + LoginActivity.USERID + "/" + WalletHandler.WalletAmount);

                    RemainingBal.setText("Remaining Balance : ₹" + String.valueOf(WalletHandler.WalletAmount));

                }

                Button ok = (Button) walletDialog.findViewById(R.id.ok);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        walletDialog.dismiss();
                        Intent intent = new Intent(PaymentDetails.this, OrderStackMainActivity.class);
                        startActivity(intent);
                    }
                });

                walletDialog.show();
            }
        });
    }
}