package xyz.itihub.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.itihub.mvc.service.StockPriceService;


import static xyz.itihub.mvc.Constants.TEST_USER_EMAIL;
import static  xyz.itihub.mvc.Constants.TEST_USER_NAME;

/**
 * 主页控制器
 */
@Controller
public class WebController {

    private final StockPriceService stockPriceService;

    public WebController(StockPriceService stockPriceService) {
        this.stockPriceService = stockPriceService;
    }

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("email", TEST_USER_EMAIL);
        model.addAttribute("stockPrices", stockPriceService.getPrice(TEST_USER_EMAIL));
        return "index";
    }
}
