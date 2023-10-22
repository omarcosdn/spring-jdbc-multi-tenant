package io.github.omarcosdn.multitenant.infrastructure.configuration;

import java.util.Objects;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

@DependsOn("defaultDataSourceRouter")
@Configuration
public class DefaultDataSourceConfiguration {

  private final DefaultDataSourceRouter defaultDataSourceRouter;

  public DefaultDataSourceConfiguration(final DefaultDataSourceRouter defaultDataSourceRouter) {
    this.defaultDataSourceRouter = Objects.requireNonNull(defaultDataSourceRouter, "DefaultDataSourceRouter must not be null.");
  }

  @Primary
  @Bean(name = "DefaultJdbcTemplate")
  public JdbcTemplate jdbcTemplate() {
    return new JdbcTemplate(defaultDataSourceRouter);
  }

}
