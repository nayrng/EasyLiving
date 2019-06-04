package com.btanhuecrng13ctong3.easyliving;

import android.content.Intent;
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
                    String admin = snapshot.child("admin").getValue(String.class);
                    ArrayList<String> users = (ArrayList<String>)snapshot.child("users").getValue();
                    ArrayList<String> chore_list= (ArrayList<String>)snapshot.child("chores").getValue();
                    String name = snapshot.child("groupname").getValue(String.class);
                    String pass = snapshot.child("grouppass").getValue(String.class);
                    Log.d("joinGroup onCreate", "groupName: " + name);
                    Log.d("joinGroup onCreate", "groupPass: " + pass);
                    Log.d("joinGroup onCreate", "Users: ");
                    for(int i = 0; i<users.size();i++){
                        Log.d("joinGroup onCreate", "Users: " + users.get(i));
                    }
                    GROUPS_OBJECT obj = new GROUPS_OBJECT(admin, users, chore_list, name, pass);
                    fire_list.add(obj);
                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        Button join = (Button) findViewById(R.id.group_choice_join);
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference dBRef = FirebaseDatabase.getInstance().getReference("Groups");
                dBRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Boolean groupExists = false;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String groupname = snapshot.child("groupname").getValue(String.class);
                            String grouppass = snapshot.child("grouppass").getValue(String.class);
                            //Log.d("onChange List Group", "Group: " + snapshot.child("groupname").getValue(String.class));
                            String stringName = groupName.getText().toString();
                            String stringPass = groupPass.getText().toString();

                            Log.d("onChange List Group", "Group: " + groupname);
                            //Log.d("StringInput", "Group Input: " + stringName);
                            if(groupname.equals(stringName)){
                                Log.d("in success", "Group Match:" + stringName);
                                groupExists = true;
                                if(grouppass.equals(stringPass)){
                                    Boolean isInGroup = false;
                                    Log.d("in pw success", "Pass match:" + grouppass);
                                    ArrayList<String> users = (ArrayList<String>)snapshot.child("users").getValue();
                                    for(int i = 0; i < users.size();i++){
                                        if(user.getEmail().equals(users.get(i))){
                                            isInGroup = true;
                                            Toast.makeText(JoinGroup.this, "You are already a part of this group!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    if(!isInGroup) {
                                        users.add(user.getEmail());
                                        FirebaseDatabase.getInstance().getReference("Groups").child(groupname).child("users").setValue(users);
                                        Toast.makeText(JoinGroup.this, "Group" + groupname + "joined!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), LandingRedesign.class);
                                        intent.putExtra("USERNAME", user.getEmail());
                                        startActivity(intent);
                                        finish();
                                    }
                                }else{
                                    Toast.makeText(JoinGroup.this, "Password incorrect!" , Toast.LENGTH_SHORT).show();
                                }
                            }else{

                            }

                        }
                        if(!groupExists){
                            Toast.makeText(JoinGroup.this, "Group does not exist!" , Toast.LENGTH_SHORT).show();
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
