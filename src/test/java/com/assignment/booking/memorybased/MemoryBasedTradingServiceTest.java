package com.assignment.booking.memorybased;

import com.assignment.booking.TradingService;
import com.assignment.model.ExcecutionException;
import com.assignment.model.Stock;
import com.assignment.model.Trade;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import static com.assignment.calculators.TestInputStocks.COMMON_INPUT_STOCK;
import static com.assignment.calculators.TestInputStocks.PREFERRED_INPUT_STOCK;
import static com.assignment.model.Direction.BUY;
import static java.math.BigDecimal.TEN;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;

public class MemoryBasedTradingServiceTest {
    public static final Trade TRADE = createTrade(LocalDateTime.now());
    public static final Trade TRADE_2 = createTrade(LocalDateTime.now().minusMinutes(14));
    public static final Trade TRADE_3 = createTrade(LocalDateTime.now().minusMinutes(12));
    private static final Trade TRADE_OLDER_THAN_FIFTEEN_MINUTES = createTrade(LocalDateTime.now().minusMinutes(16));

    private static Trade createTrade(LocalDateTime timestamp) {
        return Trade.builder()
                .withDirection(BUY)
                .withPrice(TEN)
                .withQuantity(BigInteger.ONE)
                .withTimestamp(timestamp)
                .build();
    }

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

        service.persistTrade(null, TRADE);
    }

    @Test
    public void persistTradeMustSaveGivenTrade() {
        service.persistTrade(COMMON_INPUT_STOCK, TRADE);

        Multimap<Stock, Trade> trades = service.listAllTrade();
        assertThat(trades.values(), contains(TRADE));
    }

    @Test
    public void getLatestTradesForStockMustThrowExceptionWhenNullPassedInAsAStock() {
        expectedException.expect(NullPointerException.class);

        service.getLatestTradesForStock(null);
    }

    @Test
    public void getLatestTradesForStockMustThrowExceptionNoStockFoundWithTheGivenSymbol() {
        expectedException.expect(ExcecutionException.class);
        expectedException.expectMessage("Cannot find trades for the given stock: 'test'!");

        service.getLatestTradesForStock(COMMON_INPUT_STOCK);
    }

    @Test
    public void getLatestTradesForStockMustReturnTradesOnlyForTheGivenStock() {
        createServiceWithTrades();

        List<Trade> trades = service.getLatestTradesForStock(PREFERRED_INPUT_STOCK);

        assertThat(trades, hasSize(2));
        assertThat(trades, contains(TRADE_2, TRADE_3));
    }

    @Test
    public void getLatestTradesForStockMustReturnTradesOnlyForTheGivenStockAndNotOlderThan15Minutes() {
        createServiceWithTrades();

        List<Trade> trades = service.getLatestTradesForStock(COMMON_INPUT_STOCK);

        assertThat(trades, hasSize(1));
        assertThat(trades, contains(TRADE));
    }

    private void createServiceWithTrades() {
        Multimap<Stock, Trade> multimap = ArrayListMultimap.create();
        multimap.put(COMMON_INPUT_STOCK, TRADE);
        multimap.put(PREFERRED_INPUT_STOCK, TRADE_2);
        multimap.put(PREFERRED_INPUT_STOCK, TRADE_3);
        multimap.put(COMMON_INPUT_STOCK, TRADE_OLDER_THAN_FIFTEEN_MINUTES);
        service = new MemoryBasedTradingService(multimap);
    }
}