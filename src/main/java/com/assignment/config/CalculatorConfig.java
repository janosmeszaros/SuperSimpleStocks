package com.assignment.config;

import com.assignment.booking.StockRepository;
import com.assignment.booking.TradingService;
import com.assignment.calculators.DividendYieldCalculator;
import com.assignment.calculators.IndexCalculator;
import com.assignment.calculators.PricePerEarningsRatioCalculator;
import com.assignment.calculators.StockPriceCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CalculatorConfig {
    @Autowired
    private TradingService tradingService;
    @Autowired
    private StockRepository stockRepository;

    @Bean
    public DividendYieldCalculator dividendYieldCalculator() {
        return new DividendYieldCalculator();
    }

    @Bean
    public StockPriceCalculator stockPriceCalculator() {
        return new StockPriceCalculator(tradingService);
    }

    @Bean
    public PricePerEarningsRatioCalculator pricePerEarningsRatioCalculator() {
        return new PricePerEarningsRatioCalculator(dividendYieldCalculator());
    }

    @Bean
    public IndexCalculator indexCalculator() {
        return new IndexCalculator(stockRepository, stockPriceCalculator());
    }
}
