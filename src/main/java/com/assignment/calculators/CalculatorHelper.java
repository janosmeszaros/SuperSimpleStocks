package com.assignment.calculators;

import org.apache.commons.lang3.Validate;

import java.math.BigDecimal;

import static java.math.BigDecimal.ROUND_HALF_UP;
import static java.math.BigDecimal.ZERO;

public final class CalculatorHelper {
    public static final int ROUNDING_MODE = ROUND_HALF_UP;
    public static final BigDecimal ONE_HUNDRED = new BigDecimal("100");
    public static final int SCALE = 4;


    public static BigDecimal divide(BigDecimal dividend, BigDecimal divisor) {
        Validate.isTrue(divisor.compareTo(ZERO) > 0, "Divisor must not be zero!");
        return dividend.divide(divisor, SCALE, ROUNDING_MODE).stripTrailingZeros();
    }

    private CalculatorHelper() {
    }
}
