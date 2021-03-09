package мainPackage.models.views;

import мainPackage.models.entities.Model;

import java.util.List;

public class BrandViewModel {
    private String brandName;
    private List<Model> models;

    public BrandViewModel() {
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public List<Model> getModels() {
        return models;
    }

    public void setModels(List<Model> models) {
        this.models = models;
    }
}
