package com.dwangus.gai.bphc.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
        /*
        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "Chewy.ttf");
        TextView myTextview1 = (TextView) findViewById(R.id.cancelQ);
        TextView myTextview2 = (TextView) findViewById(R.id.submitQ);
        TextView myTextview3 = (TextView) findViewById(R.id.titleQ);
        TextView myTextview4 = (TextView) findViewById(R.id.detailsQ);
        myTextview1.setTypeface(myTypeface);
        myTextview2.setTypeface(myTypeface);
        myTextview3.setTypeface(myTypeface);
        myTextview4.setTypeface(myTypeface);
        */

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

    public void toForum(){
        Intent intent = new Intent(this, Forum.class);
        startActivity(intent);
    }

    public void toNewsFeed(){
        Intent intent = new Intent(this, TimeLine.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if (id == R.id.action_Forum){
            toForum();
        }
        else if (id == R.id.action_NewsFeed){
            toNewsFeed();
        }

        return super.onOptionsItemSelected(item);
    }
}
