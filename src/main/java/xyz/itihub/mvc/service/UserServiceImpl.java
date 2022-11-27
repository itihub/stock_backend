package xyz.itihub.mvc.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import xyz.itihub.jpa.dao.UserDao;
import xyz.itihub.jpa.entity.RoleDO;
import xyz.itihub.jpa.entity.UserDO;
import xyz.itihub.mvc.dto.UserRegistration;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDO save(UserRegistration registrationDto) {
        UserDO user = new UserDO(registrationDto.getEmail(), registrationDto.getFirstName(), registrationDto.getLastName(),
                passwordEncoder.encode(registrationDto.getPassword()), Arrays.asList(new RoleDO("ROLE_USER")));

        return userDao.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDO user = userDao.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username");
        }

        return new User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    /**
     * 用户角色转换为Security框架识别的角色
     * @param roles
     * @return
     */
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<RoleDO> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

}
