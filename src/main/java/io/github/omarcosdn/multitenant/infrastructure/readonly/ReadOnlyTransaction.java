package io.github.omarcosdn.multitenant.infrastructure.readonly;

import java.util.function.Supplier;

public final class ReadOnlyTransaction {

  private static final ThreadLocal<Boolean> THREAD_LOCAL = new ThreadLocal<>();

  private ReadOnlyTransaction() {}

  public static <T> T execute(Supplier<T> supplier) {
    try {
      setReadOnlyContext();
      return supplier.get();
    } finally {
      removeReadOnlyContext();
    }
  }

  public static void execute(Runnable runnable) {
    try {
      setReadOnlyContext();
      runnable.run();
    } finally {
      removeReadOnlyContext();
    }
  }

  private static void setReadOnlyContext() {
    THREAD_LOCAL.set(Boolean.TRUE);
  }

  private static void removeReadOnlyContext() {
    THREAD_LOCAL.remove();
  }

  public static boolean isPresent() {
    return THREAD_LOCAL.get() != null;
  }
}
