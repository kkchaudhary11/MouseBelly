package com.mousebelly.app.userapp.mealPlanner;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mousebelly.app.userapp.CustomToast;
import com.mousebelly.app.userapp.GenerateOrderId;
import com.mousebelly.app.userapp.IdManager;
import com.mousebelly.app.userapp.R;
import com.mousebelly.app.userapp.Server;
import com.mousebelly.app.userapp.WalletHandler;
import com.mousebelly.app.userapp.wallet.Wallet;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import static com.mousebelly.app.userapp.Login.LoginActivity.USERID;


public class MealPlannerMainActivity extends AppCompatActivity {


    public static TextView totalAmount;
    public static LinearLayout staticCartLinearLayout;
    public static Dialog MealPlanCartdialog;
    public static MenuItem walletMenu;
    static LinearLayout l;
    static LinearLayout productDataLayout;
    static Context context;
    static ProgressBar mealPlannerProgressBar;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meal_planner_activity_main);

        mealPlannerProgressBar = (ProgressBar) findViewById(R.id.mealplanner_progress_bar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);


        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        MealPlannerMainActivity.context = MealPlannerMainActivity.this;


        LoadProducts loadProducts = new LoadProducts();
        loadProducts.execute();

        /*String respMealData = Server.s.get("https://mousebelly.herokuapp.com/mealplan/mealplan/");

        System.out.println("Meal Plan:");
        System.out.println(respMealData);


        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ProductsDataLayout);

        productDataLayout = linearLayout;

        MealPlan mealPlan = new MealPlan(MealPlannerMainActivity.this);

        mealPlan.loadData(respMealData);
*/

       /* LinearLayout melplanlayout = (LinearLayout) findViewById(R.id.MealPlanLinearLayout);

        MealPlannerMainActivity.l = melplanlayout;

        String resp = Server.s.get("https://mousebelly.herokuapp.com/preorder/preorderbymail/" + USERID);

        System.out.println("Order Plan:");
        System.out.println(resp);

        //Cart.setWalletTotalAmount();

        Date dt = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, d MMMM yyyy");


        for (int k = 0; k < 7; k++) {
            final RelativeLayout dateRelativeLayout = new RelativeLayout(getApplicationContext());

            ShapeDrawable rectShapeDrawable = new ShapeDrawable(); // pre defined class

            // get paint
            Paint paint = rectShapeDrawable.getPaint();

            // set border color, stroke and stroke width
            paint.setColor(ContextCompat.getColor(MealPlannerMainActivity.context,R.color.Amulet));
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(5); // you can change the value of 5
            paint.setShadowLayer(10, 10, 10, Color.BLACK);

            dateRelativeLayout.setBackgroundDrawable(rectShapeDrawable);

            final TextView dateValue = new TextView(getApplicationContext());

            IdManager.addId("DateTextView" + k);
            dateValue.setId((int) IdManager.stringToIdMap.get("DateTextView" + k));


            //cal.add(Calendar.DATE,k);

            if (k == 0)
                cal.add(Calendar.DATE, 0);
            else
                cal.add(Calendar.DATE, 1);

            dt = cal.getTime();
            //dateValue.setTag(formatter.format(dt).toString());

            dateValue.setText(formatter.format(dt).toString());
            dateValue.setPadding(10, 10, 10, 10);
            dateValue.setWidth(150);
            dateValue.setTextColor(Color.BLACK);

            dateRelativeLayout.addView(dateValue);
            melplanlayout.addView(dateRelativeLayout);
            //dateLayout.put(dateValue.getTag().toString(),melplanlayout);
            //System.out.println("Adding Date");
            //System.out.println(formatter.format(dt).toString());
            DateLayout.dateLayout.put(formatter.format(dt).toString(), dateRelativeLayout);
        }

        System.out.println("Meal Plan Layout : " + DateLayout.dateLayout);

        OrderPlan.loadData(resp);*/


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Cart.cartItems.isEmpty()) {
                    Cart.cartAmount = 0;
                    CustomToast.Toast(MealPlannerMainActivity.context, "Cart is empty");
                    return;
                }

                MealPlanCartdialog = new Dialog(MealPlannerMainActivity.this);
                MealPlanCartdialog.setTitle("Cart");
                MealPlanCartdialog.setContentView(R.layout.meal_planner_user_cart_dialogbox);

                Iterator it = Cart.cartItems.keySet().iterator();

                LinearLayout cartLinearLayout = (LinearLayout) MealPlanCartdialog.findViewById(R.id.CartLinearLayout);

                MealPlannerMainActivity.staticCartLinearLayout = cartLinearLayout;

                totalAmount = (TextView) MealPlanCartdialog.findViewById(R.id.totalAmount);
                System.out.println("Cart Amount : " + Cart.cartAmount);
                totalAmount.setText(String.valueOf(Cart.cartAmount));

                while (it.hasNext()) {
                    FoodObject foodObject = Cart.cartItems.get(it.next());
                    RelativeLayout rl = FoodObject.showCart(foodObject);

                    cartLinearLayout.addView(rl);

                }


                Button dialogButtonOK = (Button) MealPlanCartdialog.findViewById(R.id.dialogButtonOK);

                dialogButtonOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String orderID = GenerateOrderId.getOrderId();

                        if (WalletHandler.WalletAmount < Cart.cartAmount) {
                            CustomToast.Toast(MealPlannerMainActivity.context, "Not sufficient amount");
                            return;
                        }
                        if (Cart.cartItems.isEmpty()) {
                            CustomToast.Toast(MealPlannerMainActivity.context, "Add something to cart");
                            return;
                        }

                        //send data to server

                        // Server.s.post(("http://mousebelly.herokuapp.com/preorder/walletOrder",newitem);

                        //update wallet
                        int amount = WalletHandler.WalletAmount - Cart.cartAmount;
                        //System.out.println("New Wallet Amount : " + WalletHandler.WalletHandler);
                        WalletHandler.cutFromWallet(amount);
                        //Server.s.put("http://mousebelly.herokuapp.com/sign/walletRemainbal/" + USERID + "/" + WalletHandler.WalletHandler);
                        // WalletHandler.setWalletTotalAmount();
                        MealPlannerMainActivity.walletMenu.setTitle("₹" + String.valueOf(WalletHandler.WalletAmount));


                        //update cart
                        Cart.addCartToOrderPlan(orderID);
                        Cart.cartAmount = 0;
                        if (MealPlannerMainActivity.totalAmount != null)
                            MealPlannerMainActivity.totalAmount.setText(String.valueOf(Cart.cartAmount));
                        CustomToast.Toast(MealPlannerMainActivity.context, "Order ID : " + orderID);
                        MealPlanCartdialog.dismiss();
                    }
                });


                Button dialogButtonCancel = (Button) MealPlanCartdialog.findViewById(R.id.dialogButtonCancel);

                dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MealPlanCartdialog.dismiss();
                    }
                });

                MealPlanCartdialog.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        System.out.println("Menu Executed ");
        getMenuInflater().inflate(R.menu.meal_plan_menu, menu);
        walletMenu = menu.findItem(R.id.money);
        walletMenu.setTitle("₹" + String.valueOf(WalletHandler.WalletAmount));

        //go to wallet on tap the amount

        walletMenu.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent walletActivity = new Intent(MealPlannerMainActivity.this, Wallet.class);
                startActivity(walletActivity);
                return false;
            }
        });

        return true;
    }


    public class LoadProducts extends AsyncTask<Void, Void, Void> {

        //ProgressDialog pdLoading = new ProgressDialog(MealPlannerMainActivity.this);
        // String resp;


        String respMealData;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mealPlannerProgressBar.setVisibility(View.VISIBLE);
            //pg.setVisibility(View.VISIBLE);
          /*  pdLoading.setMessage("\t Loading...");
            pdLoading.show();*/

            //this method will be running on UI thread

        }

        @Override
        protected Void doInBackground(Void... voids) {


            respMealData = Server.s.get("https://mousebelly.herokuapp.com/mealplan/mealplan/");

            System.out.println("Meal Plan:");
            System.out.println(respMealData);

            //
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ProductsDataLayout);

            productDataLayout = linearLayout;


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            MealPlan mealPlan = new MealPlan(MealPlannerMainActivity.this);

            mealPlan.loadData(respMealData);

            // GetProductsData.loadData();
