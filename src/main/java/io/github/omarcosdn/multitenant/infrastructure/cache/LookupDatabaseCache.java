package io.github.omarcosdn.multitenant.infrastructure.cache;

import io.github.omarcosdn.multitenant.infrastructure.lookup.LookupDatabaseDTO;
import java.util.concurrent.TimeUnit;

public final class LookupDatabaseCache extends CacheStore<LookupDatabaseDTO> {

  private LookupDatabaseCache(int expiryDuration, TimeUnit timeUnit) {
    super(expiryDuration, timeUnit);
  }

  public static LookupDatabaseCache create() {
    return new LookupDatabaseCache(30, TimeUnit.SECONDS);
  }
}
