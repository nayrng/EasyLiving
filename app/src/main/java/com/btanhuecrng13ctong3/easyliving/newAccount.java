package com.btanhuecrng13ctong3.easyliving;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class newAccount extends AppCompatActivity {
    EditText userEmail;
    EditText userPW;
    Button registerAccount;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);
        userEmail = findViewById(R.id.emailText2);
        userPW = findViewById(R.id.passwordText2);
        registerAccount = findViewById(R.id.loginBtn2);
        firebaseAuth = FirebaseAuth.getInstance();
        registerAccount.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                firebaseAuth.createUserWithEmailAndPassword(userEmail.getText().toString(), userPW.getText().toString());
            }
        });
    }
}
