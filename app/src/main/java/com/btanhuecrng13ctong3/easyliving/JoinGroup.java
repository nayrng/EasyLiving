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
import java.util.Arrays;

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



        Button join = (Button) findViewById(R.id.JoinGroupButton);
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference dBRef = FirebaseDatabase.getInstance().getReference("Groups");
                dBRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

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
                                if(grouppass.equals(stringPass)){
                                    Log.d("in pw success", "Pass match:" + grouppass);
                                    
                                }
                            }
                            /*String pw = snapshot.child("grouppass").getValue(String.class);
                            String groupname = snapshot.child("groupname").getValue(String.class);
                            Iterable<DataSnapshot> obj = snapshot.getChildren();
                            Log.d("onChange List Group", "Group: " + snapshot.child("groupname").getValue(String.class));
                            for(DataSnapshot child : obj){
                                if(child.getKey()=="grouppass"){

                                }
                                */


                            /*if(groupName.getText().toString().equals(obj.getGROUPNAME())){
                                Log.d("onChange Right Group", "Group: " + obj.getGROUPNAME());
                                String pass = obj.getGROUPPASS();
                                Log.d("onChange Right Group", " Group Pass: " + obj.getGROUPPASS());
                                if(obj.getGROUPPASS().equals(groupPass.getText().toString())){

                                    ArrayList<String> curUsers = obj.getUSERS();
                                    curUsers.add(user.getEmail());
                                    for(int i = 0; i<curUsers.size();i++){
                                        Log.d("onChange joinGroup", "Users: " + curUsers.get(i));
                                    }
                                    GROUPS_OBJECT updatedGroup = new GROUPS_OBJECT(obj.getADMIN(), curUsers, obj.getCHORES(), obj.getGROUPNAME(), obj.getGROUPPASS());
                                    databaseReference.child(obj.getGROUPNAME()).setValue(updatedGroup);
                                    Toast.makeText(JoinGroup.this, "Group Joined!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }

                            }else{
                                Log.d("onChange Wrong Group", "Group: " + obj.getGROUPNAME());
                            }*/
                            //get groupName
                            //check groupPw

                            /*if (pw.equals(groupPass.getText().toString())) {
                                for (int i = 0; i < fire_list.size(); i++) {

                                    System.out.println(fire_list.get(i).users.get(i));
                                }
                                //int index = fire_list.size()-1;
                                //databaseReference.child(groupName.getText().toString()).child("users").child(String.valueOf(index)).setValue(user.getEmail());
                                //Toast.makeText(JoinGroup.this, "Group Joined!", Toast.LENGTH_SHORT).show();
                                //finish();
                            } else {
                                Toast.makeText(JoinGroup.this, "Invalid Group Pass/User", Toast.LENGTH_SHORT).show();
                            }*/
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
