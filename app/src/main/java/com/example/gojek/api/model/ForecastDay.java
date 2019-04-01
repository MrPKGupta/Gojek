package com.example.gojek.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ForecastDay implements Parcelable {
    public static final Parcelable.Creator<ForecastDay> CREATOR = new Parcelable.Creator<ForecastDay>() {
        @Override
        public ForecastDay createFromParcel(Parcel source) {
            return new ForecastDay(source);
        }

        @Override
        public ForecastDay[] newArray(int size) {
            return new ForecastDay[size];
        }
    };
    private String date;
    @SerializedName("date_epoch")
    private long dateEpoch;
    private Day day;

    public ForecastDay() {
    }

    protected ForecastDay(Parcel in) {
        this.date = in.readString();
        this.dateEpoch = in.readLong();
        this.day = in.readParcelable(Day.class.getClassLoader());
    }

    public Day getDay() {
        return day;
    }

    public String getDate() {
        return date;
    }

    public long getDateEpoch() {
        return dateEpoch;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.date);
        dest.writeLong(this.dateEpoch);
        dest.writeParcelable(this.day, flags);
    }
}
