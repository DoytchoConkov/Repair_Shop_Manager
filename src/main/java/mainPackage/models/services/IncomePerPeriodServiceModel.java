package mainPackage.models.services;

import java.math.BigDecimal;
import java.time.LocalDate;

public class IncomePerPeriodServiceModel {
    private LocalDate leaveDate;
    private BigDecimal totalSparePartsPrice;
    private BigDecimal totalRepairPrice;

    public IncomePerPeriodServiceModel() {
    }

    public IncomePerPeriodServiceModel(LocalDate leaveDate, BigDecimal totalSparePartsPrice, BigDecimal totalRepairPrice) {
        this.leaveDate = leaveDate;
        this.totalSparePartsPrice = totalSparePartsPrice;
        this.totalRepairPrice = totalRepairPrice;
    }

    public LocalDate getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(LocalDate leaveDate) {
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
