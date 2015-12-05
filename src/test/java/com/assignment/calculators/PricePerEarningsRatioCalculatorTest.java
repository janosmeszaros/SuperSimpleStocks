package com.assignment.calculators;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static com.assignment.utils.TestInputStocks.PREFERRED_INPUT_STOCK;
import static java.math.BigDecimal.TEN;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PricePerEarningsRatioCalculatorTest {
    private static final BigDecimal TICKER_PRICE = new BigDecimal("2.5");

    @Mock
    private DividendYieldCalculator dividendYieldCalculatorMock;

    private PricePerEarningsRatioCalculator calculator;

    @Before
    public void setUp() throws Exception {
        when(dividendYieldCalculatorMock.calculate(TICKER_PRICE, PREFERRED_INPUT_STOCK)).thenReturn(TEN);
        calculator = new PricePerEarningsRatioCalculator(dividendYieldCalculatorMock);
    }

    @Test(expected = NullPointerException.class)
    public void constructorMustThrowExceptionWhenNoDividendCalculatorIsPassedIn() throws Exception {
        new PricePerEarningsRatioCalculator(null);
    }

    @Test
    public void calculateMustUseTickerPriceAndDividendFromTheYieldCalculatorForTheCalculation() throws Exception {
        BigDecimal pPerERatio = calculator.calculate(TICKER_PRICE, PREFERRED_INPUT_STOCK);

        assertThat(pPerERatio, is(new BigDecimal("0.25")));
    }
}
