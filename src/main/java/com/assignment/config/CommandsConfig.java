package com.assignment.config;

import com.assignment.commands.IndexCommands;
import com.assignment.commands.StockCommands;
import com.assignment.commands.TimestampConverter;
import com.assignment.commands.TradeCommands;
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
}
