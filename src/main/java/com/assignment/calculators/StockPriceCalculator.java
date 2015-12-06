package com.assignment.calculators;

import com.assignment.booking.TradingService;
import com.assignment.model.Stock;
import com.assignment.model.Trade;
import org.apache.commons.lang3.Validate;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static com.assignment.calculators.CalculatorHelper.divide;
import static org.apache.commons.lang3.Validate.notNull;

public class StockPriceCalculator {
    private TradingService tradingService;

    public StockPriceCalculator(TradingService tradingService) {
        this.tradingService = notNull(tradingService, "tradingService must not be null!");
    }

    public BigDecimal calculate(Stock stock) {
        Validate.notNull(stock, "Stock must be present for calculate price!");
        List<Trade> trades = tradingService.getLatestTradesForStock(stock);

        if (trades.isEmpty()) {
            return BigDecimal.ZERO;
        } else {
            BigDecimal totalValue = sumTotalValues(trades);
            BigDecimal quantity = sumQuantity(trades);
            return divide(totalValue, quantity);
        }
    }

    private BigDecimal sumTotalValues(List<Trade> trades) {
        return trades.stream()
                .map(Trade::totalValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal sumQuantity(List<Trade> trades) {
        return new BigDecimal(trades.stream()
                .map(trade -> trade.quantity)
                .reduce(BigInteger.ZERO, BigInteger::add));
    }

}
