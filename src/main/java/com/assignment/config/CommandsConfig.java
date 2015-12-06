package com.assignment.config;

import com.assignment.commands.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandsConfig {
    @Bean
    public StockCommands stockCommands() {
        return new StockCommands();
    }

    @Bean
    public TradeCommands tradeCommands() {
        return new TradeCommands();
    }

    @Bean
    public IndexCommands indexCommands() {
        return new IndexCommands();
    }

    @Bean
    public TimestampConverter timestampConverter() {
        return new TimestampConverter();
    }

    @Bean
    public CustomBannerProvider customBannerProvider() {
        return new CustomBannerProvider();
    }
}
