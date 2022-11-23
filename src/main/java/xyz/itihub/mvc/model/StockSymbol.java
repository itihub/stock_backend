package xyz.itihub.mvc.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 前端页面提交表单
 */
@Data
@NoArgsConstructor
public class StockSymbol {
    /**
     * 股票代码
     */
    private String symbol;
}
