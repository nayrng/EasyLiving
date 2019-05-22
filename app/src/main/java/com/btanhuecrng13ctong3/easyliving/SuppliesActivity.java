package com.btanhuecrng13ctong3.easyliving;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SuppliesActivity extends AppCompatActivity {
    private RelativeLayout relativeLayout;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private LayoutInflater layoutInflater;
    private PopupWindow popupWindow;
    ArrayList<SUPPLIES_OBJECT> item = new ArrayList<>();

    private FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    private String supply_name;
    private String buyer_name;
    private Boolean clicked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplies);
        clicked = false;
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        databaseReference = FirebaseDatabase.getInstance().getReference("Supplies");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                item.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    String supplyName = ds.child("supply_NAME").getValue(String.class);
                    String supplyBuyer = ds.child("supply_BUYER").getValue(String.class);
                    int supplyPrice = ds.child("supply_PRICE").getValue(Integer.class);
                    int supplyQuantity = ds.child("supply_NUM").getValue(Integer.class);
                    SUPPLIES_OBJECT supplies_object = new SUPPLIES_OBJECT(supplyName, supplyBuyer,
                            supplyQuantity, supplyPrice);
                    item.add(supplies_object);
                    mAdapter = new MyAdapter(item);
                    recyclerView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        FloatingActionButton floatingActionButton = findViewById(R.id.addSupply);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), PopUp.class);
//                startActivity(intent);
                firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser user = firebaseAuth.getCurrentUser();


                relativeLayout = findViewById(R.id.mainSupply);
                layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.activity_pop_up, null);

                popupWindow = new PopupWindow(container, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,  true);
                popupWindow.setAnimationStyle(R.style.popup_window_animation);
                popupWindow.showAtLocation(relativeLayout, Gravity.CENTER, 0,0);
                popupWindow.update();

                Button submitButton = container.findViewById(R.id.submit_Supply);
                final EditText buyerName = container.findViewById(R.id.supply_Buyer);
                final EditText supplyName = container.findViewById(R.id.supply_Name);
                final EditText quantityValue = container.findViewById(R.id.supply_Quantity);
                final EditText priceValue = container.findViewById(R.id.supply_Price);


                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clicked = true;
                        supply_name = supplyName.getText().toString();
                        buyer_name = buyerName.getText().toString();
                        int quantity_value = Integer.parseInt(quantityValue.getText().toString());
                        int price_value = Integer.parseInt(priceValue.getText().toString());
                        Log.d("before adding to DB", "Content: "+ supply_name + " "+
                                buyer_name);
                        databaseReference.child(supply_name).setValue(
                                new SUPPLIES_OBJECT(supply_name, buyer_name,  quantity_value, price_value));
                        Toast.makeText(SuppliesActivity.this, "Added a Supply!", Toast.LENGTH_SHORT).show();
                        popupWindow.dismiss();
                    }
                });


            }
        });


        // specify an adapter
        //adding data into the card views
        //if submit button never got clicked, gotta set the content
        if(clicked == false){

            mAdapter = new MyAdapter(item);
            recyclerView.setAdapter(mAdapter);
        }
    }
}
