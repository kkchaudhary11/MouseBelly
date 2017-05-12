package com.mousebelly.app.userapp.Advertisment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.net.URL;

/**
 * Created by Kamal Kant on 19-04-2017.
 */

public class Advertisment {

    private String ImageUrl;

    private Bitmap ImgBmp;

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;

//        setImgBmp(imageUrl);

    }

    public Bitmap getImgBmp() {
        return ImgBmp;
    }

    public void setImgBmp(String imgUrl) {

        try {
            URL url = new URL(imgUrl);
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());

            this.ImgBmp = bmp;
            System.out.println("Bitmap Loaded");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Advertisment{" +
                "ImageUrl='" + ImageUrl + '\'' +
                ", ImgBmp=" + ImgBmp +
                '}';
    }
}
