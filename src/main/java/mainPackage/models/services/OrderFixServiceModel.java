package mainPackage.models.services;

import java.math.BigDecimal;
import java.util.List;

public class OrderFixServiceModel {
    private Long id;
    private List<Long> spId;
    private BigDecimal spPrice;
    private BigDecimal totalPrice;

    public OrderFixServiceModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getSpId() {
        return spId;
    }

    public void setSpId(List<Long> spId) {
        this.spId = spId;
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
