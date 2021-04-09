package com.example.calc.model;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.concurrent.atomic.AtomicBoolean;

public class SimpleCalculator {

    protected BigDecimal operand1 = new BigDecimal(0);
    protected BigDecimal operand2 = new BigDecimal(0);
    protected final AtomicBoolean addFlag = new AtomicBoolean();
    protected final AtomicBoolean subFlag = new AtomicBoolean();
    protected final AtomicBoolean mulFlag = new AtomicBoolean();
    protected final AtomicBoolean divFlag = new AtomicBoolean();
    protected final AtomicBoolean powFlag = new AtomicBoolean();
    protected final AtomicBoolean clearFlag = new AtomicBoolean();
    protected final TextView display;
    protected BigDecimal displayValue = new BigDecimal(0);
    protected final MathContext globalMathContext = MathContext.DECIMAL128;
    protected final int roundPrecision = 32;
    protected Context context;

    public SimpleCalculator(TextView display, Context context) {
        this(display);
        this.context = context;
    }

    public SimpleCalculator(TextView display) {
        this.display = display;
        reset();
    }

    public void restoreInstanceState(Bundle savedInstanceState) {
        operand1 = new BigDecimal(savedInstanceState.getString("operand1", "0"));
        operand2 = new BigDecimal(savedInstanceState.getString("operand2", "0"));
        displayValue = new BigDecimal(savedInstanceState.getString("displayValue", "0"));
        addFlag.set(savedInstanceState.getBoolean("addFlag", false));
        subFlag.set(savedInstanceState.getBoolean("subFlag", false));
        mulFlag.set(savedInstanceState.getBoolean("mulFlag", false));
        divFlag.set(savedInstanceState.getBoolean("divFlag", false));
        powFlag.set(savedInstanceState.getBoolean("powFlag", false));
        clearFlag.set(savedInstanceState.getBoolean("clearFlag", false));
        display.setText(savedInstanceState.getString("display"));
    }

    public void saveInstanceState(@NonNull Bundle outState) {
        outState.putString("operand1", operand1.toPlainString());
        outState.putString("operand2", operand2.toPlainString());
        outState.putString("displayValue", displayValue.toPlainString());
        outState.putBoolean("addFlag", addFlag.get());
        outState.putBoolean("subFlag", subFlag.get());
        outState.putBoolean("mulFlag", mulFlag.get());
        outState.putBoolean("divFlag", divFlag.get());
        outState.putBoolean("powFlag", powFlag.get());
        outState.putBoolean("clearFlag", clearFlag.get());
        outState.putString("display", display.getText().toString());
    }

    public void handleNumeral(int digit) {
        if (displayValue.compareTo(BigDecimal.ZERO) == 0 || clearFlag.compareAndSet(true, false)) {
            display.setText("");
        }
        display.append(Integer.toString(digit));
        displayValue = new BigDecimal(display.getText().toString());
    }

    public void handleBinaryOperation(Operator operator) {
        calculatePartialResult();
        if (!display.toString().isEmpty()) {
            operand1 = new BigDecimal(displayValue.toPlainString());
            clearFlag.set(true);
            switch (operator) {
                case ADD:
                    addFlag.set(true);
                    break;
                case SUB:
                    subFlag.set(true);
                    break;
                case MUL:
                    mulFlag.set(true);
                    break;
                case DIV:
                    divFlag.set(true);
                    break;
                case X_TO_Y:
                    powFlag.set(true);
                    break;
                default:
                    throw new UnsupportedOperationException("Operator " + operator.name() + " not allowed");
            }
        }
    }

    public void handleEquals() {
        operand2 = new BigDecimal(displayValue.toPlainString());

        if (addFlag.compareAndSet(true, false)) {
            displayValue = operand1.add(operand2);
            display.setText(formatResult(displayValue));
        } else if (subFlag.compareAndSet(true, false)) {
            displayValue = operand1.subtract(operand2);
            display.setText(formatResult(displayValue));
        } else if (mulFlag.compareAndSet(true, false)) {
            displayValue = operand1.multiply(operand2);
            display.setText(formatResult(displayValue));
        } else if (divFlag.compareAndSet(true, false)) {
            if (operand2.compareTo(BigDecimal.ZERO) == 0) {
                showToast("Division by 0 is not allowed");
                reset();
            } else {
                displayValue = operand1.divide(operand2, globalMathContext);
                display.setText(formatResult(displayValue));
            }
        } else if (powFlag.compareAndSet(true, false)) {
            displayValue = operand1.pow(operand2.intValue(), globalMathContext);
            display.setText(formatResult(displayValue));
        }
    }

    public void handleComma() {
        String text = display.getText().toString();
        if (!(text.contains(".") || text.isEmpty())) {
            display.append(".");
            displayValue = new BigDecimal(display.getText().toString());
        }
    }

    public void handleSign() {
        if (!clearFlag.get()) {
            displayValue = displayValue.negate();
            display.setText(formatResult(displayValue));
        }
    }

    public void handleClear() {
        if (!clearFlag.get()) {
            displayValue = new BigDecimal(0);
            display.setText(formatResult(displayValue));
        }
    }

    protected void calculatePartialResult() {
        if (isAnyBinaryOperatorSelected()) {
            handleEquals();
        }
    }

    public void reset() {
        operand1 = new BigDecimal(0);
        operand2 = new BigDecimal(0);
        addFlag.set(false);
        subFlag.set(false);
        mulFlag.set(false);
        divFlag.set(false);
        powFlag.set(false);
        clearFlag.set(false);
        displayValue = new BigDecimal(0);
        display.setText(formatResult(displayValue));
    }

    protected boolean isAnyBinaryOperatorSelected() {
        return addFlag.get() || subFlag.get() || mulFlag.get() || divFlag.get() || powFlag.get();
    }

    protected void showToast(String message) {
        if (context != null) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
    }

    protected String formatResult(BigDecimal number) {
        return number
                .round(new MathContext(32, RoundingMode.HALF_EVEN))
                .stripTrailingZeros()
                .toPlainString();
    }
}
