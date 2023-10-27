package io.github.omarcosdn.multitenant.infrastructure.lookup;

import java.util.UUID;

public class LookupDatabaseDTO {

  private final UUID companyId;
  private final String datasourceName;

  private LookupDatabaseDTO(final UUID companyId, final String datasourceName) {
    this.companyId = companyId;
    this.datasourceName = datasourceName;
  }

  public static LookupDatabaseDTO of(final UUID companyId, final String datasourceName) {
    return new LookupDatabaseDTO(companyId, datasourceName);
  }

  public UUID getCompanyId() {
    return this.companyId;
  }

  public String getDataSourceName() {
    return this.datasourceName;
  }
}
