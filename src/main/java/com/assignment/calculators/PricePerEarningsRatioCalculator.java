package com.assignment.calculators;

import com.assignment.model.Stock;

import java.math.BigDecimal;

import static com.assignment.calculators.CalculatorHelper.divide;
import static org.apache.commons.lang3.Validate.notNull;

public class PricePerEarningsRatioCalculator {
    private final DividendYieldCalculator dividendYieldCalculator;

    public PricePerEarningsRatioCalculator(DividendYieldCalculator dividendYieldCalculator) {
        this.dividendYieldCalculator = notNull(dividendYieldCalculator, "dividendYieldCalculator must not be null!");
    }

    public BigDecimal calculate(BigDecimal tickerPrice, Stock stock) {
        BigDecimal dividend = dividendYieldCalculator.calculate(tickerPrice, stock);
        return divide(tickerPrice, dividend);
    }
}
