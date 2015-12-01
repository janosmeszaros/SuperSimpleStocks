package com.assignment.calculators;

import com.assignment.model.Stock;
import com.assignment.model.StockType;

import java.math.BigDecimal;

import static com.assignment.model.StockType.COMMON;
import static com.assignment.model.StockType.PREFERRED;

public class TestInputStocks {
    public static final Stock COMMON_INPUT_STOCK = createTestInput(COMMON);
    public static final Stock PREFERRED_INPUT_STOCK = createTestInput(PREFERRED, new BigDecimal("4.5"));
    public static final Stock PREFERRED_STOCK_WITHOUT_FIXED_DIVIDEND = createTestInput(PREFERRED);

    private static Stock createTestInput(StockType type) {
        return createTestInput(type, null);
    }

    private static Stock createTestInput(StockType type, BigDecimal fixedDividend) {
        return Stock.builder()
                .withSymbol("test")
                .withType(type)
                .withLastDividend(new BigDecimal("8.2"))
                .withFixedDividend(fixedDividend)
                .withParValue(new BigDecimal("100"))
                .build();
    }
}
