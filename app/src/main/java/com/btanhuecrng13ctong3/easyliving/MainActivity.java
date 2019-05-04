package com.btanhuecrng13ctong3.easyliving;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText userEmail;
    EditText userPW;
    Button login;
    Button newAccount;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userEmail = findViewById(R.id.emailText);
        userPW = findViewById(R.id.passwordText);
        login = findViewById(R.id.loginBtn);
        newAccount = findViewById(R.id.newAccountBtn);
        firebaseAuth = FirebaseAuth.getInstance();

        newAccount.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), newAccount.class);
                startActivity(intent);
                Log.d("Tag","newAccountPage");
            }
        });

        //final Button myPage = (Button) findViewById(R.id.MyPage);
        //myPage.setOnClickListener(new View.OnClickListener() {
            //@Override
            //public void onClick(View v) {
            //    Intent intent = new Intent(getApplicationContext(), mypage.class);
             //   startActivity(intent);
            //}
        //});


    }
}
