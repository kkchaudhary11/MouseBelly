package com.mousebelly.app.userapp.feedback;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.media.ThumbnailUtils;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mousebelly.app.userapp.IdManager;
import com.mousebelly.app.userapp.Login.LoginActivity;
import com.mousebelly.app.userapp.R;
import com.mousebelly.app.userapp.Server;
import com.mousebelly.app.userapp.products.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kamal Kant on 14-04-2017.
 */

public class FeedFoodObjectInOrderStack {


    private String PID;
    private String Product_Name;
    private String status;
    private String HWID;
    private String Qty;
    private String stars;
    private String Price_item;
    private String orderid;
    private String Image;
    private Bitmap bmpImage;

    private float ProductRating;

    private float OrderRating = 0.0f;


    public float getOrderRating() {
        return OrderRating;
    }

    public void setOrderRating(float orderRating) {
        OrderRating = orderRating;

    }

    public float getProductRating() {
        return ProductRating;
    }

    public void setProductRating(float productRating) {
        ProductRating = productRating;
    }

    //getters and setters

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


    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        setBmpImage(image);
    }

    public Bitmap getBmpImage() {
        return bmpImage;
    }

    public void setBmpImage(String ImageUrl) {

        Image = ImageUrl;
        try {
            URL url = new URL(ImageUrl);
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());

            //this.bmpImage = bmp;
            System.out.println("Bitmap Loaded");
            final int THUMBSIZE = 200;

            Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(bmp,
                    THUMBSIZE, THUMBSIZE);

            this.bmpImage = ThumbImage;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static int Id = 0;
    int myId;
    static Double AverateRatingValue;

    @Override
    public String toString() {
        return "FeedFoodObjectInOrderStack{" +
                "PID='" + PID + '\'' +
                ", Product_Name='" + Product_Name + '\'' +
                ", status='" + status + '\'' +
                ", HWID='" + HWID + '\'' +
                ", Qty='" + Qty + '\'' +
                ", stars='" + stars + '\'' +
                ", Price_item='" + Price_item + '\'' +
                ", orderid='" + orderid + '\'' +
                ", Image='" + Image + '\'' +
                ", bmpImage=" + bmpImage +
                ", ProductRating=" + ProductRating +
                ", OrderRating=" + OrderRating +
                '}';
    }

    RelativeLayout relativeLayout;
    TextView ProductNameTextView;
    ImageView ImageView;
    RatingBar RatingBar;
    TextView RatingValueTextView;
    EditText ReviewEditText;
    ImageButton favoriteButton;

    public static HashMap<String, Float> PRating = new HashMap<>();

    public FeedFoodObjectInOrderStack() {

        Id++;
        this.myId = Id;

        this.relativeLayout = new RelativeLayout(MainActivity.context);
        IdManager.addId("relativeLayout" + this.myId);
        this.relativeLayout.setId((int) IdManager.stringToIdMap.get("relativeLayout" + this.myId));

        this.ImageView = new ImageView(MainActivity.context);
        IdManager.addId("ImageView" + this.myId);
        this.ImageView.setId((int) IdManager.stringToIdMap.get("ImageView" + this.myId));


        this.ProductNameTextView = new TextView(MainActivity.context);
        IdManager.addId("ProductNameTextView" + this.myId);
        this.ProductNameTextView.setId((int) IdManager.stringToIdMap.get("ProductNameTextView" + this.myId));

        this.RatingBar = new RatingBar(MainActivity.context);
        IdManager.addId("RatingBar" + this.myId);
        this.RatingBar.setId((int) IdManager.stringToIdMap.get("RatingBar" + this.myId));

        this.RatingValueTextView = new TextView(MainActivity.context);
        IdManager.addId("RatingValueTextView" + this.myId);
        this.RatingValueTextView.setId((int) IdManager.stringToIdMap.get("RatingValueTextView" + this.myId));

        this.ReviewEditText = new EditText(MainActivity.context);
        IdManager.addId("ReviewEditText" + this.myId);
        this.ReviewEditText.setId((int) IdManager.stringToIdMap.get("ReviewEditText" + this.myId));
        this.ReviewEditText.setVisibility(View.GONE);

        this.favoriteButton = new ImageButton(MainActivity.context);
        IdManager.addId("favoriteButton" + this.myId);
        this.favoriteButton.setId((int) IdManager.stringToIdMap.get("favoriteButton" + this.myId));


    }

    public RelativeLayout draw(final FeedFoodObjectInOrderStack feedFoodObjectInOrderStack) {

        // final FeedFoodObjectInOrderStack fobj = feedFoodObjectInOrderStack;

        this.relativeLayout = new RelativeLayout(MainActivity.context);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        this.ImageView.setLayoutParams(params);
        this.ImageView.setPadding(20, 20, 20, 20);
        this.ImageView.setImageBitmap(feedFoodObjectInOrderStack.getBmpImage());

        this.relativeLayout.addView(ImageView);

        //product name
        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.RIGHT_OF, ImageView.getId());
        this.ProductNameTextView.setLayoutParams(params);
        this.ProductNameTextView.setText(feedFoodObjectInOrderStack.getProduct_Name());
        this.ProductNameTextView.setTextSize(18);
        this.ProductNameTextView.setTypeface(null, Typeface.BOLD);
        this.ProductNameTextView.setPadding(10, 30, 10, 10);
        this.relativeLayout.addView(ProductNameTextView);

        //favorite button
        boolean isFoodFav = false;
        params = new RelativeLayout.LayoutParams(100, 100);
        params.addRule(RelativeLayout.RIGHT_OF, RatingValueTextView.getId());
        params.addRule(RelativeLayout.BELOW, ProductNameTextView.getId());
        params.setMargins(30, 0, 0, 0);
        this.favoriteButton.setLayoutParams(params);
        ArrayList<String> favList = LoginActivity.user.getFav();
        for (String fav : favList) {
            System.out.println("----");
            System.out.println("fav  : " + fav);
            System.out.println("pid  : " + feedFoodObjectInOrderStack.getPID());
            if (fav.equals(feedFoodObjectInOrderStack.getPID())) {
                System.out.println("mathced");
                this.favoriteButton.setImageResource(R.drawable.fav_border_enable);
                this.favoriteButton.setTag("FavFood");
                break;
            } else {
                this.favoriteButton.setImageResource(R.drawable.fav_border);
                this.favoriteButton.setTag("NotFavFood");
            }
        }
        this.relativeLayout.addView(favoriteButton);

        this.favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (favoriteButton.getTag().toString().equals("FavFood")) {


                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                System.out.println("unfav fav food");
                                Server.s.put("http://mousebelly.herokuapp.com/sign/deletefavorite/" + LoginActivity.USERID + "/" + feedFoodObjectInOrderStack.getPID());

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }).start();

                    favoriteButton.setImageResource(R.drawable.fav_border);

                } else {
                    final JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("Prod_id", feedFoodObjectInOrderStack.getPID());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    System.out.println(jsonObject);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Server.s.putwithdata("http://mousebelly.herokuapp.com/sign/sign1/" + LoginActivity.USERID, jsonObject);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();

                    favoriteButton.setImageResource(R.drawable.fav_border_enable);
                }
            }
        });


        //rating bar
        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW, ImageView.getId());
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        this.RatingBar.setLayoutParams(params);
        this.RatingBar.setStepSize((float) 0.1);
        this.RatingBar.setNumStars(5);
        this.RatingBar.setScaleX(0.5f);
        this.RatingBar.setScaleY(0.5f);
        relativeLayout.addView(RatingBar);

        this.RatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                System.out.println(v);
                RatingValueTextView.setText(String.valueOf(v));
                feedFoodObjectInOrderStack.setOrderRating(v);

                feedFoodObjectInOrderStack.setProductRating(v);

                PRating.put(feedFoodObjectInOrderStack.getPID(), v);

                System.out.println("Rating HashMap :  " + PRating);

                AverateRatingValue = calculateAverage(PRating);
                System.out.println("Average Rating : " + AverateRatingValue);

                if (AverateRatingValue < 3.8) {
                    LoadOrdersForFeedback.OrderRatingEditText.setVisibility(View.VISIBLE);
                } else {
                    LoadOrdersForFeedback.OrderRatingEditText.setVisibility(View.GONE);
                }


            }
        });


        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW, ProductNameTextView.getId());
        params.addRule(RelativeLayout.RIGHT_OF, ImageView.getId());

        this.RatingValueTextView.setLayoutParams(params);
        this.RatingValueTextView.setText("0");
        this.RatingValueTextView.setTypeface(null, Typeface.BOLD);
        this.RatingValueTextView.setTextColor(ContextCompat.getColor(MainActivity.context, R.color.Affair));
        this.RatingValueTextView.setPadding(20, 10, 10, 10);
        this.relativeLayout.addView(RatingValueTextView);

        //review editbox
        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW, relativeLayout.getId());
        this.ReviewEditText.setLayoutParams(params);
        this.ReviewEditText.setHint("Review");
        this.ReviewEditText.setPadding(10, 10, 10, 10);
        this.relativeLayout.addView(ReviewEditText);


        GradientDrawable shape = new GradientDrawable();
        shape.setStroke(5, ContextCompat.getColor(MainActivity.context, R.color.Amulet));
        relativeLayout.setBackground(shape);
        return relativeLayout;

    }

    private double calculateAverage(HashMap<String, Float> rating) {
        Float sum = 0f;
        if (!rating.isEmpty()) {

            for (Float mark : rating.values()) {
                sum += mark;
            }
            double res = sum.doubleValue() / rating.size();
            return (double) Math.round(res * 100) / 100;
        }

        return sum;
    }


}