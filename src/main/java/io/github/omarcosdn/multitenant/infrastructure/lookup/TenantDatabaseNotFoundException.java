package io.github.omarcosdn.multitenant.infrastructure.lookup;

import java.util.UUID;

public class TenantDatabaseNotFoundException extends RuntimeException {

  public TenantDatabaseNotFoundException(final UUID companyId) {
    super("DataSource not found for CompanyId %s".formatted(companyId));
  }
}
