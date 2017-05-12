package com.mousebelly.app.userapp.orderStack;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Vasudev on 16/04/2017.
 */

public class MyFragment extends Fragment {
    RelativeLayout relativeLayout;

    public static final MyFragment newInstance(RelativeLayout relativeLayout) {
        MyFragment f = new MyFragment();
        Bundle bdl = new Bundle(1);
        f.setArguments(bdl);

        f.relativeLayout = relativeLayout;

        return f;
    }

    public void setRelativeLayout(RelativeLayout relativeLayout) {
        this.relativeLayout = relativeLayout;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        RelativeLayout rl = new RelativeLayout(OrderStackMainActivity.context);

        TextView tv = new TextView(OrderStackMainActivity.context);
        rl.addView(tv);
        tv.setText("ABC");

        return this.relativeLayout;
    }
}
