package io.github.omarcosdn.multitenant.application;

import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("product")
public class Product {

  @Id
  @Column("product_id")
  private UUID productId;

  @Column("name")
  private String name;

  @Column("company_id")
  private UUID companyId;

  private Product(final UUID productId, final String name, final UUID companyId) {
    this.productId = productId;
    this.name = name;
    this.companyId = companyId;
  }

  public static Product restore(final UUID productId, final String name, final UUID companyId) {
    return new Product(productId, name, companyId);
  }

  public UUID getProductId() {
    return productId;
  }

  public String getName() {
    return name;
  }

  public UUID getCompanyId() {
    return companyId;
  }

}
