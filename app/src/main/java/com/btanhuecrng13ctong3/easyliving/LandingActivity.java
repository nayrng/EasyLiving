package com.btanhuecrng13ctong3.easyliving;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.view.Menu;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LandingActivity extends AppCompatActivity {
    //TextView welcomemsg;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        user = FirebaseAuth.getInstance().getCurrentUser();
        String username = getIntent().getStringExtra("USERNAME");
        Log.d("USER", username);
        TextView welcomemsg=findViewById(R.id.introText);
        String welcome = "Welcome home " + username;
        Log.d("USERWELCOME", welcome);
        welcomemsg.setText(welcome);
        //welcomemsg.append(username);


        Button chores =(Button) findViewById(R.id.buttonChores);
        chores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChoresActivity.class);
                startActivity(intent);
                //finish();
            }
        });

        Button supplies = (Button) findViewById(R.id.buttonSupplies);
        supplies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SuppliesActivity.class);
                startActivity(intent);
                //finish();
            }
        });
        
        Button api =(Button) findViewById(R.id.apibtn);
        api.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PayActivity.class);
                startActivity(intent);
                //finish();
            }
        });

        Button logout = findViewById(R.id.createGroup);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ViewGroupActivity.class);
                startActivity(intent);
                //finish();
            }
        });

        Button join_group = findViewById(R.id.JoinGroup);
        join_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), JoinGroup.class);
                startActivity(intent);
            }
        });

        Button split_payment = findViewById(R.id.button8);
        split_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LandingRedesign.class);
                intent.putExtra("USERNAME",user.getEmail());
                startActivity(intent);
            }
        });
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.logout1){
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(LandingActivity.this, "Sign out successful!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }*/
}
