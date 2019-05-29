package com.btanhuecrng13ctong3.easyliving;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class paymentDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);
        String productName = getIntent().getStringExtra("product_name");
        Log.d("ObjectPassed: ",productName );
    }
}
