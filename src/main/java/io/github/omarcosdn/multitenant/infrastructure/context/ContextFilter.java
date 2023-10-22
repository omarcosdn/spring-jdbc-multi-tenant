package io.github.omarcosdn.multitenant.infrastructure.context;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class ContextFilter extends OncePerRequestFilter {

  private static UUID getTraceId(final HttpServletRequest request) {
    final var traceId = request.getHeader("x-trace-id");
    return traceId != null ? UUID.fromString(traceId) : UUID.randomUUID();
  }

  @Override
  protected void doFilterInternal(final HttpServletRequest request, @NonNull final HttpServletResponse response,
                                  final FilterChain filterChain) throws ServletException, IOException {
    try {
      final var companyId = UUID.fromString(request.getHeader("x-company-id"));
      final var traceId = getTraceId(request);
      final var context = Context.create(companyId, traceId);
      ThreadContext.of(context);
      filterChain.doFilter(request, response);
    } finally {
      ThreadContext.clear();
    }
  }

}
