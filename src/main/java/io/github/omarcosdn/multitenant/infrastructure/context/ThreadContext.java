package io.github.omarcosdn.multitenant.infrastructure.context;

public final class ThreadContext {

  private static final ThreadLocal<Context> THREAD_LOCAL = new ThreadLocal<>();

  private ThreadContext() {
  }

  public static Context get() {
    return THREAD_LOCAL.get();
  }

  public static void of(final Context context) {
    THREAD_LOCAL.set(context);
  }

  public static void clear() {
    THREAD_LOCAL.remove();
  }

}
