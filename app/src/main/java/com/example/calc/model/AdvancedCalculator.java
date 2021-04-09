package com.example.calc.model;

import android.content.Context;
import android.widget.TextView;

import java.math.BigDecimal;

import ch.obermuhlner.math.big.BigDecimalMath;

public class AdvancedCalculator extends SimpleCalculator {

    public AdvancedCalculator(TextView display, Context context) {
        super(display, context);
    }

    public AdvancedCalculator(TextView display) {
        super(display);
    }

    public void handleUnaryOperation(Operator operator) {
        boolean isValid = true;
        BigDecimal number = new BigDecimal(displayValue.toPlainString());
        if (!clearFlag.get()) {
            switch (operator) {
                case SIN:
                    number = BigDecimalMath.sin(number, globalMathContext);
                    break;
                case COS:
                    number = BigDecimalMath.cos(number, globalMathContext);
                    break;
                case TAN:
                    number = BigDecimalMath.tan(number, globalMathContext);
                    break;
                case LN:
                    if (number.compareTo(BigDecimal.ZERO) <= 0) {
                        showToast("Log of a non-positive number is not allowed");
                        reset();
                        isValid = false;
                    } else {
                        number = BigDecimalMath.log(number, globalMathContext);
                    }
                    break;
                case LOG:
                    if (number.compareTo(BigDecimal.ZERO) <= 0) {
                        showToast("Log of a non-positive number is not allowed");
                        reset();
                        isValid = false;
                    } else {
                        number = BigDecimalMath.log10(number, globalMathContext);
                    }
                    break;
                case SQRT:
                    if (number.compareTo(BigDecimal.ZERO) < 0) {
                        showToast("Sqrt of a negative number is not allowed");
                        reset();
                        isValid = false;
                    } else {
                        number = BigDecimalMath.sqrt(number, globalMathContext);
                    }
                    break;
                case X_TO_2:
                    number = number.pow(2, globalMathContext);
                    break;
                default:
                    throw new UnsupportedOperationException("Operator " + operator + " not allowed");
            }
            if (isValid) {
                displayValue = number;
                display.setText(formatResult(displayValue));
            }
        }
    }
}
