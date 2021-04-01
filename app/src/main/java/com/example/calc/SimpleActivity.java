package com.example.calc;

import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.calc.model.Operator;
import com.example.calc.model.SimpleCalculator;

public class SimpleActivity extends AppCompatActivity {

    private SimpleCalculator calculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);

        calculator = new SimpleCalculator(findViewById(R.id.fieldDisplay), this);

        findViewById(R.id.btnCalc1).setOnClickListener(v -> calculator.handleNumeral(1));
        findViewById(R.id.btnCalc2).setOnClickListener(v -> calculator.handleNumeral(2));
        findViewById(R.id.btnCalc3).setOnClickListener(v -> calculator.handleNumeral(3));
        findViewById(R.id.btnCalc4).setOnClickListener(v -> calculator.handleNumeral(4));
        findViewById(R.id.btnCalc5).setOnClickListener(v -> calculator.handleNumeral(5));
        findViewById(R.id.btnCalc6).setOnClickListener(v -> calculator.handleNumeral(6));
        findViewById(R.id.btnCalc7).setOnClickListener(v -> calculator.handleNumeral(7));
        findViewById(R.id.btnCalc8).setOnClickListener(v -> calculator.handleNumeral(8));
        findViewById(R.id.btnCalc9).setOnClickListener(v -> calculator.handleNumeral(9));
        findViewById(R.id.btnCalc0).setOnClickListener(v -> calculator.handleNumeral(0));
        findViewById(R.id.btnCalcOpAdd).setOnClickListener(v -> calculator.handleBinaryOperation(Operator.ADD));
        findViewById(R.id.btnCalcOpSub).setOnClickListener(v -> calculator.handleBinaryOperation(Operator.SUB));
        findViewById(R.id.btnCalcOpMul).setOnClickListener(v -> calculator.handleBinaryOperation(Operator.MUL));
        findViewById(R.id.btnCalcOpDiv).setOnClickListener(v -> calculator.handleBinaryOperation(Operator.DIV));
        findViewById(R.id.btnCalcOpEquals).setOnClickListener(v -> calculator.handleEquals());
        findViewById(R.id.btnCalcOpSign).setOnClickListener(v -> calculator.handleSign());
        findViewById(R.id.btnCalcOpComma).setOnClickListener(v -> calculator.handleComma());

        findViewById(R.id.btnCalcOpAC).setOnClickListener(v -> calculator.reset());
        Button buttonClear = findViewById(R.id.btnCalcOpC);
        buttonClear.setOnClickListener(v -> calculator.handleClear());
        buttonClear.setOnLongClickListener(v -> {calculator.reset(); return true;});
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        calculator.restoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        calculator.saveInstanceState(outState);
    }
}