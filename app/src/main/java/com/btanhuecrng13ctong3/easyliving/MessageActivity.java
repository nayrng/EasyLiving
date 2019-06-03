package com.btanhuecrng13ctong3.easyliving;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MessageActivity extends AppCompatActivity {

    Context context = MessageActivity.this;
    FirebaseUser user;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    DatabaseReference groupReference;
    ArrayList<MESSAGE_OBJ> messages;
    Boolean completed;

    String group_name;
    Boolean received_group;
    ArrayList<String> users_in_group;

    public RecyclerView.Adapter mAdapter;
    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        recyclerView = findViewById(R.id.message_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewMessage.class);
                startActivity(intent);

                //TODO: finish linking to recyclerview and adapter shit

            }
        });

        messages = new ArrayList<MESSAGE_OBJ>();

        groupReference = FirebaseDatabase.getInstance().getReference("Groups");
        groupReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snap: dataSnapshot.getChildren()) {
                    for (int i=0; i<snap.child("users").getChildrenCount(); i++) {
                        ArrayList<String> group_users = (ArrayList<String>) snap.child("users").getValue();
                        if (group_users.contains(user.getEmail())) {
                            group_name = (String) snap.child("groupname").getValue();

                            users_in_group = group_users;
                            received_group = true;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("Posts");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messages.clear();
                for (DataSnapshot snap: dataSnapshot.getChildren()) {
                    if (snap.child("group_NAME").getValue(String.class).equals(group_name) && received_group) {
                        String title = snap.child("post_TITLE").getValue(String.class);
                        String body = snap.child("post_BODY").getValue(String.class);
                        String g_name = snap.child("group_NAME").getValue(String.class);
                        String author = snap.child("post_AUTHOR").getValue(String.class);
                        Boolean anon = snap.child("post_ANON").getValue(Boolean.class);

                        MESSAGE_OBJ obj = new MESSAGE_OBJ(title, body, g_name, author, anon);

                        messages.add(obj);

                    }
                }

                completed = true;
                mAdapter = new MessageAdapter(messages);
                recyclerView.setAdapter(mAdapter);
                ((MessageAdapter)mAdapter).setOnItemClickListener(new MessageAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Log.d("Position", "pos " + position);
                    }

                    @Override
                    public void onDetailClick(int position) {

                        MESSAGE_OBJ obj = messages.get(position);
                        Intent intent = new Intent(getApplicationContext(), MessageDetails.class);
                        intent.putExtra("post_title", obj.getPOST_TITLE());
                        intent.putExtra("post_body", obj.getPOST_BODY());
                        intent.putExtra("post_author", obj.getPOST_AUTHOR());
                        intent.putExtra("post_anon", obj.getPOST_ANON());
                        startActivity(intent);

                    }

                    @Override
                    public void onImageClick(int position) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

}
