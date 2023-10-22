package io.github.omarcosdn.multitenant.application;

import java.util.List;
import java.util.UUID;

public interface ProductRepository {

  List<Product> findAll(UUID companyId);

}
