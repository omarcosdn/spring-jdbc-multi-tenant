package io.github.omarcosdn.multitenant.infrastructure.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public abstract class CacheStore<T> {

  private final Cache<String, T> cache;

  protected CacheStore(int expiryDuration, TimeUnit timeUnit) {
    this.cache = CacheBuilder.newBuilder().expireAfterWrite(expiryDuration, timeUnit)
        .concurrencyLevel(Runtime.getRuntime().availableProcessors()).build();
  }

  public T get(final String key) {
    return cache.getIfPresent(Objects.requireNonNull(key, "The key must not be null"));
  }

  public void add(final String key, final T value) {
    cache.put(Objects.requireNonNull(key, "The key must not be null"),
              Objects.requireNonNull(value, "The value must not be null"));
  }

}
