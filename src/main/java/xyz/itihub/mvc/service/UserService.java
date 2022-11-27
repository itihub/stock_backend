package xyz.itihub.mvc.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import xyz.itihub.jpa.entity.UserDO;
import xyz.itihub.mvc.dto.UserRegistration;

public interface UserService extends UserDetailsService {

    /**
     * 用户存储
     * @param registrationDto
     * @return
     */
    UserDO save(UserRegistration registrationDto);

}
