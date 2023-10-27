package io.github.omarcosdn.multitenant.infrastructure.lookup;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LookupDatabaseRepository {

  Optional<LookupDatabaseDTO> findByCompanyId(UUID companyId);

  List<String> findAllDataSourceNames();
}
