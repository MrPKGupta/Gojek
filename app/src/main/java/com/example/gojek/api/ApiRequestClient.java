package com.example.gojek.api;

import com.example.gojek.api.model.Weather;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ApiRequestClient {
    private static final String API_KEY = "d1186fd768934580a97114746193003";
    private static final int FORECAST_DAYS = 5;

    private final ApiService apiService;

    @Inject
    public ApiRequestClient(ApiService apiService) {
        this.apiService = apiService;
    }

    public void getForecast(WeatherCallback weatherCallback, String latLong) {
        Observable<Weather> weatherObservable = apiService.getForecast(API_KEY, latLong, FORECAST_DAYS);
        weatherObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Weather>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Weather weather) {
                        weatherCallback.onGetWeatherSuccess(weather);
                    }

                    @Override
                    public void onError(Throwable e) {
                        weatherCallback.onGetWeatherError(e);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public interface WeatherCallback {
        void onGetWeatherSuccess(Weather weather);

        void onGetWeatherError(Throwable t);
    }

}
