package xyz.itihub.jpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.itihub.jpa.entity.UserDO;

public interface UserDao extends JpaRepository<UserDO, Long> {

    UserDO findByEmail(String email);

}
