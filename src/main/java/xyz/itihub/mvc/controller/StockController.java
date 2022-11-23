package xyz.itihub.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.itihub.mvc.model.Stock;
import xyz.itihub.mvc.model.StockSymbol;
import xyz.itihub.mvc.service.StockServiceImpl;

import java.util.List;


/**
 *
 */
@Controller
@RequestMapping("/stocks")
public class StockController {
    private final StockServiceImpl stockService;

    public StockController(StockServiceImpl stockService) {
        this.stockService = stockService;
    }

    @GetMapping
    private String getStocks(Model model) {
        List<Stock> stocks = stockService.getAllStocks();
        model.addAttribute("stocks", stocks);
        model.addAttribute("stockSymbol", new StockSymbol());
        return "stocks";
    }
}
