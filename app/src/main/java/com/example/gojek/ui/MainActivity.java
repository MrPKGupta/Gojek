package com.example.gojek.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.gojek.AppPermissionHelper;
import com.example.gojek.GojekApplication;
import com.example.gojek.R;
import com.example.gojek.api.ApiRequestClient;
import com.example.gojek.api.model.Forecast;
import com.example.gojek.api.model.Weather;
import com.example.gojek.di.component.ActivityComponent;
import com.example.gojek.di.component.AppComponent;
import com.example.gojek.di.component.DaggerActivityComponent;
import com.example.gojek.di.module.ActivityModule;
import com.example.gojek.ui.model.CurrentTemp;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.Task;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements
        ApiRequestClient.WeatherCallback,
        ErrorFragment.OnFragmentInteractionListener,
        MainContract.View {

    public static final int REQUEST_CHECK_SETTINGS = 1;
    public static final int REQUEST_CODE_LOCATION = 2;
    private static final String TAG_CURRENT_TEMP_FRAGMENT = "current_temp_fragment";
    private static final String DEFAULT_PLACE = "Hyderabad";
    private static final int DELAY_FORECAST_DIALOG = 300;
    private static final int LOCATION_UPDATE_INTERVAL = 120000;
    private static final int LOCATION_UPDATE_FASTEST_INTERVAL = 60000;
    @Inject
    MainContract.Presenter presenter;
    @Inject
    ApiRequestClient apiRequestClient;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private Location currentLocation;
    private LocationRequest locationRequest;
    private boolean isGpsFetched;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependency();
        setContentView(R.layout.activity_main);

        locationRequest = LocationRequest.create();
        locationRequest.setInterval(LOCATION_UPDATE_INTERVAL);
        locationRequest.setFastestInterval(LOCATION_UPDATE_FASTEST_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null) {
                    Location location = locationResult.getLastLocation();
                    if (location != null) {
                        currentLocation = location;
                        if (!isGpsFetched) {
                            getForecast(location);
                            isGpsFetched = true;
                        }
                    }
                }
            }
        };

        addProgressFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdates();
    }

    @Override
    protected void onPause() {
        super.onPause();
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    @Override
    protected void onStart() {
        super.onStart();
        createLocationRequest();
    }

    @Override
    protected void onStop() {
        super.onStop();
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    private void createLocationRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(this, locationSettingsResponse -> checkGpsPermission());
        task.addOnFailureListener(this, e -> {
            if (e instanceof ResolvableApiException) {
                try {
                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    resolvable.startResolutionForResult(this, REQUEST_CHECK_SETTINGS);
                } catch (IntentSender.SendIntentException sendEx) {
                    Timber.d("Error in requesting gps.");
                }
            }
        });
    }

    private void checkGpsPermission() {
        if (AppPermissionHelper.checkSelfPermissions(this,
                new String[]{AppPermissionHelper.ACCESS_COARSE_LOCATION})) {
            getLocationUpdate();
            startLocationUpdates();
        } else {
            requestPermissions();
        }
    }

    private void requestPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                AppPermissionHelper.ACCESS_FINE_LOCATION)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert);
            builder.setMessage(getString(R.string.permission_location_rationale))
                    .setPositiveButton(getString(android.R.string.ok), (dialog, id) ->
                            AppPermissionHelper.requestAllPermission(this,
                                    new String[]{AppPermissionHelper.ACCESS_COARSE_LOCATION,
                                            AppPermissionHelper.ACCESS_FINE_LOCATION},
                                    REQUEST_CODE_LOCATION)).create();
            builder.show();
        } else {
            AppPermissionHelper.requestAllPermission(this,
                    new String[]{AppPermissionHelper.ACCESS_FINE_LOCATION, AppPermissionHelper.ACCESS_COARSE_LOCATION},
                    REQUEST_CODE_LOCATION);
        }
    }

    @SuppressWarnings("MissingPermission")
    private void startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    @SuppressWarnings("MissingPermission")
    private void getLocationUpdate() {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    if (location != null) {
                        currentLocation = location;
                        if (!isGpsFetched) {
                            getForecast(location);
                            isGpsFetched = true;
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    Timber.d("Error in fetching location.");
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION) {
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onPermission(true);
                } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    onPermission(false);
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == RESULT_OK) {
                onGpsEnabled(true);
            } else {
                onGpsEnabled(false);
            }
        }
    }

    public void onGpsEnabled(boolean granted) {
        if (granted) {
            checkGpsPermission();
        } else {
            getForecast(currentLocation);
            Toast.makeText(this, R.string.default_location_message, Toast.LENGTH_SHORT).show();
        }
    }

    public void onPermission(boolean isPermitted) {
        if (isPermitted) {
            startLocationUpdates();
            getLocationUpdate();
        } else {
            getForecast(currentLocation);
            Toast.makeText(this, R.string.default_location_message, Toast.LENGTH_SHORT).show();
        }
    }

    private void getForecast(Location currentLocation) {
        String query;
        if (currentLocation != null) {
            query = currentLocation.getLatitude() + "," + currentLocation.getLongitude();
        } else {
            query = DEFAULT_PLACE;
        }
        apiRequestClient.getForecast(this, query);
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
                .replace(R.id.container, currentTempFragment, TAG_CURRENT_TEMP_FRAGMENT)
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
        forecastFragment.show(getSupportFragmentManager(), null);
    }

    @Override
    public void onGetWeatherSuccess(Weather weather) {
        CurrentTemp currentTemp = new CurrentTemp(weather.getLocationDetails().getName(),
                weather.getCurrent().getTemp());
        addCurrentTempFragment(currentTemp);
        new Handler().postDelayed(() -> addForecastFragment(weather.getForecast()), DELAY_FORECAST_DIALOG);
    }

    @Override
    public void onGetWeatherError(Throwable t) {
        addErrorFragment();
    }

    @Override
    public void onRetry() {
        addProgressFragment();
        getForecast(currentLocation);
    }

    private void injectDependency() {
        AppComponent appComponent = ((GojekApplication) getApplication()).getAppComponent();
        ActivityComponent activityComponent = DaggerActivityComponent.builder()
                .appComponent(appComponent)
                .activityModule(new ActivityModule(this, this))
                .build();
        activityComponent.inject(this);

    }
}
