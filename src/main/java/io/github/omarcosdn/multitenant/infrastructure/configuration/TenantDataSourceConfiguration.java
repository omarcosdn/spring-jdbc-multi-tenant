package io.github.omarcosdn.multitenant.infrastructure.configuration;

import java.util.Objects;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class TenantDataSourceConfiguration {

  private final DataSourceProperties properties;

  public TenantDataSourceConfiguration(final DataSourceProperties properties) {
    this.properties = Objects.requireNonNull(properties, "DataSourceProperties must not be null.");
  }

  @Bean(name = "TenantDataSource")
  public DataSource tenantDataSource() {
    final var dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(properties.getDriverClassName());
    dataSource.setUrl(properties.getUrl().formatted("multi_tenant"));
    dataSource.setUsername(properties.getUsername());
    dataSource.setPassword(properties.getPassword());
    return dataSource;
  }

  @Bean(name = "TenantJdbcTemplate")
  public JdbcTemplate tenantJdbcTemplate() {
    return new JdbcTemplate(tenantDataSource());
  }

}
