package com.dwangus.gai.bphc.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
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
        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "Chewy.ttf");
        TextView myTextview1 = (TextView) findViewById(R.id.toForumOptions);
        TextView myTextview2 = (TextView) findViewById(R.id.descriptionOptions);
        TextView myTextview3 = (TextView) findViewById(R.id.toNewsFeedOptions);

        myTextview1.setTypeface(myTypeface);
        myTextview2.setTypeface(myTypeface);
        myTextview3.setTypeface(myTypeface);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @OnClick(R.id.toForumOptions)
    public void toForum(){
        Intent intent = new Intent(this, Forum.class);
        startActivity(intent);
    }
    @OnClick (R.id.toNewsFeedOptions)
    public void toNewsFeed(){
        Intent intent = new Intent(this, TimeLine.class);
        startActivity(intent);
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
