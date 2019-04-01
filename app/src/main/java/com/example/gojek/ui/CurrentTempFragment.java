package com.example.gojek.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gojek.R;
import com.example.gojek.ui.model.CurrentTemp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CurrentTempFragment extends Fragment {
    private static final String ARG_CURRENT_TEMP = "current_temp";

    @BindView(R.id.tv_temp)
    TextView tvTemp;
    @BindView(R.id.tv_place)
    TextView tvPlace;

    private Unbinder unbinder;
    private CurrentTemp currentTemp;

    public CurrentTempFragment() {
        // Required empty public constructor
    }

    public static CurrentTempFragment getInstance(CurrentTemp currentTemp) {
        CurrentTempFragment currentTempFragment = new CurrentTempFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_CURRENT_TEMP, currentTemp);
        currentTempFragment.setArguments(bundle);
        return currentTempFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentTemp = getArguments().getParcelable(ARG_CURRENT_TEMP);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_current_temp, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        setWeatherDetails();

        return rootView;
    }

    private void setWeatherDetails() {
        tvPlace.setText(currentTemp.getPlace());
        tvTemp.setText(getString(R.string.format_degree, Math.round(currentTemp.getCurrentTemp())));
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }
}
