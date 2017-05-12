package com.mousebelly.app.userapp.Advertisment;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class DisplayAd extends PagerAdapter {
    Context mContext;

    DisplayAd(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        //System.out.println("Count: " + LoadAd.Ad.size());
        System.out.println("Inside count");
        return LoadAd.Ad.size();
    }

    @Override
    public boolean isViewFromObject(View v, Object obj) {
        return v == obj;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int i) {
        ImageView mImageView = new ImageView(mContext);
        mImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        Advertisment ads = LoadAd.Ad.get(i);
        System.out.println("Ad image url : " + ads.getImgBmp());

        mImageView.setImageBitmap(ads.getImgBmp());
        container.addView(mImageView, 0);
        return mImageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int i, Object obj) {
        container.removeView((ImageView) obj);
    }
}