package io.github.omarcosdn.multitenant.infrastructure.cache;

import io.github.omarcosdn.multitenant.infrastructure.tenant.TenantConfigDTO;
import java.util.concurrent.TimeUnit;

public final class TenantConfigCache extends CacheStore<TenantConfigDTO> {

  private TenantConfigCache(int expiryDuration, TimeUnit timeUnit) {
    super(expiryDuration, timeUnit);
  }

  public static TenantConfigCache create() {
    return new TenantConfigCache(30, TimeUnit.SECONDS);
  }

}
