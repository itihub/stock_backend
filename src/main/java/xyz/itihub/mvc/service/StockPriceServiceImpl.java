package xyz.itihub.mvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.itihub.jpa.dao.StockSubscriptionDao;
import xyz.itihub.jpa.entity.StockSubscriptionDO;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class StockPriceServiceImpl implements StockPriceService {
    @Autowired
    private StockSubscriptionDao subscriptionDao;

    @Autowired
    private PriceQueryEngine priceQueryEngine;

    @Override
    public Map<String, String> getPrice(String email) {
        List<StockSubscriptionDO> subscriptions = subscriptionDao.findByEmail(email);
        return subscriptions.stream()
                .map(StockSubscriptionDO::getSymbol)
                .collect(Collectors.toMap(
                                Function.identity(),
                                priceQueryEngine::getPriceForSymbol
                        )
                );
    }
}
