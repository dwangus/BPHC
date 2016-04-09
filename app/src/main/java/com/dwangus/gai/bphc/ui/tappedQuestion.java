package com.dwangus.gai.bphc.ui;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

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
        Intent intent = new Intent(this, submitAnswer.class);
        intent.putExtra("FIREBASE_URL", Forum.FIREBASE_URL);
        intent.putExtra("Q_ID", mQuestion.getID());
        intent.putExtra("NUMBER", mQuestion.getNumAnswers());
        startActivity(intent);
    }
}
