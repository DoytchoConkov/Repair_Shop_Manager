package mainPackage.models.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public class OrderFixServiceModel {
    private Long id;
    private Set<Long> sparePartIds;
    private BigDecimal sparePartPrice;
    private BigDecimal totalPrice;

    public OrderFixServiceModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
