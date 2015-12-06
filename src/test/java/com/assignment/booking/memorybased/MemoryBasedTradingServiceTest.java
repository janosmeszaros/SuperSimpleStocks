package com.assignment.booking.memorybased;

import com.assignment.booking.TradingService;
import com.assignment.model.Stock;
import com.assignment.model.Trade;
import com.assignment.utils.TestInputTrades;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static com.assignment.utils.TestInputStocks.COMMON_INPUT_STOCK;
import static com.assignment.utils.TestInputStocks.PREFERRED_INPUT_STOCK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;

public class MemoryBasedTradingServiceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private TradingService service;

    @Before
    public void setUp() throws Exception {
        service = new MemoryBasedTradingService();
    }

    @Test
    public void persistTradeMustThrowExceptionWhenNullPassedInAsATrade() {
        expectedException.expect(NullPointerException.class);

        service.persistTrade(COMMON_INPUT_STOCK, null);
    }

    @Test
    public void persistTradeMustThrowExceptionWhenNullPassedInAsAStock() {
        expectedException.expect(NullPointerException.class);

        service.persistTrade(null, TestInputTrades.TRADE);
    }

    @Test
    public void persistTradeMustSaveGivenTrade() {
        service.persistTrade(COMMON_INPUT_STOCK, TestInputTrades.TRADE);

        Multimap<Stock, Trade> trades = service.listAllTrade();
        assertThat(trades.values(), contains(TestInputTrades.TRADE));
    }

    @Test
    public void getLatestTradesForStockMustThrowExceptionWhenNullPassedInAsAStock() {
        expectedException.expect(NullPointerException.class);

        service.getLatestTradesForStock(null);
    }

    @Test
    public void getLatestTradesForStockMustReturnEmptyListWhenNoStockFoundWithTheGivenSymbol() {
        List<Trade> trades = service.getLatestTradesForStock(COMMON_INPUT_STOCK);

        assertThat(trades, hasSize(0));
    }

    @Test
    public void getLatestTradesForStockMustReturnTradesOnlyForTheGivenStock() {
        createServiceWithTrades();

        List<Trade> trades = service.getLatestTradesForStock(PREFERRED_INPUT_STOCK);

        assertThat(trades, hasSize(2));
        assertThat(trades, contains(TestInputTrades.TRADE_2, TestInputTrades.TRADE_3));
    }

    @Test
    public void getLatestTradesForStockMustReturnTradesOnlyForTheGivenStockAndNotOlderThan15Minutes() {
        createServiceWithTrades();

        List<Trade> trades = service.getLatestTradesForStock(COMMON_INPUT_STOCK);

        assertThat(trades, hasSize(1));
        assertThat(trades, contains(TestInputTrades.TRADE));
    }

    private void createServiceWithTrades() {
        Multimap<Stock, Trade> multimap = ArrayListMultimap.create();
        multimap.put(COMMON_INPUT_STOCK, TestInputTrades.TRADE);
        multimap.put(PREFERRED_INPUT_STOCK, TestInputTrades.TRADE_2);
        multimap.put(PREFERRED_INPUT_STOCK, TestInputTrades.TRADE_3);
        multimap.put(COMMON_INPUT_STOCK, TestInputTrades.TRADE_OLDER_THAN_FIFTEEN_MINUTES);
        service = new MemoryBasedTradingService(multimap);
    }
}