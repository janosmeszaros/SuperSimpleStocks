package com.assignment.calculators;

import com.assignment.model.ExcecutionException;
import com.assignment.model.Stock;
import com.assignment.model.StockType;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static com.assignment.model.StockType.COMMON;
import static com.assignment.model.StockType.PREFERRED;
import static java.math.BigDecimal.ONE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class DividendYieldCalculatorTest {
    public static final BigDecimal TICKER_PRICE = new BigDecimal("2");
    private static final Stock COMMON_INPUT_STOCK = createTestInput(COMMON);
    private static final Stock PREFERRED_INPUT_STOCK = createTestInput(PREFERRED, new BigDecimal("4.5"));
    private static final Stock PREFERRED_STOCK_WITHOUT_FIXED_DIVIDEND = createTestInput(PREFERRED);
    private DividendYieldCalculator calculator;

    @Before
    public void setUp() throws Exception {
        this.calculator = new DividendYieldCalculator();
    }

    @Test
    public void calculateMustDivideLastDividendWithTickerPrice() throws Exception {
        BigDecimal yield = calculator.calculate(ONE, COMMON_INPUT_STOCK);

        assertThat(yield, is(new BigDecimal("8.2")));
    }

    @Test
    public void calculateMustDivideLastDividendWithOtherTickerPriceValue() throws Exception {
        BigDecimal yield = calculator.calculate(TICKER_PRICE, COMMON_INPUT_STOCK);

        assertThat(yield, is(new BigDecimal("4.1")));
    }

    @Test
    public void calculateMustMultipleFixedDividendWithParValueAndDivideWithTickerPriceValueForPreferred() throws Exception {
        BigDecimal yield = calculator.calculate(TICKER_PRICE, PREFERRED_INPUT_STOCK);

        assertThat(yield, is(new BigDecimal("2.25")));
    }

    @Test(expected = ExcecutionException.class)
    public void calculateMustThrowExceptionWhenNoFixedDividendIsPresentForPreferredStock() throws Exception {
        calculator.calculate(TICKER_PRICE, PREFERRED_STOCK_WITHOUT_FIXED_DIVIDEND);
    }

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