//            pg.setVisibility(View.GONE);

            //this method will be running on UI thread

            // pdLoading.dismiss();

            LoadMealPlan loadMealPlan = new LoadMealPlan();
            loadMealPlan.execute();

        }

    }


    public class LoadMealPlan extends AsyncTask<Void, Void, Void> {

        // ProgressDialog pdLoading = new ProgressDialog(MealPlannerMainActivity.this);
        String resp;
        LinearLayout melplanlayout;


        String respMealData;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            mealPlannerProgressBar.setVisibility(View.VISIBLE);

            //pg.setVisibility(View.VISIBLE);
           /* pdLoading.setMessage("\t Loading...");
            pdLoading.show();*/

            //this method will be running on UI thread

        }

        @Override
        protected Void doInBackground(Void... voids) {

            melplanlayout = (LinearLayout) findViewById(R.id.MealPlanLinearLayout);

            MealPlannerMainActivity.l = melplanlayout;

            resp = Server.s.get("https://mousebelly.herokuapp.com/preorder/preorderbymail/" + USERID);

            System.out.println("Order Plan:");
            System.out.println(resp);


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            Date dt = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(dt);
            SimpleDateFormat formatter = new SimpleDateFormat("EEEE, d MMMM yyyy");


            for (int k = 0; k < 7; k++) {
                final RelativeLayout dateRelativeLayout = new RelativeLayout(getApplicationContext());

                ShapeDrawable rectShapeDrawable = new ShapeDrawable(); // pre defined class

                // get paint
                Paint paint = rectShapeDrawable.getPaint();

                // set border color, stroke and stroke width
                paint.setColor(ContextCompat.getColor(MealPlannerMainActivity.context, R.color.Amulet));
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(5); // you can change the value of 5
                paint.setShadowLayer(10, 10, 10, Color.BLACK);

                dateRelativeLayout.setBackgroundDrawable(rectShapeDrawable);

                final TextView dateValue = new TextView(getApplicationContext());

                IdManager.addId("DateTextView" + k);
                dateValue.setId((int) IdManager.stringToIdMap.get("DateTextView" + k));


                //cal.add(Calendar.DATE,k);

                if (k == 0)
                    cal.add(Calendar.DATE, 0);
                else
                    cal.add(Calendar.DATE, 1);

                dt = cal.getTime();
                //dateValue.setTag(formatter.format(dt).toString());

                dateValue.setText(formatter.format(dt).toString());
                dateValue.setPadding(20, 50, 20, 50);
                dateValue.setWidth(250);
                dateValue.setTextColor(Color.BLACK);


                dateRelativeLayout.addView(dateValue);
                melplanlayout.addView(dateRelativeLayout);
                //dateLayout.put(dateValue.getTag().toString(),melplanlayout);
                //System.out.println("Adding Date");
                //System.out.println(formatter.format(dt).toString());
                DateLayout.dateLayout.put(formatter.format(dt).toString(), dateRelativeLayout);
            }

            System.out.println("Meal Plan Layout : " + DateLayout.dateLayout);

            OrderPlan.loadData(resp);
            // GetProductsData.loadData();
//            pg.setVisibility(View.GONE);

            //this method will be running on UI thread

            // pdLoading.dismiss();

            mealPlannerProgressBar.setVisibility(View.GONE);
        }

    }


}