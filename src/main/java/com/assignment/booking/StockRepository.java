package com.assignment.booking;

import com.assignment.model.Stock;

import java.util.List;

public interface StockRepository {
    List<Stock> getAllStocks();

    Stock findStockByName(String name);
}
