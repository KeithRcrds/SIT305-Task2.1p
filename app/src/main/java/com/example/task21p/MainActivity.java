package com.example.task21p;

import android.os.Bundle;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Spinner spinnerCategory, spinnerFrom, spinnerTo;
    EditText editTextValue;
    Button buttonConvert;
    TextView textView5;

    String[] categories = {"Currency", "Fuel", "Temperature"};
    String[] currencyUnits = {"USD", "AUD", "EUR", "JPY", "GBP"};
    String[] fuelUnits = {"MPG", "KM/L", "Gallon (US)", "Liter", "Nautical Mile", "Kilometer"};
    String[] temperatureUnits = {"Celsius", "Fahrenheit", "Kelvin"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        spinnerCategory = findViewById(R.id.spinnerCategory);
        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);
        editTextValue = findViewById(R.id.editTextValue);
        buttonConvert = findViewById(R.id.buttonConvert);
        textView5 = findViewById(R.id.textView5);

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                categories
        );

        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);

        spinnerCategory.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {

                String selectedCategory = categories[position];
                String[] units;

                if (selectedCategory.equals("Currency")) {
                    units = currencyUnits;
                }
                else if (selectedCategory.equals("Fuel")) {
                    units = fuelUnits;
                }
                else {
                    units = temperatureUnits;
                }

                ArrayAdapter<String> unitAdapter = new ArrayAdapter<>(
                        MainActivity.this,
                        android.R.layout.simple_spinner_item,
                        units
                );

                unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spinnerFrom.setAdapter(unitAdapter);
                spinnerTo.setAdapter(unitAdapter);
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {

            }
        });

        buttonConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String valueText = editTextValue.getText().toString();

                if (valueText.isEmpty()) {
                    textView5.setText("Enter a value");
                    return;
                }

                double value = Double.parseDouble(valueText);

                String selectedCategory = spinnerCategory.getSelectedItem().toString();
                String fromUnit = spinnerFrom.getSelectedItem().toString();
                String toUnit = spinnerTo.getSelectedItem().toString();

                double result = value;


                if (selectedCategory.equals("Currency")) {

                    double usdValue = 0;

                    if (fromUnit.equals("USD")) {
                        usdValue = value;
                    } else if (fromUnit.equals("AUD")) {
                        usdValue = value / 1.55;
                    } else if (fromUnit.equals("EUR")) {
                        usdValue = value / 0.92;
                    } else if (fromUnit.equals("JPY")) {
                        usdValue = value / 148.50;
                    } else if (fromUnit.equals("GBP")) {
                        usdValue = value / 0.78;
                    }

                    if (toUnit.equals("USD")) {
                        result = usdValue;
                    } else if (toUnit.equals("AUD")) {
                        result = usdValue * 1.55;
                    } else if (toUnit.equals("EUR")) {
                        result = usdValue * 0.92;
                    } else if (toUnit.equals("JPY")) {
                        result = usdValue * 148.50;
                    } else if (toUnit.equals("GBP")) {
                        result = usdValue * 0.78;
                    }

                } else if (selectedCategory.equals("Fuel")) {

                    if (fromUnit.equals("MPG") && toUnit.equals("KM/L")) {
                        result = value * 0.425;
                    } else if (fromUnit.equals("KM/L") && toUnit.equals("MPG")) {
                        result = value / 0.425;
                    } else if (fromUnit.equals("Gallon (US)") && toUnit.equals("Liter")) {
                        result = value * 3.785;
                    } else if (fromUnit.equals("Liter") && toUnit.equals("Gallon (US)")) {
                        result = value / 3.785;
                    } else if (fromUnit.equals("Nautical Mile") && toUnit.equals("Kilometer")) {
                        result = value * 1.852;
                    } else if (fromUnit.equals("Kilometer") && toUnit.equals("Nautical Mile")) {
                        result = value / 1.852;
                    } else if (fromUnit.equals(toUnit)) {
                        result = value;
                    } else {
                        textView5.setText("This conversion is not supported");
                        return;
                    }

                } else if (selectedCategory.equals("Temperature")) {

                    if (fromUnit.equals("Celsius") && toUnit.equals("Fahrenheit")) {
                        result = (value * 1.8) + 32;
                    } else if (fromUnit.equals("Fahrenheit") && toUnit.equals("Celsius")) {
                        result = (value - 32) / 1.8;
                    } else if (fromUnit.equals("Celsius") && toUnit.equals("Kelvin")) {
                        result = value + 273.15;
                    } else if (fromUnit.equals("Kelvin") && toUnit.equals("Celsius")) {
                        result = value - 273.15;
                    } else if (fromUnit.equals("Fahrenheit") && toUnit.equals("Kelvin")) {
                        result = ((value - 32) / 1.8) + 273.15;
                    } else if (fromUnit.equals("Kelvin") && toUnit.equals("Fahrenheit")) {
                        result = ((value - 273.15) * 1.8) + 32;
                    } else if (fromUnit.equals(toUnit)) {
                        result = value;
                    }
                }

                textView5.setText("Result: " + result);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}