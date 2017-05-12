package com.mousebelly.app.userapp.signUp;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mousebelly.app.userapp.R;
import com.mousebelly.app.userapp.Server;

import org.json.JSONArray;
import org.json.JSONObject;

public class UserPro extends AppCompatActivity {

    public static SignUpBean signUpBean = new SignUpBean();
    EditText Username, Email, Password, Cnfrmpass, Phone;
    TextView ph, cnf, pass, errmail, erruser;
    Button Nextmap;
    String Check, CnfCheck, emailCheck, PassChack, PhnCheck, Usernameof, Emailnameof, Passnameof, Cnfpassnameof, Phonenameof;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userpro);

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        Username = (EditText) findViewById(R.id.username);
        Email = (EditText) findViewById(R.id.email);
        Password = (EditText) findViewById(R.id.password);
        Cnfrmpass = (EditText) findViewById(R.id.confrmpass);
        Phone = (EditText) findViewById(R.id.phone);
        ph = (TextView) findViewById(R.id.phn);
        cnf = (TextView) findViewById(R.id.cnf);
        pass = (TextView) findViewById(R.id.pass);
        errmail = (TextView) findViewById(R.id.erroremail);
        erruser = (TextView) findViewById(R.id.errusername);
        Nextmap = (Button) findViewById(R.id.nextmap);


        Nextmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(Usernameof)) {
                    Username.setError("This field is required");
                    return;
                }
                if (TextUtils.isEmpty(Emailnameof)) {
                    Email.setError("This field is required");
                    return;
                }
                if (TextUtils.isEmpty(Passnameof)) {
                    Password.setError("This field is requidred");
                    return;
                }
                if (TextUtils.isEmpty(Cnfpassnameof)) {
                    Cnfrmpass.setError("This field is required");
                    return;
                }
                if (TextUtils.isEmpty(Phonenameof)) {
                    Phone.setError("This field is required");
                    return;
                }

                if (Username.getText().toString().equals("") || Username.getText().toString() == null || !Check.equals("Ok") ||
                        Email.getText().toString().equals("") || Email.getText().toString() == null || !emailCheck.equals("Ok") ||
                        Password.getText().toString().equals("") || Password.getText().toString() == null || !PassChack.equals("Ok") ||
                        Cnfrmpass.getText().toString().equals("") || Cnfrmpass.getText().toString() == null || !CnfCheck.equals("Ok") ||
                        Phone.getText().toString().equals("") || Phone.getText().toString() == null || !PhnCheck.equals("Ok")
                        ) {
                    Toast.makeText(UserPro.this, "One or more fields are not properly filled", Toast.LENGTH_LONG).show();
                } else {

                    SignUpMainActivity obj = new SignUpMainActivity();
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

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Usernameof = Username.getText().toString();
                Check = signUpBean.validateUsername(Username.getText().toString());
                erruser.setVisibility(View.VISIBLE);
                Username.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                erruser.setText(Check);
                if (Check.equals("Ok")) {
                    erruser.setVisibility(View.GONE);
                    //  Username.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.check, 0);
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
                Emailnameof = Email.getText().toString();
                Email.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                emailCheck = signUpBean.validateEmail(Email.getText().toString());
                errmail.setVisibility(View.VISIBLE);
                errmail.setText(emailCheck);
                if (emailCheck.equals("Ok")) {
                    errmail.setVisibility(View.GONE);
                }
            }
        });

        //check the existing email
        Email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                //on unfocus
                if (!b) {
                    //check if email is valid or not
                    if (emailCheck.equals("Ok")) {

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    System.out.println("Fouces changed");
                                    //get the user data from server
                                    String resp = Server.s.get("http://mousebelly.herokuapp.com/sign/sign/" + Emailnameof);
                                    if (resp != null) {
                                        JSONArray em = new JSONArray(resp);
                                        //if email does't exist arrya lenght will be 0
                                        if (em.length() == 0) {
                                            System.out.println("valid email");
                                            emailCheck = "Ok";
                                            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Email.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.check, 0);
                                                }
                                            }, 100);
                                            //Toast.makeText(UserPro.this, "Email Already exist", Toast.LENGTH_SHORT).show();
                                        } else {
                                            System.out.println("Error!  :  Email already exist");

                                            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    emailCheck = "not valid";
                                                    errmail.setText("Email Already Exist");
                                                    errmail.setVisibility(View.VISIBLE);
                                                }
                                            }, 100);
                                        }
                                    } else {
                                        System.out.println("Could't get data");
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
                }
            }
        });


        Password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Passnameof = Password.getText().toString();
                PassChack = signUpBean.validatePassword(Password.getText().toString());
                Cnfrmpass.setText("");
                cnf.setVisibility(View.GONE);
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
                Cnfpassnameof = Cnfrmpass.getText().toString();
                CnfCheck = signUpBean.validateCnfrmPassword(Cnfrmpass.getText().toString());
                cnf.setVisibility(View.VISIBLE);
                cnf.setText(CnfCheck);
                if (CnfCheck.equals("Ok")) {
                    cnf.setVisibility(View.GONE);
                }
            }
        });


        Phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Phonenameof = Phone.getText().toString();
                PhnCheck = signUpBean.validatePhone(Phone.getText().toString());
                ph.setVisibility(View.VISIBLE);
                ph.setText(PhnCheck);
                if (PhnCheck.equals("Ok")) {
                    ph.setVisibility(View.GONE);
                }
            }
        });
    }

}
