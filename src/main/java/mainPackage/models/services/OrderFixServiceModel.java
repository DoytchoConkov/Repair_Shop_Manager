package mainPackage.models.services;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Set;

public class OrderFixServiceModel {
    private Long id;
    private MultipartFile imageUrl;
    private Set<Long> sparePartIds;
    private BigDecimal sparePartPrice;
    private BigDecimal totalPrice;
    private String note;

    public OrderFixServiceModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MultipartFile getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(MultipartFile imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Set<Long> getSparePartIds() {
        return sparePartIds;
    }

    public void setSparePartIds(Set<Long> sparePartIds) {
        this.sparePartIds = sparePartIds;
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
