package com.assignment.model;

import org.apache.commons.lang3.Validate;

import java.math.BigDecimal;
import java.util.Optional;

import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;

public class Stock {
    public final String symbol;
    public final StockType type;
    public final BigDecimal lastDividend;
    public final Optional<BigDecimal> fixedDividend;
    public final BigDecimal parValue;

    private Stock(String symbol, StockType type, BigDecimal lastDividend, Optional<BigDecimal> fixedDividend, BigDecimal parValue) {
        this.symbol = notEmpty(symbol, "symbol must not be empty!");
        this.type = notNull(type, "type must not be null!");
        this.lastDividend = notNull(lastDividend, "lastDividend must not be null!");
        this.fixedDividend = notNull(fixedDividend, "fixedDividend must not be null!");
        this.parValue = notNull(parValue, "parValue must not be null!");
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String symbol;
        private StockType type;
        private BigDecimal lastDividend;
        private BigDecimal fixedDividend;
        private BigDecimal parValue;

        public Builder withSymbol(String symbol) {
            this.symbol = symbol;
            return this;
        }

        public Builder withType(StockType type) {
            this.type = type;
            return this;
        }

        public Builder withLastDividend(BigDecimal lastDividend) {
            this.lastDividend = lastDividend;
            return this;
        }

        public Builder withFixedDividend(BigDecimal fixedDividend) {
            this.fixedDividend = fixedDividend;
            return this;
        }

        public Builder withParValue(BigDecimal parValue) {
            this.parValue = parValue;
            return this;
        }

        public Stock build() {
            return new Stock(symbol, type, lastDividend, Optional.ofNullable(fixedDividend), parValue);
        }
    }
}
