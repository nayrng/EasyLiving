package com.btanhuecrng13ctong3.easyliving;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class JoinGroup extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    DatabaseReference databaseReference;

    EditText groupName;
    EditText groupPass;

    ArrayList<GROUPS_OBJECT> fire_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_group);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Groups");

        groupName = findViewById(R.id.GroupName);
        groupPass = findViewById(R.id.GroupPasscode);

        fire_list = new ArrayList<GROUPS_OBJECT>();

        Log.d("testing", "about to do valuelistener");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("testing", "inside OnDataChange");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.d("testing", "inside snapshot loop");
                    String admin = snapshot.child("ADMIN").getValue(String.class);
                    ArrayList<String> users = (ArrayList<String>)snapshot.child("users").getValue();
                    ArrayList<String> chore_list= (ArrayList<String>)snapshot.child("chores").getValue();
                    String name = snapshot.child("groupName").getValue(String.class);
                    String pass = snapshot.child("groupPass").getValue(String.class);
                    GROUPS_OBJECT obj = new GROUPS_OBJECT(admin, users, chore_list, name, pass);
                    fire_list.add(obj);
                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        Button join = (Button) findViewById(R.id.JoinGroupButton);
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference dBRef = FirebaseDatabase.getInstance().getReference("Groups");
                dBRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String pw = snapshot.child("groupPass").getValue(String.class);
                            if (pw.equals(groupPass.getText().toString())) {
                                for (int i = 0; i < fire_list.size()-1; i++) {
                                    //System.out.println(fire_list.get(i).users.get(i));
                                    System.out.println("okokok");
                                }
                                String list_size = String.valueOf(fire_list.size());
                                databaseReference.child(groupName.getText().toString()).child("users").child(list_size).setValue(user.getEmail());
                                Toast.makeText(JoinGroup.this, "Group Joined!", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(JoinGroup.this, "Invalid Group Pass/User", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                /*for (int i = 0; i < fire_list.size(); i++) {
                    System.out.println(fire_list.get(i).users.get(i));
                    System.out.println("okokok");
                }
                String list_size = String.valueOf(fire_list.size());
                if(databaseReference.child(groupName.getText().toString()).child("groupPass").getKey().equals(groupPass.getText().toString())){
                    databaseReference.child(groupName.getText().toString()).child("users").child(list_size).setValue(user);
                    Toast.makeText(JoinGroup.this, "Group Joined!", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(JoinGroup.this, "Invalid Group Pass/User", Toast.LENGTH_SHORT).show();
                    Log.d("join", databaseReference.child(groupName.getText().toString()).child("groupPass").);
                }*/



            }
        });

    }



}
