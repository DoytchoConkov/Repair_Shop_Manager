package mainPackage.models.views;

import java.math.BigDecimal;

public class IncomePerDayViewModel {
    private String leaveDate;
    private BigDecimal sparePartsTotalPrice;
    private BigDecimal totalPrice;

    public IncomePerDayViewModel() {
    }

    public String getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(String leaveDate) {
        this.leaveDate = leaveDate;
    }

    public BigDecimal getSparePartsTotalPrice() {
        return sparePartsTotalPrice;
    }

    public void setSparePartsTotalPrice(BigDecimal sparePartsTotalPrice) {
        this.sparePartsTotalPrice = sparePartsTotalPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
