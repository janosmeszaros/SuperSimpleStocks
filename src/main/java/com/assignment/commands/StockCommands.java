package com.assignment.commands;

import com.assignment.booking.StockRepository;
import com.assignment.calculators.DividendYieldCalculator;
import com.assignment.calculators.PricePerEarningsRatioCalculator;
import com.assignment.calculators.StockPriceCalculator;
import com.assignment.model.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliAvailabilityIndicator;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;

import java.math.BigDecimal;
import java.util.stream.Collectors;

public class StockCommands implements CommandMarker {

    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private StockPriceCalculator stockPriceCalculator;
    @Autowired
    private DividendYieldCalculator yieldCalculator;
    @Autowired
    private PricePerEarningsRatioCalculator ratioCalculator;

    @CliAvailabilityIndicator({"stock list"})
    public boolean isStockListAvailable() {
        return true;
    }

    @CliAvailabilityIndicator({"stock yield"})
    public boolean isStockYieldAvailable() {
        return true;
    }

    @CliAvailabilityIndicator({"stock ratio"})
    public boolean isStockRatioAvailable() {
        return true;
    }

    @CliAvailabilityIndicator({"stock price"})
    public boolean isStockPriceAvailable() {
        return true;
    }

    @CliCommand(value = "stock list", help = "Print all of the available stocks")
    public String listStocks() {
        return stockRepository.getAllStocks().stream()
                .map(stock -> stock.toString())
                .collect(Collectors.joining("\r\n"));
    }

    @CliCommand(value = "stock yield", help = "Calculate dividend yield for the given stock on the given price")
    public BigDecimal stockYield(
            @CliOption(key = {"stockName"}, mandatory = true, help = "The stock's name") final String stockName,
            @CliOption(key = {"price"}, mandatory = true, help = "The stock's price") final BigDecimal price) {
        Stock stock = stockRepository.findStockByName(stockName);
        return yieldCalculator.calculate(price, stock);
    }

    @CliCommand(value = "stock ratio", help = "Calculate P/E ratio for the given stock on the given price")
    public BigDecimal stockRatio(
            @CliOption(key = {"stockName"}, mandatory = true, help = "The stock's name") final String stockName,
            @CliOption(key = {"price"}, mandatory = true, help = "The stock's price") final BigDecimal price) {
        Stock stock = stockRepository.findStockByName(stockName);
        return ratioCalculator.calculate(price, stock);
    }

    @CliCommand(value = "stock price", help = "Calculate price for the given stock")
    public BigDecimal stockPrice(
            @CliOption(key = {"stockName"}, mandatory = true, help = "The stock's name") final String stockName) {
        Stock stock = stockRepository.findStockByName(stockName);
        return stockPriceCalculator.calculate(stock);
    }

}
