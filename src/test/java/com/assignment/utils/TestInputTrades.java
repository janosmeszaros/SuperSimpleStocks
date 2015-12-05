package com.assignment.utils;

import com.assignment.model.Trade;

import java.math.BigInteger;
import java.time.LocalDateTime;

import static com.assignment.model.Direction.BUY;
import static java.math.BigDecimal.TEN;

public class TestInputTrades {
    public static final Trade TRADE = createTrade(LocalDateTime.now());
    public static final Trade TRADE_2 = createTrade(LocalDateTime.now().minusMinutes(14));
    public static final Trade TRADE_3 = createTrade(LocalDateTime.now().minusMinutes(12));
    public static final Trade TRADE_OLDER_THAN_FIFTEEN_MINUTES = createTrade(LocalDateTime.now().minusMinutes(16));

    public static Trade createTrade(LocalDateTime timestamp) {
        return Trade.builder()
                .withDirection(BUY)
                .withPrice(TEN)
                .withQuantity(BigInteger.ONE)
                .withTimestamp(timestamp)
                .build();
    }
}
