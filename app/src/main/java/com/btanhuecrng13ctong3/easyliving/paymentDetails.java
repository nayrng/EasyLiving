package com.btanhuecrng13ctong3.easyliving;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class paymentDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);
        DecimalFormat df = new DecimalFormat("0.00");
        String productName = getIntent().getStringExtra("product_name");
        String passedproductPrice = getIntent().getStringExtra("product_price");
        String passedfinPrice = getIntent().getStringExtra("remOwed");
        String sender = getIntent().getStringExtra("sender");
        ArrayList<String> receiversArray = getIntent().getStringArrayListExtra("receivers_array");
        ArrayList<String> completedArray = getIntent().getStringArrayListExtra("comp_array");
        String productPrice = "$"+passedproductPrice;//+passedproductPrice;
        String productFin = "$" + passedfinPrice;
        Log.d("Product Name Passed: ",productName );
        String purchaserBuffer = "Purchased by " + sender;
        TextView prodName = findViewById(R.id.itemName);
        prodName.setText(productName);
        prodName.setTextSize(30);
        TextView prodPrice = findViewById(R.id.ogPrc);
        prodPrice.setText(productPrice);
        prodPrice.setTextSize(25);
        TextView finPrcView = findViewById(R.id.finPrc);
        finPrcView.setText(productFin);
        finPrcView.setTextSize(25);
        TextView purchaserView = findViewById(R.id.purchaser);
        purchaserView.setText(purchaserBuffer);
        purchaserView.setTextSize(15);

        LayoutParams params = new LayoutParams (LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        params.setMargins(20,0,20,0);

        TableLayout tL = findViewById(R.id.detailTable);
        TextView recView1 =(TextView) new TextView(this);
        TextView valueView1 = (TextView) new TextView(this);
        TextView endView1 =(TextView) new TextView(this);
        TableRow tRow1 = (TableRow) new TableRow(this);

        recView1.setText("Username");
        recView1.setGravity(Gravity.CENTER);
        valueView1.setText("Charge Amount");
        valueView1.setGravity(Gravity.CENTER);
        endView1.setText("Status");
        endView1.setGravity(Gravity.CENTER);
        recView1.setTextSize(17);
        valueView1.setTextSize(17);
        endView1.setTextSize(17);
        tRow1.addView(recView1,params);
        tRow1.addView(valueView1, params);
        tRow1.addView(endView1, params);
        tL.addView(tRow1);


        for(int i=0; i<receiversArray.size();i++){


            TextView recView =(TextView) new TextView(this);
            TextView valueView = (TextView) new TextView(this);
            TextView endView =(TextView) new TextView(this);
            TableRow tRow = (TableRow) new TableRow(this);




            tRow.setWeightSum(3);
            String curRec = receiversArray.get(i);
            String endVal;
            String value = "$" + (df.format(Double.parseDouble(passedproductPrice) / receiversArray.size()));
            if(curRec.equals(sender)){
                endVal = "Purchaser";
            }else if(completedArray.contains(curRec)){
                int index = completedArray.indexOf(curRec);
                endVal = "Completed";
            }else{
                endVal = "Incomplete";
            }
            recView.setText(curRec);
            recView.setGravity(Gravity.CENTER);
            valueView.setText(value);
            valueView.setGravity(Gravity.CENTER);
            endView.setText(endVal);
            endView.setGravity(Gravity.CENTER);
            tRow.addView(recView,params);
            tRow.addView(valueView, params);
            tRow.addView(endView, params);
            tL.addView(tRow);


        }
    }
}
