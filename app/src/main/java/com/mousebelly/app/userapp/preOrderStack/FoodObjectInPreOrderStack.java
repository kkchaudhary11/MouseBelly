package com.mousebelly.app.userapp.preOrderStack;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mousebelly.app.userapp.IdManager;

import java.net.URL;

import static android.view.Gravity.RIGHT;

/**
 * Created by Kamal Kant on 14-04-2017.
 */

public class FoodObjectInPreOrderStack {

    static public int orderCount = 0;
    RelativeLayout ProductInsideOrder;
    LinearLayout imageLinearLayout;
    LinearLayout productView;
    TextView productname;
    TextView price, productqty, prod_status;
    ImageView image;
    TextView RupeeSymbol, qtyText, statusText, deliveryDate;
    LinearLayout priceView;
    LinearLayout qtyView;
    private String PID;
    private String Product_Name;
    private String status;
    private String HWID;
    private String Qty;
    private String stars;
    private String Price_item;
    private String orderid;
    private String Image;
    private String delivery_date;
    private Bitmap bmpImage;
    private int Id;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public FoodObjectInPreOrderStack() {


        FoodObjectInPreOrderStack.orderCount++;
        Log.d(String.valueOf(FoodObjectInPreOrderStack.orderCount), "Orader Stack: ");
        this.Id = FoodObjectInPreOrderStack.orderCount;
        Log.d(String.valueOf(PreOrderMainActivity.context), "Food Satack [LOG]: " + this.Id);


        GradientDrawable shape = new GradientDrawable();
        shape.setCornerRadius(8);
        shape.setColor(Color.parseColor("#ffffff"));
        shape.setStroke(5, Color.parseColor("#6c448d"));

        this.ProductInsideOrder = new RelativeLayout(PreOrderMainActivity.context);
        IdManager.addId("ProductInsideOrder" + this.Id);
        this.ProductInsideOrder.setId((Integer) IdManager.stringToIdMap.get("ProductInsideOrder" + this.Id));
        ProductInsideOrder.setBackground(shape);

        Typeface rupeeSymbol = Typeface.createFromAsset(PreOrderMainActivity.context.getAssets(), "fonts/IndianRupee.ttf");
        final Typeface maze = Typeface.createFromAsset(PreOrderMainActivity.context.getAssets(), "fonts/The Heart Maze Demo.ttf");
        Typeface novaoval = Typeface.createFromAsset(PreOrderMainActivity.context.getAssets(), "fonts/NovaOval.ttf");

        this.productView = new LinearLayout(PreOrderMainActivity.context);
        IdManager.addId("productView" + this.Id);
        this.productView.setId((Integer) IdManager.stringToIdMap.get("productView" + this.Id));
        //this.productView.setPadding(30,10,30,30);
        this.productView.setOrientation(LinearLayout.VERTICAL);

        this.imageLinearLayout = new LinearLayout(PreOrderMainActivity.context);
        IdManager.addId("imageLinearLayout" + this.Id);
        this.imageLinearLayout.setId((Integer) IdManager.stringToIdMap.get("imageLinearLayout" + this.Id));
        this.imageLinearLayout.setPadding(10, 30, 60, 10);
        this.imageLinearLayout.setGravity(RIGHT);

        this.image = new ImageView(PreOrderMainActivity.context);
        IdManager.addId("imageInsideOrder" + this.Id);
        this.image.setId((Integer) IdManager.stringToIdMap.get("imageInsideOrder" + this.Id));

        this.productname = new TextView(PreOrderMainActivity.context);
        IdManager.addId("productnameInsideOrder" + this.Id);
        this.productname.setId((Integer) IdManager.stringToIdMap.get("productnameInsideOrder" + this.Id));
        this.productname.setTypeface(maze, Typeface.BOLD);
        this.productname.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        this.productname.setTextColor(Color.BLACK);
        this.productname.setPadding(30, 10, 0, 10);

        this.priceView = new LinearLayout(PreOrderMainActivity.context);
        IdManager.addId("priceView" + this.Id);
        this.priceView.setId((Integer) IdManager.stringToIdMap.get("priceView" + this.Id));
        this.priceView.setPadding(30, 0, 30, 0);

        this.RupeeSymbol = new TextView(PreOrderMainActivity.context);
        IdManager.addId("orderrupeesymbol" + this.Id);
        this.RupeeSymbol.setId((int) IdManager.stringToIdMap.get("orderrupeesymbol" + this.Id));
        this.RupeeSymbol.setTypeface(rupeeSymbol);
        this.RupeeSymbol.setPadding(40, 10, 0, 0);
        this.RupeeSymbol.setText("`");

        this.price = new TextView(PreOrderMainActivity.context);
        IdManager.addId("productpriceInsideOrder" + this.Id);
        this.price.setId((Integer) IdManager.stringToIdMap.get("productpriceInsideOrder" + this.Id));
        this.price.setPadding(5, 5, 5, 5);
        this.price.setTypeface(novaoval);
        this.price.setTextColor(Color.parseColor("#000000"));

        this.qtyView = new LinearLayout(PreOrderMainActivity.context);
        IdManager.addId("qtyView" + this.Id);
        this.qtyView.setId((Integer) IdManager.stringToIdMap.get("qtyView" + this.Id));
        this.qtyView.setPadding(30, 10, 30, 0);

        this.qtyText = new TextView(PreOrderMainActivity.context);
        IdManager.addId("orderrupeesymbol" + this.Id);
        this.qtyText.setId((int) IdManager.stringToIdMap.get("orderrupeesymbol" + this.Id));
        this.qtyText.setTypeface(novaoval);
        this.qtyText.setPadding(40, 10, 0, 0);
        this.qtyText.setText("Qty :");

        this.productqty = new TextView(PreOrderMainActivity.context);
        IdManager.addId("productqtyInsideOrder" + this.Id);
        this.productqty.setId((Integer) IdManager.stringToIdMap.get("productqtyInsideOrder" + this.Id));
        this.productqty.setPadding(5, 5, 5, 5);
        this.productqty.setTypeface(novaoval);
        this.productqty.setTextColor(Color.parseColor("#000000"));

        this.statusText = new TextView(PreOrderMainActivity.context);
        IdManager.addId("statusrupeesymbol" + this.Id);
        this.statusText.setId((int) IdManager.stringToIdMap.get("statusrupeesymbol" + this.Id));
        this.statusText.setTypeface(novaoval);
        this.statusText.setPadding(40, 10, 0, 0);
        this.statusText.setText("Status :");


        this.prod_status = new TextView(PreOrderMainActivity.context);
        IdManager.addId("prod_statusInsideOrder" + this.Id);
        this.prod_status.setId((Integer) IdManager.stringToIdMap.get("prod_statusInsideOrder" + this.Id));

        this.prod_status.setPadding(10, 5, 10, 5);
        this.prod_status.setTypeface(novaoval);

        this.deliveryDate = new TextView(PreOrderMainActivity.context);
        IdManager.addId("deliveryDate" + this.Id);
        this.deliveryDate.setId((Integer) IdManager.stringToIdMap.get("deliveryDate" + this.Id));
        this.deliveryDate.setGravity(Gravity.CENTER);
        this.deliveryDate.setTextSize(22);
        this.deliveryDate.setTypeface(null, Typeface.BOLD);
        this.deliveryDate.setPadding(50, 0, 0, 50);

    }

