package com.assignment.booking.memorybased;


import com.assignment.model.ExcecutionException;
import com.assignment.model.Stock;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static com.assignment.model.StockType.COMMON;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class MemoryBasedStockRepositoryTest {
    private static final Stock STOCK = createStock("stock");
    private static final Stock STOCK_2 = createStock("stock2");
    private static final List<Stock> ALL_STOCK = Lists.newArrayList(STOCK, STOCK_2);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private MemoryBasedStockRepository repository;

    @Before
    public void setUp() throws Exception {
        repository = new MemoryBasedStockRepository(ALL_STOCK);
    }

    @Test
    public void getAllStockMustReturnAllStoredStocks() {
        List<Stock> stocks = repository.getAllStocks();

        assertThat(stocks, is(ALL_STOCK));
    }

    @Test(expected = IllegalArgumentException.class)
    public void findStockByNameMustThrowExceptionInCaseOfEmptyInputString() {
        repository.findStockByName("");
    }

    @Test(expected = NullPointerException.class)
    public void findStockByNameMustThrowExceptionInCaseOfNullInputString() {
        repository.findStockByName(null);
    }

    @Test
    public void findStockByNameMustReturnStockWithTheGivenName() {
        Stock stock = repository.findStockByName("stock");

        assertThat(stock.symbol, is(STOCK.symbol));
    }


    @Test
    public void findStockByNameMustThrowExceptionNoStockFoundWithTheGivenSymbol() {
        expectedException.expect(ExcecutionException.class);
        expectedException.expectMessage("Cannot find stock with the given name: 'notExists'!");

        repository.findStockByName("notExists");
    }

    private static Stock createStock(String symbol) {
        return Stock.builder()
                .withSymbol(symbol)
                .withParValue(TEN)
                .withLastDividend(ONE)
                .withType(COMMON)
                .build();
    }
}