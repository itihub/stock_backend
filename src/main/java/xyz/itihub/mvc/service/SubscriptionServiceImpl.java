package xyz.itihub.mvc.service;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import xyz.itihub.jpa.dao.StockDao;
import xyz.itihub.jpa.dao.StockSubscriptionDao;
import xyz.itihub.jpa.entity.StockDO;
import xyz.itihub.jpa.entity.StockSubscriptionDO;
import xyz.itihub.mvc.model.StockSubscription;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubscriptionServiceImpl implements  SubscriptionService{
    private final StockDao stockDao;

    private final StockSubscriptionDao subscriptionDao;

    public SubscriptionServiceImpl(StockDao stockDao, StockSubscriptionDao subscriptionDao) {
        this.stockDao = stockDao;
        this.subscriptionDao = subscriptionDao;
    }

    public List<StockSubscription> findByEmail(String email) {
        List<StockSubscriptionDO> subscriptions = subscriptionDao.findByEmail(email);
        return subscriptions.stream().map(stockSubscriptionDO ->
                StockSubscription.builder()
                        .symbol(stockSubscriptionDO.getSymbol())
                        .email(stockSubscriptionDO.getEmail())
                        .build()
        ).collect(Collectors.toList());
    }

    @Override
    public void addSubscription(String email, String symbol) {
        checkValidStock(symbol);
        checkSubscriptionExists(email, symbol);
        StockSubscriptionDO subscriptionDO = new StockSubscriptionDO();
        subscriptionDO.setEmail(email);
        subscriptionDO.setSymbol(symbol);
        subscriptionDao.save(subscriptionDO);
    }

    private void checkSubscriptionExists(String email, String symbol) {
        List<StockSubscriptionDO> matched = subscriptionDao.findByEmailAndSymbol(email, symbol);
        if (!CollectionUtils.isEmpty(matched)) {
            throw new RuntimeException("subscription already exists for this user");
        }
    }

    private void checkValidStock(String symbol) {
        Optional<StockDO> matched = stockDao.findAll()
                .stream()
                .filter(stockDO -> stockDO.getSymbol().equalsIgnoreCase(symbol))
                .findAny();
        if (!matched.isPresent()) {
            throw new RuntimeException("stock symbol not valid");
        }
    }
}
