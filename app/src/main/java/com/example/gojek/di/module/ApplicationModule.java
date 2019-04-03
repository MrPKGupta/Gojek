package com.example.gojek.di.module;

import android.app.Application;
import android.content.Context;

import com.example.gojek.api.ApiRequestClient;
import com.example.gojek.api.ApiService;
import com.example.gojek.api.ServiceGenerator;
import com.example.gojek.di.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    ApiService provideApiService() {
        return ServiceGenerator.createService(ApiService.class);
    }

    @Provides
    @Singleton
    ApiRequestClient provideApiRequestClient(ApiService apiService) {
        return new ApiRequestClient(apiService);
    }
}
