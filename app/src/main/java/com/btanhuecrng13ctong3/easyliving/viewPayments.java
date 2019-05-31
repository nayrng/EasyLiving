package com.btanhuecrng13ctong3.easyliving;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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

public class viewPayments extends AppCompatActivity {
    FirebaseUser curUser;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    ArrayList<PAYMENT_OBJ> payments;
    Boolean completed = false;
    Boolean youOwe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_payments);
        firebaseAuth = FirebaseAuth.getInstance();
        curUser = firebaseAuth.getCurrentUser();
        youOwe = getIntent().getBooleanExtra("YOUOWE",false);
        Button newCharge = (Button)findViewById(R.id.nCharge);
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
                        ArrayList<String> chargecompleted = (ArrayList<String>)snap.child("chargecompleted").getValue();
                        Log.d("product in loop", product);
                        PAYMENT_OBJ obj = new PAYMENT_OBJ(curUser.getEmail(), product, groupname, price, receivers, chargecompleted);
                        payments.add(obj);
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
                            PAYMENT_OBJ obj = new PAYMENT_OBJ(sender, product, groupname, price, receivers, chargecompleted);
                            payments.add(obj);
                        }
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
        LinearLayout org_layout = findViewById(R.id.payLayout);
        ScrollView scroll = findViewById(R.id.scrollView2);
        scroll.removeAllViews();
        //payObj is the array of all PAYMENT_OBJECTS where the current user is the sender
        //We must extract the users who still have not completed the request
        Log.d("In create List", ": " + youOwe);
        for(int i =0; i< payObj.size();i++){
            LinearLayout new_layout = new LinearLayout(this);
            PAYMENT_OBJ obj = payObj.get(i);
            TextView product =(TextView) new TextView(this);
            TextView price =(TextView) new TextView(this);
            Button button = new Button(this);
            Button debug = new Button(this);
            ImageView youOweImg = new ImageView(this);

            product.setId(i);
            product.setText(obj.product);
            new_layout.addView(product);
            product.setTextSize(20);
            setTextViewAttributes(product);

            price.setId(i);
            DecimalFormat df = new DecimalFormat("0.00");
            String doubleAdapter = "default";
            if(youOwe == true) {
                if(obj.chargecompleted.contains(curUser.getEmail())){
                    ArrayList<String> completed = obj.chargecompleted;
                    int dateIndex = completed.indexOf(curUser.getEmail()) + 1;
                    Log.d("Index", ":" + dateIndex);
                    doubleAdapter = "Completed on " + completed.get(dateIndex) ;
                }else {
                    Log.d("createList Size: ", ":" + obj.receivers.size());
                    doubleAdapter = "$" + (df.format(obj.price / obj.receivers.size())) + " requested by " + obj.sender;
                }
            }else if(youOwe == false){
                Double owed = Double.parseDouble(df.format(balance(obj)));
                if(owed==0){
                    doubleAdapter = "All charges completed!";
                }else{
                    doubleAdapter = "Amount Left Owed To You: $" + (df.format(balance(obj)));
                }

            }
            price.setText(doubleAdapter);
            new_layout.addView(price);
            price.setTextSize(15);
            setTextViewAttributes(price);


            button.setId(i);
            button.setText("View Details");
            new_layout.addView(button);
            if(obj.chargecompleted.contains(curUser.getEmail())){

            }else{
                youOweImg.setId(i);
                youOweImg.setImageDrawable(resize(R.drawable.venmoicon));
                new_layout.addView(youOweImg);
                youOweImg.setOnClickListener(new venmoOnClickListener(obj));
            }
            org_layout.addView(new_layout);
            button.setOnClickListener(new customOnClickListener(obj));



        }
        scroll.addView(org_layout);
        payObj.clear();
    }

    /*public void chargeComplete(PAYMENT_OBJ obj){
        ArrayList<String> originalArray = obj.chargecompleted;
        originalArray.add(curUser.getEmail());
        databaseReference.child(obj.product).child("chargecompleted").setValue(originalArray);
    }*/

    public class customOnClickListener implements View.OnClickListener{
        PAYMENT_OBJ obj;
        public customOnClickListener(PAYMENT_OBJ obj){
            this.obj = obj;
        }
        @Override
        public void onClick(View v){
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

            //probably dont need these extra arrayLists since the object is already being passed lol
            //intent.putStringArrayListExtra("receivers_list", obj.receivers);
            //intent.putStringArrayListExtra("completed_list", obj.chargecompleted);
            startActivity(intent);
        }
    }

    public class venmoOnClickListener implements View.OnClickListener{
        PAYMENT_OBJ obj;
        public venmoOnClickListener(PAYMENT_OBJ obj){
            this.obj = obj;
        }
        @Override
        public void onClick(View v){
            Intent i = getPackageManager().getLaunchIntentForPackage("com.venmo");
            Log.d("venmoCustomOnClick: ",obj.product );
            ArrayList<String> originalArray = obj.chargecompleted;
            originalArray.add(curUser.getEmail());
            SimpleDateFormat formatter= new SimpleDateFormat("MMM-dd-yyyy");
            Date date = new Date(System.currentTimeMillis());
            originalArray.add(formatter.format(date));
            databaseReference.child(obj.product).child("chargecompleted").setValue(originalArray);
            if(i==null){
                Toast.makeText(viewPayments.this, "Please download Venmo for this functionality", Toast.LENGTH_LONG).show();
            }else{

                startActivity(i);
            }

        }
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
