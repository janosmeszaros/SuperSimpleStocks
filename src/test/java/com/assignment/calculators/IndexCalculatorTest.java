package com.assignment.calculators;

import com.assignment.booking.StockRepository;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static com.assignment.utils.TestInputStocks.COMMON_INPUT_STOCK;
import static com.assignment.utils.TestInputStocks.PREFERRED_INPUT_STOCK;
import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class IndexCalculatorTest {
    @Mock
    private StockRepository stockRepositoryMock;
    @Mock
    private StockPriceCalculator stockPriceCalculatorMock;

    private IndexCalculator calculator;

    @Before
    public void setUp() {
        when(stockRepositoryMock.getAllStocks()).thenReturn(Lists.newArrayList(COMMON_INPUT_STOCK, PREFERRED_INPUT_STOCK));
        this.calculator = new IndexCalculator(stockRepositoryMock, stockPriceCalculatorMock);
    }

    @Test(expected = NullPointerException.class)
    public void constructorMustThrowExceptionWhenNullPassedAsStockRepo() {
        new IndexCalculator(null, stockPriceCalculatorMock);
    }

    @Test(expected = NullPointerException.class)
    public void constructorMustThrowExceptionWhenNullPassedAsCalculator() {
        new IndexCalculator(stockRepositoryMock, null);
    }

    @Test
    public void calculateIndexMustReturnZeroWhenNoStocks() {
        when(stockRepositoryMock.getAllStocks()).thenReturn(Lists.newArrayList());

        BigDecimal index = calculator.calculateIndex();

        verify(stockRepositoryMock).getAllStocks();
        verifyNoMoreInteractions(stockPriceCalculatorMock, stockRepositoryMock);
        assertThat(index, is(ZERO));
    }

    @Test
    public void calculateIndexMustQueryRepoForAllStocksAndCalculateTheirPrices() {
        when(stockPriceCalculatorMock.calculate(any())).thenReturn(TEN);

        calculator.calculateIndex();

        verify(stockRepositoryMock).getAllStocks();
        verify(stockPriceCalculatorMock).calculate(COMMON_INPUT_STOCK);
        verify(stockPriceCalculatorMock).calculate(PREFERRED_INPUT_STOCK);
        verifyNoMoreInteractions(stockPriceCalculatorMock, stockRepositoryMock);
    }

    @Test
    public void calculateIndexMustReturnGeometricMeanOfThePrice() {
        when(stockPriceCalculatorMock.calculate(any())).thenReturn(new BigDecimal("4.5"));
        when(stockRepositoryMock.getAllStocks()).thenReturn(Lists.newArrayList(COMMON_INPUT_STOCK));

        BigDecimal index = calculator.calculateIndex();

        assertThat(index, is(new BigDecimal("4.5")));
    }

    @Test
    public void calculateIndexMustReturnGeometricMeanOfThePrices() {
        when(stockPriceCalculatorMock.calculate(any()))
                .thenReturn(new BigDecimal("4"))
                .thenReturn(new BigDecimal("6"));

        BigDecimal index = calculator.calculateIndex();

        assertThat(index, is(new BigDecimal("4.899")));
    }

    @Test
    public void calculateIndexMustReturnGeometricMeanOfMultiplePrices() {
        when(stockRepositoryMock.getAllStocks())
                .thenReturn(Lists.newArrayList(COMMON_INPUT_STOCK, PREFERRED_INPUT_STOCK, COMMON_INPUT_STOCK));

        when(stockPriceCalculatorMock.calculate(any()))
                .thenReturn(new BigDecimal("4.5"))
                .thenReturn(new BigDecimal("6.12"))
                .thenReturn(new BigDecimal("8.765"));

        BigDecimal index = calculator.calculateIndex();

        assertThat(index, is(new BigDecimal("6.2264")));
    }
}