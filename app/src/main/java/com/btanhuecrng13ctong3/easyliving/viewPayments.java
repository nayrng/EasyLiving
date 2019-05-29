package com.btanhuecrng13ctong3.easyliving;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class viewPayments extends AppCompatActivity {
    FirebaseUser curUser;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    ArrayList<PAYMENT_OBJ> payments;
    Boolean completed = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_payments);
        firebaseAuth = FirebaseAuth.getInstance();
        curUser = firebaseAuth.getCurrentUser();
        Button newCharge = (Button)findViewById(R.id.nCharge);
        newCharge.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), PayActivity.class);
                startActivity(intent);
            }
        });


        payments = new ArrayList<PAYMENT_OBJ>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Payments");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snap: dataSnapshot.getChildren()){

                    Log.d("viewPayments", snap.child("groupname").getValue(String.class));
                    if(snap.child("sender").getValue(String.class).equals(curUser.getEmail())){
                        /*          this.sender = usr; y
                                    this.product = prod;  y
                                    this.groupname = grp; y
                                    this.price = prc; y
                                    this.receivers = rec;*/
                        String product = snap.child("product").getValue(String.class);
                        String groupname = snap.child("groupname").getValue(String.class);
                        Double price = snap.child("price").getValue(Double.class);
                        ArrayList<String> receivers = (ArrayList<String>)snap.child("receivers").getValue();
                        Log.d("product in loop", product);
                        PAYMENT_OBJ obj = new PAYMENT_OBJ(curUser.getEmail(), product, groupname, price, receivers);
                        payments.add(obj);
                    }
                }
                completed = true;
                Log.d("SizeOfPayments",Integer.toString(payments.size()));
                /*if(completed) {
                    for (int i = 0; i < payments.size(); i++) {
                        Log.d("In loop", "loop");
                        Log.d("product", payments.get(i).product);
                        Log.d("grpname", payments.get(i).groupname);
                    }
                }*/
                createList(payments);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        /*FloatingActionButton fab = findViewById(R.id.fab1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("viewPayments", curUser.getEmail().toString());
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(viewPayments.this);
                mBuilder.setTitle("Add a new charge!");
            }
        });*/
    }

    public void createList(ArrayList<PAYMENT_OBJ> payObj){
        LinearLayout layout = findViewById(R.id.payLayout);
        layout.removeAllViews();
        //payObj is the array of all PAYMENT_OBJECTS where the current user is the sender
        //We must extract the users who still have not completed the request
        for(int i =0; i< payObj.size();i++){
            PAYMENT_OBJ obj = payObj.get(i);
            TextView product =(TextView) new TextView(this);
            TextView price =(TextView) new TextView(this);
            Button button = new Button(this);


            product.setId(i);
            product.setText(obj.product);
            layout.addView(product);
            product.setTextSize(20);
            setTextViewAttributes(product);

            price.setId(i);
            String doubleAdapter = "Price: $" + Double.toString(Math.floor(obj.price*100)/100);
            price.setText(doubleAdapter);
            layout.addView(price);
            price.setTextSize(15);
            setTextViewAttributes(price);


            button.setId(i);
            button.setText("View Details");
            layout.addView(button);
            /*button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Details Pressed: ", Integer.toString(v.getId()));
                    PAYMENT_OBJ pass = payments.get(v.getId());
                    Log.d("Payment Detail: ",pass.product );
                    Intent intent = new Intent(getApplicationContext(), paymentDetails.class);
                    intent.putExtra("Key", payments);
                    startActivity(intent);
                }
            });*/
            button.setOnClickListener(new customOnClickListener(obj));



        }
        payObj.clear();
    }

    public class customOnClickListener implements View.OnClickListener{
        PAYMENT_OBJ obj;
        public customOnClickListener(PAYMENT_OBJ obj){
            this.obj = obj;
        }
        @Override
        public void onClick(View v){
            Log.d("CustomOnClick: ",obj.product );
            Intent intent = new Intent(getApplicationContext(), paymentDetails.class);
            intent.putExtra("product_name",obj.product);
            intent.putStringArrayListExtra("receivers_list", obj.receivers);
            startActivity(intent);
        }
    }

    private void setTextViewAttributes(TextView textView) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        params.setMargins(convertDpToPixel(16),
                convertDpToPixel(16),
                0, 0
        );

        textView.setTextColor(Color.BLACK);
        //textView.setTextSize(20);
        textView.setLayoutParams(params);
    }

    private int convertDpToPixel(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
    }
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_payments, menu);
        return true;
    }*/
}
