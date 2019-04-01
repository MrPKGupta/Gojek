package com.example.gojek.api.model;

import com.google.gson.annotations.SerializedName;

public class Weather {
    @SerializedName("location")
    private LocationDetails locationDetails;
    private Current current;
    private Forecast forecast;

    public Current getCurrent() {
        return current;
    }

    public Forecast getForecast() {
        return forecast;
    }

    public LocationDetails getLocationDetails() {
        return locationDetails;
    }

    public class Current {
        @SerializedName("temp_c")
        private float temp;

        public float getTemp() {
            return temp;
        }
    }

    public class LocationDetails {
        private String name;

        public String getName() {
            return name;
        }
    }
}
