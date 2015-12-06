package com.assignment.commands;

import com.assignment.booking.StockRepository;
import com.assignment.booking.TradingService;
import com.assignment.model.Direction;
import com.assignment.model.Stock;
import com.assignment.model.Trade;
import com.google.common.base.Joiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliAvailabilityIndicator;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

public class TradeCommands implements CommandMarker {

    public static final Joiner.MapJoiner MAP_JOINER = Joiner.on("\r\n\r\n").withKeyValueSeparator(":\r\n\t");
    @Autowired
    private TradingService tradingService;
    @Autowired
    private StockRepository stockRepository;

    @CliAvailabilityIndicator({"trade list"})
    public boolean isTradeListAvailable() {
        return true;
    }

    @CliAvailabilityIndicator({"trade add"})
    public boolean isTradeAddAvailable() {
        return true;
    }

    @CliCommand(value = "trade list", help = "Print all recoded trades")
    public String listTrades() {
        return MAP_JOINER.join(tradingService.listAllTrade().asMap());
    }

    @CliCommand(value = "trade add", help = "Add the specified trade!")
    public void addTrade(
            @CliOption(key = {"stockName"}, mandatory = true, help = "The stock's name") final String stockName,
            @CliOption(key = {"price"}, mandatory = true, help = "The trade's price") final BigDecimal price,
            @CliOption(key = {"timestamp"}, mandatory = true, help = "The trade's time") final LocalDateTime time,
            @CliOption(key = {"direction"}, mandatory = true, help = "The trade's direction") final Direction direction,
            @CliOption(key = {"quantity"}, mandatory = true, help = "The trade's quantity") final BigInteger quantity) {
        Stock stock = stockRepository.findStockByName(stockName);
        Trade trade = Trade.builder().withTimestamp(time).withDirection(direction)
                .withPrice(price).withQuantity(quantity).build();
        tradingService.persistTrade(stock, trade);
    }
}
