package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editTextTemperature;
    private Spinner spinnerUnit;
    private Button buttonSubmit;
    private TextView textViewResult;

    private String selectedUnit = "Celsius (°C)"; // Default unit

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        editTextTemperature = findViewById(R.id.editTextTemperature);
        spinnerUnit = findViewById(R.id.spinnerUnit);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        textViewResult = findViewById(R.id.textViewResult);

        // Set up Spinner (if not already set via XML)
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.temperature_units, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUnit.setAdapter(adapter);

        // Handle Spinner selection
        spinnerUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedUnit = parent.getItemAtPosition(position).toString();
                // Optionally, clear previous results when unit changes
                textViewResult.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Default behavior if nothing is selected
                selectedUnit = "Celsius (°C)";
            }
        });

        // Handle Button click
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tempInput = editTextTemperature.getText().toString().trim();

                if (tempInput.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a temperature value.", Toast.LENGTH_SHORT).show();
                    return;
                }

                double temperature;

                try {
                    temperature = Double.parseDouble(tempInput);
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Invalid temperature value.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Process based on selected unit
                if (selectedUnit.equals("Celsius (°C)")) {
                    double fahrenheit = celsiusToFahrenheit(temperature);
                    textViewResult.setText(String.format("%.2f °C = %.2f °F", temperature, fahrenheit));
                } else if (selectedUnit.equals("Fahrenheit (°F)")) {
                    double celsius = fahrenheitToCelsius(temperature);
                    textViewResult.setText(String.format("%.2f °F = %.2f °C", temperature, celsius));
                } else {
                    textViewResult.setText("Unknown unit selected.");
                }
            }
        });
    }

    // Conversion methods
    private double celsiusToFahrenheit(double celsius) {
        return (celsius * 9/5) + 32;
    }

    private double fahrenheitToCelsius(double fahrenheit) {
        return (fahrenheit - 32) * 5/9;
    }
}
