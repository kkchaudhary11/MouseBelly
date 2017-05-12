package com.mousebelly.app.userapp.mealPlanner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mousebelly.app.userapp.IdManager;
import com.mousebelly.app.userapp.R;
import com.mousebelly.app.userapp.Server;

import org.json.JSONArray;

import java.net.URL;

/**
 * Created by Aaru on 07/04/2017.
 */

public class FoodObject {
    static int Id = 0;
    private static int callCount = 0;
    int myId;
    Context c;
    RelativeLayout relativeLayout;
    ImageView imageView;
    TextView productNameTextView;
    TextView layoutTextView;
    TextView startTimeTextView;
    TextView endTimeTextView;
    TextView dateTextView;
    TextView HWFNameTextView;
    TextView priceTextView;
    CheckBox selectedCheckBox;
    private String _id;
    private String Prod_name;
    private String Prod_category, Start_Time, End_Time, Date, Description, Price, Prod_id, feedback, Image, starsize, isApproved, isRejected, HWFEmail_id;
    private String HWF_Name, Phone_no, countrate;
    private Bitmap bitmapImage;
    private int qty = 1;
    private String orderId;
    private boolean selected = false;
    private boolean newVisit = false;

    public FoodObject() {
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public FoodObject(Context c) {
        this.c = c;
        Id++;
        this.myId = Id;
        this.relativeLayout = new RelativeLayout(this.c);

        //image view
        this.imageView = new ImageView(this.c);
        IdManager.addId("ProductUnitImage" + this.myId);
        this.imageView.setId((int) IdManager.stringToIdMap.get("ProductUnitImage" + this.myId));

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(200, 200);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.setMargins(10, 10, 10, 10);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        this.imageView.setLayoutParams(params);

        this.relativeLayout.addView(this.imageView);

        GradientDrawable shape = new GradientDrawable();
        shape = new GradientDrawable();
        shape.setCornerRadius(8);
        shape.setColor(ContextCompat.getColor(MealPlannerMainActivity.context, R.color.Amulet));

        // product name view

        this.productNameTextView = new TextView(c);
        IdManager.addId("productNameTextView" + this.myId);
        this.productNameTextView.setId((int) IdManager.stringToIdMap.get("productNameTextView" + this.myId));
        this.productNameTextView.setBackground(shape);
        this.productNameTextView.setPadding(10, 0, 10, 0);
        this.productNameTextView.setTextColor(Color.WHITE);

        this.relativeLayout.addView(this.productNameTextView);

        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW, this.imageView.getId());
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.setMargins(10, 10, 10, 10);
        this.productNameTextView.setLayoutParams(params);
        this.productNameTextView.setText(this.Prod_name);


        // - layout

        this.layoutTextView = new TextView(c);
        IdManager.addId("layoutTextView" + this.myId);
        this.layoutTextView.setId((int) IdManager.stringToIdMap.get("layoutTextView" + this.myId));
        //this.dateTextView.setBackground(shape);
        this.relativeLayout.addView(this.layoutTextView);

        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW, this.productNameTextView.getId());
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        this.layoutTextView.setLayoutParams(params);
        this.layoutTextView.setPadding(10, 0, 10, 0);
        this.layoutTextView.setTypeface(null, Typeface.BOLD);
        this.layoutTextView.setText("-");

        // start time view

        this.startTimeTextView = new TextView(c);
        IdManager.addId("startTimeTextView" + this.myId);
        this.startTimeTextView.setId((int) IdManager.stringToIdMap.get("startTimeTextView" + this.myId));
        // this.startTimeTextView.setBackground(shape);
        //this.startTimeTextView.setPadding(0, 0, 0, 0);
        this.startTimeTextView.setTextColor(Color.BLACK);

