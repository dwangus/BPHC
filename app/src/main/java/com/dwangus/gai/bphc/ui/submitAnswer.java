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
import com.dwangus.gai.bphc.model.Answers;
import com.dwangus.gai.bphc.model.Questions;
import com.firebase.client.Firebase;

import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.HashMap;

public class submitAnswer extends AppCompatActivity {

    private long num;
    @InjectView(R.id.headerA) EditText mHeader;
    @InjectView(R.id.submitAnswer) Button mSubmitA;
    @InjectView(R.id.detailsAnswer) EditText mDetailsA;
    @InjectView(R.id.cancelAnswer) Button mCancelA;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_answer);
        ButterKnife.inject(this);

        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "Chewy.ttf");
        TextView myTextview1 = (TextView) findViewById(R.id.cancelAnswer);
        TextView myTextview2 = (TextView) findViewById(R.id.submitAnswer);
        TextView myTextview3 = (TextView) findViewById(R.id.headerA);
        TextView myTextview4 = (TextView) findViewById(R.id.detailsAnswer);

        myTextview1.setTypeface(myTypeface);
        myTextview2.setTypeface(myTypeface);
        myTextview3.setTypeface(myTypeface);
        myTextview4.setTypeface(myTypeface);

        Intent intent = getIntent();
        final String url = intent.getStringExtra("FIREBASE_URL");
        final String qid = intent.getStringExtra("Q_ID");
        num = intent.getLongExtra("NUMBER", 0L);
        final Firebase mRootRef = new Firebase(url);
        mSubmitA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Firebase ARef = mRootRef.child("answers");
                String header = mHeader.getText().toString();
                String detailsA = mDetailsA.getText().toString();
                Toast.makeText(submitAnswer.this, "Answer received!", Toast.LENGTH_LONG).show();
                Answers new_Answer = new Answers();
                new_Answer.setAnswer(detailsA);
                new_Answer.setHeader(header);
                Long tsLong = System.currentTimeMillis()/1000;
                new_Answer.setTimeAnswered(tsLong);
                new_Answer.setQID(qid);
                ARef.push().setValue(new_Answer);

                num++;
                Firebase questionRef = mRootRef.child("questions").child(qid);
                HashMap<String, Object> newNumAns = new HashMap<String, Object>();
                newNumAns.put("numAnswers", num);
                questionRef.updateChildren(newNumAns);
                finish();
            }
        });

        mCancelA.setOnClickListener(new View.OnClickListener() {
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
