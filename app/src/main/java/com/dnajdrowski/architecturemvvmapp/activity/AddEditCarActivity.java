package com.dnajdrowski.architecturemvvmapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.dnajdrowski.architecturemvvmapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AddEditCarActivity extends AppCompatActivity {

    @BindView(R.id.brand_edit_text)
    EditText brandEditText;
    @BindView(R.id.model_edit_text)
    EditText modelEditText;
    @BindView(R.id.mileage_edit_text)
    EditText mileageEditText;
    @BindView(R.id.registration_number_edit_text)
    EditText registrationNumberEditText;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_car);
        ButterKnife.bind(this);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Car");
            brandEditText.setText(intent.getStringExtra(EXTRA_BRAND));
            modelEditText.setText(intent.getStringExtra(EXTRA_MODEL));
            registrationNumberEditText.setText(intent.getStringExtra(EXTRA_REGISTRATION));
            mileageEditText.setText(String.valueOf(intent.getIntExtra(EXTRA_MILEAGE, 0)));
        } else {
            setTitle("Add Car");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_car_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save_car) {
            saveCar();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void saveCar() {
        String brand = brandEditText.getText().toString().trim();
        String registrationNumber = registrationNumberEditText.getText().toString().trim();
        String model = modelEditText.getText().toString().trim();
        String mileage = mileageEditText.getText().toString().trim();
        if (brand.isEmpty() || registrationNumber.isEmpty() || mileage.isEmpty() || model.isEmpty()) {
            Toast.makeText(this, "Please insert valid data", Toast.LENGTH_LONG).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_BRAND, brand);
        data.putExtra(EXTRA_MODEL, model);
        data.putExtra(EXTRA_MILEAGE, mileage);
        data.putExtra(EXTRA_REGISTRATION, registrationNumber);

        int carId = getIntent().getIntExtra(EXTRA_ID, -1);
        if( carId != -1) {
            data.putExtra(EXTRA_ID, carId);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    public static final String EXTRA_BRAND = "EXTRA_BRAND";
    public static final String EXTRA_MODEL = "EXTRA_MODEL";
    public static final String EXTRA_MILEAGE = "EXTRA_MILEAGE";
    public static final String EXTRA_REGISTRATION = "EXTRA_REGISTRATION";
    public static final String EXTRA_ID = "EXTRA_ID";


}
