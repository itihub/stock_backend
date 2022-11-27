package xyz.itihub.mvc.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.itihub.mvc.model.Stock;
import xyz.itihub.mvc.model.User;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 给前端提供的Rest接口
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    private final NotificationService notificationService;

    public ApiController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * 参数校验错误处理方式一
     * 抛出异常由全局异常处理进行捕获
     * @param user
     * @return
     */
    @PostMapping("/validate/user/v1")
    public Object validateUserV1(@Valid @RequestBody User user) {
        return "cool";
    }

    /**
     * 参数校验错误处理方式二
     * 由控制器来接受处理 BindingResult
     * @param user
     * @param bindingResult
     * @return
     */
    @PostMapping("/validate/user/v2")
    public Object validateUserV2(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return bindingResult.getAllErrors();
        } else {
            return "cool";
        }
    }

    /**
     * 参数校验之调用方法进行参数校验
     * @param user
     * @return
     */
    @PostMapping("/validate/user/v3")
    public Object validateUserV3(@RequestBody User user) {
        notificationService.sendEmail(user.getEmail());
        return "cool";
    }

    @PostMapping("/price")
    public Map<Stock, String> getPrice() {
        Map<Stock, String> result = new HashMap<>();
        result.put(Stock.builder().symbol("APPL").build(), "USD101.00");
        result.put(Stock.builder().symbol("AMZN").build(), "USD3298.75");
        result.put(Stock.builder().symbol("TSLA").build(), "USD701.98");
        return result;
    }

}
