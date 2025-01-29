package com.example.busscheduleapp;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    DBHelper dbHelper;
    Spinner routeSpinner;
    EditText busNameInput, busNumberInput, timeInput;
    Button addButton, updateButton, deleteButton, viewButton;

    String selectedRoute = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);

        routeSpinner = findViewById(R.id.routeSpinner);
        busNameInput = findViewById(R.id.busNameInput);
        busNumberInput = findViewById(R.id.busNumberInput);
        timeInput = findViewById(R.id.timeInput);
        addButton = findViewById(R.id.addButton);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);
        viewButton = findViewById(R.id.viewButton);

        routeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedRoute = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        addButton.setOnClickListener(v -> {
            String busName = busNameInput.getText().toString();
            String busNumber = busNumberInput.getText().toString();
            String time = timeInput.getText().toString();

            if (dbHelper.insertData(selectedRoute, busName, busNumber, time)) {
                Toast.makeText(this, "Schedule Added", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error Adding Schedule", Toast.LENGTH_SHORT).show();
            }
        });

        updateButton.setOnClickListener(v -> {
            String id = busNumberInput.getText().toString(); // ID stored in `busNumber` field
            String busName = busNameInput.getText().toString();
            String time = timeInput.getText().toString();

            if (dbHelper.updateData(id, selectedRoute, busName, id, time)) {
                Toast.makeText(this, "Schedule Updated", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error Updating Schedule", Toast.LENGTH_SHORT).show();
            }
        });

        deleteButton.setOnClickListener(v -> {
            String id = busNumberInput.getText().toString();
            if (dbHelper.deleteData(id)) {
                Toast.makeText(this, "Schedule Deleted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error Deleting Schedule", Toast.LENGTH_SHORT).show();
            }
        });

        viewButton.setOnClickListener(v -> {
            Cursor result = dbHelper.getAllData();
            if (result.getCount() == 0) {
                showMessage("Error", "No Data Found");
                return;
            }

            StringBuilder buffer = new StringBuilder();
            while (result.moveToNext()) {
                buffer.append("ID: ").append(result.getString(0)).append("\n");
                buffer.append("Route: ").append(result.getString(1)).append("\n");
                buffer.append("Bus Name: ").append(result.getString(2)).append("\n");
                buffer.append("Bus Number: ").append(result.getString(3)).append("\n");
                buffer.append("Time: ").append(result.getString(4)).append("\n\n");
            }

            showMessage("Bus Schedules", buffer.toString());
        });
    }

    private void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}

