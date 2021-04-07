package mainPackage.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "brands")
public class BrandEntity extends BaseEntity {
    private String brandName;

    public BrandEntity() {
    }

    public BrandEntity(String brandName) {
        this.brandName = brandName;
    }

    @Column(name = "brand_name", nullable = false, length = 20, unique = true)
    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}
