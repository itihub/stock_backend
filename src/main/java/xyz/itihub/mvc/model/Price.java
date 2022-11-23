package xyz.itihub.mvc.model;

import lombok.Builder;
import lombok.Data;

/**
 * 股票价格
 */
@Data
@Builder
public class Price {

    private Integer coefficient;
    private Integer exponent;
    private String currency;

}
