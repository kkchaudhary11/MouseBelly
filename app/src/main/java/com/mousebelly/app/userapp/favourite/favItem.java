package com.mousebelly.app.userapp.favourite;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mousebelly.app.userapp.IdManager;
import com.mousebelly.app.userapp.Login.LoginActivity;
import com.mousebelly.app.userapp.R;
import com.mousebelly.app.userapp.Server;
import com.mousebelly.app.userapp.products.MainActivity;

import java.net.URL;

/**
 * Created by Kamal Kant on 12-05-2017.
 */

public class favItem {

    String Prod_name;
    String Prod_category;
    String Description;
    String Price;
    String Prod_id;
    String Image;
    String starsize;
    String HWF_Name;
    Bitmap bmpImage;


    //getters and setters
    public String getProd_name() {
        return Prod_name;
    }

    public void setProd_name(String prod_name) {
        Prod_name = prod_name;
    }

    public String getProd_category() {
        return Prod_category;
    }

    public void setProd_category(String prod_category) {
        Prod_category = prod_category;
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
    }

    public String getProd_id() {
        return Prod_id;
    }

    public void setProd_id(String prod_id) {
        Prod_id = prod_id;
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

    public String getHWF_Name() {
        return HWF_Name;
    }

    public void setHWF_Name(String HWF_Name) {
        this.HWF_Name = HWF_Name;
    }

    static int Id = 0;
    int myId;

    RelativeLayout rl;
    TextView productNameTextView;
    ImageView productIV;
    TextView productCategoryTextView;
    TextView hwfNameTextView;
    Button removeButton;

    TextView separator;

    public favItem() {

        Id++;
        this.myId = Id;

        this.rl = new RelativeLayout(favMainActivity.favItemContext);
        IdManager.addId("relativeLayout" + this.myId);
        this.rl.setId((int) IdManager.stringToIdMap.get("relativeLayout" + this.myId));

        this.productNameTextView = new TextView(favMainActivity.favItemContext);
        IdManager.addId("productNameTextView" + this.myId);
        this.productNameTextView.setId((int) IdManager.stringToIdMap.get("productNameTextView" + this.myId));

        this.productIV = new ImageView(favMainActivity.favItemContext);
        IdManager.addId("productIV" + this.myId);
        this.productIV.setId((int) IdManager.stringToIdMap.get("productIV" + this.myId));

        this.separator = new TextView(favMainActivity.favItemContext);
        IdManager.addId("separator" + this.myId);
        this.separator.setId((int) IdManager.stringToIdMap.get("separator" + this.myId));


        this.productCategoryTextView = new TextView(favMainActivity.favItemContext);
        IdManager.addId("productCategoryTextView" + this.myId);
        this.productCategoryTextView.setId((int) IdManager.stringToIdMap.get("productCategoryTextView" + this.myId));

        this.hwfNameTextView = new TextView(favMainActivity.favItemContext);
        IdManager.addId("hwfNameTextView" + this.myId);
        this.hwfNameTextView.setId((int) IdManager.stringToIdMap.get("hwfNameTextView" + this.myId));

        this.removeButton = new Button(favMainActivity.favItemContext);
        IdManager.addId("removeButton" + this.myId);
        this.removeButton.setId((int) IdManager.stringToIdMap.get("removeButton" + this.myId));

    }

    public RelativeLayout draw(final favItem favItem) {

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        GradientDrawable shape = new GradientDrawable();
        //shape.setCornerRadius(8);
        shape.setStroke(5, ContextCompat.getColor(favMainActivity.favItemContext, R.color.Affair));
        rl.setBackground(shape);

        //image view
        params = new RelativeLayout.LayoutParams(270, 270);
        this.productIV.setLayoutParams(params);
        this.productIV.setPadding(20, 20, 20, 20);
        this.productIV.setImageBitmap(favItem.bmpImage);
        rl.addView(productIV);

        //product name
        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.RIGHT_OF, this.productIV.getId());
        this.productNameTextView.setPadding(10, 10, 10, 10);
        this.productNameTextView.setTextSize(21);
        this.productNameTextView.setLayoutParams(params);
        this.productNameTextView.setText(favItem.getProd_name());
        rl.addView(productNameTextView);


        //product category
        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.RIGHT_OF, this.productIV.getId());
        params.addRule(RelativeLayout.BELOW, this.productNameTextView.getId());
        this.productNameTextView.setTypeface(null, Typeface.BOLD);
        this.productCategoryTextView.setPadding(10, 10, 10, 10);
        this.productCategoryTextView.setLayoutParams(params);
        this.productCategoryTextView.setText(favItem.getProd_category());
        rl.addView(productCategoryTextView);


        //hwf name
        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.RIGHT_OF, this.productIV.getId());
        params.addRule(RelativeLayout.BELOW, this.productCategoryTextView.getId());
        this.hwfNameTextView.setTypeface(null, Typeface.BOLD);
        this.hwfNameTextView.setPadding(10, 10, 10, 10);
        this.hwfNameTextView.setLayoutParams(params);
        this.hwfNameTextView.setText(favItem.getHWF_Name());
        rl.addView(hwfNameTextView);


        //remove button
        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params.addRule(RelativeLayout.BELOW, this.productNameTextView.getId());
        params.setMargins(10, 10, 10, 10);
        this.removeButton.setBackgroundColor(ContextCompat.getColor(favMainActivity.favItemContext, R.color.Roman));
        this.removeButton.setTextColor(Color.WHITE);
        this.removeButton.setLayoutParams(params);
        this.removeButton.setText("Remove");
        rl.addView(removeButton);

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            System.out.println("unfav fav food");
                            Server.s.put("http://mousebelly.herokuapp.com/sign/deletefavorite/" + LoginActivity.USERID + "/" + favItem.getProd_id());

                            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    favMainActivity.favItem.removeView(rl);
                                }
                            }, 100);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
            }
        });

        return rl;
    }
}
