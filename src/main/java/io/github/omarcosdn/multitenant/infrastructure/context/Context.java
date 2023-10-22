package io.github.omarcosdn.multitenant.infrastructure.context;

import java.util.UUID;

public final class Context {

  private final UUID companyId;
  private final UUID traceId;

  private Context(final UUID companyId, final UUID traceId) {
    this.companyId = companyId;
    this.traceId = traceId;
  }

  public static Context create(final UUID companyId, final UUID traceId) {
    return new Context(companyId, traceId);
  }

  public UUID getCompanyId() {
    return companyId;
  }

  public UUID getTraceId() {
    return this.traceId;
  }

}
