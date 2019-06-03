package com.btanhuecrng13ctong3.easyliving;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MessageDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);

        String title = getIntent().getStringExtra("post_title");
        String body = getIntent().getStringExtra("post_body");
        String author = getIntent().getStringExtra("post_author");
        Boolean anon = getIntent().getBooleanExtra("post_anon", false);


        TextView post_title = findViewById(R.id.details_post_title);
        post_title.setText(title);

        TextView post_author = findViewById(R.id.details_post_author);
        if (!anon) {
            post_author.setText(author);
        } else {
            post_author.setText("Anonymous");
        }


        TextView post_body = findViewById(R.id.details_post_body);
        post_body.setText(body);

    }
}
