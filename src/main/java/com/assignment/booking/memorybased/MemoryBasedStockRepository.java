package com.assignment.booking.memorybased;

import com.assignment.booking.StockRepository;
import com.assignment.model.ExcecutionException;
import com.assignment.model.Stock;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.Validate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.Validate.notNull;

public class MemoryBasedStockRepository implements StockRepository {
    private Map<String, Stock> stocks;

    public MemoryBasedStockRepository(List<Stock> stocks) {
        notNull(stocks, "stocks must not be null!");
        this.stocks = stocks.stream().collect(Collectors.toMap(stock -> stock.symbol, stock -> stock));
    }

    @Override
    public List<Stock> getAllStocks() {
        return Lists.newArrayList(stocks.values());
    }

    @Override
    public Stock findStockByName(String name) {
        Validate.notBlank(name, "Stock's name must not be empty!");
        if (!stocks.containsKey(name)) {
            throw new ExcecutionException(String.format("Cannot find stock with the given name: '%s'!", name));
        }

        return stocks.get(name);
    }
}
