package com.example.gojek.api;

import com.example.gojek.api.model.Weather;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ApiRequestClient {
    private static final String API_KEY = "d1186fd768934580a97114746193003";
    private final ApiService apiService;

    public ApiRequestClient(ApiService apiService) {
        this.apiService = apiService;
    }

    public void getForecast(WeatherCallback weatherCallback) {
        Observable<Weather> weatherObservable = apiService.getForecast(API_KEY, "Bangalore", 5);
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
