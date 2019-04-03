package com.example.gojek.di.component;

import com.example.gojek.di.ActivityScoped;
import com.example.gojek.di.module.ActivityModule;
import com.example.gojek.ui.MainActivity;

import dagger.Component;

@ActivityScoped
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(MainActivity mainActivity);
}
