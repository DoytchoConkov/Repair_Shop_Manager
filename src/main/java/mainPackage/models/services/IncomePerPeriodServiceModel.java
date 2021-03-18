package mainPackage.models.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class IncomePerPeriodServiceModel {
    private LocalDateTime leaveDate;
    private BigDecimal totalSparePartsPrice;
    private BigDecimal totalRepairPrice;

    public IncomePerPeriodServiceModel(LocalDateTime leaveDate, BigDecimal totalSparePartsPrice, BigDecimal totalRepairPrice) {
        this.leaveDate = leaveDate;
        this.totalSparePartsPrice = totalSparePartsPrice;
        this.totalRepairPrice = totalRepairPrice;
    }

    public IncomePerPeriodServiceModel() {

    }

    public LocalDateTime getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(LocalDateTime leaveDate) {
        this.leaveDate = leaveDate;
    }

    public BigDecimal getTotalSparePartsPrice() {
        return totalSparePartsPrice;
    }

    public void setTotalSparePartsPrice(BigDecimal totalSparePartsPrice) {
        this.totalSparePartsPrice = totalSparePartsPrice;
    }

    public BigDecimal getTotalRepairPrice() {
        return totalRepairPrice;
    }

    public void setTotalRepairPrice(BigDecimal totalRepairPrice) {
        this.totalRepairPrice = totalRepairPrice;
    }
}
