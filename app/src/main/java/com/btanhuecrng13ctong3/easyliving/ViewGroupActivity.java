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

public class ViewGroupActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    ArrayList<GROUPS_OBJECT> groups;
    FirebaseUser user;
    Button createGroup;

    EditText groupName;
    EditText groupPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_group);
        databaseReference = FirebaseDatabase.getInstance().getReference("Groups");
        groups = new ArrayList<GROUPS_OBJECT>();
        createGroup = findViewById(R.id.createGroup);

        groupName = findViewById(R.id.groupUser);
        groupPass = findViewById(R.id.groupPass);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //ArrayList<String> users = new ArrayList<String>();
                //ArrayList<String> chore_list = new ArrayList<String>();
                for (DataSnapshot snapShot: dataSnapshot.getChildren()) {
                    //Log.i("testing", zoneShapshot.child("CHORE_NAME").getValue(String.class));
//                    String user = snapShot.child("")
//                    String chore_name = snapShot.child("CHORE_NAME").getValue(String.class);
                    String user = snapShot.child("USER_ID").getValue(String.class);
                    ArrayList<String> users = (ArrayList<String>)snapShot.child("users").getValue();
                    ArrayList<String> chore_list= (ArrayList<String>)snapShot.child("chores").getValue();
                    String group_name = snapShot.child("groupName").getValue(String.class);
                    String group_pass = snapShot.child("pass").getValue(String.class);
                    //
                    GROUPS_OBJECT obj = new GROUPS_OBJECT(user, users, chore_list, group_name,group_pass);
                    groups.add(obj);

                }
                //createList(items);

                /*for (int i=0; i<items.size(); i++) {
                    Log.d("testing22", items.get(i).CHORE_NAME);
                }*/
//                LinearLayout layout = findViewById(R.id.choresLayout);
//                layout.removeAllViews();
//                layout.removeAllViewsInLayout();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        createGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth = FirebaseAuth.getInstance();
                user = firebaseAuth.getCurrentUser();
                ArrayList<String> users = new ArrayList<String>();
                users.add(user.getEmail());
                ArrayList<String> chores = new ArrayList<>();
                databaseReference.child(groupName.getText().toString()).setValue(new GROUPS_OBJECT(user.getEmail(), users, chores, groupName.getText().toString(), groupPass.getText().toString()));
                Toast.makeText(ViewGroupActivity.this, "Group" + groupName.getText().toString() + "joined!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), LandingRedesign.class);
                intent.putExtra("USERNAME", user.getEmail());
                startActivity(intent);
                finish();

            }
        });
    }
}
