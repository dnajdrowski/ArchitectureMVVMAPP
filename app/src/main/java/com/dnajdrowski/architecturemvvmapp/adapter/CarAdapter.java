package com.dnajdrowski.architecturemvvmapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.dnajdrowski.architecturemvvmapp.R;
import com.dnajdrowski.architecturemvvmapp.activity.AddEditCarActivity;
import com.dnajdrowski.architecturemvvmapp.activity.MainActivity;
import com.dnajdrowski.architecturemvvmapp.model.Car;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CarAdapter extends ListAdapter<Car, CarAdapter.CarHolder> {
    private Context context;

    public CarAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    private static final DiffUtil.ItemCallback<Car> DIFF_CALLBACK = new DiffUtil.ItemCallback<Car>() {
        @Override
        public boolean areItemsTheSame(@NonNull Car oldItem, @NonNull Car newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Car oldItem, @NonNull Car newItem) {
            return oldItem.getBrand().equals(newItem.getBrand()) &&
                    oldItem.getMileage() == newItem.getMileage() &&
                    oldItem.getModel().equals(newItem.getModel()) &&
                    oldItem.getRegistrationNumber().equals(newItem.getRegistrationNumber());
        }
    };


    @NonNull
    @Override
    public CarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_item, parent, false);
        return new CarHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarHolder holder, int position) {
        Car currentCar = getItem(position);

        holder.brandTextView.setText(currentCar.getBrand());
        holder.modelTextView.setText(currentCar.getModel());
        holder.mileageTextView.setText(String.format("%s %s",
                holder.itemView.getResources().getString(R.string.mileage),
                currentCar.getMileage()));
        holder.registrationTextView.setText(currentCar.getRegistrationNumber());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, AddEditCarActivity.class);
            intent.putExtra(AddEditCarActivity.EXTRA_ID, currentCar.getId());
            intent.putExtra(AddEditCarActivity.EXTRA_BRAND, currentCar.getBrand());
            intent.putExtra(AddEditCarActivity.EXTRA_MODEL, currentCar.getModel());
            intent.putExtra(AddEditCarActivity.EXTRA_MILEAGE, currentCar.getMileage());
            intent.putExtra(AddEditCarActivity.EXTRA_REGISTRATION, currentCar.getRegistrationNumber());
            ((Activity) context).startActivityForResult(intent, MainActivity.EDIT_CAR_REQUEST);
        });

    }

    public Car getCarAt(int position) {
        return getItem(position);
    }

    class CarHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.brand_text_view)
        TextView brandTextView;
        @BindView(R.id.model_text_view)
        TextView modelTextView;
        @BindView(R.id.mileage_text_view)
        TextView mileageTextView;
        @BindView(R.id.registration_number_text_view)
        TextView registrationTextView;

        CarHolder(@NonNull View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
