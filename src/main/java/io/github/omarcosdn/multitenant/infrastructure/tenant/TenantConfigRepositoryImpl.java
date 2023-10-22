package io.github.omarcosdn.multitenant.infrastructure.tenant;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class TenantConfigRepositoryImpl implements TenantConfigRepository {

  private final JdbcTemplate jdbcTemplate;

  public TenantConfigRepositoryImpl(@Qualifier("TenantJdbcTemplate") final JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = Objects.requireNonNull(jdbcTemplate, "JdbcTemplate must not be null.");
  }

  @Override
  public Optional<TenantConfigDTO> findByCompanyId(final UUID companyId) {
    final var query = "SELECT company_id, datasource_name FROM company_datasource WHERE company_id = ?";

    final var resultList = jdbcTemplate.query(query, new TenantConfigRowMapper(), companyId);

    return resultList.stream().findFirst();
  }

  @Override
  public List<String> findAllDataSourceNames() {
    final var query = "SELECT DISTINCT datasource_name FROM company_datasource";

    return jdbcTemplate.query(query, new TenantConfigDataSourceNameRowMapper());
  }

  private static class TenantConfigDataSourceNameRowMapper implements RowMapper<String> {

    @Override
    public String mapRow(final ResultSet resultSet, int rowNum) throws SQLException {
      return resultSet.getString("datasource_name");
    }
  }

  private static class TenantConfigRowMapper implements RowMapper<TenantConfigDTO> {

    @Override
    public TenantConfigDTO mapRow(final ResultSet resultSet, int rowNum) throws SQLException {
      final var companyId = resultSet.getObject("company_id", UUID.class);
      final var datasourceName = resultSet.getString("datasource_name");
      return TenantConfigDTO.of(companyId, datasourceName);
    }
  }

}
