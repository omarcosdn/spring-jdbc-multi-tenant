package io.github.omarcosdn.multitenant.infrastructure.context;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

public final class ContextHolder {

  private static final ThreadLocal<Context> THREAD_LOCAL = new ThreadLocal<>();

  private ContextHolder() {}

  public static Context get() {
    final var context = THREAD_LOCAL.get();
    if (context == null) {
      throw new NoSuchElementException("No context present");
    }
    return context;
  }

  public static void create(final Context context) {
    Objects.requireNonNull(context);
    THREAD_LOCAL.set(context);
  }

  public static void clear() {
    THREAD_LOCAL.remove();
  }

  public static void ifPresent(Consumer<Context> action) {
    Objects.requireNonNull(action);
    final var context = THREAD_LOCAL.get();
    if (context != null) {
      action.accept(context);
    }
  }

  public static <U> U map(Function<Context, U> mapper) {
    Objects.requireNonNull(mapper);
    return mapper.apply(get());
  }

  public static boolean isPresent() {
    return THREAD_LOCAL.get() != null;
  }
}
