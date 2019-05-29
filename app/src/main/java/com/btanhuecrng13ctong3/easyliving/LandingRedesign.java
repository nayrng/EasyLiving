package com.btanhuecrng13ctong3.easyliving;

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
                }
            });
        }
    }
}
