package com.mousebelly.app.userapp;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.CountDownTimer;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Kamal Kant on 13-04-2017.
 */

public class CustomToast {


    public static void Toast(Context c, String msg) {


        LinearLayout layout = new LinearLayout(c);
        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setBackgroundColor(Color.parseColor("#7C5A7E"));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            layout.setClipToOutline(true);
        }
        layout.setBackgroundResource(R.drawable.rounded_layer);


        ImageView imgView = new ImageView(c);
        imgView.setImageResource(R.drawable.logo);
//                imgView.setPadding(20,20,20,10);
        imgView.setAdjustViewBounds(true);
        layout.addView(imgView);


        LinearLayout layout2 = new LinearLayout(c);
        layout2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        layout2.setOrientation(LinearLayout.VERTICAL);
        layout2.setBackgroundColor(Color.parseColor("#ffffff"));
        layout.addView(layout2);

        TextView listText = new TextView(c);
        listText.setTextColor(Color.RED);
        listText.setPadding(90, 50, 90, 50);
        listText.setTextSize(25);
        listText.setText("MouseBelly");
        listText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 35);
//            listText.setTextSize(18 * getResources().getDisplayMetrics().density);
        // final Typeface maze = Typeface.createFromAsset(getAssets(), "fonts/The Heart Maze Demo.ttf");
        //listText.setTypeface(maze, maze.BOLD);
        layout2.addView(listText);

        TextView listText2 = new TextView(c);
        listText2.setTextColor(Color.RED);
        listText2.setPadding(90, 30, 90, 50);
        listText2.setTextSize(25);
        listText2.setText(msg);
        listText2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        //listText2.setTypeface(maze, maze.BOLD);
        layout2.addView(listText2);

        final Toast toast = new Toast(c);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);


        int toastDurationInMilliSeconds = 3000;  // 3 sec
        CountDownTimer toastCountDown;
        toastCountDown = new CountDownTimer(toastDurationInMilliSeconds, 1000 /*Tick duration*/) {
            public void onTick(long millisUntilFinished) {
                toast.show();
            }

            public void onFinish() {
                toast.cancel();
            }
        };
        toast.show();
        toastCountDown.start();

    }

}
