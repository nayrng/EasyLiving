package com.btanhuecrng13ctong3.easyliving;

import android.os.Bundle;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class viewPayments2 extends AppCompatActivity {
    FirebaseUser curUser;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    ArrayList<PAYMENT_OBJ> payments;
    Boolean completed = false;
    Boolean youOwe;
    Button newCharge;

    public RecyclerView.Adapter mAdapter;

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_payments2);

        recyclerView= findViewById(R.id.view_payment_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        firebaseAuth = FirebaseAuth.getInstance();
        curUser = firebaseAuth.getCurrentUser();
        youOwe = getIntent().getBooleanExtra("YOUOWE",false);
        newCharge = (Button)findViewById(R.id.nCharge);
        if(youOwe==true){
            newCharge.setVisibility(View.INVISIBLE);
        }
        newCharge.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), PayActivity.class);
                startActivity(intent);
            }
        });

        Log.d("Boolean Intent", ": " + youOwe);

        payments = new ArrayList<PAYMENT_OBJ>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Payments");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                payments.clear();
                for(DataSnapshot snap: dataSnapshot.getChildren()){
                    Log.d("Boolean Snap Loop", ": " + youOwe);
                    Log.d("viewPayments", snap.child("groupname").getValue(String.class));
                    if(snap.child("sender").getValue(String.class).equals(curUser.getEmail()) && youOwe == false){
                        /*          this.sender = usr; y
                                    this.product = prod;  y
                                    this.groupname = grp; y
                                    this.price = prc; y
                                    this.receivers = rec;*/
                        String product = snap.child("product").getValue(String.class);
                        String groupname = snap.child("groupname").getValue(String.class);
                        Double price = snap.child("price").getValue(Double.class);
                        ArrayList<String> receivers = (ArrayList<String>)snap.child("receivers").getValue();
                        ArrayList<String> chargecomp = (ArrayList<String>)snap.child("chargecompleted").getValue();
                        Log.d("product in loop", product);
                        PAYMENT_OBJ obj = new PAYMENT_OBJ(curUser.getEmail(), product, groupname, price, receivers, chargecomp);
                        payments.add(obj);
                        /*mAdapter = new PaymentAdapter(payments, youOwe);
                        recyclerView.setAdapter(mAdapter);

                        ((PaymentAdapter)mAdapter).setOnItemClickListener(new PaymentAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                Log.d("Position", "pos" + position);
                            }

                            @Override
                            public void onDetailClick(int position) {
                                Log.d("Position", "pos" + position);

                                DecimalFormat df = new DecimalFormat("0.00");
                                String remOwed = (df.format(balance(obj)));
                                String passprc = df.format(obj.price);
                                Log.d("CustomOnClick: ",obj.product );
                                Intent intent = new Intent(getApplicationContext(), paymentDetails.class);
                                intent.putExtra("product_name",obj.product);
                                intent.putExtra("product_price", passprc);
                                intent.putExtra("receivers_array", obj.receivers);
                                intent.putExtra("comp_array", obj.chargecompleted);
                                intent.putExtra("sender", obj.sender);
                                intent.putExtra("remOwed", remOwed);
                                startActivity(intent);

                            }

                            @Override
                            public void onImageClick(int position) {
                                Intent i = getPackageManager().getLaunchIntentForPackage("com.venmo");
                                startActivity(i);
                            }

                        });*/


                    }else if(youOwe == true){
                        ArrayList<String> receivers = (ArrayList<String>)snap.child("receivers").getValue();
                        //if you owe money, basically if ur in the "receivers" and also not the "sender"
                        if(receivers.contains(curUser.getEmail()) && !(snap.child("sender").getValue(String.class).equals(curUser.getEmail()))){
                            String product = snap.child("product").getValue(String.class);
                            String groupname = snap.child("groupname").getValue(String.class);
                            String sender = snap.child("sender").getValue(String.class);
                            Double price = snap.child("price").getValue(Double.class);
                            ArrayList<String> chargecompleted = (ArrayList<String>)snap.child("chargecompleted").getValue();
                            Log.d("youOwe receiversSize: ", ":" + receivers.size());

                            PAYMENT_OBJ obj = new PAYMENT_OBJ(sender, product, groupname, price, receivers,chargecompleted);
                            payments.add(obj);


                            /*((PaymentAdapter)mAdapter).setOnItemClickListener(new PaymentAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(int position) {
                                    Log.d("Position", "pos" + position);
                                }

                                @Override
                                public void onDetailClick(int position) {
                                    DecimalFormat df = new DecimalFormat("0.00");
                                    String remOwed = (df.format(balance(obj)));
                                    String passprc = df.format(obj.price);
                                    Log.d("CustomOnClick: ",obj.product );
                                    Intent intent = new Intent(getApplicationContext(), paymentDetails.class);
                                    intent.putExtra("product_name",obj.product);
                                    intent.putExtra("product_price", passprc);
                                    intent.putExtra("receivers_array", obj.receivers);
                                    intent.putExtra("comp_array", obj.chargecompleted);
                                    intent.putExtra("sender", obj.sender);
                                    intent.putExtra("remOwed", remOwed);
                                    startActivity(intent);

                                }

                                @Override
                                public void onImageClick(int position) {
                                    Intent i = getPackageManager().getLaunchIntentForPackage("com.venmo");
                                    startActivity(i);
                                }

                            });*/
                        }
                    }
                }
                completed = true;
                Log.d("SizeOfPayments",Integer.toString(payments.size()));
                mAdapter = new PaymentAdapter(payments, youOwe);
                recyclerView.setAdapter(mAdapter);
                ((PaymentAdapter)mAdapter).setOnItemClickListener(new PaymentAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Log.d("Position", "pos" + position);
                    }

                    @Override
                    public void onDetailClick(int position) {
                        PAYMENT_OBJ obj = payments.get(position);
                        Log.d("Position", "pos" + position);
                        DecimalFormat df = new DecimalFormat("0.00");
                        String remOwed = (df.format(balance(obj)));
                        String passprc = df.format(obj.price);
                        Log.d("CustomOnClick: ",obj.product );
                        Intent intent = new Intent(getApplicationContext(), paymentDetails.class);
                        intent.putExtra("product_name",obj.product);
                        intent.putExtra("product_price", passprc);
                        intent.putExtra("receivers_array", obj.receivers);
                        intent.putExtra("comp_array", obj.chargecompleted);
                        intent.putExtra("sender", obj.sender);
                        intent.putExtra("remOwed", remOwed);
                        startActivity(intent);

                    }

                    @Override
                    public void onImageClick(int position) {
                        PAYMENT_OBJ obj = payments.get(position);
                        if(obj.chargecompleted.contains(curUser.getEmail())){
                            Log.d("ImageClick", "Completed");
                        }else {
                            Intent i = getPackageManager().getLaunchIntentForPackage("com.venmo");
                            Log.d("venmoCustomOnClick: ", obj.product);
                            ArrayList<String> originalArray = obj.chargecompleted;
                            originalArray.add(curUser.getEmail());
                            SimpleDateFormat formatter = new SimpleDateFormat("MMM-dd-yyyy");
                            Date date = new Date(System.currentTimeMillis());
                            originalArray.add(formatter.format(date));
                            databaseReference.child(obj.product).child("chargecompleted").setValue(originalArray);
                            if (i == null) {
                                Toast.makeText(viewPayments2.this, "Please download Venmo for this functionality", Toast.LENGTH_LONG).show();
                            } else {
                                startActivity(i);
                            }
                        }
                    }

                });
                /*if(completed) {
                    for (int i = 0; i < payments.size(); i++) {
                        Log.d("In loop", "loop");
                        Log.d("product", payments.get(i).product);
                        Log.d("grpname", payments.get(i).groupname);
                    }
                }*/


                //createList(payments);

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

    public Double balance(PAYMENT_OBJ obj){
        //input should be the price
        //originally divided as (total price)/(total members)
        //expected output should be
        // newCost = (total price) - ((1+(numofCompletions-1/2))((total price)/(total members))
        double totalCost = obj.price;
        int numofMembers = obj.receivers.size();
        int payCompleted = obj.chargecompleted.size();
        double newCost;
        //if(obj.chargecompleted.size() != 1 && (obj.chargecompleted.size()%2)==1){
        newCost = totalCost - ((1+((payCompleted-1)/2))*(totalCost/numofMembers));
        //}
        return newCost;
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

    private void setImageAttributes(ImageView checkBox) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        params.setMargins(convertDpToPixel(16),
                convertDpToPixel(16),
                convertDpToPixel(16),
                0
        );
    }
    private Drawable resize(int img) {
        Drawable dr = getResources().getDrawable(img);
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
// Scale it to 50 x 50
        Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
        return d;
    }
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_payments, menu);
        return true;
    }*/
}
