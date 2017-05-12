package com.mousebelly.app.userapp;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Kamal Kant on 26-04-2017.
 */

public class NoProductsMessage {
    //

    public static RelativeLayout show(Context c, String msg) {

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout rl = new RelativeLayout(c);
        rl.setLayoutParams(params);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        TextView noProducts = new TextView(c);
        noProducts.setLayoutParams(params);
        noProducts.setText(msg);
        noProducts.setTypeface(null, Typeface.BOLD);
        noProducts.setGravity(Gravity.CENTER);
        noProducts.setPadding(10, 100, 10, 0);
        noProducts.setTextSize(21);
        noProducts.setTextColor(ContextCompat.getColor(c, R.color.Amulet));

        rl.addView(noProducts);

        return rl;


    }
}
