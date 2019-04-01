package com.example.gojek.ui;

import android.os.Bundle;

import com.example.gojek.GojekApplication;
import com.example.gojek.R;
import com.example.gojek.api.ApiRequestClient;
import com.example.gojek.api.model.Forecast;
import com.example.gojek.api.model.Weather;
import com.example.gojek.ui.model.CurrentTemp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity implements
        ApiRequestClient.WeatherCallback,
        ErrorFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addProgressFragment();
        getForecast();
    }

    private void getForecast() {
        GojekApplication.getsInstance().getApiRequestClient().getForecast(this);
    }

    private void addProgressFragment() {
        ProgressFragment progressFragment = ProgressFragment.getInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, progressFragment)
                .commit();
    }

    private void addCurrentTempFragment(CurrentTemp currentTemp) {
        CurrentTempFragment currentTempFragment = CurrentTempFragment.getInstance(currentTemp);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, currentTempFragment)
                .commit();
    }

    private void addErrorFragment() {
        ErrorFragment errorFragment = ErrorFragment.getInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, errorFragment)
                .commit();
    }

    private void addForecastFragment(Forecast forecast) {
        ForecastFragment forecastFragment = ForecastFragment.getInstance(forecast);
        forecastFragment.setCancelable(false);
        forecastFragment.show(getSupportFragmentManager(), null);
    }

    @Override
    public void onGetWeatherSuccess(Weather weather) {
        /*CurrentTemp currentTemp = new CurrentTemp(weather.getLocationDetails().getName(),
                weather.getCurrent().getTemp());
        addCurrentTempFragment(currentTemp);
        new Handler().postDelayed(() -> addForecastFragment(weather.getForecast()), 300);*/
        addErrorFragment();
    }

    @Override
    public void onGetWeatherError(Throwable t) {
        addErrorFragment();
    }

    @Override
    public void onRetry() {
        getForecast();
    }
}
