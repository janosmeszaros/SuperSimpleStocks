package com.assignment.commands;

import com.assignment.booking.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliAvailabilityIndicator;
import org.springframework.shell.core.annotation.CliCommand;

import java.util.stream.Collectors;

public class StockCommands implements CommandMarker {

    @Autowired
    private StockRepository stockRepository;

    @CliAvailabilityIndicator({"stock list"})
    public boolean isStockListAvailable() {
        return true;
    }

    @CliCommand(value = "stock list", help = "Print all of the available stocks")
    public String listStocks() {
        return stockRepository.getAllStocks().stream()
                .map(stock -> stock.toString())
                .collect(Collectors.joining("\r\n"));
    }

}
