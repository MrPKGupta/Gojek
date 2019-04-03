package com.example.gojek.di.component;

import android.app.Application;
import android.content.Context;

import com.example.gojek.GojekApplication;
import com.example.gojek.api.ApiRequestClient;
import com.example.gojek.di.ApplicationContext;
import com.example.gojek.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface AppComponent {
    void inject(GojekApplication application);

    @ApplicationContext
    Context getContext();

    Application getApplication();

    ApiRequestClient getApiRequestClient();
}
