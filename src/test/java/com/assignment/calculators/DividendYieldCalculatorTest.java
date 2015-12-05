package com.assignment.calculators;

import com.assignment.model.ExcecutionException;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static com.assignment.utils.TestInputStocks.*;
import static java.math.BigDecimal.ONE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class DividendYieldCalculatorTest {
    public static final BigDecimal TICKER_PRICE = new BigDecimal("2");
    private DividendYieldCalculator calculator;

    @Before
    public void setUp() {
        this.calculator = new DividendYieldCalculator();
    }

    @Test
    public void calculateMustDivideLastDividendWithTickerPrice() {
        BigDecimal yield = calculator.calculate(ONE, COMMON_INPUT_STOCK);

        assertThat(yield, is(new BigDecimal("8.2")));
    }

    @Test
    public void calculateMustDivideLastDividendWithOtherTickerPriceValue() {
        BigDecimal yield = calculator.calculate(TICKER_PRICE, COMMON_INPUT_STOCK);

        assertThat(yield, is(new BigDecimal("4.1")));
    }

    @Test
    public void calculateMustMultipleFixedDividendWithParValueAndDivideWithTickerPriceValueForPreferred() {
        BigDecimal yield = calculator.calculate(TICKER_PRICE, PREFERRED_INPUT_STOCK);

        assertThat(yield, is(new BigDecimal("2.25")));
    }

    @Test(expected = ExcecutionException.class)
    public void calculateMustThrowExceptionWhenNoFixedDividendIsPresentForPreferredStock() {
        calculator.calculate(TICKER_PRICE, PREFERRED_STOCK_WITHOUT_FIXED_DIVIDEND);
    }
}
