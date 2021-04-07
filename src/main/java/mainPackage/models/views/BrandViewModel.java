package mainPackage.models.views;

import mainPackage.models.entities.ModelEntity;

import java.util.List;

public class BrandViewModel {
    private String brandName;
    private List<ModelEntity> models;

    public BrandViewModel() {
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public List<ModelEntity> getModels() {
        return models;
    }

    public void setModels(List<ModelEntity> models) {
        this.models = models;
    }
}
