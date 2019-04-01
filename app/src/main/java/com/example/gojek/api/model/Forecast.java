package com.example.gojek.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Forecast implements Parcelable {
    public static final Parcelable.Creator<Forecast> CREATOR = new Parcelable.Creator<Forecast>() {
        @Override
        public Forecast createFromParcel(Parcel source) {
            return new Forecast(source);
        }

        @Override
        public Forecast[] newArray(int size) {
            return new Forecast[size];
        }
    };
    @SerializedName("forecastday")
    List<ForecastDay> forecastDays;

    public Forecast() {
    }

    protected Forecast(Parcel in) {
        this.forecastDays = in.createTypedArrayList(ForecastDay.CREATOR);
    }

    public List<ForecastDay> getForecastDays() {
        return forecastDays;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.forecastDays);
    }
}
