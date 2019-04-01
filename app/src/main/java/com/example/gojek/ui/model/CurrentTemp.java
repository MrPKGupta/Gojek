package com.example.gojek.ui.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CurrentTemp implements Parcelable {
    public static final Parcelable.Creator<CurrentTemp> CREATOR = new Parcelable.Creator<CurrentTemp>() {
        @Override
        public CurrentTemp createFromParcel(Parcel source) {
            return new CurrentTemp(source);
        }

        @Override
        public CurrentTemp[] newArray(int size) {
            return new CurrentTemp[size];
        }
    };
    private String place;
    private float currentTemp;

    public CurrentTemp(String place, float currentTemp) {
        this.place = place;
        this.currentTemp = currentTemp;
    }

    protected CurrentTemp(Parcel in) {
        this.place = in.readString();
        this.currentTemp = in.readFloat();
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public float getCurrentTemp() {
        return currentTemp;
    }

    public void setCurrentTemp(float currentTemp) {
        this.currentTemp = currentTemp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.place);
        dest.writeFloat(this.currentTemp);
    }
}
