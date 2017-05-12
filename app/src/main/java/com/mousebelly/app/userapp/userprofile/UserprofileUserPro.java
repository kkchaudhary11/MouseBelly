package com.mousebelly.app.userapp.userprofile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mousebelly.app.userapp.R;
import com.roger.catloadinglibrary.CatLoadingView;

import static com.mousebelly.app.userapp.userprofile.GetUserProfileData.userprofileUserBean;

public class UserprofileUserPro extends AppCompatActivity {

    EditText Username, Password, Cnfrmpass, Phone;
    TextView Email;
    TextView ph, cnf, pass, errmail, erruser;
    Button Nextmap;
    String Check = "Ok", emailCheck = "Ok", PhnCheck = "Ok";

    String Usernameof, Emailof, Passof, cnfpassof, phoneof;
    CatLoadingView mView;
    TextWatcher obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userprofileactivity_userpro);
        mView = new CatLoadingView();
        // mView.show(getSupportFragmentManager(), "");

        GetUserProfileData getProfileData = new GetUserProfileData(UserprofileUserPro.this, mView);
        getProfileData.execute();
        int profilewaitcount = 0;
        while (profilewaitcount < 10) {
            if (getProfileData.status) break;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            profilewaitcount++;
        }

        if (profilewaitcount == 10) {

            Toast.makeText(this, "Unable to fetch Profile. Try Again", Toast.LENGTH_SHORT).show();
        } else {

            Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show();
        }
        //mView.dismiss();

        Username = (EditText) findViewById(R.id.username);
        Email = (TextView) findViewById(R.id.email);
       /* Password = (EditText) findViewById(R.id.password);
        Cnfrmpass = (EditText) findViewById(R.id.confrmpass);*/
        Phone = (EditText) findViewById(R.id.phone);
        ph = (TextView) findViewById(R.id.phn);
        /*cnf = (TextView) findViewById(R.id.cnf);
        pass = (TextView) findViewById(R.id.pass);*/
        errmail = (TextView) findViewById(R.id.erroremail);
        erruser = (TextView) findViewById(R.id.errusername);
        Nextmap = (Button) findViewById(R.id.nextmap);

        Username.setText(userprofileUserBean.getUserName());
        Usernameof = Username.getText().toString();

        Email.setText(userprofileUserBean.getEmail());

        Phone.setText(userprofileUserBean.getPhone());


        Nextmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println(Username.getText().toString());

                if (TextUtils.isEmpty(Username.getText().toString())) {
                    Username.setError("This field is required");
                    return;
                }
                if (TextUtils.isEmpty(Phone.getText().toString())) {
                    Phone.setError("This field is required");
                    return;
                }


                System.out.println("This is username before ifff::::::::::::::::::::" + Username.getText().toString());
                System.out.println("This is username before ifff::::::::::::::::::::" + Phone.getText().toString());


                if (Username.getText().toString().equals("") || Username.getText().toString() == null || !Check.equals("Ok") ||
                        Phone.getText().toString().equals("") || Phone.getText().toString() == null || !PhnCheck.equals("Ok")
                        ) {
                    Toast.makeText(UserprofileUserPro.this, "One or more field are not properly filled", Toast.LENGTH_LONG).show();
                } else {
                    UserprofileMainActivity obj = new UserprofileMainActivity();
                    obj.display();

                }

            }
        });


        Username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                System.out.println("jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj" + "");
            }

            @Override
            public void afterTextChanged(Editable editable) {

                // Check = "Ok";
                Usernameof = Username.getText().toString();

                System.out.println("This is Username of : " + Usernameof);
                System.out.println("This is Username length : " + Usernameof.length());
                Check = userprofileUserBean.validateUsername(Username.getText().toString());
                erruser.setVisibility(View.VISIBLE);
                erruser.setText(Check);
                if (Check.equals("Ok")) {
                    erruser.setVisibility(View.GONE);
                }
            }
        });

        Email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {


                emailCheck = userprofileUserBean.validateEmail(Email.getText().toString());
                errmail.setVisibility(View.VISIBLE);
                errmail.setText(emailCheck);
                if (emailCheck.equals("Ok")) {
                    errmail.setVisibility(View.GONE);
                }

            }
        });


        /*Password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                PassChack = userprofileUserBean.validatePassword(Password.getText().toString());
                pass.setVisibility(View.VISIBLE);
                pass.setText(PassChack);
                if (PassChack.equals("Ok")) {
                    pass.setVisibility(View.GONE);
                }

            }
        });

        Cnfrmpass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                CnfCheck = userprofileUserBean.validateCnfrmPassword(Cnfrmpass.getText().toString());
                cnf.setVisibility(View.VISIBLE);
                cnf.setText(CnfCheck);
                if (CnfCheck.equals("Ok")) {
                    cnf.setVisibility(View.GONE);
                }
            }
        });*/


        Phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                PhnCheck = userprofileUserBean.validatePhone(Phone.getText().toString());
                ph.setVisibility(View.VISIBLE);
                ph.setText(PhnCheck);
                if (PhnCheck.equals("Ok")) {
                    ph.setVisibility(View.GONE);
                }
            }
        });
    }

}
