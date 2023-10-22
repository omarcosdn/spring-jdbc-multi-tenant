package io.github.omarcosdn.multitenant.application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

  private final JdbcTemplate jdbcTemplate;

  public ProductRepositoryImpl(final JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = Objects.requireNonNull(jdbcTemplate);
  }

  @Override
  public List<Product> findAll(final UUID companyId) {
    final var query = "SELECT * FROM product WHERE company_id = ?";

    return jdbcTemplate.query(query, new ProductRowMapper(), companyId);
  }

  private static class ProductRowMapper implements RowMapper<Product> {

    @Override
    public Product mapRow(final ResultSet resultSet, int rowNum) throws SQLException {
      final var productId = resultSet.getObject("product_id", UUID.class);
      final var productName = resultSet.getString("name");
      final var companyId = resultSet.getObject("company_id", UUID.class);
      return Product.restore(productId, productName, companyId);
    }
  }

}
