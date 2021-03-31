package mainPackage.models.bindings;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class OrderFixBindingModel {
    private Long id;
    private String[] sparePartIds;
    private MultipartFile imageUrl;
    @NotNull(message = "Price can not be empty.")
    @DecimalMin(value = "0", message = "Price must be positive")
    private BigDecimal sparePartPrice;
    @NotNull(message = "Price can not be empty.")
    @DecimalMin(value = "0", message = "Price must be positive")
    private BigDecimal totalPrice;
    private String note;

    public OrderFixBindingModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String[] getSparePartIds() {
        return sparePartIds;
    }

    public void setSparePartIds(String[] sparePartIds) {
        this.sparePartIds = sparePartIds;
    }

    public MultipartFile  getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(MultipartFile  imageUrl) {
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
