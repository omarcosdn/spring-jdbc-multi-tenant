package io.github.omarcosdn.multitenant.infrastructure.tenant;

public class TenantConfigNotFoundException extends RuntimeException {

  public TenantConfigNotFoundException(final String message) {
    super(message);
  }

}
