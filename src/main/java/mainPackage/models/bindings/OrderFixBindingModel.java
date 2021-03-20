package mainPackage.models.bindings;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public class OrderFixBindingModel {
    private String sparePartName;
    private String imageUrl;
    @NotNull(message = "Price can not be empty.")
    @DecimalMin(value = "0", message = "Price must be positive")
    private BigDecimal sparePartPrice;
    @NotNull(message = "Price can not be empty.")
    @DecimalMin(value = "0", message = "Price must be positive")
    private BigDecimal totalPrice;

    public OrderFixBindingModel() {
    }

    public String getSparePartName() {
        return sparePartName;
    }

    public void setSparePartName(String sparePartName) {
        this.sparePartName = sparePartName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BigDecimal getSparePartPrice() {
        return sparePartPrice;
    }

    public void setSparePartPrice(BigDecimal sparePartPrice) {
        this.sparePartPrice = sparePartPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
