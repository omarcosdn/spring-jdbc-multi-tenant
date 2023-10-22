package io.github.omarcosdn.multitenant.application;

import io.github.omarcosdn.multitenant.infrastructure.context.ThreadContext;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

  private final ProductRepository repository;

  public ProductService(final ProductRepository repository) {
    this.repository = Objects.requireNonNull(repository);
  }

  public List<Product> findAll() {
    final var companyId = ThreadContext.get().getCompanyId();
    return repository.findAll(companyId);
  }

}
