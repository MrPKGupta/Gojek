package com.example.gojek.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Day implements Parcelable {
    public static final Creator<Day> CREATOR = new Creator<Day>() {
        @Override
        public Day createFromParcel(Parcel source) {
            return new Day(source);
        }

        @Override
        public Day[] newArray(int size) {
            return new Day[size];
        }
    };
    @SerializedName("avgtemp_c")
    private float avgTemp;

    public Day() {
    }

    protected Day(Parcel in) {
        this.avgTemp = in.readFloat();
    }

    public float getAvgTemp() {
        return avgTemp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.avgTemp);
    }
}
