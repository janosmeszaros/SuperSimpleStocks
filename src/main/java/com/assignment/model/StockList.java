package com.assignment.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "stocks")
public class StockList {
    @XmlElement(name = "stock")
    public List<Stock> stocks;
}
