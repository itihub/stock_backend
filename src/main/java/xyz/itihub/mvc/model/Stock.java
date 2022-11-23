package xyz.itihub.mvc.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 股票
 */
@Data
@Builder
public class Stock {
    /**
     * 股票代码
     */
    private String symbol;
    /**
     * 股票名称
     */
    private String name;
}
