package com.mousebelly.app.userapp.products;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.mousebelly.app.userapp.CurrentContext;
import com.mousebelly.app.userapp.CustomToast;
import com.mousebelly.app.userapp.Login.LoginActivity;
import com.mousebelly.app.userapp.R;
import com.mousebelly.app.userapp.Server;
import com.mousebelly.app.userapp.favourite.favMainActivity;
import com.mousebelly.app.userapp.feedback.LoadOrdersForFeedback;
import com.mousebelly.app.userapp.mealPlanner.MealPlannerMainActivity;
import com.mousebelly.app.userapp.orderStack.OrderStackMainActivity;
import com.mousebelly.app.userapp.payment.PaymentDetails;
import com.mousebelly.app.userapp.preOrderStack.PreOrderMainActivity;
import com.mousebelly.app.userapp.userprofile.UserprofileMainActivity;
import com.mousebelly.app.userapp.wallet.Wallet;

import java.util.ArrayList;
import java.util.Iterator;

import static com.mousebelly.app.userapp.Login.LoginActivity.USERID;
import static com.mousebelly.app.userapp.Login.LoginActivity.USERNAME;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static Context context;
    public static Activity activity;
    public static LinearLayout productsLayout;
    public static LinearLayout cartLayout;
    public static TextView totalAmount;
    public static Dialog ProductCartDialog;
    public static LinearLayout staticCartLinearLayout;
    public ScrollView cartScrollView;

    ProgressBar pg;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MainActivity.context = MainActivity.this;
        MainActivity.activity = MainActivity.this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        CurrentContext.context = MainActivity.this;

        //vew cart button

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setBackgroundColor(Color.parseColor("#7c5a7e"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                fab.setBackground(getDrawable(R.color.colorPrimary));

            }
        }
        fab.setOnClickListener(new View.OnClickListener() {
           /* public Context getActivity() {
                return context;
            }*/

            @Override
            public void onClick(View view) {

                if (Cart.cartItems.isEmpty()) {
                    CustomToast.Toast(MainActivity.this, "Cart is Empty");
                    return;
                }

                ProductCartDialog = new Dialog(MainActivity.this);
                ProductCartDialog.setTitle("Cart");
                ProductCartDialog.setContentView(R.layout.products_user_cart_dialogbox);

                LinearLayout cartLinearLayout = (LinearLayout) ProductCartDialog.findViewById(R.id.CartLinearLayout);

                MainActivity.staticCartLinearLayout = cartLinearLayout;

                totalAmount = (TextView) ProductCartDialog.findViewById(R.id.totalAmount);
                System.out.println("Cart Amount : " + Cart.cartAmount);
                totalAmount.setText(String.valueOf(Cart.cartAmount));

                Iterator it = Cart.cartItems.keySet().iterator();

                while (it.hasNext()) {
                    ProductUnit productUnit = Cart.cartItems.get(it.next());
                    ProductUnit p = new ProductUnit();
                    RelativeLayout rl = p.showCart(productUnit);
                    //RelativeLayout rl = ProductUnit.showCart(productUnit);

                    cartLinearLayout.addView(rl);
                }


                Button dialogButtonOK = (Button) ProductCartDialog.findViewById(R.id.dialogButtonOK);

                dialogButtonOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent movetopayment = new Intent(MainActivity.this, PaymentDetails.class);
                        startActivity(movetopayment);
                    }
                });


                Button dialogButtonCancel = (Button) ProductCartDialog.findViewById(R.id.dialogButtonCancel);

                dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ProductCartDialog.dismiss();
                    }
                });

                ProductCartDialog.show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView = navigationView.getHeaderView(0);
        TextView nav_user = (TextView) hView.findViewById(R.id.user_email_id);
        TextView nav_username = (TextView) hView.findViewById(R.id.user_name);

        nav_user.setText(USERID);
        nav_username.setText(USERNAME);

        //calling ad



      /*  LoadAds loadAds = new LoadAds();
        loadAds.execute();*/

        //getting layout

        MainActivity.productsLayout = (LinearLayout) findViewById(R.id.ProductsLayout);

        pg = (ProgressBar) findViewById(R.id.products_Progress_Bar);

        // to access internet

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        /////////////////////CUSTOM ACTION BAR
        // Update the action bar title with the TypefaceSpan instance
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(actionBar.getDisplayOptions()
                | ActionBar.DISPLAY_SHOW_CUSTOM);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT, Gravity.LEFT
                | Gravity.CENTER_VERTICAL);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        ActionBar.LayoutParams titleLayout = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT, Gravity.CENTER_VERTICAL);

        Typeface trumpitFaceHeader = Typeface.createFromAsset(getAssets(), "fonts/Trumpit.ttf");

        LinearLayout mainTitleLayout = new LinearLayout(MainActivity.this);
        mainTitleLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        mainTitleLayout.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout maintitle = new LinearLayout(MainActivity.this);
        maintitle.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            maintitle.setClipToOutline(true);
        }
        maintitle.setBackgroundResource(R.drawable.rounded_layer);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            maintitle.setElevation(2.0f);
        }
        mainTitleLayout.addView(maintitle);


        TextView actionBarText = new TextView(MainActivity.this);
        actionBarText.setTextColor(Color.parseColor("#AAC706"));
        actionBarText.setPadding(20, 5, 0, 6);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            actionBarText.setElevation(4.0f);
        }
        actionBarText.setText("Mouse");
        //actionBarText.setBackgroundColor(Color.parseColor("#ffffff"));
        actionBarText.setTextSize(25);
        actionBarText.setTypeface(trumpitFaceHeader, Typeface.BOLD);

        actionBarText.setLayoutParams(titleLayout);
        maintitle.addView(actionBarText);

        TextView actionBarText1 = new TextView(MainActivity.this);
        actionBarText1.setTextColor(Color.parseColor("#800000"));
        actionBarText1.setPadding(0, 5, 20, 5);
        actionBarText1.setText(" - Belly");
        //actionBarText1.setBackgroundColor(Color.parseColor("#ffffff"));
        actionBarText1.setTextSize(25);
        actionBarText1.setTypeface(trumpitFaceHeader, Typeface.BOLD);
        maintitle.addView(actionBarText1);
        actionBar.setTitle("");
        actionBar.setCustomView(mainTitleLayout);

        //END OF CUSTOM ACTION BAR
        /////////////////////////

        LoadProducts loadProducts = new LoadProducts();
        loadProducts.execute();



   /*     String resp = Server.s.get("http://mousebelly.herokuapp.com/housewifesign/getauthorisedhw/" + LoginActivity.user.getLat() + "/" + LoginActivity.user.getLongitude());

        System.out.println(resp);

        GetProductsData.convertDataToArrayList(resp);

        GetProductsData.loadData();*/
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Exit Application?");
            alertDialogBuilder
                    .setMessage("Do You Want to Exit")
                    .setCancelable(false)
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    moveTaskToBack(true);
                                    android.os.Process.killProcess(android.os.Process.myPid());
                                    System.exit(1);
                                }
                            })

                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            dialog.cancel();
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    private Menu menu;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.change_location) {
            Intent i = new Intent(MainActivity.this, ChangeLocation.class);
            startActivity(i);
           /* ChangeLocation changeLocation = new ChangeLocation();
            changeLocation.change();*/

            return true;
        }

        if (id == R.id.show_fav) {

            MenuItem menuItem = menu.findItem(R.id.show_fav);

            LinearLayout l = MainActivity.productsLayout;

            if (menuItem.getTitle().equals("All food")) {

         /*       for(int i=0;i<l.getChildCount();i++){
                    RelativeLayout rl = (RelativeLayout) l.getChildAt(i);


                        System.out.println("removing layout");
                        l.removeView(rl);
                }*/
                l.removeAllViews();
                menuItem.setTitle("Favourite");

                MainActivity.LoadProducts loadProducts = new LoadProducts();
                loadProducts.execute();


            }

            //TODO: write show fav code here
            ArrayList<String> favItems = LoginActivity.user.getFav();
            System.out.println("Fav. Items : " + favItems);

            if (!favItems.isEmpty() && !GetProductsData.productUnits.isEmpty() && favItems != null) {

                for (String foodId : favItems) {
                    Iterator it = GetProductsData.productUnits.keySet().iterator();
                    while (it.hasNext()) {
                        String key = it.next().toString();

                        if (!key.equals(foodId)) {
                            for (int i = 0; i < l.getChildCount(); i++) {
                                RelativeLayout rl = (RelativeLayout) l.getChildAt(i);

                                /*System.out.println("Tag : " + rl.getTag());
                                System.out.println("Food : " + foodId);
*/
                                if (rl.getTag().equals(foodId)) {
                                    System.out.println("removing layout");
                                    l.removeView(rl);
                                    menuItem.setTitle("All food");
                                } else {

                                }
                            }
                        }
                    }
                }
                if (menuItem.getTitle().equals("Favourite")) {
                    CustomToast.Toast(MainActivity.this, "Your Favourite Food is not available");
                }
            } else {
                CustomToast.Toast(MainActivity.this, "No Favourite Food");
            }
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.view_order) {
            Intent intent = new Intent(MainActivity.this, OrderStackMainActivity.class);
            startActivity(intent);
        } else if (id == R.id.view_pre_order) {
            Intent intent = new Intent(MainActivity.this, PreOrderMainActivity.class);
            startActivity(intent);
        } else if (id == R.id.view_profile) {
            Intent intent = new Intent(MainActivity.this, UserprofileMainActivity.class);
            startActivity(intent);
            //Toast.makeText(MainActivity.this, "Working on It", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_fav) {
            Intent intent = new Intent(MainActivity.this, favMainActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_logout) {
//            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//            startActivity(intent);
            Toast.makeText(MainActivity.this, "Working on It", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.mealplanner) {
            com.mousebelly.app.userapp.mealPlanner.Cart.cartItems.clear();
            Intent intent = new Intent(MainActivity.this, MealPlannerMainActivity.class);
            startActivity(intent);

            //Toast.makeText(MainActivity.this, "Working on It", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_feedback) {
//            Intent intent = new Intent(MainActivity.this, FeedBack.class);
//            startActivity(intent);
            Toast.makeText(MainActivity.this, "Working on It", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.wallet) {
            Intent intent = new Intent(MainActivity.this, Wallet.class);
            startActivity(intent);
            // Toast.makeText(MainActivity.this, "Working on It", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public class LoadProducts extends AsyncTask<Void, Void, Void> {

        // ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);
        String resp;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pg.setVisibility(View.VISIBLE);
/*
            pdLoading.setMessage("\t Loading...");
            pdLoading.show();
            */

            //this method will be running on UI thread

        }

        @Override
        protected Void doInBackground(Void... voids) {
            resp = Server.s.get("http://mousebelly.herokuapp.com/housewifesign/getauthorisedhw/" + LoginActivity.user.getLat() + "/" + LoginActivity.user.getLongitude());
            GetProductsData.convertDataToArrayList(resp);
            System.out.println(resp);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);


            GetProductsData.loadData();

           /* AdMainActivity adMainActivity = new AdMainActivity();
            adMainActivity.startAd();*/

            pg.setVisibility(View.GONE);


            LoadOrdersForFeedback loadOrders = new LoadOrdersForFeedback();
            loadOrders.execute();

        }

    }


    @Override
    public void onResume() {
        super.onResume();


    }

}
