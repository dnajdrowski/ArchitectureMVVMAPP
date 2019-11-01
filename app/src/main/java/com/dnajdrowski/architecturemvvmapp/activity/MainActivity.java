package com.dnajdrowski.architecturemvvmapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.dnajdrowski.architecturemvvmapp.R;
import com.dnajdrowski.architecturemvvmapp.adapter.CarAdapter;
import com.dnajdrowski.architecturemvvmapp.model.Car;
import com.dnajdrowski.architecturemvvmapp.viemodel.CarViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        carAdapter = new CarAdapter(this);
        recyclerView.setAdapter(carAdapter);

        carViewModel = ViewModelProviders.of(this).get(CarViewModel.class);
        carViewModel.getAllCars().observe(this, cars -> carAdapter.submitList(cars));

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                carViewModel.delete(carAdapter.getCarAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);
    }

    @OnClick(R.id.add_car_button)
    public void addNewCarActivity() {
        Intent intent = new Intent(MainActivity.this, AddEditCarActivity.class);
        startActivityForResult(intent, ADD_CAR_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_CAR_REQUEST && resultCode == RESULT_OK) {
            String brand = data.getStringExtra(AddEditCarActivity.EXTRA_BRAND);
            String model = data.getStringExtra(AddEditCarActivity.EXTRA_MODEL);
            String mileage = data.getStringExtra(AddEditCarActivity.EXTRA_MILEAGE);
            String registrationNumber = data.getStringExtra(AddEditCarActivity.EXTRA_REGISTRATION);

            Car car = new Car(registrationNumber, Integer.valueOf(mileage), brand, model);
            carViewModel.insert(car);

            Toast.makeText(this, "Car saved", Toast.LENGTH_LONG).show();
        } else if (requestCode == EDIT_CAR_REQUEST && resultCode == RESULT_OK) {
            int carId = data.getIntExtra(AddEditCarActivity.EXTRA_ID, -1);

            if(carId == -1) {
                Toast.makeText(this, "Note can't be updated", Toast.LENGTH_LONG).show();
            }
            String brand = data.getStringExtra(AddEditCarActivity.EXTRA_BRAND);
            String model = data.getStringExtra(AddEditCarActivity.EXTRA_MODEL);
            String mileage = data.getStringExtra(AddEditCarActivity.EXTRA_MILEAGE);
            String registrationNumber = data.getStringExtra(AddEditCarActivity.EXTRA_REGISTRATION);

            Car car = new Car(registrationNumber, Integer.valueOf(mileage), brand, model);
            car.setId(carId);

            carViewModel.update(car);
            Toast.makeText(this, "Car updated", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this, "Car not saved", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete_all_cars) {
            carViewModel.deleteAllCars();
            Toast.makeText(this, "All cars deleted", Toast.LENGTH_LONG).show();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private CarViewModel carViewModel;
    private CarAdapter carAdapter;
    public static final int ADD_CAR_REQUEST = 1;
    public static final int EDIT_CAR_REQUEST = 2;
}
