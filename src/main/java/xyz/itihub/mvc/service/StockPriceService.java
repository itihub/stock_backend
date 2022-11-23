package xyz.itihub.mvc.service;


import java.util.Map;

public interface StockPriceService {

    /**
     * 获取当前用户订阅股票实时价格
     * @param user
     * @return
     */
    Map<String, String> getPrice(String user);
}
