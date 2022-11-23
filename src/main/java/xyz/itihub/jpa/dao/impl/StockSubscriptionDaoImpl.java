//package xyz.itihub.jpa.dao.impl;
//
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Repository;
//import xyz.itihub.jpa.dao.StockSubscriptionDao;
//import xyz.itihub.jpa.entity.StockSubscriptionDO;
//
//import java.util.List;
//
//@Repository
//public class StockSubscriptionDaoImpl implements StockSubscriptionDao {
//    private final JdbcTemplate jdbcTemplate;
//
//    public StockSubscriptionDaoImpl(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    private final String SELECT_BY_EMAIL_SQL = "SELECT * FROM stock_subscription WHERE email = ?";
//
//    @Override
//    public List<StockSubscriptionDO> findByEmail(String email) {
//        return jdbcTemplate.query(SELECT_BY_EMAIL_SQL, new BeanPropertyRowMapper<>(StockSubscriptionDO.class), email);
//    }
//
//}
