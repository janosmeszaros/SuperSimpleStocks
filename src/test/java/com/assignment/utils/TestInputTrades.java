package com.assignment.utils;

import com.assignment.model.Trade;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

import static com.assignment.model.Direction.BUY;
import static java.math.BigDecimal.TEN;
import static java.math.BigInteger.ONE;

public class TestInputTrades {
    public static final Trade TRADE = createTrade(LocalDateTime.now(), TEN, ONE);
    public static final Trade TRADE_2 = createTrade(LocalDateTime.now().minusMinutes(14), new BigDecimal(10.5), new BigInteger("5"));
    public static final Trade TRADE_3 = createTrade(LocalDateTime.now().minusMinutes(12), new BigDecimal(10.25), new BigInteger("15"));
    public static final Trade TRADE_OLDER_THAN_FIFTEEN_MINUTES = createTrade(LocalDateTime.now().minusMinutes(16), TEN, ONE);

    public static Trade createTrade(LocalDateTime timestamp, BigDecimal price, BigInteger quantity) {
        return Trade.builder()
                .withDirection(BUY)
                .withPrice(price)
                .withQuantity(quantity)
                .withTimestamp(timestamp)
                .build();
    }
}
