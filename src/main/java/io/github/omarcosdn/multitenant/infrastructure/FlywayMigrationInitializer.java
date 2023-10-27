package io.github.omarcosdn.multitenant.infrastructure;

import io.github.omarcosdn.multitenant.infrastructure.configuration.TenantDataSourceRouter;
import jakarta.annotation.PostConstruct;
import java.util.Objects;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.springframework.stereotype.Component;

@Component
public class FlywayMigrationInitializer {

  private final TenantDataSourceRouter router;

  public FlywayMigrationInitializer(TenantDataSourceRouter router) {
    this.router = Objects.requireNonNull(router, "TenantDataSourceRouter must not be null.");
  }

  @PostConstruct
  public void migrate() {
    final var dataSourceMap = router.getResolvedDataSources();

    for (var dataSource : dataSourceMap.values()) {
      final var fluentConfiguration =
          new FluentConfiguration()
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
