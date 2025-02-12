package com.example.latihan1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private String currentInput = "";
    private String operator = "";
    private double firstNumber = 0;
    private boolean isNewOperation = true; // Untuk reset input saat operator ditekan

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView4);
        setButtonListeners();
    }

    private void setButtonListeners() {
        int[] numberButtons = {
                R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
                R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9
        };

        int[] operatorButtons = { R.id.buttonTambah, R.id.buttonKurang, R.id.buttonKali, R.id.buttonBagi };

        // Listener untuk tombol angka
        for (int id : numberButtons) {
            Button button = findViewById(id);
            button.setOnClickListener(v -> {
                if (isNewOperation) {
                    currentInput = ""; // Reset jika operator baru ditekan
                    isNewOperation = false;
                }

                // Mencegah angka diawali "00" atau "01" dan seterusnya
                if (currentInput.equals("0")) {
                    currentInput = button.getText().toString();
                } else {
                    currentInput += button.getText().toString();
                }

                updateTextView();
            });
        }

        // Listener untuk tombol operator
        for (int id : operatorButtons) {
            Button button = findViewById(id);
            button.setOnClickListener(v -> {
                if (!currentInput.isEmpty()) {
                    firstNumber = Double.parseDouble(currentInput);
                    operator = button.getText().toString();
                    isNewOperation = true;
                }
            });
        }

        // Listener untuk tombol titik "."
        findViewById(R.id.buttonTitik).setOnClickListener(v -> {
            if (isNewOperation) {
                currentInput = "0."; // Jika operator baru ditekan, mulai dengan "0."
                isNewOperation = false;
            } else if (!currentInput.contains(".")) {
                currentInput += ".";
            }
            updateTextView();
        });

        // Listener untuk tombol "="
        findViewById(R.id.buttonSamaDengan).setOnClickListener(v -> calculateResult());

        // Listener untuk tombol hapus satu karakter (⌫)
        findViewById(R.id.buttonDel).setOnClickListener(v -> {
            if (!currentInput.isEmpty()) {
                currentInput = currentInput.substring(0, currentInput.length() - 1);
                updateTextView();
            }
        });

        // Listener untuk tombol AC (reset)
        findViewById(R.id.buttonAC).setOnClickListener(v -> resetCalculator());

        // Listener untuk tombol "+/-"
        findViewById(R.id.buttonPlusMinus).setOnClickListener(v -> {
            if (!currentInput.isEmpty() && !currentInput.equals("0")) {
                double value = Double.parseDouble(currentInput) * -1;
                currentInput = formatNumber(value);
                updateTextView();
            }
        });

        // Listener untuk tombol "%"
        findViewById(R.id.buttonPersen).setOnClickListener(v -> {
            if (!currentInput.isEmpty()) {
                double value = Double.parseDouble(currentInput) / 100;
                currentInput = formatNumber(value);
                updateTextView();
            }
        });
    }

    private void calculateResult() {
        if (!currentInput.isEmpty() && !operator.isEmpty()) {
            double secondNumber = Double.parseDouble(currentInput);
            double result = 0;

            switch (operator) {
                case "+":
                    result = firstNumber + secondNumber;
                    break;
                case "-":
                    result = firstNumber - secondNumber;
                    break;
                case "×":
                case "x":
                case "*":
                    result = firstNumber * secondNumber;
                    break;
                case "÷":
                case "/":
                    if (secondNumber != 0) {
                        result = firstNumber / secondNumber;
                    } else {
                        textView.setText("Error");
                        return;
                    }
                    break;
            }

            currentInput = formatNumber(result);
            operator = "";
            isNewOperation = true;
            updateTextView();
        }
    }

    private void resetCalculator() {
        currentInput = "";
        firstNumber = 0;
        operator = "";
        isNewOperation = true;
        updateTextView();
    }

    private void updateTextView() {
        textView.setText(currentInput.isEmpty() ? "0" : currentInput);
    }

    private String formatNumber(double number) {
        // Jika hasilnya bilangan bulat, tampilkan tanpa ".0"
        if (number == (int) number) {
            return String.valueOf((int) number);
        } else {
            return String.valueOf(number);
        }
    }
}
