package xyz.itihub.mvc.service;


import xyz.itihub.mvc.model.Stock;

import java.util.List;

public interface StockService {
    /**
     * 获取可订阅股票列表
     * @return
     */
    List<Stock> getAllStocks();

}
