package мainPackage.models.entities;

import javax.persistence.*;

@Entity
@Table(name = "models")
public class Model extends BaseEntity{
    private Brand brand;
    private String modelName;

    public Model() {
    }

    public Model(String modelName, Brand brand) {
        this.modelName = modelName;
        this.brand = brand;
    }

    @Column(name = "model_name", nullable = false, length = 100, unique = true)
    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    @ManyToOne
    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }
}
