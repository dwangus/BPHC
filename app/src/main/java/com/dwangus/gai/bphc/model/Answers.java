package com.dwangus.gai.bphc.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by David Wang on 4/5/2016.
 */
public class Answers implements Parcelable {
    private String mHeader;
    private long mTimeAnswered;
    private String mAnswer;
    private String mAQID;

    //private String mUsername;
    //private Boolean mDelete;

    public Answers() {}

    public String getQID(){
        return mAQID;
    }

    public void setQID(String id){
        this.mAQID = id;
    }

    public String getAnswer() {
        return mAnswer;
    }

    public void setAnswer(String answer) {
        this.mAnswer = answer;
    }

    public long getTimeAnswered() {
        return mTimeAnswered;
    }

    public void setTimeAnswered(long date) {
        this.mTimeAnswered = date;
    }

    public String getformattedTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("h:mma MMM. dd, yyyy");
        Date date = new Date(mTimeAnswered * 1000);
        return formatter.format(date);
    }

    public String getHeader() {
        return mHeader;
    }

    public void setHeader(String mTitle) {
        this.mHeader = mTitle;
    }

    @Override
    public int describeContents() {
        return 0; // ignore
    }
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mHeader);
        //dest.writeString(mUsername);
        dest.writeLong(mTimeAnswered);
        dest.writeString(mAnswer);
    }
    private Answers(Parcel in){
        mHeader = in.readString();
        //mUsername = in.readString();
        mTimeAnswered = in.readLong();
        mAnswer = in.readString();
    }
    public static final Creator<Answers> CREATOR = new Creator<Answers>() {
        @Override
        public Answers createFromParcel(Parcel source) {
            return new Answers(source);
        }

        @Override
        public Answers[] newArray(int size) {
            return new Answers[size];
        }
    };

}
