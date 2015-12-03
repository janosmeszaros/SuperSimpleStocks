package com.assignment.booking.memorybased;

import com.assignment.booking.TradingService;
import com.assignment.model.ExcecutionException;
import com.assignment.model.Stock;
import com.assignment.model.Trade;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.apache.commons.lang3.Validate;

import java.util.List;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.now;
import static java.time.temporal.ChronoUnit.MINUTES;

public class MemoryBasedTradingService implements TradingService {
    public static final int RECENT_BORDER = 15;
    private Multimap<Stock, Trade> trades = ArrayListMultimap.create();

    public MemoryBasedTradingService() {
    }

    protected MemoryBasedTradingService(Multimap<Stock, Trade> trades) {
        this.trades = trades;
    }

    @Override
    public void persistTrade(Stock stock, Trade trade) {
        Validate.notNull(stock);
        Validate.notNull(trade);
        trades.put(stock, trade);
    }

    @Override
    public List<Trade> getLatestTradesForStock(Stock stock) {
        Validate.notNull(stock);
        validateKey(stock);

        return trades.get(stock)
                .stream()
                .filter(trade -> MINUTES.between(trade.timestamp, now()) <= RECENT_BORDER)
                .collect(Collectors.toList());
    }

    private void validateKey(Stock stock) {
        if (!trades.containsKey(stock)) {
            throw new ExcecutionException(String.format("Cannot find trades for the given stock: '%s'!", stock.symbol));
        }
    }

    @Override
    public Multimap<Stock, Trade> listAllTrade() {
        return ArrayListMultimap.create(trades);
    }
}
