package com.dwangus.gai.bphc.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by David Wang on 4/4/2016.
 */
public class Questions implements Parcelable {
    private String mTitle;
    private long mDate;
    private String mDescription;
    private long mNumAnswers = 0;
    private String mQID;

    public Questions() {}

    public String getID(){
        return mQID;
    }

    public void setID(String id){
        this.mQID = id;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public long getDate() {
        return mDate;
    }

    public void setDate(long date) {
        this.mDate = date;
    }

    public String getformattedDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("h:mma MMM. dd, yyyy");
        Date date = new Date(mDate * 1000);
        return formatter.format(date);
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public long getNumAnswers() {
        return mNumAnswers;
    }

    public String getStringNumAnswers(){
        return String.valueOf(this.getNumAnswers());
    }

    public void setNumAnswers(long mNumAnswers) {
        this.mNumAnswers = mNumAnswers;
    }

    @Override
    public int describeContents() {
        return 0; // ignore
    }
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mQID);
        dest.writeString(mTitle);
        dest.writeLong(mDate);
        dest.writeString(mDescription);
        dest.writeLong(mNumAnswers);
    }
    private Questions(Parcel in){
        mQID = in.readString();
        mTitle = in.readString();
        mDate = in.readLong();
        mDescription = in.readString();
        mNumAnswers = in.readInt();
    }
    public static final Creator<Questions> CREATOR = new Creator<Questions>() {
        @Override
        public Questions createFromParcel(Parcel source) {
            return new Questions(source);
        }

        @Override
        public Questions[] newArray(int size) {
            return new Questions[size];
        }
    };
}
