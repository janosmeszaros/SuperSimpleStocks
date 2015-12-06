package com.assignment.config;

import com.assignment.commands.StockCommands;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandsConfig {
    @Bean
    public StockCommands stockCommands() {
        return new StockCommands();
    }
}
