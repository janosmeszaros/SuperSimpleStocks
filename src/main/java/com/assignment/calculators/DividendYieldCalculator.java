package com.assignment.calculators;

import com.assignment.model.ExcecutionException;
import com.assignment.model.Stock;

import java.math.BigDecimal;

import static com.assignment.calculators.CalculatorHelper.ONE_HUNDRED;
import static com.assignment.calculators.CalculatorHelper.divide;

public class DividendYieldCalculator {

    public BigDecimal calculate(BigDecimal tickerPrice, Stock stock) {
        switch (stock.type) {
            case COMMON:
                return divide(stock.lastDividend, tickerPrice);
            case PREFERRED:
                BigDecimal dividend =
                        stock.fixedDividend.orElseThrow(() -> new ExcecutionException("Fixed dividend must be provided for preferred stock!"));
                BigDecimal value = divide(dividend, ONE_HUNDRED).multiply(stock.parValue);
                return divide(value, tickerPrice);
            default:
                throw new ExcecutionException(String.format("Unknown stock type: %s", stock.type));
        }
    }
}
