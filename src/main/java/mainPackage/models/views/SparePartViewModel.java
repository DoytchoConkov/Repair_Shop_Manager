package mainPackage.models.views;

import java.math.BigDecimal;

public class SparePartViewModel {
    private Long id;
    private String brand;
    private String model;
    private String sparePartName;
    private BigDecimal price;
    private int pieces;

    public SparePartViewModel() {
    }

    public SparePartViewModel(String brand, String model, String sparePartName) {
        this.brand = brand;
        this.model = model;
        this.sparePartName = sparePartName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSparePartName() {
        return sparePartName;
    }

    public void setSparePartName(String sparePartName) {
        this.sparePartName = sparePartName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getPieces() { return pieces; }

    public void setPieces(int pieces) {
        this.pieces = pieces;
    }
}
