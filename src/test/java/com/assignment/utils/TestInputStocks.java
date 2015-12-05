package com.assignment.utils;

import com.assignment.model.Stock;
import com.assignment.model.StockType;

import java.math.BigDecimal;

import static com.assignment.model.StockType.COMMON;
import static com.assignment.model.StockType.PREFERRED;

public class TestInputStocks {
    public static final Stock COMMON_INPUT_STOCK = createTestInput(COMMON);
    public static final Stock PREFERRED_INPUT_STOCK = createTestInput(PREFERRED, new BigDecimal("4.5"), "test");
    public static final Stock PREFERRED_STOCK_WITHOUT_FIXED_DIVIDEND = createTestInput(PREFERRED);

    public static Stock createTestInput(String name) {
        return createTestInput(COMMON, null, name);
    }

    private static Stock createTestInput(StockType type) {
        return createTestInput(type, null, "test");
    }

    private static Stock createTestInput(StockType type, BigDecimal fixedDividend, String name) {
        return Stock.builder()
                .withSymbol(name)
                .withType(type)
                .withLastDividend(new BigDecimal("8.2"))
                .withFixedDividend(fixedDividend)
                .withParValue(new BigDecimal("100"))
                .build();
    }
}
