package mainPackage.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "brands")
public class Brand extends BaseEntity {
    private String brandName;
    private List<Model> models;

    public Brand() {
    }

    public Brand(String brandName) {
        this.brandName = brandName;
    }

    @Column(name = "brand_name", nullable = false, length = 15, unique = true)
    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    @OneToMany(mappedBy = "brand")
    public List<Model> getModels() {
        return models;
    }

    public void setModels(List<Model> models) {
        this.models = models;
    }
}