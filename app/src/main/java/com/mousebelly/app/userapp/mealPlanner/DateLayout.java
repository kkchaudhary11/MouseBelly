package com.mousebelly.app.userapp.mealPlanner;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;

/**
 * Created by Kamal Kant on 08-04-2017.
 */

public class DateLayout {

    public static HashMap<String, RelativeLayout> dateLayout = new HashMap<>();

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static void AddToLayout(FoodObject foodObject) {
        System.out.println(foodObject.getDate());

        RelativeLayout rl = dateLayout.get(foodObject.getDate());

        if (rl != null)
            if (rl.getChildCount() == 1) {
                TextView tv = (TextView) rl.getChildAt(0);

                RelativeLayout rl1 = FoodObject.dispFood(foodObject);

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(10, 10, 10, 10);
                params.addRule(RelativeLayout.RIGHT_OF, tv.getId());

                rl1.setLayoutParams(params);

                rl.addView(rl1);
            } else {
                TextView tv = (TextView) rl.getChildAt(0);

                RelativeLayout rl2 = (RelativeLayout) rl.getChildAt(rl.getChildCount() - 1);

                RelativeLayout rl1 = FoodObject.dispFood(foodObject);

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                params.setMargins(10, 10, 10, 10);
                params.addRule(RelativeLayout.RIGHT_OF, tv.getId());
                params.addRule(RelativeLayout.BELOW, rl2.getId());

                rl1.setLayoutParams(params);

                rl.addView(rl1);
            }
    }

    public static void RemoveFromLayout(FoodObject foodObject) {
        RelativeLayout rl = dateLayout.get(foodObject.getDate());

        if (rl != null)
            for (int i = 1; i < rl.getChildCount(); i++) {
                RelativeLayout rl1 = (RelativeLayout) rl.getChildAt(i);

                if (rl1.getTag().equals(foodObject.getDate() + "_" + foodObject.getProd_id())) {
                    rl.removeView(rl1);
                    break;
                }
            }
    }

}
