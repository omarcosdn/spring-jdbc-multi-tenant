package io.github.omarcosdn.multitenant.infrastructure.configuration;

import io.github.omarcosdn.multitenant.infrastructure.context.ThreadContext;
import io.github.omarcosdn.multitenant.infrastructure.tenant.TenantConfigService;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

@Component
public class DefaultDataSourceRouter extends AbstractRoutingDataSource {

  private final DataSourceProperties properties;
  private final TenantConfigService service;

  public DefaultDataSourceRouter(final DataSourceProperties properties, final TenantConfigService service) {
    this.properties = Objects.requireNonNull(properties, "DataSourceProperties must not be null.");
    this.service = Objects.requireNonNull(service, "TenantConfigService must not be null.");

    final Map<Object, Object> dataSourceMap = service.findAllDataSourceNames().stream()
        .collect(Collectors.toMap(datasourceName -> datasourceName, this::createDataSource));

    this.setTargetDataSources(dataSourceMap);
  }

  @Override
  protected Object determineCurrentLookupKey() {
    final var companyId = ThreadContext.get().getCompanyId();
    return service.findDataSourceNameByCompanyId(companyId);
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
