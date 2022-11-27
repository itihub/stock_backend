package xyz.itihub.mvc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

/**
 * Service层参数校验
 */
@Slf4j
@Service
@Validated
public class NotificationService {

    /**
     * 方法参数校验
     * 底层使用AOP技术实现
     * 注册切面类 MethodValidationPostProcessor（只对Spring的校验注解有作用）
     * @param email
     */
    public void sendEmail(@NotEmpty String email) {
        log.info("send email to:{}", email);
    }
}