    public static void updateOrderStatus(FoodObjectInPreOrderStack foodObjectInPreOrderStack, String status) {
        System.out.println("update status : " + status);

        System.out.println("old status : " + foodObjectInPreOrderStack.getStatus());
        foodObjectInPreOrderStack.setStatus(status);

    }

    public String getPID() {
        return PID;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }

    public String getProduct_Name() {
        return Product_Name;
    }

    public void setProduct_Name(String product_Name) {
        Product_Name = product_Name;
        this.productname.setText(product_Name);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void run() {

                prod_status.setText(status);

                //change color on status change
                GradientDrawable gd = new GradientDrawable();
                gd.setCornerRadius(15);

                if (status.equals("ordered")) {
                    prod_status.setTextColor(Color.parseColor("#800000"));
                    gd.setColor(Color.parseColor("#ffc706"));//yellow
                    prod_status.setBackground(gd);
                } else if (status.equals("cooked")) {
                    prod_status.setTextColor(Color.parseColor("#ffffff"));
                    gd.setColor(Color.parseColor("#6c448d"));//magenta
                    prod_status.setBackground(gd);
                } else if (status.equals("accepted")) {
                    prod_status.setTextColor(Color.parseColor("#ffffff"));
                    gd.setColor(Color.parseColor("#769e70"));
                    prod_status.setBackground(gd);
                } else {
                    prod_status.setTextColor(Color.parseColor("#ffffff"));
                    gd.setColor(Color.parseColor("#000000"));
                    prod_status.setBackground(gd);
                }
            }
        }, 100);

    }

    public String getHWID() {
        return HWID;
    }

    public void setHWID(String HWID) {
        this.HWID = HWID;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
        this.productqty.setText(qty);
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getPrice_item() {
        return Price_item;
    }

    public void setPrice_item(String price_item) {
        Price_item = price_item;
        this.price.setText(price_item);
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
        try {
            URL url = new URL(image);
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());

            this.bmpImage = bmp;
            System.out.println("Bitmap Loaded");
            final int THUMBSIZE = 200;

            Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(bmp,
                    THUMBSIZE, THUMBSIZE);

            this.image.setImageBitmap(ThumbImage);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public String getDelivery_date() {
        return delivery_date;
    }

    //View line;

    public void setDelivery_date(String delivery_date) {
        this.delivery_date = delivery_date;
        this.deliveryDate.setText(delivery_date);
    }

    @Override
    public String toString() {
        return "FoodObjectInPreOrderStack{" +
                "PID='" + PID + '\'' +
                ", Product_Name='" + Product_Name + '\'' +
                ", status='" + status + '\'' +
                ", HWID='" + HWID + '\'' +
                ", Qty='" + Qty + '\'' +
                ", stars='" + stars + '\'' +
                ", Price_item='" + Price_item + '\'' +
                ", orderid='" + orderid + '\'' +
                ", Image='" + Image + '\'' +
                '}';
    }

    public RelativeLayout orderDraw() {

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(PreOrderMainActivity.displayWidth - 20, LinearLayout.LayoutParams.WRAP_CONTENT);
        this.productView.setLayoutParams(params);
        //this.productView.addView(image);

        this.imageLinearLayout.setLayoutParams(params);
        this.imageLinearLayout.addView(image);

        this.priceView.setLayoutParams(params);
        this.priceView.addView(productname);
        this.priceView.addView(RupeeSymbol);
        this.priceView.addView(price);

        this.qtyView.setLayoutParams(params);
        this.qtyView.addView(qtyText);
        this.qtyView.addView(productqty);
        this.qtyView.addView(statusText);
        this.qtyView.addView(prod_status);

        this.productView.setLayoutParams(params);
        this.productView.addView(priceView);
        this.productView.addView(qtyView);
        this.productView.addView(deliveryDate);

        params.setMargins(0, 0, 0, 40);
        ProductInsideOrder.setLayoutParams(params);
        this.ProductInsideOrder.setLayoutParams(params);
        this.ProductInsideOrder.addView(productView);
        this.ProductInsideOrder.addView(imageLinearLayout);

        return this.ProductInsideOrder;
    }


}