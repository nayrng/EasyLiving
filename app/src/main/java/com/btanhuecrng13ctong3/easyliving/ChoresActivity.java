package com.btanhuecrng13ctong3.easyliving;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChoresActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    DatabaseReference groupReference;
    private DatabaseReference usernameGet;

    public RecyclerView.Adapter mAdapter;
    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;


    //DatabaseReference group_ref;
    String group_name;


    ArrayList<CHORES_OBJECT> items;


    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String[] test = new String[1];
    Context ctx;

    Boolean received_group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chores);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        items = new ArrayList();

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();


        recyclerView = findViewById(R.id.chores_recycler_view);
        //recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        items = new ArrayList<>();

        recyclerView.setHasFixedSize(true);


        groupReference = FirebaseDatabase.getInstance().getReference("Groups");
        groupReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    for (int i = 0; i < snapshot.child("users").getChildrenCount(); i++) {
                        ArrayList<String> group_users = (ArrayList<String>) snapshot.child("users").getValue();
                        if (group_users.contains(user.getEmail())) {
                            group_name = (String) snapshot.child("groupname").getValue();
                            received_group = true;
                        }
                    }
                }

                databaseReference = FirebaseDatabase.getInstance().getReference("Chores");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        items.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            if (snapshot.child("GROUP_ID").getValue(String.class).equals(group_name) && received_group) {
                                String user = snapshot.child("USER_ID").getValue(String.class);
                                String chore = snapshot.child("CHORE_NAME").getValue(String.class);
                                Boolean chore_done = snapshot.child("CHORE_DONE").getValue(Boolean.class);
                                String group_id = snapshot.child("GROUP_ID").getValue(String.class);
                                String ass_date = snapshot.child("ASS_DATE").getValue(String.class);

                                CHORES_OBJECT obj = new CHORES_OBJECT(user, chore, chore_done, group_id, ass_date);
                                items.add(obj);
                            }
                        }

                        // adapter
                        mAdapter = new ChoresAdapter(items);
                        recyclerView.setAdapter(mAdapter);
                        ((ChoresAdapter) mAdapter).setOnItemClickListener(new ChoresAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {

                            }

                            @Override
                            public void onChoreStatusClick(int position) {
                                CHORES_OBJECT obj = items.get(position);
                                databaseReference.child(obj.CHORE_NAME).setValue(new CHORES_OBJECT(obj.USER_ID, obj.CHORE_NAME, !obj.CHORE_DONE, obj.GROUP_ID, obj.ASS_DATE));
//databaseReference.child(input.getText().toString()).setValue(new CHORES_OBJECT(user.getEmail(), input.getText().toString(), false, group_name, strDate));
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

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });




        FloatingActionButton fab = findViewById(R.id.chores_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat formatter = new SimpleDateFormat("MMM-dd-yyyy");
                Date date = new Date(System.currentTimeMillis());
                final String strDate = formatter.format(date);
                firebaseAuth = FirebaseAuth.getInstance();
                user = firebaseAuth.getCurrentUser();
                Log.d("testing", user.toString());
//                final DatabaseReference data = FirebaseDatabase.getInstance();
//                DatabaseReference mDataBase;
//                mDataBase = FirebaseDatabase.getInstance().getReference();


                Log.d("testing", "about to write value");


                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ChoresActivity.this);
                mBuilder.setTitle("Add a chore!");
                final EditText input = new EditText(ChoresActivity.this);
                mBuilder.setView(input);

                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String mID = databaseReference.child(input.getText().toString()).push().getKey();
                        databaseReference.child(input.getText().toString()).setValue(new CHORES_OBJECT(user.getEmail(), input.getText().toString(), false, group_name, strDate));
                        System.out.println(mID);
                    }
                });
                mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                mBuilder.show();

            }


        });


