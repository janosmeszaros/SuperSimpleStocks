package com.assignment.calculators;

import com.assignment.model.ExcecutionException;
import com.assignment.model.Stock;

import java.math.BigDecimal;

import static com.assignment.calculators.CalculatorConstants.*;

public class DividendYieldCalculator {

    public BigDecimal calculate(BigDecimal tickerPrice, Stock stock) {
        switch (stock.type) {
            case COMMON:
                return stock.lastDividend
                        .divide(tickerPrice, SCALE, ROUNDING_MODE)
                        .stripTrailingZeros();
            case PREFERRED:
                BigDecimal dividend =
                        stock.fixedDividend.orElseThrow(() -> new ExcecutionException("Fixed dividend must be provided for preferred stock!"));
                return dividend.divide(ONE_HUNDRED, SCALE, ROUNDING_MODE)
                        .multiply(stock.parValue)
                        .divide(tickerPrice, SCALE, ROUNDING_MODE)
                        .stripTrailingZeros();
            default:
                throw new ExcecutionException(String.format("Unknown stock type: %s", stock.type));
        }
    }
}
