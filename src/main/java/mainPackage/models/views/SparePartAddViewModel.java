package mainPackage.models.views;

import java.math.BigDecimal;

public class SparePartAddViewModel {
    private Long id;
    private String sparePartName;
    private BigDecimal price;

    public SparePartAddViewModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
