package com.mousebelly.app.userapp.wallet;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mousebelly.app.userapp.CustomToast;
import com.mousebelly.app.userapp.R;
import com.mousebelly.app.userapp.WalletHandler;
import com.mousebelly.app.userapp.payment.AddToWalletPayment;
import com.mousebelly.app.userapp.products.MainActivity;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

public class Wallet extends AppCompatActivity {

    Dialog walletDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);


        final TextView walletAmount = (TextView) findViewById(R.id.wallet_amount);

        Button addToWallet = (Button) findViewById(R.id.add_to_wallet);

        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.getDefault());
        format.setCurrency(Currency.getInstance("INR"));

        // String result = format.format(1234567.89);
        walletAmount.setText(format.format(WalletHandler.WalletAmount));
        walletAmount.setTextColor(ContextCompat.getColor(Wallet.this, R.color.Amulet));
        walletAmount.setTypeface(null, Typeface.BOLD);


        addToWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                walletDialog = new Dialog(Wallet.this);
                walletDialog.setTitle("Add to Wallet");
                walletDialog.setContentView(R.layout.add_wallet_amount_dialog_box);

                final EditText amountToAdd = (EditText) walletDialog.findViewById(R.id.amount_to_add);

                Button dialogButtonOK = (Button) walletDialog.findViewById(R.id.dialogButtonOK);

                dialogButtonOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (amountToAdd.getText().toString().matches("") || Integer.parseInt(amountToAdd.getText().toString()) < 1 || Integer.parseInt(amountToAdd.getText().toString()) > 10000) {
                            CustomToast.Toast(Wallet.this, "Enter valid Amount");
                            return;
                        }

                        Intent i = new Intent(Wallet.this, AddToWalletPayment.class);
                        i.putExtra("AmountToAdd", amountToAdd.getText().toString());
                        startActivity(i);

                    }
                });


                walletDialog.show();

            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Wallet.this, MainActivity.class);
        MainActivity.productsLayout.removeAllViews();
        startActivity(i);
    }

}
