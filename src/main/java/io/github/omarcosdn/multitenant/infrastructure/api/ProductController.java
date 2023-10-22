package io.github.omarcosdn.multitenant.infrastructure.api;

import io.github.omarcosdn.multitenant.application.Product;
import io.github.omarcosdn.multitenant.application.ProductService;
import java.util.List;
import java.util.Objects;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "product")
@RestController
public class ProductController {

  private final ProductService service;

  public ProductController(final ProductService service) {
    this.service = Objects.requireNonNull(service);
  }

  @GetMapping
  public ResponseEntity<List<Product>> findAll() {
    final var resultList = service.findAll();
    return ResponseEntity.ok(resultList);
  }

}
