package com.mousebelly.app.userapp.orderStack;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.mousebelly.app.userapp.IdManager;
import com.mousebelly.app.userapp.R;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Kamal Kant on 14-04-2017.
 */

public class OrderStackObject {

    static int Id = 0;
    int myId;
    RelativeLayout relativeLayout;
    RelativeLayout topRelativeLayout;
    private String _id;
    private String userId;
    private String Total_bill;
    private String Payment_status;
    private String order_date;
    private String OrderId;
    private HashMap<String, FoodObjectInOrderStack> prodDetails = new HashMap<>();
    private TextView OrderDateTextView;
    private TextView OrderIdTextView;
    private TextView TotalAmountTextView;
    private ScrollView scrollView;
    private LinearLayout scrollViewLinearLayout;

    public OrderStackObject() {

        Id++;
        this.myId = Id;

        this.relativeLayout = new RelativeLayout(OrderStackMainActivity.context);

        this.topRelativeLayout = new RelativeLayout(OrderStackMainActivity.context);
        IdManager.addId("topRelativeLayout" + this.myId);
        this.topRelativeLayout.setId((int) IdManager.stringToIdMap.get("topRelativeLayout" + this.myId));


        this.OrderDateTextView = new TextView(OrderStackMainActivity.context);
        IdManager.addId("OrderDateTextView" + this.myId);
        this.OrderDateTextView.setId((int) IdManager.stringToIdMap.get("OrderDateTextView" + this.myId));

        this.OrderIdTextView = new TextView(OrderStackMainActivity.context);
        IdManager.addId("OrderIdTextView" + this.myId);
        this.OrderIdTextView.setId((int) IdManager.stringToIdMap.get("OrderIdTextView" + this.myId));

        this.TotalAmountTextView = new TextView(OrderStackMainActivity.context);
        IdManager.addId("TotalAmountTextView" + this.myId);
        this.TotalAmountTextView.setId((int) IdManager.stringToIdMap.get("TotalAmountTextView" + this.myId));

        this.scrollView = new ScrollView(OrderStackMainActivity.context);
        IdManager.addId("OrderStackObjectScrollView" + this.myId);
        this.scrollView.setId((int) IdManager.stringToIdMap.get("OrderStackObjectScrollView" + this.myId));

        this.scrollViewLinearLayout = new LinearLayout(OrderStackMainActivity.context);
        IdManager.addId("OrderStackObjectScrollViewLinearLayout" + this.myId);
        this.scrollViewLinearLayout.setId((int) IdManager.stringToIdMap.get("OrderStackObjectScrollViewLinearLayout" + this.myId));

    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTotal_bill() {
        return Total_bill;
    }

    public void setTotal_bill(String total_bill) {
        Total_bill = total_bill;
        this.TotalAmountTextView.setText("Total : ₹" + total_bill);
    }

    public String getPayment_status() {
        return Payment_status;
    }

    public void setPayment_status(String payment_status) {
        Payment_status = payment_status;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        String[] datetime = order_date.split("/");
        String date = datetime[0];

        String dateAtoms[] = date.split("-");

        for (int i = 0; i < dateAtoms.length; i++) {
            if (dateAtoms[i].length() == 1)
                dateAtoms[i] = "0" + dateAtoms[i];
        }

        String time = datetime[1];
        order_date = "Date: " + dateAtoms[0] + "/" + dateAtoms[1] + "/" + dateAtoms[2] + " ";
        this.order_date = order_date;
        this.OrderDateTextView.setText(order_date);
    }

    public String getOrderId() {
        return OrderId;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void setOrderId(String orderId) {

        OrderId = orderId;
        this.OrderIdTextView.setText("Order Id: " + orderId);

    }

    public HashMap<String, FoodObjectInOrderStack> getProdDetails() {
        return prodDetails;
    }

    public void setProdDetails(HashMap<String, FoodObjectInOrderStack> prodDetails) {
        this.prodDetails = prodDetails;
    }

    @Override
    public String toString() {
        return "FeedOrderStackObject{" +
                "_id='" + _id + '\'' +
                ", userId='" + userId + '\'' +
                ", Total_bill='" + Total_bill + '\'' +
                ", Payment_status='" + Payment_status + '\'' +
                ", order_date='" + order_date + '\'' +
                ", OrderId='" + OrderId + '\'' +
                ", prodDetails=" + prodDetails +
                '}';
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public RelativeLayout draw() {

        try {

            GradientDrawable shape = new GradientDrawable();
            shape.setCornerRadius(8);
            shape.setStroke(5, Color.BLACK);
            relativeLayout.setBackground(shape);

            this.relativeLayout = new RelativeLayout(OrderStackMainActivity.context);


            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(OrderStackMainActivity.displayWidth, RelativeLayout.LayoutParams.WRAP_CONTENT);

            this.topRelativeLayout.setLayoutParams(params);
            this.topRelativeLayout.setBackgroundColor(ContextCompat.getColor(OrderStackMainActivity.context, R.color.Bossanova));

            Typeface tahomabd = Typeface.createFromAsset(OrderStackMainActivity.context.getAssets(), "fonts/tahomabd.ttf");
            // Typeface trumpitFaceHeader = Typeface.createFromAsset(OrderStackOrderStackMainActivity.getAssets(), "fonts/Trumpit.ttf");

            GradientDrawable shape3 = new GradientDrawable();
            //shape2.setStroke(5,Color.BLACK);
            float corners[] = {25, 25, 25, 25, 0, 0, 0, 0};
            shape3.setCornerRadii(corners);
            shape3.setColor(ContextCompat.getColor(OrderStackMainActivity.context, R.color.Affair));

            params = new RelativeLayout.LayoutParams(OrderStackMainActivity.displayWidth - 100, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.CENTER_HORIZONTAL);
            params.setMargins(20, 50, 20, 0);
            this.OrderIdTextView.setPadding(20, 20, 20, 20);
            this.OrderIdTextView.setLayoutParams(params);
            this.OrderIdTextView.setTextColor(ContextCompat.getColor(OrderStackMainActivity.context, R.color.Rose_White));
            this.OrderIdTextView.setGravity(Gravity.CENTER);
            this.OrderIdTextView.setBackground(shape3);
            this.OrderIdTextView.setTypeface(tahomabd);
            this.OrderIdTextView.setTextSize(20);
            this.topRelativeLayout.addView(this.OrderIdTextView);

            params = new RelativeLayout.LayoutParams(OrderStackMainActivity.displayWidth - 100, RelativeLayout.LayoutParams.WRAP_CONTENT);

            GradientDrawable shape2 = new GradientDrawable();
            //shape2.setStroke(5,Color.BLACK);
            float corners1[] = {0, 0, 0, 0, 25, 25, 25, 25};
            shape2.setCornerRadii(corners1);
            shape2.setColor(ContextCompat.getColor(OrderStackMainActivity.context, R.color.Amulet));
            params.addRule(RelativeLayout.BELOW, this.OrderIdTextView.getId());
            params.addRule(RelativeLayout.CENTER_HORIZONTAL);

            params.setMargins(20, 0, 20, 20);
            this.OrderDateTextView.setPadding(120, 60, 120, 60);
            this.OrderDateTextView.setTextColor(ContextCompat.getColor(OrderStackMainActivity.context, R.color.Rose_White));
            this.OrderDateTextView.setTextSize(25);
            this.OrderDateTextView.setBackgroundColor(ContextCompat.getColor(OrderStackMainActivity.context, R.color.Amulet));
            this.OrderDateTextView.setBackground(shape2);
            this.OrderDateTextView.setLayoutParams(params);

            this.OrderDateTextView.setTypeface(tahomabd);
            this.topRelativeLayout.addView(this.OrderDateTextView);

            /*int OrderDateTextViewWidth = this.OrderDateTextView.getLayoutParams().width;

            this.OrderIdTextView.setWidth(OrderDateTextViewWidth);*/

            params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.BELOW, this.OrderDateTextView.getId());
            params.addRule(RelativeLayout.CENTER_HORIZONTAL);
            params.setMargins(100, 10, 10, 10);
            this.TotalAmountTextView.setTextColor(ContextCompat.getColor(OrderStackMainActivity.context, R.color.Rose_White));
            this.TotalAmountTextView.setTextSize(20);
            this.TotalAmountTextView.setTypeface(null, Typeface.BOLD);
            this.TotalAmountTextView.setLayoutParams(params);
            this.TotalAmountTextView.setPadding(120, 30, 120, 30);
            this.topRelativeLayout.addView(this.TotalAmountTextView);

            relativeLayout.addView(topRelativeLayout);

            params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            params.addRule(RelativeLayout.BELOW, this.topRelativeLayout.getId());
            params.setMargins(10, 10, 10, 10);
            this.scrollView.setLayoutParams(params);
            this.relativeLayout.addView(this.scrollView);

            params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            params.setMargins(10, 10, 10, 10);
            this.scrollViewLinearLayout.setLayoutParams(params);
            this.scrollViewLinearLayout.setOrientation(LinearLayout.VERTICAL);
            this.scrollView.addView(this.scrollViewLinearLayout);


            HashMap<String, FoodObjectInOrderStack> hm = this.getProdDetails();

            Iterator i = hm.keySet().iterator();

            int count = 0;

            while (i.hasNext()) {
                count++;
                System.out.println("COUNT:" + count);

                String key = i.next().toString();

                System.out.println("PROD ID:");
                System.out.println(key);

                FoodObjectInOrderStack foodObjectInOrderStack = hm.get(key);

                RelativeLayout rl = foodObjectInOrderStack.orderDraw();

                if (this.scrollViewLinearLayout.getChildCount() == 0)
                    this.scrollViewLinearLayout.addView(rl);

                else {
                    RelativeLayout rl1 = (RelativeLayout) this.scrollViewLinearLayout.getChildAt(this.scrollViewLinearLayout.getChildCount() - 1);

                    params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    params.addRule(RelativeLayout.BELOW, rl1.getId());
                    params.setMargins(10, 10, 10, 10);
                    rl.setLayoutParams(params);
                    this.scrollViewLinearLayout.addView(rl);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this.relativeLayout;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void updateOrderIdColor(String status) {

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void run() {
                GradientDrawable shape3 = new GradientDrawable();
                //shape2.setStroke(5,Color.BLACK);
                float corners[] = {25, 25, 25, 25, 0, 0, 0, 0};
                shape3.setCornerRadii(corners);

                shape3.setColor(ContextCompat.getColor(OrderStackMainActivity.context, R.color.Cannon_Pink));

                OrderIdTextView.setBackground(shape3);
            }
        }, 100);


    }


}
