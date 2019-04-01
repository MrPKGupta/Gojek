package com.example.gojek;

import android.app.Application;

import com.example.gojek.api.ApiRequestClient;
import com.example.gojek.api.ApiService;
import com.example.gojek.api.ServiceGenerator;

import timber.log.Timber;

public class GojekApplication extends Application {
    private static GojekApplication sInstance;
    private ApiRequestClient apiRequestClient;

    public static GojekApplication getsInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    public ApiRequestClient getApiRequestClient() {
        if (apiRequestClient == null) {
            ApiService apiService = ServiceGenerator.createService(ApiService.class);
            apiRequestClient = new ApiRequestClient(apiService);
        }
        return apiRequestClient;
    }
}
