package com.example.gojek.api;

import com.example.gojek.api.model.Weather;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("forecast.json")
    Observable<Weather> getForecast(@Query("key") String key,
                                    @Query("q") String latLong,
                                    @Query("days") int days);
}
