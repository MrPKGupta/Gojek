package com.example.gojek.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gojek.R;
import com.example.gojek.Utils;
import com.example.gojek.api.model.ForecastDay;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastHolder> {
    private Context context;
    private List<ForecastDay> forecastDays;

    public ForecastAdapter(List<ForecastDay> forecastDays, Context context) {
        this.forecastDays = forecastDays;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return forecastDays == null ? 0 : forecastDays.size();
    }

    @NonNull
    @Override
    public ForecastHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forecast,
                parent, false);
        return new ForecastHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastHolder holder, int position) {
        final ForecastDay forecastDay = forecastDays.get(position);
        holder.tvDay.setText(Utils.getDayFromDate(forecastDay.getDate()));
        holder.tvTemp.setText(context.getString(R.string.format_celsius,
                Math.round(forecastDay.getDay().getAvgTemp())));
    }

    class ForecastHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_day)
        TextView tvDay;
        @BindView(R.id.tv_temp)
        TextView tvTemp;

        ForecastHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
