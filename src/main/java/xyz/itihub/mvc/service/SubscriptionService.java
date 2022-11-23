
package xyz.itihub.mvc.service;

import xyz.itihub.mvc.model.StockSubscription;

import java.util.List;

public interface SubscriptionService {
    /**
     * 查找当前用户订阅的股票信息
     * @param email
     * @return
     */
    List<StockSubscription> findByEmail(String email);

    /**
     * 添加股票订阅
     * @param email
     * @param symbol
     */
    void addSubscription(String email, String symbol);
}
