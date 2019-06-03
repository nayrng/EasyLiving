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
import android.widget.CheckBox;
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
    ArrayList<String> groupMembers;

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    DatabaseReference rootReference;
    DatabaseReference paymentReference;

    FirebaseUser user;
    String group_name;

    private String supply_name;
    private String supply_buyer;
    private String group;
    private Boolean clicked;

    ArrayList<SUPPLIES_OBJECT> items;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplies);
        clicked = false;
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //items = new ArrayList();
        items = new ArrayList<>();
        groupMembers = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Supplies");
        paymentReference = FirebaseDatabase.getInstance().getReference("Payments");
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                item.clear();
//                for(DataSnapshot ds: dataSnapshot.getChildren()){
//                    String supplyName = ds.child("supply_NAME").getValue(String.class);
//                    String supplyBuyer = ds.child("supply_BUYER").getValue(String.class);
//                    int supplyPrice = ds.child("supply_PRICE").getValue(Integer.class);
//                    int supplyQuantity = ds.child("supply_NUM").getValue(Integer.class);
//                    SUPPLIES_OBJECT supplies_object = new SUPPLIES_OBJECT(supplyName, supplyBuyer,
//                            supplyQuantity, supplyPrice);
//                    item.add(supplies_object);
//                    mAdapter = new MyAdapter(item);
//                    recyclerView.setAdapter(mAdapter);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        rootReference = FirebaseDatabase.getInstance().getReference();
        rootReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                items.clear();
                DatabaseReference rootGroupRef = rootReference.child("Groups");
                rootGroupRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot groupSnapShot: dataSnapshot.getChildren()) {
                            ArrayList<String> members = (ArrayList<String>) groupSnapShot.child("users").getValue();
                            String groups = groupSnapShot.child("groupname").getValue(String.class);
                            if(members.contains(user.getEmail())) {
                                group_name = groups;
                                Log.d("group name is ", group_name);
                                groupMembers = (ArrayList<String>)groupSnapShot.child("users").getValue();
                                break;
                            }
                        }

                        DatabaseReference rootSupplyRef = rootReference.child("Supplies");
                        Log.d("entering", " rootSupplyRefListener");
                        rootSupplyRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                items.clear();
                                for(DataSnapshot supplySnapShot: dataSnapshot.getChildren()) {
                                    if (group_name.equals(supplySnapShot.child("SUPPLY_GROUP").getValue(String.class))) {
                                        String supply_buyer = supplySnapShot.child("SUPPLY_BUYER").getValue(String.class);
                                        String supply_name = supplySnapShot.child("SUPPLY_NAME").getValue(String.class);
                                        String supply_group = supplySnapShot.child("SUPPLY_GROUP").getValue(String.class);
                                        int supply_num = supplySnapShot.child("SUPPLY_NUM").getValue(Integer.class);
                                        Double supply_price = supplySnapShot.child("SUPPLY_PRICE").getValue(Double.class);
                                        Log.d("Supply Name", ": " + supply_name);
                                        SUPPLIES_OBJECT obj = new SUPPLIES_OBJECT(supply_name, supply_buyer, supply_group, supply_num, supply_price);
                                        items.add(obj);
                                    }
                                    createList();
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });

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
                //firebaseAuth = FirebaseAuth.getInstance();
                //FirebaseUser user = firebaseAuth.getCurrentUser();

                System.out.println("okay");
                Log.d("Supplies GroupName", ":" + group_name);
                System.out.println("okay2");


                relativeLayout = findViewById(R.id.mainSupply);
                layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.activity_pop_up, null);

                popupWindow = new PopupWindow(container, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,  true);
                popupWindow.setAnimationStyle(R.style.popup_window_animation);
                popupWindow.showAtLocation(relativeLayout, Gravity.CENTER, 0,0);
                popupWindow.update();

                Button submitButton = container.findViewById(R.id.submit_Supply);
                //final EditText buyerName = container.findViewById(R.id.supply_Buyer);
                final EditText supplyName = container.findViewById(R.id.supply_Name);
                final EditText quantityValue = container.findViewById(R.id.supply_Quantity);
                final EditText priceValue = container.findViewById(R.id.supply_Price);
                final CheckBox charge = container.findViewById(R.id.payCheck);


                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clicked = true;
                        supply_name = supplyName.getText().toString();
                        //supply_buyer = buyerName.getText().toString();
                        //group = group_name;
                        Log.d("testing", group_name);
                        int quantity_value = Integer.parseInt(quantityValue.getText().toString());
                        double price_value = Double.parseDouble(priceValue.getText().toString());
                        Log.d("before adding to DB", "Content: "+ supply_name + " "+
                                supply_buyer + " " + group_name);
//                        databaseReference.child(supply_name).setValue(
//                                new SUPPLIES_OBJECT(supply_name, buyer_name,  quantity_value, price_value));
                        databaseReference.child(supply_name).setValue(new SUPPLIES_OBJECT(supply_name, user.getEmail(), group_name, quantity_value, price_value));
                        if(charge.isChecked()){
                            ArrayList<String> EmptyArray = new ArrayList();
                            EmptyArray.add(user.getEmail());
                            paymentReference.child(supply_name).setValue(new PAYMENT_OBJ(user.getEmail(), supply_name, group_name, price_value, groupMembers, EmptyArray));
                        }
                        System.out.println(group_name);
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
    public void createList(){
        mAdapter = new MyAdapter(items);
        recyclerView.setAdapter(mAdapter);
        Log.d("Size of Array", ":" + items.size());
    }
}
