package com.mousebelly.app.userapp.signUp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.mousebelly.cropper.CropImage;
import com.mousebelly.cropper.CropImageView;
import com.mousebelly.app.userapp.Login.LoginActivity;
import com.mousebelly.app.userapp.Login.VerifyLogin;
import com.mousebelly.app.userapp.R;
import com.mousebelly.app.userapp.Server;
import com.mousebelly.app.userapp.WalletHandler;
import com.mousebelly.app.userapp.products.MainActivity;
import com.roger.catloadinglibrary.CatLoadingView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.mousebelly.app.userapp.signUp.UserPro.signUpBean;

public class Uploadphoto extends AppCompatActivity {
    Button register;
    CatLoadingView mView;
    int status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
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
                    int status = server.post("http://mousebelly.herokuapp.com/sign/sign", signUpBean.tojson());
                    if (status==200){
                        mView.dismiss();
                        Toast.makeText(Uploadphoto.this, "Account Created", Toast.LENGTH_SHORT).show();
                    }else {
                        mView.dismiss();
                        Toast.makeText(Uploadphoto.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/

                new Usersend().execute();
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

    public class Usersend extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mView.show(getSupportFragmentManager(), "");
        }

        @Override
        protected Void doInBackground(Void... voids) {
            System.out.println("This is do in background");

            try {
                status = Server.s.post("http://mousebelly.herokuapp.com/sign/sign", signUpBean.tojson());
                System.out.println("THis is Status:::::::::::::::::" + status);

                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    public void run() {
                        if (status == 200) {
                            Toast.makeText(Uploadphoto.this, "Account Created", Toast.LENGTH_SHORT).show();

                            String resp = Server.s.get("http://mousebelly.herokuapp.com/sign/sign/" + signUpBean.getEmail());

                            if (resp != null) {
                                try {
                                    JSONArray jaar = new JSONArray(resp);
                                    for (int i = 0; i < jaar.length(); i++) {
                                        JSONObject jsonObject = jaar.getJSONObject(i);
                                        String Email = jsonObject.getString("UserEmail_id");
                                        String Pwd = jsonObject.getString("Pwd");
                                        String UserName = jsonObject.getString("U_name");
                                        String Wallet = jsonObject.getString("wallet");
                                        WalletHandler.WalletAmount = Integer.parseInt(Wallet);
                                        LoginActivity.USERNAME = UserName;
                                        LoginActivity.user.setU_name(jsonObject.getString("U_name"));
                                        LoginActivity.user.setUserEmail_id(jsonObject.getString("UserEmail_id"));
                                        LoginActivity.USERID = jsonObject.getString("UserEmail_id");
                                        LoginActivity.user.setPhone_No(jsonObject.getString("Phone_No"));
                                        LoginActivity.user.setWallet(Wallet);

                                        JSONArray cordjaar = jsonObject.getJSONArray("Cordinates");
                                        for (int j = 0; j < cordjaar.length(); j++) {
                                            JSONObject jsoncord = cordjaar.getJSONObject(j);
                                            LoginActivity.user.setLongitude(jsoncord.getString("long"));
                                            LoginActivity.user.setLat(jsoncord.getString("lat"));
                                        }

                                        JSONArray addjaar = jsonObject.getJSONArray("Address");
                                        for (int j = 0; j < addjaar.length(); j++) {
                                            JSONObject jsonObject2 = addjaar.getJSONObject(j);
                                            LoginActivity.user.setStreet_name(jsonObject2.getString("street_name"));
                                            LoginActivity.user.setZip_code(jsonObject2.getString("zip_code"));
                                            LoginActivity.user.setState_name(jsonObject2.getString("state_name"));
                                            LoginActivity.user.setCity_name(jsonObject2.getString("city_name"));
                                            LoginActivity.user.setCountry(jsonObject2.getString("country"));
                                        }

                                        // System.out.println("hello1 "+mPassword);
                                        if (Email.equals(signUpBean.getEmail())) {
                                            String result = VerifyLogin.compare(signUpBean.getPassword(), Pwd) ? "Login Successful" : "Login Failed";
                                            System.out.println("Hash" + Pwd);

                                            if (result.equals("Login Successful")) {
                                                System.out.println("::::::::: Login Successfull :::::::::");
                                                Intent intent = new Intent(Uploadphoto.this, MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Toast.makeText(Uploadphoto.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                                            }


                                        }
//                        String result = VerifyLogin.compare(mPassword,Pwd)?"Login Successful":"Login Failed";
                                        // Toast.makeText(getApplicationContext(),  , Toast.LENGTH_LONG).show();

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }


                        } else {
                            Toast.makeText(Uploadphoto.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                    /*Toast.makeText(Uploadphoto.this, "Account Created", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Uploadphoto.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }*/
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
    }
}
