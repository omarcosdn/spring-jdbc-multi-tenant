package io.github.omarcosdn.multitenant.infrastructure.tenant;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TenantConfigRepository {

  Optional<TenantConfigDTO> findByCompanyId(UUID companyId);

  List<String> findAllDataSourceNames();

}
