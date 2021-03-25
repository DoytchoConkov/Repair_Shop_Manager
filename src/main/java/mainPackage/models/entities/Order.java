package mainPackage.models.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    private Model model;
    private String serialNumber;
    private Damage damage;
    private String notes;
    private Client client;
    private LocalDateTime receiveDate;
    private String imageUrl;
    private List<SparePart> spareParts = new ArrayList<>();
    private BigDecimal totalSparePartsPrice;
    private BigDecimal totalRepairPrice;
    private User user;
    private LocalDateTime leaveDate;

    public Order() {
    }

    @Column(name = "serial_number", nullable = false, length = 20)
    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Column(name = "notes")
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Column(name = "receive_date", nullable = false)
    public LocalDateTime getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(LocalDateTime receiveDate) {
        this.receiveDate = receiveDate;
    }

    @Column(name = "leave_date")
    public LocalDateTime getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(LocalDateTime leaveDate) {
        this.leaveDate = leaveDate;
    }

    @ManyToOne
    public Damage getDamage() {
        return damage;
    }

    public void setDamage(Damage damage) {
        this.damage = damage;
    }

    @Column(name = "image_Url")
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @ManyToMany
    public List<SparePart> getSpareParts() {
        return spareParts;
    }

    public void setSpareParts(List<SparePart> spareParts) {
        this.spareParts = spareParts;
    }

    @Column(name = "total_spare_parts_price")
    public BigDecimal getTotalSparePartsPrice() {
        return totalSparePartsPrice;
    }

    public void setTotalSparePartsPrice(BigDecimal totalSparePartsPrice) {
        this.totalSparePartsPrice = totalSparePartsPrice;
    }

    @Column(name = "total_repair_price")
    public BigDecimal getTotalRepairPrice() {
        return totalRepairPrice;
    }

    public void setTotalRepairPrice(BigDecimal totalRepairPrice) {
        this.totalRepairPrice = totalRepairPrice;
    }

    @ManyToOne
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @ManyToOne
    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    @ManyToOne
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
