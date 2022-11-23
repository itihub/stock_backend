package xyz.itihub.mvc.service;

import com.viso.jpa.dao.StockSubscriptionDao;
import com.viso.jpa.entity.StockSubscriptionDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Map<String, String> priceMap = subscriptions.stream()
                .map(subscription -> subscription.getSymbol())
                .collect(Collectors.toMap(
                                Function.identity(),
                                priceQueryEngine::getPriceForSymbol
                        )
                );
        return priceMap;
    }
}