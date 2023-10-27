package io.github.omarcosdn.multitenant.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import static java.util.Objects.requireNonNull;

@DependsOn("tenantDataSourceRouter")
@Configuration
public class TenantDataSourceConfiguration {

  private final TenantDataSourceRouter router;

  public TenantDataSourceConfiguration(final TenantDataSourceRouter tenantDataSourceRouter) {
    this.router = requireNonNull(tenantDataSourceRouter, "TenantDataSourceRouter must not be null.");
  }

  @Bean
  @Primary
  public JdbcTemplate tenantJdbcTemplate() {
    return new JdbcTemplate(router);
  }
}
