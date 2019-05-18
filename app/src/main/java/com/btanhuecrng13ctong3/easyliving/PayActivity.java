package com.btanhuecrng13ctong3.easyliving;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

//passKuliat
public class PayActivity extends AppCompatActivity {

    Button splitButton;
    EditText totalRent;
    static TextView textRent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay2);
        splitButton = findViewById(R.id.sendrequest);
        totalRent = findViewById(R.id.totalRent);
        textRent = findViewById(R.id.rentShow);

        splitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(totalRent.getText().toString().equals("")){
                    Toast.makeText(PayActivity.this, "No input provided", Toast.LENGTH_LONG).show();
                }else{
                    try{
                        double sRent = (Double.parseDouble(totalRent.getText().toString()))/4 ;// 4 should be replaced with num people
                        textRent.setText(Double.toString(sRent));
                    }catch (NumberFormatException ex){
                        Toast.makeText(PayActivity.this, "Invalid Input", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });



    }


}