        this.relativeLayout.addView(this.startTimeTextView);

        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW, this.productNameTextView.getId());
        params.addRule(RelativeLayout.LEFT_OF, this.layoutTextView.getId());
        this.startTimeTextView.setLayoutParams(params);


        //end text view

        this.endTimeTextView = new TextView(c);
        IdManager.addId("endTimeTextView" + this.myId);
        this.endTimeTextView.setId((int) IdManager.stringToIdMap.get("endTimeTextView" + this.myId));
        // this.endTimeTextView.setBackground(shape);
        //  this.endTimeTextView.setPadding(0, 0, 0, 0);
        this.endTimeTextView.setTextColor(Color.BLACK);

        this.relativeLayout.addView(this.endTimeTextView);

        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW, this.productNameTextView.getId());
        params.addRule(RelativeLayout.RIGHT_OF, this.layoutTextView.getId());
        this.endTimeTextView.setLayoutParams(params);
        System.out.println("This is end time " + getEnd_Time());


        //date view

        this.dateTextView = new TextView(c);
        IdManager.addId("dateTextView" + this.myId);
        this.dateTextView.setId((int) IdManager.stringToIdMap.get("dateTextView" + this.myId));
        //this.dateTextView.setBackground(shape);
        this.dateTextView.setTextColor(ContextCompat.getColor(MealPlannerMainActivity.context, R.color.Affair));
        this.dateTextView.setTextSize(12);

        this.relativeLayout.addView(this.dateTextView);

        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW, this.productNameTextView.getId());
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.setMargins(10, 10, 10, 10);
        this.dateTextView.setLayoutParams(params);
        this.dateTextView.setText(this.Date);

        //HWF name

        this.HWFNameTextView = new TextView(c);
        IdManager.addId("HWFNameTextView" + this.myId);
        this.HWFNameTextView.setId((int) IdManager.stringToIdMap.get("HWFNameTextView" + this.myId));

        this.relativeLayout.addView(this.HWFNameTextView);

        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW, this.dateTextView.getId());
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.setMargins(10, 5, 10, 5);
        this.HWFNameTextView.setLayoutParams(params);

        this.HWFNameTextView.setText(this.HWF_Name);

        //Price Text view
        this.priceTextView = new TextView(c);
        IdManager.addId("priceTextView" + this.myId);
        this.priceTextView.setId((int) IdManager.stringToIdMap.get("priceTextView" + this.myId));

        this.relativeLayout.addView(this.priceTextView);

        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW, this.HWFNameTextView.getId());
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.setMargins(10, 5, 10, 5);
        this.priceTextView.setLayoutParams(params);
        this.priceTextView.setTypeface(null, Typeface.BOLD);

        this.priceTextView.setText(this.Price);

        //checkbox

        this.selectedCheckBox = new CheckBox(c);
        IdManager.addId("selectedCheckBox" + this.myId);
        this.selectedCheckBox.setId((int) IdManager.stringToIdMap.get("selectedCheckBox" + this.myId));

        this.relativeLayout.addView(this.selectedCheckBox);

        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.RIGHT_OF, this.imageView.getId());
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        params.setMargins(10, 10, 10, 10);
        this.selectedCheckBox.setLayoutParams(params);
        this.selectedCheckBox.setChecked(this.isSelected());

    }

    public static void FoodObject_To_FoodObjectInOrderPlan(FoodObject foodObject) {
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static RelativeLayout dispFood(FoodObject foodObject) {

        callCount++;

        RelativeLayout rl = new RelativeLayout(MealPlannerMainActivity.context);
        IdManager.addId("productNameTextView" + callCount);
        rl.setId((int) IdManager.stringToIdMap.get("productNameTextView" + callCount));

        rl.setTag(foodObject.getDate() + "_" + foodObject.getProd_id());


        ImageView iv = new ImageView(MealPlannerMainActivity.context);
        IdManager.addId("productImageView" + callCount);
        iv.setId((int) IdManager.stringToIdMap.get("productImageView" + callCount));


        TextView tv = new TextView(MealPlannerMainActivity.context);
        IdManager.addId("productNameView" + callCount);
        tv.setId((int) IdManager.stringToIdMap.get("productNameView" + callCount));

        if (foodObject.orderId == null) {
            //Red Border
            GradientDrawable shape = new GradientDrawable();
            shape.setCornerRadius(8);
            shape.setStroke(5, Color.RED);

            rl.setBackground(shape);

        } else {
            // Green Border
            GradientDrawable shape = new GradientDrawable();
            shape.setCornerRadius(8);
            shape.setStroke(5, Color.GREEN);
            rl.setBackground(shape);

        }
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(20, 10, 20, 10);
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        params.addRule(RelativeLayout.RIGHT_OF, iv.getId());
        tv.setLayoutParams(params);
        tv.setTypeface(null, Typeface.BOLD);
        tv.setText(foodObject.getProd_name());

        params = new RelativeLayout.LayoutParams(200, 200);
        params.setMargins(10, 10, 10, 10);
        iv.setLayoutParams(params);
        iv.setImageBitmap(foodObject.getBitmapImage());

        rl.addView(iv);
        rl.addView(tv);

        return rl;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static RelativeLayout showCart(final FoodObject foodObject) {

        callCount++;

        RelativeLayout rl = new RelativeLayout(MealPlannerMainActivity.context);

        IdManager.addId("productNameTextView" + callCount);
        rl.setId((int) IdManager.stringToIdMap.get("productNameTextView" + callCount));

        rl.setTag(foodObject.getDate() + "_" + foodObject.getProd_id());

        ImageView iv = new ImageView(MealPlannerMainActivity.context);
        IdManager.addId("productImageView" + callCount);
        iv.setId((int) IdManager.stringToIdMap.get("productImageView" + callCount));

        TextView tv = new TextView(MealPlannerMainActivity.context);
        IdManager.addId("productNameView" + callCount);
        tv.setId((int) IdManager.stringToIdMap.get("productNameView" + callCount));

        final TextView qty = new TextView(MealPlannerMainActivity.context);
        IdManager.addId("productQtyView" + callCount);
        qty.setId((int) IdManager.stringToIdMap.get("productQtyView" + callCount));

        Button delete = new Button(MealPlannerMainActivity.context);
        IdManager.addId("productDeleteView" + callCount);
        delete.setId((int) IdManager.stringToIdMap.get("productDeleteView" + callCount));

        Button incQty = new Button(MealPlannerMainActivity.context);
        IdManager.addId("productIncView" + callCount);
        incQty.setId((int) IdManager.stringToIdMap.get("productIncView" + callCount));

        Button decQty = new Button(MealPlannerMainActivity.context);
        IdManager.addId("productDecView" + callCount);
        decQty.setId((int) IdManager.stringToIdMap.get("productDecView" + callCount));


        // product image
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(250, 250);
        params.setMargins(10, 10, 10, 0);
        iv.setLayoutParams(params);
        iv.setImageBitmap(foodObject.getBitmapImage());
        iv.setPadding(10, 10, 0, 0);

        //product name
        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.RIGHT_OF, iv.getId());
        tv.setLayoutParams(params);
        tv.setTypeface(null, Typeface.BOLD);
        tv.setTextSize(19);
        tv.setPadding(20, 100, 0, 0);
        tv.setText(foodObject.getProd_name());

        //decbutton
        params = new RelativeLayout.LayoutParams(100, 100);
        params.setMargins(20, 0, 0, 0);
        params.addRule(RelativeLayout.BELOW, iv.getId());
        decQty.setLayoutParams(params);
        decQty.setPadding(0, 0, 0, 0);
        decQty.setTextSize(25);
        decQty.setBackgroundColor(ContextCompat.getColor(MealPlannerMainActivity.context, R.color.Affair));
        decQty.setTextColor(Color.WHITE);
        decQty.getBackground().setAlpha(150);
        decQty.setText("-");

        //qty
        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        // params.setMargins(10, 10, 10, 10);
        params.addRule(RelativeLayout.RIGHT_OF, decQty.getId());
        params.addRule(RelativeLayout.BELOW, iv.getId());
        qty.setLayoutParams(params);
        qty.setTextSize(21);
        qty.setPadding(20, 0, 20, 15);
        qty.setTextColor(Color.WHITE);
        qty.setBackgroundColor(ContextCompat.getColor(MealPlannerMainActivity.context, R.color.Amulet));
        qty.setTypeface(null, Typeface.BOLD);
        qty.setText(String.valueOf(foodObject.getQty()));


        //incqty
        params = new RelativeLayout.LayoutParams(100, 100);

        params.addRule(RelativeLayout.RIGHT_OF, qty.getId());
        params.addRule(RelativeLayout.BELOW, iv.getId());
        incQty.setPadding(0, 0, 0, 0);
        incQty.setLayoutParams(params);
        incQty.setTextSize(25);
        incQty.setBackgroundColor(ContextCompat.getColor(MealPlannerMainActivity.context, R.color.Affair));
        incQty.setTextColor(Color.WHITE);
        incQty.getBackground().setAlpha(150);
        incQty.setText("+");


        //delete button
        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 100);

        params.addRule(RelativeLayout.BELOW, iv.getId());
        params.setMargins(0, 0, 20, 20);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        delete.setPadding(0, 0, 0, 0);
        delete.setLayoutParams(params);
        delete.setBackgroundColor(ContextCompat.getColor(MealPlannerMainActivity.context, R.color.Roman));
        delete.setTextColor(Color.WHITE);
        delete.setText("Remove");


        rl.addView(iv);
        rl.addView(tv);
        rl.addView(delete);
        rl.addView(decQty);
        rl.addView(qty);
        rl.addView(incQty);

        GradientDrawable shape = new GradientDrawable();
        //shape.setCornerRadius( 8 );
        shape.setStroke(5, ContextCompat.getColor(MealPlannerMainActivity.context, R.color.Amulet));
        rl.setBackground(shape);

        rl.setBackground(shape);

        incQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int oldValue = foodObject.getQty();
                if (oldValue < 7) {
                    int temp = foodObject.getQty();
                    temp++;
                    foodObject.setQty(temp);
                    System.out.println("QTY : " + temp);
                    qty.setText(String.valueOf(temp));

                    boolean flag = Cart.updateCart(foodObject, oldValue);
                    if (flag == false) {
                        qty.setText(String.valueOf(temp - 1));
                    }
                }
            }
        });

        decQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (foodObject.getQty() <= 1) {
                    return;
                }
                int oldValue = foodObject.getQty();
                int temp = foodObject.getQty();
                temp--;
                foodObject.setQty(temp);
                System.out.println("QTY : " + temp);

                qty.setText(String.valueOf(temp));

                Cart.updateCart(foodObject, oldValue);

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foodObject.setSelected(false);

                Cart.removeFromCart(foodObject);

                if (Cart.cartItems.isEmpty()) {
                    Cart.cartAmount = 0;
                    MealPlannerMainActivity.MealPlanCartdialog.dismiss();
                }

            }
        });
        return rl;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(final boolean selected) {
        this.selected = selected;
        if (selected) {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    selectedCheckBox.setChecked(true);
                }
            }, 100);
        } else {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    selectedCheckBox.setChecked(false);
                }
            }, 100);
        }
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getProd_name() {
        return Prod_name;
    }

    public void setProd_name(String prod_name) {
        this.Prod_name = prod_name;
        this.productNameTextView.setText(prod_name);
    }

    public String getProd_category() {
        return Prod_category;
    }

    public void setProd_category(String prod_category) {
        Prod_category = prod_category;
    }

    public String getStart_Time() {
        return Start_Time;
    }

    public void setStart_Time(String start_Time) {
        Start_Time = start_Time;
        this.startTimeTextView.setText(start_Time);
    }

    public String getEnd_Time() {
        return End_Time;
    }

    public void setEnd_Time(String end_Time) {
        End_Time = end_Time;
        //System.out.println("End time : "  +end_Time);
        this.endTimeTextView.setText(end_Time);
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
        this.dateTextView.setText(date);
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
        this.priceTextView.setText("₹" + price);
    }

    public String getProd_id() {
        return Prod_id;
    }

    public void setProd_id(String prod_id) {
        Prod_id = prod_id;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        this.Image = image;
    }

    public Bitmap getBitmapImage() {
        return bitmapImage;
    }

    public void setBitmapImage(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            this.bitmapImage = bmp;

            System.out.println("Bitmap Loaded");

            this.setImage(imageUrl);
            this.imageView.setImageBitmap(this.bitmapImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getStarsize() {
        return starsize;
    }

    public void setStarsize(String starsize) {
        this.starsize = starsize;
    }

    public String getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(String isApproved) {
        this.isApproved = isApproved;
    }

    public String getIsRejected() {
        return isRejected;
    }

    public void setIsRejected(String isRejected) {
        this.isRejected = isRejected;
    }

    public String getHWFEmail_id() {
        return HWFEmail_id;
    }

    public void setHWFEmail_id(String HWFEmail_id) {
        this.HWFEmail_id = HWFEmail_id;
    }

    public String getHWF_Name() {
        return HWF_Name;
    }

    public void setHWF_Name(String HWF_Name) {
        this.HWF_Name = HWF_Name;
        this.HWFNameTextView.setText("HWF : " + HWF_Name);
    }

    public String getPhone_no() {
        return Phone_no;
    }

    public void setPhone_no(String phone_no) {
        Phone_no = phone_no;
    }

    public String getCountrate() {
        return countrate;
    }

    public void setCountrate(String countrate) {
        this.countrate = countrate;
    }

    @Override
    public String toString() {
        return "FoodObject{" +
                "Prod_name='" + Prod_name + '\'' +
                ", Prod_id='" + Prod_id + '\'' +
                ", selected=" + selected +
                '}';
    }

    private FoodObject getThisObject() {
        return this;
    }

    public RelativeLayout draw() {
        System.out.println("Product Unit Draw Begin");

        if (getOrderId() == null)
            newVisit = true;

        this.selectedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (newVisit) {
                    if (b) {
                        if (getOrderId() != null) {
                            setOrderId(null);
                        }


                        FoodObject f = getThisObject();

                        DateLayout.AddToLayout(f);

                        Cart.addToCart(f);

                        JSONArray jsonObject = OrderPlan.toJSON();

                        System.out.println("JSON Object : " + jsonObject);
                    } else {
                        FoodObject f = getThisObject();

                        if (getOrderId() != null) {
                            OrderPlan.removeFromOrderPlan(f);

                            System.out.println("PROD DETAILS JSON");
                            System.out.println(OrderPlan.getProdDetailsJSON(getOrderId()));


                            Server.s.putwithdata("http://mousebelly.herokuapp.com/preorder/preorder/" + getOrderId(), OrderPlan.getProdDetailsJSON(getOrderId()));

                        }

                        DateLayout.RemoveFromLayout(f);

                        Cart.removeFromCart(f);

                        if (Cart.cartItems.isEmpty()) {
                            Cart.cartAmount = 0;
                        }

                    }
                } else {
                    newVisit = true;
                }

            }
        });

        System.out.println("Product Unit Draw Complete");

        return this.relativeLayout;

    }
}
