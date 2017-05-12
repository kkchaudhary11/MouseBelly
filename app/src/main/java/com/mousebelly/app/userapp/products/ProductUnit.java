package com.mousebelly.app.userapp.products;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mousebelly.app.userapp.IdManager;
import com.mousebelly.app.userapp.R;

import java.net.URL;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.widget.ImageView.ScaleType;
import static com.mousebelly.app.userapp.products.MainActivity.context;

/**
 * Created by Vasudev on 09/04/2017.
 */

public class ProductUnit {

    static public int productDetailsCount = 0;
    private static int callCount = 0;
    RelativeLayout productViewLayout;
    CardView cards;
    ImageView itemImage;
    ImageView plus;
    ImageView minus;
    TextView totalQty;
    TextView productNameText;
    TextView prodCategoryText;
    TextView prodPriceText;
    TextView RupeeSymbol;
    RatingBar prodRatingbar;
    LinearLayout wrapWholeContent;
    LinearLayout imgLayout;
    LinearLayout TextContents;
    LinearLayout Prod_nameLayout;
    LinearLayout addtocartIcons;
    LinearLayout prodCategoryLayout;
    LinearLayout bottomLayout;
    LinearLayout categoryLayput;
    private String _id;
    private String Prod_name;
    private String Prod_category;
    private String Description;
    private String Price;
    private String Prod_id;
    private String feedback;
    private String isApproved;
    private String isRejected;
    private String Image;
    private String starsize;
    private String HWFEmail_id;
    private String HWF_Name;
    private String Phone_no;
    private String __v;
    private String countrate;
    private int Qty = 0;
    private Bitmap bmpImage;
    private int Id;

