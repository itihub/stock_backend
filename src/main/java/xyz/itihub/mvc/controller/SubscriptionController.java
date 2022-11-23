package xyz.itihub.mvc.controller;

import com.viso.mvc.model.StockSubscription;
import com.viso.mvc.model.StockSymbol;
import com.viso.mvc.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.itihub.mvc.model.StockSubscription;
import xyz.itihub.mvc.model.StockSymbol;
import xyz.itihub.mvc.service.SubscriptionService;

import java.util.List;

import static com.viso.mvc.Constants.TEST_USER_EMAIL;
import static xyz.itihub.mvc.Constants.TEST_USER_EMAIL;

@Controller
@RequestMapping("/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    /**
     * 新增订阅股票
     * @param symbol
     * @return
     */
    @PostMapping
    public String addSubscription(@ModelAttribute(value = "stockSymbol") StockSymbol symbol) {
        String name = TEST_USER_EMAIL;
        subscriptionService.addSubscription(name, symbol.getSymbol());
        return "redirect:/subscriptions?added=" + symbol.getSymbol();
    }

    /**
     * 股票列表
     * @param model
     * @return
     */
    @GetMapping
    public String subscription(Model model) {
        String name = TEST_USER_EMAIL;
        List<StockSubscription> subscriptions = subscriptionService.findByEmail(name);
        model.addAttribute("email", name);
        model.addAttribute("subscriptions", subscriptions);

        return "subscription";
    }


}
