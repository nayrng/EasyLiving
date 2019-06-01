package com.btanhuecrng13ctong3.easyliving;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.ArrayList;

//passKuliat
public class PayActivity extends AppCompatActivity {
    FirebaseUser user;
    DatabaseReference group_reference;
    Button splitButton;
    EditText itemDesc;
    EditText dollarAmt;
    TextView textRent;
    String group_name;
    FirebaseAuth firebaseAuth;
    ArrayList<String> pass_group_users;
    DatabaseReference databaseReference;
    Boolean receivedGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay2);
        splitButton = findViewById(R.id.sendrequest);
        itemDesc = findViewById(R.id.itemdesc);
        dollarAmt = findViewById(R.id.dollaramt);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        receivedGroup = false;

        databaseReference = FirebaseDatabase.getInstance().getReference("Payments");
        group_reference = FirebaseDatabase.getInstance().getReference("Groups");
        group_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snap: dataSnapshot.getChildren()){
                    //returns groups
                    for (int i=0; i<snap.child("users").getChildrenCount(); i++) {
                        String map =  snap.child("users").child(String.valueOf(i)).getValue(String.class);
                        Log.d("PayActivity", "Current Group Name:" + snap.child("groupname").getValue(String.class));
                        Log.d("Payactivity ", "User Name: "+ map);
                        ArrayList<String> group_users = (ArrayList<String>)snap.child("users").getValue();
                        if(group_users.contains(user.getEmail())){
                            group_name = (String) snap.child("groupname").getValue();
                            pass_group_users = group_users;

                            /*Lgog.d("SUCCESS", "Group" + group_name);
                            for(int j = 0; j<group_users.size();j++){
                                Log.d("PayActivity", "Users List: " + pass_group_users.get(j));
                            }*/
                            receivedGroup = true;
                        }
                        /*if (map.equals(user.getEmail())) {
                            group_name = (String) snap.child("groupname").getValue();
                            group_users = (ArrayList<String>)snap.child("users").getValue();
                            for(int j = 0; j<group_users.size();j++){
                                Log.d("PayActivity", "Users: " + group_users.get(i));
                            }
                            Log.d("Current User: ", "Logged in: " + user.getEmail());
                            System.out.println(group_name);
                            break;
                        }*/
                    }
                    Log.d("PayActivity", "Final Group Name:" + group_name);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }



        });





        splitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                /*if(totalRent.getText().toString().equals("")){
                    Toast.makeText(PayActivity.this, "No input provided", Toast.LENGTH_LONG).show();
                }else{
                    try{
                        double sRent = (Double.parseDouble(totalRent.getText().toString()))/4 ;// 4 should be replaced with num people
                        textRent.setText(Double.toString(sRent));
                    }catch (NumberFormatException ex){
                        Toast.makeText(PayActivity.this, "Invalid Input", Toast.LENGTH_LONG).show();
                    }
                }*/

                if(receivedGroup) {
                    Log.d("FULL SUCCESS", "Group" + group_name);
                    ArrayList<String> EmptyArray = new ArrayList();
                    EmptyArray.add(user.getEmail());
                    //for (int j = 0; j < pass_group_users.size(); j++) {
                        //Log.d("FULL SUCCESS", "Users List: " + pass_group_users.get(j));
                        PAYMENT_OBJ obj = new PAYMENT_OBJ(user.getEmail(), itemDesc.getText().toString(), group_name, (Double.parseDouble(dollarAmt.getText().toString())), pass_group_users, EmptyArray);
                        String head = itemDesc.getText().toString();
                        databaseReference.child(head).setValue(obj);
                        Toast.makeText(PayActivity.this, "Members Charged!", Toast.LENGTH_SHORT).show();
                        finish();
                    //}
                }
            }
        });



    }


}
