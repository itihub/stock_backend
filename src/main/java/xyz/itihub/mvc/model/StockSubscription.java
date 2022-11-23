package xyz.itihub.mvc.model;

import lombok.Builder;
import lombok.Data;

/**
 * 股票订阅
 */
@Data
@Builder
public class StockSubscription {
    /**
     * 订阅者邮箱
     */
    private String email;
    /**
     * 股票代码
     */
    private String symbol;
}
