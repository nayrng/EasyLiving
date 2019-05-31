package com.btanhuecrng13ctong3.easyliving;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

public class paymentDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);
        String productName = getIntent().getStringExtra("product_name");
        String passedproductPrice = getIntent().getStringExtra("product_price");
        String productPrice = "$"+passedproductPrice;
        Log.d("Product Name Passed: ",productName );

        TextView prodName = findViewById(R.id.itemName);
        prodName.setText(productName);
        prodName.setTextSize(30);
        TextView prodPrice = findViewById(R.id.itemPrice);
        prodPrice.setText(passedproductPrice);
        prodPrice.setTextSize(30);
    }
}
