package io.github.omarcosdn.multitenant.infrastructure.configuration;

import static java.util.Objects.requireNonNull;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class LookupDataSourceConfiguration {

  private final LookupDataSourceProperties properties;

  public LookupDataSourceConfiguration(final LookupDataSourceProperties properties) {
    this.properties = requireNonNull(properties, "LookupDataSourceProperties must not be null.");
  }

  @Bean
  public DataSource lookupDataSource() {
    final var dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(properties.getDriverClassName());
    dataSource.setUrl(properties.getUrl());
    dataSource.setUsername(properties.getUsername());
    dataSource.setPassword(properties.getPassword());
    return dataSource;
  }

  @Bean
  public JdbcTemplate lookupJdbcTemplate() {
    return new JdbcTemplate(lookupDataSource());
  }
}
