package com.assignment.config;

import com.assignment.booking.StockRepository;
import com.assignment.booking.TradingService;
import com.assignment.booking.memorybased.MemoryBasedStockRepository;
import com.assignment.booking.memorybased.MemoryBasedTradingService;
import com.assignment.model.ExcecutionException;
import com.assignment.model.Stock;
import com.assignment.model.StockList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Configuration
public class Config {
    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(StockList.class);
        return marshaller;
    }

    @Bean
    public List<Stock> stocks() {
        try (InputStream input = new ClassPathResource("stocks.xml").getInputStream()) {
            return ((StockList) marshaller().unmarshal(new StreamSource(input))).stocks;
        } catch (IOException e) {
            throw new ExcecutionException("Stocks cannot be parsed from xml.");
        }
    }

    @Bean
    public StockRepository stockRepository() {
        return new MemoryBasedStockRepository(stocks());
    }

    @Bean
    public TradingService tradingService() {
        return new MemoryBasedTradingService();
    }
}
