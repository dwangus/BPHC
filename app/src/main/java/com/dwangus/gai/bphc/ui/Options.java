package com.dwangus.gai.bphc.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dwangus.gai.bphc.R;
import com.dwangus.gai.bphc.model.Answers;
import com.dwangus.gai.bphc.model.QAForum;
import com.dwangus.gai.bphc.model.Questions;
import com.firebase.client.Firebase;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;

public class Options extends AppCompatActivity {
    public static final String TAG = Options.class.getSimpleName();
    //public static final String FORUM_Q = "FORUM_Q";
    //public static final String FORUM_A = "FORUM_A";
    //private QAForum mQA;

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "jpQ6wJspsCJ0lpzUL7jgpEwuO";
    private static final String TWITTER_SECRET = "07t6hJhiIw5JvG35ji91gaWvvSmf8cCt7GNRYdgZgEYnqbsTB9";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_options);
        ButterKnife.inject(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
    }
    /*
    private void getForum(Firebase ref){
        if (isNetworkAvailable()){
            try {
                String jsonData = ; // call Firebase to get JSONData back
                Log.v(TAG, jsonData);
                mQA = parseDatabaseData(jsonData);
            }
            catch (IOException e) {
                Log.e(TAG, "Exception caught: ", e);
            }
            catch (JSONException e) {
                Log.e(TAG, "Exception caught: ", e);
            }
        }
        else{
            Toast.makeText(this, getString(R.string.network_unavailable_message),
                    Toast.LENGTH_LONG).show();
        }
    }
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
    private QAForum parseDatabaseData(String jsonData) throws JSONException {
        QAForum qa = new QAForum();

        qa.setQuestionsList(getQAQs(jsonData));
        qa.setAnswersList(getQAAs(jsonData));

        return qa;
    }
    private Questions[] getQAQs(String jsonData) throws JSONException{
        JSONObject QA = new JSONObject(jsonData);
        //String timezone = forecast.getString("timezone");
        //JSONObject hourly = forecast.getJSONObject("hourly");
        //JSONArray data = hourly.getJSONArray("data");
        Questions[] Qs = new Questions[data.length()];

        for (int i = 0; i < data.length(); i++){
            *//*
            JSONObject jsonHour = data.getJSONObject(i);
            Hour hour = new Hour();

            hour.setSummary(jsonHour.getString("summary"));
            hour.setIcon(jsonHour.getString("icon"));
            hour.setTemperature(jsonHour.getDouble("temperature"));
            hour.setTime(jsonHour.getLong("time"));
            hour.setTimezone(timezone);

            hours[i] = hour;
             *//*
        }

        return Qs;
    }
    private Answers[] getQAAs(String jsonData) throws JSONException{
        JSONObject QA = new JSONObject(jsonData);
        //String timezone = forecast.getString("timezone");
        //JSONObject hourly = forecast.getJSONObject("hourly");
        //JSONArray data = hourly.getJSONArray("data");
        Answers[] As = new Answers[data.length()];

        for (int i = 0; i < data.length(); i++){
            *//*
            JSONObject jsonHour = data.getJSONObject(i);
            Hour hour = new Hour();

            hour.setSummary(jsonHour.getString("summary"));
            hour.setIcon(jsonHour.getString("icon"));
            hour.setTemperature(jsonHour.getDouble("temperature"));
            hour.setTime(jsonHour.getLong("time"));
            hour.setTimezone(timezone);

            hours[i] = hour;
             *//*
        }

        return As;
    }*/

    @OnClick(R.id.toForumOptions)
    public void toForum(View view){
        Intent intent = new Intent(this, Forum.class);
        startActivity(intent);
    }
    @OnClick (R.id.toNewsFeedOptions)
    public void toNewsFeed(View view){
        Intent intent = new Intent(this, TimeLine.class);
        startActivity(intent);
    }

}
