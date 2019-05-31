package com.btanhuecrng13ctong3.easyliving;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LandingRedesign extends AppCompatActivity {
    FirebaseUser user;
    GridLayout gridButtons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_redesign);
        // START OF WELCOME TEXT CODE
        user = FirebaseAuth.getInstance().getCurrentUser();
        String username = getIntent().getStringExtra("USERNAME");
        TextView message = findViewById(R.id.introText);
        String welcomeMessage = "Welcome home, " + username;
        message.setText(welcomeMessage);
        // END OF WELCOME TEXT CODE
        gridButtons = findViewById(R.id.mainGrid);
        setToggleEvent(gridButtons);



    }

    private void setToggleEvent(GridLayout gridInput){
        for(int i=0; i<gridInput.getChildCount();i++){
            CardView card = (CardView)gridInput.getChildAt(i);
            final int finalI = i;
            card.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Log.d("LandingRedesign", "Index: "+ finalI);
                    if(finalI == 0){
                        // Chores
                        Intent intent = new Intent(getApplicationContext(), ChoresActivity.class);
                        startActivity(intent);
                    }else if(finalI == 1){
                        // Bill Splitting
                        Intent intent = new Intent(getApplicationContext(), viewPayments2.class);
                        intent.putExtra("USERNAME",user.getEmail());
                        startActivity(intent);
                    }else if(finalI == 2){
                        // Supplies
                        Intent intent = new Intent(getApplicationContext(), SuppliesActivity.class);
                        startActivity(intent);
                        //finish();
                    }else if(finalI == 3){
                        // Your Payments
                        Intent intent = new Intent(getApplicationContext(), viewPayments2.class);
                        intent.putExtra("YOUOWE",true);
                        startActivity(intent);
                    }else if(finalI==4){
                        // Message Board
                        Intent intent = new Intent(getApplicationContext(), MessageActivity.class);
                        intent.putExtra("USERNAME", user.getEmail());
                        startActivity(intent);
                    }else if(finalI==5){
                        // Logout
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(LandingRedesign.this, "Sign out successful!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else if(finalI == 6){
                        // Debug
                        Intent intent = new Intent(getApplicationContext(), LandingActivity.class);
                        intent.putExtra("USERNAME", user.getEmail());
                        startActivity(intent);
                    }else if(finalI == 7){
                        Intent intent = new Intent(getApplicationContext(), viewPayments2.class);
                        intent.putExtra("USERNAME", user.getEmail());
                        startActivity(intent);
                    }
                }
            });
        }
    }
}
