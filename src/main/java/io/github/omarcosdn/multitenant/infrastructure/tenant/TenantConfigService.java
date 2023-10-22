package io.github.omarcosdn.multitenant.infrastructure.tenant;

import io.github.omarcosdn.multitenant.infrastructure.cache.TenantConfigCache;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TenantConfigService {

  private static final Logger log = LoggerFactory.getLogger(TenantConfigService.class);
  
  private final TenantConfigCache cache;
  private final TenantConfigRepository repository;

  public TenantConfigService(final TenantConfigCache cache, final TenantConfigRepository repository) {
    this.cache = Objects.requireNonNull(cache, "TenantConfigCache must not be null.");
    this.repository = Objects.requireNonNull(repository, "TenantConfigRepository must not be null.");
  }

  private static Supplier<TenantConfigNotFoundException> notFound(final UUID companyId) {
    return () -> new TenantConfigNotFoundException("DataSource not found for CompanyId %s".formatted(companyId));
  }

  public String findDataSourceNameByCompanyId(final UUID companyId) {
    final var cachedDataSourceName = cache.get(companyId.toString());
    if (cachedDataSourceName != null) {
      log.info("DataSource name {} retrieved from cache.", cachedDataSourceName.getDataSourceName());
      return cachedDataSourceName.getDataSourceName();
    }

    final var tenantConfig = repository.findByCompanyId(companyId).orElseThrow(notFound(companyId));

    cache.add(companyId.toString(), tenantConfig);

    log.info("DataSource name {} retrieved from database.", tenantConfig.getDataSourceName());

    return tenantConfig.getDataSourceName();
  }

  public List<String> findAllDataSourceNames() {
    return repository.findAllDataSourceNames().stream().toList();
  }

}
