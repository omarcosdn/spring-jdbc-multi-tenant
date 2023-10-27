package io.github.omarcosdn.multitenant.infrastructure.configuration;

import static java.util.Objects.requireNonNull;

import io.github.omarcosdn.multitenant.infrastructure.context.ContextHolder;
import io.github.omarcosdn.multitenant.infrastructure.lookup.LookupDatabaseService;
import java.util.Map;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

@Component
public class TenantDataSourceRouter extends AbstractRoutingDataSource {

  private final TenantDataSourceProperties properties;
  private final LookupDatabaseService service;

  public TenantDataSourceRouter(final TenantDataSourceProperties properties, final LookupDatabaseService service) {
    this.properties = requireNonNull(properties, "TenantDataSourceProperties must not be null.");
    this.service = requireNonNull(service, "LookupDatabaseService must not be null.");

    final Map<Object, Object> dataSourceMap = service.findAllDataSourceNames().stream()
        .collect(Collectors.toMap(datasourceName -> datasourceName, this::createDataSource));

    this.setTargetDataSources(dataSourceMap);
  }

  @Override
  protected Object determineCurrentLookupKey() {
    return ContextHolder.map(ctx -> service.findDataSourceNameByCompanyId(ctx.getCompanyId()));
  }

  private DataSource createDataSource(final String datasourceName) {
    final var dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(properties.getDriverClassName());
    dataSource.setUrl(properties.getUrl().formatted(datasourceName));
    dataSource.setUsername(properties.getUsername());
    dataSource.setPassword(properties.getPassword());
    return dataSource;
  }

}
