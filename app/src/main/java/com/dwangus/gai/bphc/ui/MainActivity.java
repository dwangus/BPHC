package com.dwangus.gai.bphc.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.OnClick;

import com.dwangus.gai.bphc.R;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "Kt0wflyjuYYiaw1nfPrb0s8dO";
    private static final String TWITTER_SECRET = "ajzZW7Hai5YH0lGTqrWrAF8NMg2B7WMbIQFk2NhPZ3FYKpY4ll";

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "Chewy.ttf");
        TextView myTextview1 = (TextView) findViewById(R.id.welcomeText);
        TextView myTextview2 = (TextView) findViewById(R.id.welcomeButton);
        myTextview1.setTypeface(myTypeface);
        myTextview2.setTypeface(myTypeface);

    }




    @OnClick(R.id.welcomeButton)
    public void toMission() {
        Intent intent = new Intent(this, mission.class);
        startActivity(intent);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
