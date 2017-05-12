package com.mousebelly.app.userapp.userprofile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.mousebelly.cropper.CropImage;
import com.mousebelly.cropper.CropImageView;
import com.mousebelly.app.userapp.R;
import com.roger.catloadinglibrary.CatLoadingView;

public class UserprofileUploadphoto extends AppCompatActivity {
    Button register;
    CatLoadingView mView;
    int status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userprofileactivity_photo);
        mView = new CatLoadingView();

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        register = (Button) findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*mView.show(getSupportFragmentManager(), "");

                try {
                    int status = server.post("http://mousebelly.herokuapp.com/sign/sign", userprofileUserBean.tojson());
                    if (status==200){
                        mView.dismiss();
                        Toast.makeText(UserprofileUploadphoto.this, "Account Created", Toast.LENGTH_SHORT).show();
                    }else {
                        mView.dismiss();
                        Toast.makeText(UserprofileUploadphoto.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/

                //  new Usersend().execute();
            }
        });
    }

    public void onSelectImageClick(View view) {
        startCropImageActivity(null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            System.out.println("This is Result ::::::::::::::::" + result);
            if (resultCode == RESULT_OK) {
                ((ImageView) findViewById(R.id.quick_start_cropped_image)).setImageURI(result.getUri());

                System.out.println("This is result get URI::::::::::::::::::::" + result.getUri());
                Toast.makeText(this, "Cropping successful, Sample: " + result.getSampleSize(), Toast.LENGTH_LONG).show();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Start crop image activity for the given image.
     */
    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);

    }

   /* public class Usersend extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mView.show(getSupportFragmentManager(), "");
        }

        @Override
        protected Void doInBackground(Void... voids) {
            System.out.println("This is do in background");

            try {
                status = server.post("http://mousebelly.herokuapp.com/sign/sign", userprofileUserBean.tojson());

                //if (status==200){

                   // new Handler(new Looper(new Runnable(){

                   // }));
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        public void run() {
                            if (status==200){
                                Toast.makeText(UserprofileUploadphoto.this, "Account Created", Toast.LENGTH_SHORT).show();
                                *//*Intent intent = new Intent(UserprofileUploadphoto.this, FoodActivity.class);
                                startActivity(intent);*//*
                            }else {
                                Toast.makeText(UserprofileUploadphoto.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    *//*Toast.makeText(UserprofileUploadphoto.this, "Account Created", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(UserprofileUploadphoto.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }*//*
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPreExecute();
            mView.dismiss();

        }
    }*/
}
