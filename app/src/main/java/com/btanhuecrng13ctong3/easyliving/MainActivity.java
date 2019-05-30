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

public class MainActivity extends AppCompatActivity {
    EditText userEmail;
    EditText userPW;
    Button login;
    Button newAccount;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    ProgressBar loginProg;

    DatabaseReference group_reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userEmail = findViewById(R.id.emailText);
        userPW = findViewById(R.id.passwordText);
        login = findViewById(R.id.loginBtn);
        newAccount = findViewById(R.id.newAccountBtn);
        loginProg = findViewById(R.id.loginBar);
        firebaseAuth = FirebaseAuth.getInstance();
        Log.d("testing", "started main");

        user = FirebaseAuth.getInstance().getCurrentUser();
        if( user != null){
            Toast.makeText(MainActivity.this, "Welcome Back!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), LandingRedesign.class);
            intent.putExtra("USERNAME", user.getEmail());
            startActivity(intent);
            finish();
        }else{
            Log.d("Tag","No user currently logged in");
            loginProg.setVisibility(View.INVISIBLE);
        }


        newAccount.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), newAccount.class);
                startActivity(intent);
                Log.d("Tag","newAccountPage");
            }
        });

        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                loginProg.setVisibility(View.VISIBLE);
                if(TextUtils.isEmpty(userEmail.getText())){
                    Toast.makeText(MainActivity.this, "Please enter a valid email", Toast.LENGTH_SHORT).show(); //displays notification
                    return;//prevents the app from running any further
                }
                if(TextUtils.isEmpty(userPW.getText())){
                    Toast.makeText(MainActivity.this, "Please enter a valid password", Toast.LENGTH_SHORT).show(); //displays notification
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(userEmail.getText().toString(), userPW.getText().toString())
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    //Log.d(TAG, "createUserWithEmail:success");
                                    //FirebaseUser user = firebaseAuth.getCurrentUser(); <--Firebase Code IDK what it does
                                    //updateUI(user);<--Firebase Code IDK what it does
                                    loginProg.setVisibility(View.INVISIBLE);
                                    Toast.makeText(MainActivity.this, "Login success!", Toast.LENGTH_SHORT).show();
                                    user = FirebaseAuth.getInstance().getCurrentUser();

                                    Intent intent = new Intent(getApplicationContext(), LandingRedesign.class);
                                    intent.putExtra("USERNAME", user.getEmail());
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    //Log.w(TAG, "createUserWithEmail:failure", task.getException());

                                    Toast.makeText(MainActivity.this, "Login failed, please try again.", Toast.LENGTH_SHORT).show();
                                    loginProg.setVisibility(View.INVISIBLE);
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
