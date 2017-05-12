package com.mousebelly.app.userapp.payment;

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

import com.mousebelly.app.userapp.CustomToast;
import com.mousebelly.app.userapp.GenerateOrderId;
import com.mousebelly.app.userapp.Login.LoginActivity;
import com.mousebelly.app.userapp.R;
import com.mousebelly.app.userapp.Server;
import com.mousebelly.app.userapp.WalletHandler;
import com.mousebelly.app.userapp.wallet.Wallet;

import org.json.JSONObject;

public class AddToWalletPayment extends AppCompatActivity {

    public static String orderID;
    TextView TotalBill, CustomerId, OrderId;
    EditText PromoCode;
    Button useWallet, Pay;
    WebView webView;
    LinearLayout hidden_layout;
    ProgressBar WebProgresesBar;
    JSONObject jobj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_payment_details);

        OrderId = (TextView) findViewById(R.id.order_id);
        CustomerId = (TextView) findViewById(R.id.customer_id);
        TotalBill = (TextView) findViewById(R.id.total_price);
        PromoCode = (EditText) findViewById(R.id.promo_code);
        useWallet = (Button) findViewById(R.id.usewallet);
        Pay = (Button) findViewById(R.id.pay);
        //hidden_layout = (LinearLayout) findViewById(R.id.hiddenlayout);
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

        Intent intent = getIntent();

        final String AmountToAdd = intent.getStringExtra("AmountToAdd");

        // orderID = GenerateOrderId.getOrderId();

        String oid = GenerateOrderId.getOrderId().replaceAll("[^0-9]", "");


        orderID = "wp" + oid;

        OrderId.setText(orderID);
        TotalBill.setText(AmountToAdd);
        CustomerId.setText(LoginActivity.USERID);


        // Order JSON

        jobj = new JSONObject();


        System.out.println("ORDER ID ::::: " + orderID);


        // pay with bank

        Pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String dataSet[] =
                        {
                                "merchant_id", "124827"
                                ,
                                "order_id", orderID
                                ,
                                "currency", "INR"
                                ,
                                "amount", AmountToAdd
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
                            // WalletHandler.addToWallet(Integer.parseInt(AmountToAdd));
                            CustomToast.Toast(AddToWalletPayment.this, "Amount Added to Wallet");
                            WalletHandler.setWalletTotalAmount();
                            Intent webviewIntent = new Intent(AddToWalletPayment.this, Wallet.class);
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


    }
}