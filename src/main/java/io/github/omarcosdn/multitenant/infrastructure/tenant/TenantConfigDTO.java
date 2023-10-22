package io.github.omarcosdn.multitenant.infrastructure.tenant;

import java.util.UUID;

public class TenantConfigDTO {

  private final UUID companyId;
  private final String datasourceName;

  private TenantConfigDTO(final UUID companyId, final String datasourceName) {
    this.companyId = companyId;
    this.datasourceName = datasourceName;
  }

  public static TenantConfigDTO of(final UUID companyId, final String datasourceName) {
    return new TenantConfigDTO(companyId, datasourceName);
  }

  public UUID getCompanyId() {
    return this.companyId;
  }

  public String getDataSourceName() {
    return this.datasourceName;
  }

}
