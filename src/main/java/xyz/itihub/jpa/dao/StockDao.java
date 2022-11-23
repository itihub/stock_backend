package xyz.itihub.jpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.itihub.jpa.entity.StockDO;

import java.util.List;

/**
 * 使用 Data Jpa
 * 使用匿名接口方式实现数据访问层的逻辑
 */
@Repository
public interface StockDao extends JpaRepository<StockDO, String> {

    List<StockDO> findAll();

    StockDO findBySymbol(String symbol);

}
