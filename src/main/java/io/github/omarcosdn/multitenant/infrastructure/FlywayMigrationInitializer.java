package io.github.omarcosdn.multitenant.infrastructure;

import io.github.omarcosdn.multitenant.infrastructure.configuration.DefaultDataSourceRouter;
import jakarta.annotation.PostConstruct;
import java.util.Objects;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@DependsOn("defaultDataSourceRouter")
@Configuration
public class FlywayMigrationInitializer {

  private final DefaultDataSourceRouter dataSourceRouter;

  public FlywayMigrationInitializer(DefaultDataSourceRouter dataSourceRouter) {
    this.dataSourceRouter = Objects.requireNonNull(dataSourceRouter, "DefaultDataSourceRouter must not be null.");
  }

  @PostConstruct
  public void migrate() {
    final var dataSourceMap = dataSourceRouter.getResolvedDataSources();

    for (var dataSource : dataSourceMap.values()) {
      final var fluentConfiguration = new FluentConfiguration()
          .defaultSchema("public")
          .dataSource(dataSource)
          .cleanDisabled(true)
          .schemas("public")
          .baselineOnMigrate(true)
          .baselineVersion("0")
          .outOfOrder(false);
      final var flyway = new Flyway(fluentConfiguration);
      flyway.repair();
      flyway.migrate();
    }
  }
}