    public ProductUnit() {
        Typeface trumpitFace = Typeface.createFromAsset(context.getAssets(), "fonts/Trumpit.ttf");
        Typeface rupeeSymbol = Typeface.createFromAsset(context.getAssets(), "fonts/IndianRupee.ttf");
        final Typeface maze = Typeface.createFromAsset(context.getAssets(), "fonts/The Heart Maze Demo.ttf");
        final Typeface epicselfie = Typeface.createFromAsset(context.getAssets(), "fonts/My Epic Selfie Demo.ttf");
        Typeface novaoval = Typeface.createFromAsset(context.getAssets(), "fonts/NovaOval.ttf");

        ProductUnit.productDetailsCount++;
        Log.d(String.valueOf(ProductUnit.productDetailsCount), "ProductUnit: ");
        this.Id = ProductUnit.productDetailsCount;
        Log.d(String.valueOf(context), "ProductUnit [LOG]: " + this.Id);

//        Setting Parant Layout and ids
        this.productViewLayout = new RelativeLayout(context);
        IdManager.addId("Productlistid" + this.Id);
        this.productViewLayout.setId((Integer) IdManager.stringToIdMap.get("Productlistid" + this.Id));


        this.cards = new CardView(context);
        IdManager.addId("Productlistcards" + this.Id);
        this.cards.setId((Integer) IdManager.stringToIdMap.get("Productlistcards" + this.Id));
        this.cards.setLayoutParams(new CardView.LayoutParams(MATCH_PARENT,
                CardView.LayoutParams.WRAP_CONTENT));
        this.cards.setContentPadding(0, 0, 0, 0);
        this.cards.setCardElevation(15);
        this.cards.setMaxCardElevation(20);
        this.cards.setUseCompatPadding(true);
        this.cards.setCardBackgroundColor(Color.parseColor("#EEEEEE"));

        this.wrapWholeContent = new LinearLayout(context);
        IdManager.addId("ProductlistWrapLayout" + this.Id);
        this.wrapWholeContent.setId((int) IdManager.stringToIdMap.get("ProductlistWrapLayout" + this.Id));
        this.wrapWholeContent.setOrientation(LinearLayout.VERTICAL);
        this.wrapWholeContent.setPadding(10, 10, 10, 10);

        this.imgLayout = new LinearLayout(context);
        IdManager.addId("ProductlistimgLayout" + this.Id);
        this.imgLayout.setId((int) IdManager.stringToIdMap.get("ProductlistimgLayout" + this.Id));
        this.imgLayout.setOrientation(LinearLayout.HORIZONTAL);
        this.imgLayout.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

        this.itemImage = new ImageView(context);
        IdManager.addId("ProductlistImage" + this.Id);
        this.itemImage.setId((int) IdManager.stringToIdMap.get("ProductlistImage" + this.Id));
        this.itemImage.setAdjustViewBounds(true);
        this.itemImage.setScaleType(ScaleType.CENTER_CROP);
        this.itemImage.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 500));

        this.TextContents = new LinearLayout(context);
        IdManager.addId("ProductlistTextLayout" + this.Id);
        this.TextContents.setId((int) IdManager.stringToIdMap.get("ProductlistTextLayout" + this.Id));
        this.TextContents.setPadding(0, 10, 0, 10);
        this.TextContents.setOrientation(LinearLayout.HORIZONTAL);
        this.TextContents.setLayoutParams(new LinearLayout.LayoutParams(
                MATCH_PARENT, MATCH_PARENT
        ));

        this.Prod_nameLayout = new LinearLayout(context);
        IdManager.addId("ProductlistprodnameLayout" + this.Id);
        this.Prod_nameLayout.setId((int) IdManager.stringToIdMap.get("ProductlistprodnameLayout" + this.Id));
        this.Prod_nameLayout.setGravity(Gravity.LEFT);

        this.productNameText = new TextView(context);
        IdManager.addId("Productlistproductname" + this.Id);
        this.productNameText.setId((int) IdManager.stringToIdMap.get("Productlistproductname" + this.Id));
        this.productNameText.setTypeface(maze, Typeface.BOLD);
        this.productNameText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        this.productNameText.setTextColor(Color.BLACK);
        this.productNameText.setPadding(10, 10, 0, 10);

        this.addtocartIcons = new LinearLayout(context);
        IdManager.addId("Productlistcartlayout" + this.Id);
        this.addtocartIcons.setId((int) IdManager.stringToIdMap.get("Productlistcartlayout" + this.Id));
        this.addtocartIcons.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, 75));
        this.addtocartIcons.setOrientation(LinearLayout.HORIZONTAL);
        this.addtocartIcons.setGravity(Gravity.RIGHT);


        this.plus = new ImageView(context);
        IdManager.addId("Productlistplusicon" + this.Id);
        this.plus.setId((int) IdManager.stringToIdMap.get("Productlistplusicon" + this.Id));
        this.plus.setImageResource(R.drawable.plus);
        this.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plus.setColorFilter(ContextCompat.getColor(context, R.color.Amulet));
                //Toast.makeText(context, "Product Added to Cart", Toast.LENGTH_SHORT).show();
                if (getQty() > 7) {
                    return;
                }

                if (getQty() > 0) {
                    int oldValue = getQty();
                    if (oldValue < 7) {
                        int temp = getQty();
                        temp++;
                        setQty(temp);
                        System.out.println("QTY : " + temp);
                        totalQty.setText(String.valueOf(temp));


                        boolean flag = Cart.updateCart(ProductUnit.this, oldValue);
                    }

                } else {
                    Qty++;
                    totalQty.setText(String.valueOf(getQty()));
                    Cart.addToCart(ProductUnit.this);
                }
                System.out.println("Cart DAta : " + Cart.cartItems);

                plus.setColorFilter(ContextCompat.getColor(context, R.color.Black));
            }
        });


        this.minus = new ImageView(context);
        IdManager.addId("Productlistminus" + this.Id);
        this.minus.setId((int) IdManager.stringToIdMap.get("Productlistminus" + this.Id));
        this.minus.setImageResource(R.drawable.minustwo);
        this.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plus.setColorFilter(ContextCompat.getColor(context, R.color.Amulet));

                if (getQty() <= 0) {
                    return;
                }

                if (getQty() <= 1) {
                    System.out.println("---------Item Removed from the cart ");
                    Cart.removeFromCart(ProductUnit.this);
                    Qty--;
                    totalQty.setText(String.valueOf(getQty()));

                } else {
                    int oldValue = getQty();
                    int temp = getQty();
                    temp--;
                    setQty(temp);
                    System.out.println("QTY : " + temp);
                    totalQty.setText(String.valueOf(temp));
                    Cart.updateCart(ProductUnit.this, oldValue);

                }

                System.out.println("Cart DAta : " + Cart.cartItems);


                minus.setColorFilter(ContextCompat.getColor(context, R.color.Black));
            }
        });


        this.totalQty = new TextView(context);
        IdManager.addId("Productlistqty" + this.Id);
        this.totalQty.setId((int) IdManager.stringToIdMap.get("Productlistqty" + this.Id));
        this.totalQty.setPadding(5, 0, 5, 0);
        this.totalQty.setTextSize(25);
        this.totalQty.setText(String.valueOf(getQty()));

        this.prodCategoryLayout = new LinearLayout(context);
        IdManager.addId("ProductprodCategoryLayout" + this.Id);
        this.prodCategoryLayout.setId((int) IdManager.stringToIdMap.get("ProductprodCategoryLayout" + this.Id));


        this.prodCategoryText = new TextView(context);
        IdManager.addId("ProductlistCategory" + this.Id);
        this.prodCategoryText.setId((int) IdManager.stringToIdMap.get("ProductlistCategory" + this.Id));
        this.prodCategoryText.setTextColor(Color.DKGRAY);
        this.prodCategoryText.setPadding(30, 0, 0, 0);
        this.prodCategoryText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        this.prodCategoryText.setTypeface(epicselfie);

        this.bottomLayout = new LinearLayout(context);
        IdManager.addId("bottomLayout" + this.Id);
        this.bottomLayout.setId((int) IdManager.stringToIdMap.get("bottomLayout" + this.Id));

        this.prodPriceText = new TextView(context);
        IdManager.addId("Productlistprice" + this.Id);
        this.prodPriceText.setId((int) IdManager.stringToIdMap.get("Productlistprice" + this.Id));

        this.RupeeSymbol = new TextView(context);
        IdManager.addId("Productlistrupeesymbol" + this.Id);
        this.RupeeSymbol.setId((int) IdManager.stringToIdMap.get("Productlistrupeesymbol" + this.Id));
        this.RupeeSymbol.setTypeface(rupeeSymbol);
        this.RupeeSymbol.setPadding(40, 30, 0, 0);
        this.RupeeSymbol.setText("`");


        this.prodRatingbar = new RatingBar(context);
        IdManager.addId("Productlistratingbar" + this.Id);
        this.prodRatingbar.setId((int) IdManager.stringToIdMap.get("Productlistratingbar" + this.Id));
        this.prodRatingbar.setStepSize((float) 0.1);
        this.prodRatingbar.setNumStars(5);
        this.prodRatingbar.setScaleX(0.5f);
        this.prodRatingbar.setScaleY(0.5f);
        this.prodRatingbar.setIsIndicator(true);
        this.prodRatingbar.setBackgroundColor(Color.parseColor("#FFFF00"));
    }

    public int getQty() {
        return Qty;
    }

    public void setQty(int qty) {
        Qty = qty;
        this.totalQty.setText(String.valueOf(qty));
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
        Prod_name = prod_name;
        this.productNameText.setText(prod_name);
    }

    public String getProd_category() {
        return Prod_category;
    }

    public void setProd_category(String prod_category) {
        Prod_category = prod_category;
        this.prodCategoryText.setText(prod_category);
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
        this.prodPriceText.setText(price);
    }

    //UI Handling

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

            this.itemImage.setImageBitmap(this.bmpImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getStarsize() {
        return starsize;
    }

    public void setStarsize(String starsize) {
        this.starsize = starsize;
        this.prodRatingbar.setRating(Float.parseFloat(starsize));
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
    }

    public String getPhone_no() {
        return Phone_no;
    }

    public void setPhone_no(String phone_no) {
        Phone_no = phone_no;
    }

    public String get__v() {
        return __v;
    }

    public void set__v(String __v) {
        this.__v = __v;
    }

    public String getCountrate() {
        return countrate;
    }

    public void setCountrate(String countrate) {
        this.countrate = countrate;
    }

//    Dynamic list

    @Override
    public String toString() {
        return "{" +
                "_id:'" + _id + '\'' +
                ", Prod_name:'" + Prod_name + '\'' +
                ", Prod_category:'" + Prod_category + '\'' +
                ", Description:'" + Description + '\'' +
                ", Price:'" + Price + '\'' +
                ", Prod_id:'" + Prod_id + '\'' +
                ", feedback:'" + feedback + '\'' +
                ", isApproved:'" + isApproved + '\'' +
                ", isRejected:'" + isRejected + '\'' +
                ", Image:'" + Image + '\'' +
                ", starsize:'" + starsize + '\'' +
                ", HWFEmail_id:'" + HWFEmail_id + '\'' +
                ", HWF_Name:'" + HWF_Name + '\'' +
                ", Phone_no:'" + Phone_no + '\'' +
                ", __v:'" + __v + '\'' +
                ", countrate:'" + countrate + '\'' +
                ", Qty:'" + Qty + '\'' +
                '}';
    }


    //show cart items

    public RelativeLayout draw() {
//        this.productViewLayout.addView(this.itemImage);
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
//        this.productViewLayout.setLayoutParams(params);

        this.productViewLayout.setTag(this.Prod_id);

        this.imgLayout.addView(this.itemImage);


        this.cards.addView(this.wrapWholeContent);
        this.wrapWholeContent.addView(this.imgLayout);
        this.wrapWholeContent.addView(this.TextContents);

        this.TextContents.addView(this.Prod_nameLayout);
        this.Prod_nameLayout.addView(this.productNameText);

        this.TextContents.addView(this.addtocartIcons);
        this.addtocartIcons.addView(this.minus);
        this.addtocartIcons.addView(this.totalQty);
        this.addtocartIcons.addView(this.plus);

        this.wrapWholeContent.addView(this.prodCategoryLayout);
        this.prodCategoryLayout.addView(this.prodCategoryText);

        this.wrapWholeContent.addView(this.bottomLayout);
        this.bottomLayout.addView(this.RupeeSymbol);
        this.bottomLayout.addView(this.prodPriceText);
        this.bottomLayout.addView(this.prodRatingbar);

        productViewLayout.addView(this.cards);

        System.out.println("Tag of ProductViewLayout : " + productViewLayout.getTag());

        return productViewLayout;
    }

    public RelativeLayout showCart(final ProductUnit productUnit) {

        callCount++;

        RelativeLayout rl = new RelativeLayout(MainActivity.context);

        IdManager.addId("productNameTextView" + callCount);
        rl.setId((int) IdManager.stringToIdMap.get("productNameTextView" + callCount));

        rl.setTag(productUnit.getProd_id());

        ImageView iv = new ImageView(MainActivity.context);
        IdManager.addId("productImageView" + callCount);
        iv.setId((int) IdManager.stringToIdMap.get("productImageView" + callCount));

        TextView tv = new TextView(MainActivity.context);
        IdManager.addId("productNameView" + callCount);
        tv.setId((int) IdManager.stringToIdMap.get("productNameView" + callCount));

        final TextView qty = new TextView(MainActivity.context);
        IdManager.addId("productQtyView" + callCount);
        qty.setId((int) IdManager.stringToIdMap.get("productQtyView" + callCount));

        Button delete = new Button(MainActivity.context);
        IdManager.addId("productDeleteView" + callCount);
        delete.setId((int) IdManager.stringToIdMap.get("productDeleteView" + callCount));

        Button incQty = new Button(MainActivity.context);
        IdManager.addId("productIncView" + callCount);
        incQty.setId((int) IdManager.stringToIdMap.get("productIncView" + callCount));

        Button decQty = new Button(MainActivity.context);
        IdManager.addId("productDecView" + callCount);
        decQty.setId((int) IdManager.stringToIdMap.get("productDecView" + callCount));


        // product image
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(250, 250);
        params.setMargins(10, 10, 10, 0);
        iv.setLayoutParams(params);
        iv.setImageBitmap(productUnit.bmpImage);
        iv.setPadding(10, 10, 0, 0);

        //product name
        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.RIGHT_OF, iv.getId());
        tv.setLayoutParams(params);
        tv.setTypeface(null, Typeface.BOLD);
        tv.setTextSize(19);
        tv.setPadding(20, 100, 0, 0);
        tv.setText(productUnit.getProd_name());

        //decbutton
        params = new RelativeLayout.LayoutParams(100, 100);
        params.setMargins(20, 0, 0, 0);
        params.addRule(RelativeLayout.BELOW, iv.getId());
        decQty.setLayoutParams(params);
        decQty.setPadding(0, 0, 0, 0);
        decQty.setTextSize(25);
        decQty.setBackgroundColor(ContextCompat.getColor(MainActivity.context, R.color.Affair));
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
        qty.setBackgroundColor(ContextCompat.getColor(MainActivity.context, R.color.Amulet));
        qty.setTypeface(null, Typeface.BOLD);
        qty.setText(String.valueOf(productUnit.getQty()));


        //incqty
        params = new RelativeLayout.LayoutParams(100, 100);

        params.addRule(RelativeLayout.RIGHT_OF, qty.getId());
        params.addRule(RelativeLayout.BELOW, iv.getId());
        incQty.setPadding(0, 0, 0, 0);
        incQty.setLayoutParams(params);
        incQty.setTextSize(25);
        incQty.setBackgroundColor(ContextCompat.getColor(MainActivity.context, R.color.Affair));
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
        delete.setBackgroundColor(ContextCompat.getColor(MainActivity.context, R.color.Roman));
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
        shape.setStroke(5, ContextCompat.getColor(MainActivity.context, R.color.Amulet));
        rl.setBackground(shape);

        incQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int oldValue = productUnit.getQty();
                if (oldValue < 7) {
                    int temp = productUnit.getQty();
                    temp++;
                    productUnit.setQty(temp);
                    System.out.println("QTY : " + temp);
                    qty.setText(String.valueOf(temp));
                    totalQty.setText(String.valueOf(temp));

                    boolean flag = Cart.updateCart(productUnit, oldValue);
                    if (flag == false) {
                        qty.setText(String.valueOf(temp - 1));
                    }
                }
            }
        });

        decQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (productUnit.getQty() <= 1) {
                    return;
                }
                int oldValue = productUnit.getQty();
                int temp = productUnit.getQty();
                temp--;
                productUnit.setQty(temp);
                System.out.println("QTY : " + temp);

                qty.setText(String.valueOf(temp));

                Cart.updateCart(productUnit, oldValue);

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cart.removeFromCart(productUnit);

                productUnit.setQty(0);
                totalQty.setText(String.valueOf(productUnit.getQty()));

                if (Cart.cartItems.isEmpty()) {
                    Cart.cartAmount = 0;
                    MainActivity.ProductCartDialog.dismiss();
                }

            }
        });


        return rl;
    }

}
