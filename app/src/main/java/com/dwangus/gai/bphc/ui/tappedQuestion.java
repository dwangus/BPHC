package com.dwangus.gai.bphc.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dwangus.gai.bphc.R;
import com.dwangus.gai.bphc.adapters.AnswersAdapter;
import com.dwangus.gai.bphc.model.Answers;
import com.dwangus.gai.bphc.model.Questions;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class tappedQuestion extends AppCompatActivity {

    private Questions mQuestion = new Questions();
    private List<Answers> mAnswers = new ArrayList<Answers>();
    private AnswersAdapter adapter;
    private String mPassword;

    @InjectView(R.id.tQTitle) TextView mQTitle;
    @InjectView(R.id.qDate) TextView mDateSubmit;
    @InjectView(R.id.qDetails) TextView mQDetails;
    @InjectView(R.id.numberOfAnswers) TextView mNumberOfAnswers;
    @InjectView(R.id.recyclerQView) RecyclerView mRecyclerQView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tapped_question);
        ButterKnife.inject(this);

        /*
        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "Chewy.ttf");
        TextView myTextview1 = (TextView) findViewById(R.id.tQTitle);
        TextView myTextview2 = (TextView) findViewById(R.id.qDate);
        TextView myTextview3 = (TextView) findViewById(R.id.qDetails);
        TextView myTextview4 = (TextView) findViewById(R.id.numberOfAnswers);
        TextView myTextview5 = (TextView) findViewById(R.id.newAnswer);
        myTextview1.setTypeface(myTypeface);
        myTextview2.setTypeface(myTypeface);
        myTextview3.setTypeface(myTypeface);
        myTextview4.setTypeface(myTypeface);
        myTextview5.setTypeface(myTypeface);
        */


        Intent intent = getIntent();
        final String url = intent.getStringExtra("URL");
        final String qID = intent.getStringExtra("QUESTION_ID");

        Firebase rootRef = new Firebase(url);
        Query qIDRef = rootRef.child("questions").orderByChild("id").equalTo(qID);
        //System.out.println(qID);
        //System.out.println("Hello before");
        qIDRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //System.out.println(dataSnapshot);
                mQuestion.setID(qID);
                mQuestion.setDate((long) dataSnapshot.child("date").getValue());
                mQuestion.setDescription((String) dataSnapshot.child("description").getValue());
                mQuestion.setTitle((String) dataSnapshot.child("title").getValue());
                mQuestion.setNumAnswers((long) dataSnapshot.child("numAnswers").getValue());
                mQTitle.setText(mQuestion.getTitle());
                mDateSubmit.setText(mQuestion.getformattedDate());
                mQDetails.setText(mQuestion.getDescription());
                mNumberOfAnswers.setText("Answers (" + mQuestion.getNumAnswers() + ")");
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                long newNum = (long) dataSnapshot.child("numAnswers").getValue();
                mQuestion.setNumAnswers(newNum);
                mNumberOfAnswers.setText("Answers (" + mQuestion.getStringNumAnswers() + ")");
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        //Firebase rootRef = new Firebase(url);

        Query answersQID = rootRef.child("answers").orderByChild("qid").equalTo(qID);

        answersQID.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.getValue() != 0) {
                    Answers temp = new Answers();
                    temp.setQID((String) dataSnapshot.child("qid").getValue());
                    temp.setTimeAnswered((long) dataSnapshot.child("timeAnswered").getValue());
                    temp.setAnswer((String) dataSnapshot.child("answer").getValue());
                    temp.setHeader((String) dataSnapshot.child("header").getValue());
                    if (mAnswers.size() == 0){
                        mAnswers.add(temp);
                    }
                    else{
                        for (int i = 0; i < mAnswers.size(); i++){
                            if (mAnswers.get(i).getTimeAnswered() < temp.getTimeAnswered()){
                                mAnswers.add(i, temp);
                                break;
                            }
                        }
                    }
                    System.out.println(mAnswers.get(0));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        mQTitle.setText("Question Title");
        mDateSubmit.setText("--:-- AM/PM");
        mQDetails.setText("Question Details");
        mNumberOfAnswers.setText("Answers (0)");

        adapter = new AnswersAdapter(this, mAnswers);
        mRecyclerQView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerQView.setLayoutManager(layoutManager);
    }
    @OnClick(R.id.newAnswer)
    public void newAnswer(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Enter your password")
                .setTitle("Log in");

        LayoutInflater inflater = this.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.login_pop_up, null));

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                AlertDialog dlg = (AlertDialog) dialog;
                mPassword = ((TextView) dlg.findViewById(R.id.password)).getText().toString();
                if (mPassword.equals("BPHC"))
                {
                    passwordSuccess();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Incorrect Password", Toast.LENGTH_LONG).show();
                }
            }
        });

        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();


    }


    public void passwordSuccess(){
        Intent intent = new Intent(this, submitAnswer.class);
        intent.putExtra("FIREBASE_URL", Forum.FIREBASE_URL);
        intent.putExtra("Q_ID", mQuestion.getID());
        intent.putExtra("NUMBER", mQuestion.getNumAnswers());
        startActivity(intent);
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