//        rootReference = FirebaseDatabase.getInstance().getReference();//reference the root
//        rootReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    Log.d("ChoresActivity", "RootReference started");
//                    DatabaseReference rootGroupRef = rootReference.child("Groups");
//                    rootGroupRef.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            for(DataSnapshot groupSnap: dataSnapshot.getChildren()){
//                                ArrayList<String> memb_ref = (ArrayList<String>)groupSnap.child("users").getValue();
//                                String c_group = groupSnap.child("groupname").getValue(String.class);
//                                Log.d("Group Snap Loop", ":" + c_group);
//                                if(memb_ref.contains(user.getEmail())){
//                                    group_name = c_group;
//                                }
//                            }
//                            Log.d("Exited Group Reference", ":" + group_name);
//                            DatabaseReference rootChoresRef= rootReference.child("Chores");
//                            rootChoresRef.addValueEventListener(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                    Log.d("testing", "doing shit");
//                                    for (DataSnapshot snapShot: dataSnapshot.getChildren()) {
//                                        if(group_name.equals(snapShot.child("GROUP_ID").getValue(String.class))) {
//                                            String user = snapShot.child("USER_ID").getValue(String.class);
//                                            String chore_name = snapShot.child("CHORE_NAME").getValue(String.class);
//                                            Boolean chore_status = snapShot.child("CHORE_DONE").getValue(Boolean.class);
//                                            String id = snapShot.child("GROUP_ID").getValue(String.class);
//                                            String date = snapShot.child("ASS_DATE").getValue(String.class);
//                                            CHORES_OBJECT obj = new CHORES_OBJECT(user, chore_name, chore_status, id, date);
//                                            items.add(obj);
//                                        }
//                                    }
//                                    for (int i=0; i<items.size(); i++) {
//                                        Log.d("View correct chores: ", items.get(i).CHORE_NAME);
//                                    }
//                                    //createList(items);
//
//                                }
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError databaseError) {
//                                }
//                            });
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//                        }
//                    });
//
//
//                    /*while(group_name == null){
//                        Log.d("ChoresActivity", "group_name is null");
//                        for (int i=0; i<group_ref.child("users").getChildrenCount(); i++) {
//                            String map =  group_ref.child("users").child(String.valueOf(i)).getValue(String.class);
//                            Log.d("Wtf is map: ", "Map: "+ map);
//                            if (map.equals(user.getEmail())) {
//                                group_name = (String) group_ref.child("groupname").getValue();
//                                Log.d("groupname ref:", group_name);
//                                break;
//                            }
//                            Log.d("ChoresChange", "Users" + i);
//                        }
//                    }*/
//                //}
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//
//
//
//
//        databaseReference = FirebaseDatabase.getInstance().getReference("Chores");

        /*group_ref = FirebaseDatabase.getInstance().getReference("Groups");

        group_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    for (int i=0; i<snapshot.child("users").getChildrenCount(); i++) {
                        String map =  snapshot.child("users").child(String.valueOf(i)).getValue(String.class);
                        Log.d("Wtf is map: ", "Map: "+ map);
                        if (map.equals(user.getEmail())) {
                            group_name = (String) snapshot.child("groupname").getValue();
                            Log.d("groupname ref:", group_name);
                            break;
                        }
                        Log.d("ChoresChange", "Users" + i);
                    }
                    //String pw = snapshot.child("groupName").getValue(String.class);


//                    for (int i=0; i<snapshot.child("users").getChildrenCount(); i++) {
//                        //System.out.println(snapshot.child("users").child(String.valueOf(i)).getValue());
//                        System.out.println(i);
//                        System.out.println(snapshot.child("users").child(String.valueOf(i)).getValue().getClass());
//                        HashMap map = (HashMap) snapshot.child("users").child(String.valueOf(i)).getValue();
//                        System.out.println(map.get("email"));
//                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/


        // Set up the buttons


        /*databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("testing", "doing shit");

                for (DataSnapshot snapShot: dataSnapshot.getChildren()) {
                    //Log.i("testing", zoneShapshot.child("CHORE_NAME").getValue(String.class));
//                    String user = snapShot.child("")
//                    String chore_name = snapShot.child("CHORE_NAME").getValue(String.class);
                    String user = snapShot.child("USER_ID").getValue(String.class);
                    String chore_name = snapShot.child("CHORE_NAME").getValue(String.class);
                    Boolean chore_status = snapShot.child("CHORE_DONE").getValue(Boolean.class);

                    String id = snapShot.child("GROUP_ID").getValue(String.class);

                    CHORES_OBJECT obj = new CHORES_OBJECT(user, chore_name, chore_status, id);

                    //CHORES_OBJECT obj = new CHORES_OBJECT(user, chore_name, chore_status);
                    items.add(obj);

                }
                createList(items);

                for (int i=0; i<items.size(); i++) {
                    Log.d("testing22", items.get(i).CHORE_NAME);
                }
//                LinearLayout layout = findViewById(R.id.choresLayout);
//                layout.removeAllViews();
//                layout.removeAllViewsInLayout();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/


    }


//        public void createList(ArrayList<CHORES_OBJECT> data_list) {
//
//        LinearLayout layout = findViewById(R.id.choresLayout);
//        layout.removeAllViews();
//
//        for (int i=0; i<data_list.size(); i++) {
//            TextView user =(TextView) new TextView(this);
//            TextView chore = (TextView) new TextView(this);
//            CheckBox checkBox = (CheckBox) new CheckBox(this);
//            Button button = new Button(this);
//            Log.d("ChoresObj", "Chore: " + i);
//            CHORES_OBJECT obj = data_list.get(i);
//
//            //if ((obj.GROUP_ID).equals(group_name)) {
//
//
//                user.setId(i);
//                user.setText("Chore assigned by: " + obj.USER_ID);
//
//                chore.setId(i);
//                chore.setText(obj.CHORE_NAME);
//
//                checkBox.setId(i);
//                checkBox.setChecked(obj.CHORE_DONE);
//                //button.setText("View Details");
//
//
//                layout.addView(user);
//                setTextViewAttributes(user);
//                layout.addView(chore);
//                setTextViewAttributes(chore);
//                layout.addView(checkBox);
//                setCheckBoxAttributes(checkBox);
//                onCheckBoxClicked(checkBox, obj);
//                //layout.addView(button);
//                layout.setBackgroundResource(R.drawable.black_border);
//           // }
//
//        }
//        data_list.clear();
//    }

    public void onCheckBoxClicked(final CheckBox checkBox, CHORES_OBJECT object) {
        final CHORES_OBJECT object1 = object;
        checkBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your code to be executed on click :)
                //databaseReference.child(input.getText().toString()).setValue(new CHORES_OBJECT(user.getEmail(), input.getText().toString(), false));

                //-------------------------
                databaseReference.child(object1.CHORE_NAME).setValue(new CHORES_OBJECT(object1.USER_ID, object1.CHORE_NAME, !object1.CHORE_DONE, object1.GROUP_ID, object1.ASS_DATE));
            }
        });
    }


    private void setCheckBoxAttributes(CheckBox checkBox) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        params.setMargins(convertDpToPixel(16),
                convertDpToPixel(16),
                convertDpToPixel(16),
                0
        );

        checkBox.setLayoutParams(params);

        //This is used to place the checkbox on the right side of the textview
        //By default, the checkbox is placed at the left side
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(android.R.attr.listChoiceIndicatorMultiple,
                typedValue, true);

        checkBox.setButtonDrawable(null);
        checkBox.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                typedValue.resourceId, 0);
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
        textView.setLayoutParams(params);
    }

    private int convertDpToPixel(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
    }


}
