package mainPackage.models.entities;

import javax.persistence.*;

@Entity
@Table(name = "models")
public class ModelEntity extends BaseEntity{
    private BrandEntity brand;
    private String modelName;

    public ModelEntity() {
    }

    public ModelEntity(String modelName, BrandEntity brand) {
        this.modelName = modelName;
        this.brand = brand;
    }

    @Column(name = "model_name", nullable = false, length = 30, unique = true)
    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    @ManyToOne
    public BrandEntity getBrand() {
        return brand;
    }

    public void setBrand(BrandEntity brand) {
        this.brand = brand;
    }
}
