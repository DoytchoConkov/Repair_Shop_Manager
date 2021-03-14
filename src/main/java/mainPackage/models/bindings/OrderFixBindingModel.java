package mainPackage.models.bindings;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class OrderFixBindingModel {
    private Long spId;

    private String imageUrl;
    @NotNull(message = "Price can not be empty.")
    @DecimalMin(value = "0", message = "Price must be positive")
    private BigDecimal spPrice;
    @NotNull(message = "Price can not be empty.")
    @DecimalMin(value = "0", message = "Price must be positive")
    private BigDecimal totalPrice;

    public OrderFixBindingModel() {
    }

    public Long getSpId() {
        return spId;
    }

    public void setSpId(Long spId) {
        this.spId = spId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BigDecimal getSpPrice() {
        return spPrice;
    }

    public void setSpPrice(BigDecimal spPrice) {
        this.spPrice = spPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
