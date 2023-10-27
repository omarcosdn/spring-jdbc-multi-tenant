package io.github.omarcosdn.multitenant.infrastructure.lookup;

import io.github.omarcosdn.multitenant.infrastructure.cache.LookupDatabaseCache;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LookupDatabaseService {

  private static final Logger log = LoggerFactory.getLogger(LookupDatabaseService.class);

  private final LookupDatabaseCache cache;
  private final LookupDatabaseRepository repository;

  public LookupDatabaseService(final LookupDatabaseCache cache, final LookupDatabaseRepository repository) {
    this.cache = Objects.requireNonNull(cache, "LookupDatabaseCache must not be null.");
    this.repository = Objects.requireNonNull(repository, "LookupDatabaseRepository must not be null.");
  }

  private static Supplier<TenantDatabaseNotFoundException> notFound(final UUID companyId) {
    return () -> new TenantDatabaseNotFoundException(companyId);
  }

  public String findDataSourceNameByCompanyId(final UUID companyId) {
    final var cachedName = cache.get(companyId.toString());
    if (cachedName != null) {
      log.info("DataSource name {} retrieved from cache.", cachedName.getDataSourceName());
      return cachedName.getDataSourceName();
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
