package io.github.omarcosdn.multitenant.infrastructure.cache;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfiguration {

  @Bean
  public TenantConfigCache tenantConfigCache() {
    return TenantConfigCache.create();
  }

}
