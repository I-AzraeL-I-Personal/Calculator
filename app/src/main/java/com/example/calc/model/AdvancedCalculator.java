package com.example.calc.model;

import android.content.Context;
import android.widget.TextView;

public class AdvancedCalculator extends SimpleCalculator {

    public AdvancedCalculator(TextView display, Context context) {
        super(display, context);
    }

    public AdvancedCalculator(TextView display) {
        super(display);
    }

    public void handleUnaryOperation(Operator operator) {
        boolean isValid = true;
        String text = display.getText().toString();
        double number = Double.parseDouble(text);
        if (!clearFlag.get()) {
            switch (operator) {
                case SIN:
                    number = Math.sin(number);
                    break;
                case COS:
                    number = Math.cos(number);
                    break;
                case TAN:
                    number = Math.tan(number);
                    break;
                case LN:
                    if (number <= 0) {
                        showToast("Log of a non-positive number is not allowed");
                        reset();
                        isValid = false;
                    } else {
                        number = Math.log(number);
                    }
                    break;
                case LOG:
                    if (number <= 0) {
                        showToast("Log of a non-positive number is not allowed");
                        reset();
                        isValid = false;
                    } else {
                        number = Math.log10(number);
                    }
                    break;
                case SQRT:
                    if (number < 0) {
                        showToast("Sqrt of a negative number is not allowed");
                        reset();
                        isValid = false;
                    } else {
                        number = Math.sqrt(number);
                    }
                    break;
                case X_TO_2:
                    number = Math.pow(number, 2);
                    break;
                default:
                    throw new UnsupportedOperationException("Operator " + operator + " not allowed");
            }
            if (isValid) {
                display.setText(formatResult(number));
            }
        }
    }
}
