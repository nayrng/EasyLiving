package com.btanhuecrng13ctong3.easyliving;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NewMessage extends AppCompatActivity {

    FirebaseUser user;
    DatabaseReference group_reference;
    Button submit_button;
    EditText title;
    EditText body;
    CheckBox checkbox;
    String group_name_messages;
    FirebaseAuth firebaseAuth;
    ArrayList<String> pass_group_users;
    DatabaseReference databaseReference;
    Boolean receivedGroup;
    Boolean anonymous;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_message);
        submit_button = findViewById(R.id.make_post);
        title = findViewById(R.id.new_post_title);
        body = findViewById(R.id.new_post_body);

        checkbox = findViewById(R.id.anon_checkbox);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        receivedGroup = false;
        anonymous = false;

        databaseReference = FirebaseDatabase.getInstance().getReference("Posts");
        group_reference = FirebaseDatabase.getInstance().getReference("Groups");

        group_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snap: dataSnapshot.getChildren()) {
                    for (int i=0; i<snap.child("users").getChildrenCount(); i++) {
                        String map = snap.child("users").child(String.valueOf(i)).getValue(String.class);
                        ArrayList<String> group_users = (ArrayList<String>)snap.child("users").getValue();
                        if (group_users.contains(user.getEmail())) {
                            group_name_messages = (String) snap.child("groupname").getValue();
                            pass_group_users = group_users;
                            receivedGroup = true;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                anonymous = true;
            }
        });

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (receivedGroup) {
                    ArrayList<String> EmptyArray = new ArrayList();
                    EmptyArray.add(user.getEmail());
                    MESSAGE_OBJ obj = new MESSAGE_OBJ(title.getText().toString(), body.getText().toString(), group_name_messages, user.getEmail(), anonymous);
                    String head = title.getText().toString();
                    databaseReference.child(head).setValue(obj);
                    //databaseReference.child("Messages").setValue(obj);
                    finish();
                }
            }
        });


    }
}
