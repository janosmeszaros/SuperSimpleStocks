package com.assignment.calculators;

import com.assignment.booking.TradingService;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static com.assignment.utils.TestInputStocks.COMMON_INPUT_STOCK;
import static com.assignment.utils.TestInputTrades.*;
import static java.math.BigDecimal.TEN;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StockPriceCalculatorTest {
    @Mock
    private TradingService tradingServiceMock;

    private StockPriceCalculator calculator;

    @Before
    public void setUp() {
        this.calculator = new StockPriceCalculator(tradingServiceMock);
    }

    @Test(expected = NullPointerException.class)
    public void constructorMustThrowExceptionWhenNullPassedIn() {
        new StockPriceCalculator(null);
    }

    @Test(expected = NullPointerException.class)
    public void calculateMustThrowExceptionWhenNullPassedIn() {
        calculator.calculate(null);
    }

    @Test
    public void calculateMustReturnZeroWhenNoTradeIsPresentForTheStock() {
        when(tradingServiceMock.getLatestTradesForStock(any())).thenReturn(Lists.newArrayList());

        BigDecimal result = calculator.calculate(COMMON_INPUT_STOCK);

        assertThat(result, is(BigDecimal.ZERO));
    }

    @Test
    public void calculateMustQueryTradingServiceAndCalculateBasedOnTheResult() {
        when(tradingServiceMock.getLatestTradesForStock(any())).thenReturn(Lists.newArrayList(TRADE));

        BigDecimal result = calculator.calculate(COMMON_INPUT_STOCK);

        verify(tradingServiceMock).getLatestTradesForStock(COMMON_INPUT_STOCK);
        verifyNoMoreInteractions(tradingServiceMock);
        assertThat(result, is(TEN.stripTrailingZeros()));
    }

    @Test
    public void calculateMustReturnTickerPriceForOneTradeWithMultipleQuantity() {
        when(tradingServiceMock.getLatestTradesForStock(any()))
                .thenReturn(Lists.newArrayList(TRADE_2));

        BigDecimal result = calculator.calculate(COMMON_INPUT_STOCK);

        assertThat(result, is(new BigDecimal("10.5")));
    }

    @Test
    public void calculateMustGoodResultForMultipleTrades() {
        when(tradingServiceMock.getLatestTradesForStock(any()))
                .thenReturn(Lists.newArrayList(TRADE, TRADE_2, TRADE_3));

        BigDecimal result = calculator.calculate(COMMON_INPUT_STOCK);

        assertThat(result, is(new BigDecimal("10.2976")));
    }
}