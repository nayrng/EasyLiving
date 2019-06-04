package com.btanhuecrng13ctong3.easyliving;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class newAccount extends AppCompatActivity {

    EditText userEmail;
    EditText userPW;
    Button registerAccount;
    FirebaseAuth firebaseAuth;
    ProgressBar loginProg;

    DatabaseReference group_ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);
        loginProg = findViewById(R.id.createBar);
        userEmail = findViewById(R.id.emailText2);
        userPW = findViewById(R.id.passwordText2);
        registerAccount = findViewById(R.id.loginBtn2);
        firebaseAuth = FirebaseAuth.getInstance();
        loginProg.setVisibility(View.INVISIBLE);
        registerAccount.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                loginProg.setVisibility(View.VISIBLE);
                if(TextUtils.isEmpty(userEmail.getText())){
                    Toast.makeText(newAccount.this, "Please enter a valid email", Toast.LENGTH_SHORT).show(); //displays notification
                    return;//prevents the app from running any further
                }
                if(TextUtils.isEmpty(userPW.getText())){
                    Toast.makeText(newAccount.this, "Please enter a valid password", Toast.LENGTH_SHORT).show(); //displays notification
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(userEmail.getText().toString(), userPW.getText().toString())
                        .addOnCompleteListener(newAccount.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    loginProg.setVisibility(View.INVISIBLE);
                                    // Sign in success, update UI with the signed-in user's information
                                    //Log.d(TAG, "createUserWithEmail:success");
                                    //FirebaseUser user = firebaseAuth.getCurrentUser(); <--Firebase Code IDK what it does
                                    //updateUI(user);<--Firebase Code IDK what it does
                                    Toast.makeText(newAccount.this, "Registration success!", Toast.LENGTH_SHORT).show();



                                    Intent intent = new Intent(getApplicationContext(), GroupChoice.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    loginProg.setVisibility(View.INVISIBLE);
                                    // If sign in fails, display a message to the user.
                                    //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(newAccount.this, "Registration failed, please try again.", Toast.LENGTH_SHORT).show();
                                    //Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",<--Firebase Code IDK what it does
                                     //       Toast.LENGTH_SHORT).show();<--Firebase Code IDK what it does
                                    //updateUI(null);<--Firebase Code IDK what it does
                                }
                                // ...
                            }
                        });
            }
        });
    }
}
