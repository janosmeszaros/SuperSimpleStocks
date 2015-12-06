package com.assignment.calculators;

import com.assignment.booking.StockRepository;
import com.assignment.model.Stock;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

import static com.assignment.calculators.CalculatorHelper.ROUNDING_MODE;
import static com.assignment.calculators.CalculatorHelper.SCALE;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static org.apache.commons.lang3.Validate.notNull;

public class IndexCalculator {
    private final StockRepository stockRepository;
    private final StockPriceCalculator priceCalculator;

    public IndexCalculator(StockRepository stockRepository,
                           StockPriceCalculator stockPriceCalculator) {
        this.stockRepository = notNull(stockRepository, "stockRepository must not be null!");
        this.priceCalculator = notNull(stockPriceCalculator, "stockPriceCalculator must not be null!");
    }

    public BigDecimal calculateIndex() {
        List<Stock> stocks = stockRepository.getAllStocks();
        if (stocks.isEmpty()) {
            return ZERO;
        } else {
            double value = stocks.stream()
                    .map(priceCalculator::calculate)
                    .filter(price -> price.compareTo(ZERO) > 0)
                    .reduce(ONE, BigDecimal::multiply)
                    .doubleValue();
            // Not the best solution but BigDecimal's pow function only accepts integers so cannot be used for root calculation
            return new BigDecimal(Math.pow(value, 1.0 / stocks.size()), MathContext.DECIMAL128)
                    .setScale(SCALE, ROUNDING_MODE)
                    .stripTrailingZeros();
        }
    }
}
