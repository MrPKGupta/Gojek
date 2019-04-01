package com.example.gojek.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gojek.R;
import com.example.gojek.api.model.Forecast;
import com.example.gojek.api.model.ForecastDay;
import com.example.gojek.ui.adapter.ForecastAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ForecastFragment extends BottomSheetDialogFragment {
    private static final String ARG_FORECAST = "forecast";

    @BindView(R.id.rv_forecast)
    RecyclerView rvForecast;

    private Unbinder unbinder;
    private ForecastAdapter forecastAdapter;
    private List<ForecastDay> forecastDays;

    public static ForecastFragment getInstance(Forecast forecast) {
        ForecastFragment forecastFragment = new ForecastFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_FORECAST, forecast);
        forecastFragment.setArguments(bundle);
        return forecastFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Forecast forecast = getArguments().getParcelable(ARG_FORECAST);
            forecastDays = forecast.getForecastDays();
            removeForecastForToday();
        }
    }

    private void removeForecastForToday() {
        if (forecastDays.size() > 1) {
            forecastDays.remove(0);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_forecast, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        if (forecastAdapter == null) {
            forecastAdapter = new ForecastAdapter(forecastDays, getActivity());
        }

        rvForecast.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvForecast.setAdapter(forecastAdapter);
        rvForecast.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        return rootView;
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }
}
