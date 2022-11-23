//package xyz.itihub.jpa.dao.impl;
//
//import org.springframework.jdbc.core.DataClassRowMapper;
//import org.springframework.jdbc.core.JdbcTemplate;
//import xyz.itihub.jpa.dao.StockDao;
//import xyz.itihub.jpa.dao.entity.StockDO;
//
//import java.util.List;
//
///**
// * 使用JdbcTemplate 实现数据访问层逻辑（这里主要是和JPA对比）
// */
//public class StockDaoImpl implements StockDao {
//
//    private final JdbcTemplate jdbcTemplate;
//
//    private final String SELECT_ALL_SQL = "SELECT * FROM stock";
//    private final String SELECT_BY_SYMBOL_SQL = "SELECT * FROM stock WHERE symbol = ?";
//
//    public StockDaoImpl(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    @Override
//    public List<StockDO> findAll() {
//        return jdbcTemplate.query(SELECT_ALL_SQL, new DataClassRowMapper<>(StockDO.class));
//    }
//
//    @Override
//    public StockDO findBySymbol(String symbol) {
//        return jdbcTemplate.queryForObject(SELECT_BY_SYMBOL_SQL, new DataClassRowMapper<>(StockDO.class), symbol);
//    }
//}
