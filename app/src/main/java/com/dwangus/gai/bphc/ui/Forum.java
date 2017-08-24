package com.dwangus.gai.bphc.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.dwangus.gai.bphc.R;
import com.dwangus.gai.bphc.adapters.QuestionsAdapter;
import com.dwangus.gai.bphc.model.Answers;
import com.dwangus.gai.bphc.model.QAForum;
import com.dwangus.gai.bphc.model.Questions;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

public class Forum extends AppCompatActivity {
    @InjectView(R.id.recyclerView) RecyclerView mRecyclerView;
    public static final String TAG = Forum.class.getSimpleName();
    private List<Questions> mQuestions = new ArrayList<Questions>();
    private QuestionsAdapter adapter;
    //public static Boolean hasChanged = false;
    //change the URL based on the account
    //ALSO: must initialize a "questions: 0" and "answers: 0" in the database url
    public static final String FIREBASE_URL = "https://bphc2.firebaseio.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        ButterKnife.inject(this);
        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "Chewy.ttf");
        TextView myTextview1 = (TextView) findViewById(R.id.newQ);
        myTextview1.setTypeface(myTypeface);

        if (isNetworkAvailable()) {
            Firebase.setAndroidContext(this);
            Firebase firebaseRef = new Firebase(FIREBASE_URL);
            Query queryRef = firebaseRef.child("questions").orderByChild("date");
            //getForum(firebaseRef);

            queryRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    if (dataSnapshot.getValue() != 0) {
                        Questions anotherQuestion = new Questions();
                        anotherQuestion.setID((String) dataSnapshot.child("id").getValue());
                        anotherQuestion.setDate((long) dataSnapshot.child("date").getValue());
                        anotherQuestion.setDescription((String) dataSnapshot.child("description").getValue());
                        anotherQuestion.setTitle((String) dataSnapshot.child("title").getValue());
                        anotherQuestion.setNumAnswers((long) dataSnapshot.child("numAnswers").getValue());
                        mQuestions.add(0, anotherQuestion);
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    String uniqueID = (String) dataSnapshot.getKey();
                    long newNum = (long) dataSnapshot.child("numAnswers").getValue();
                    for (int i = 0; i < mQuestions.size(); i++){
                        if (mQuestions.get(i).getID().equals(uniqueID)){
                            mQuestions.get(i).setNumAnswers(newNum);
                            break;
                        }
                    }
                    adapter.notifyDataSetChanged();
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
            Intent intent = getIntent();
            /*
            Parcelable[] parcelables_Q = intent.getParcelableArrayExtra(Options.FORUM_Q);
            mQuestions = Arrays.copyOf(parcelables_Q, parcelables_Q.length, Questions[].class);
            Parcelable[] parcelables_A = intent.getParcelableArrayExtra(Options.FORUM_A);
            mAnswers = Arrays.copyOf(parcelables_A, parcelables_A.length, Answers[].class);
            */
            adapter = new QuestionsAdapter(this, mQuestions);
            mRecyclerView.setAdapter(adapter);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(layoutManager);
        }
        else{
            Toast.makeText(this, getString(R.string.network_unavailable_message),
                    Toast.LENGTH_LONG).show();
        }
        Log.d(TAG, "Forum UI code is running!");
    }
    @OnClick(R.id.newQ)
    public void newQuestion(View view){
        Intent intent = new Intent(this, submitQuestion.class);
        intent.putExtra("FIREBASE_URL", FIREBASE_URL);
        startActivity(intent);
    }
    /*
    private void getForum(Firebase ref){
        if (isNetworkAvailable()){
            ref.child("questions").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        if (dataSnapshot.getValue() != 0){
                            Questions anotherQuestion = dataSnapshot.getValue(Questions.class);
                            mQuestions.add(anotherQuestion);
                        }
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    System.out.println("The read failed: " + firebaseError.getMessage());
                }
            });
            ref.child("answers").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        if (postSnapshot.getValue() != 0) {
                            Answers answer = postSnapshot.getValue(Answers.class);
                            Forum.mAnswers.add(answer);
                        }
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    System.out.println("The read failed: " + firebaseError.getMessage());
                }
            });
        } else {
            Toast.makeText(this, getString(R.string.network_unavailable_message),
                    Toast.LENGTH_LONG).show();
        }
    }*/
    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }

        return isAvailable;
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

    /*
    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }*/
}
