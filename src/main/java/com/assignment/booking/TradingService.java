package com.assignment.booking;

import com.assignment.model.Stock;
import com.assignment.model.Trade;
import com.google.common.collect.Multimap;

import java.util.List;

public interface TradingService {
    void persistTrade(Stock stock, Trade trade);

    List<Trade> getLatestTradesForStock(Stock stock);

    Multimap<Stock, Trade> listAllTrade();
}
