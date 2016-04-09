package com.dwangus.gai.bphc.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dwangus.gai.bphc.R;
import com.dwangus.gai.bphc.model.Questions;
import com.firebase.client.Firebase;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class submitQuestion extends AppCompatActivity {

    @InjectView(R.id.titleQ) EditText mTitle;
    @InjectView(R.id.submitQ) Button mSubmit;
    @InjectView(R.id.detailsQ) EditText mDetails;
    @InjectView(R.id.cancelQ) Button mCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_question);
        ButterKnife.inject(this);
        Intent intent = getIntent();
        final String url = intent.getStringExtra("FIREBASE_URL");
        final Firebase mRootRef = new Firebase(url);

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Firebase qRef = mRootRef.child("questions");
                String title = mTitle.getText().toString();
                String details = mDetails.getText().toString();
                Toast.makeText(submitQuestion.this, "Thank you for your question!", Toast.LENGTH_LONG).show();
                Questions new_Question = new Questions();
                new_Question.setDescription(details);
                new_Question.setTitle(title);
                Long tsLong = System.currentTimeMillis()/1000;
                new_Question.setDate(tsLong);
                Firebase tempRef = qRef.push();
                String ID = (String) tempRef.getKey();
                new_Question.setID(ID);
                tempRef.setValue(new_Question);
                finish();
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
