package com.assignment.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

import static org.apache.commons.lang3.Validate.notNull;

public class Trade {
    public final LocalDateTime timestamp;
    public final BigInteger quantity;
    public final Direction direction;
    public final BigDecimal price;

    public Trade(LocalDateTime timestamp, BigInteger quantity, Direction direction, BigDecimal price) {
        this.timestamp = notNull(timestamp, "timestamp must not be null!");
        this.quantity = notNull(quantity, "quantity must not be null!");
        this.direction = notNull(direction, "direction must not be null!");
        this.price = notNull(price, "price must not be null!");
    }

    public BigDecimal totalValue() {
        return price.multiply(new BigDecimal(quantity));
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private LocalDateTime timestamp;
        private BigInteger quantity;
        private Direction direction;
        private BigDecimal price;

        public Builder withTimestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder withQuantity(BigInteger quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder withDirection(Direction direction) {
            this.direction = direction;
            return this;
        }

        public Builder withPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Trade build() {
            return new Trade(timestamp, quantity, direction, price);
        }
    }
}
