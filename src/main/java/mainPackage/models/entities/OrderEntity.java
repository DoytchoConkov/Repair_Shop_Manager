package mainPackage.models.entities;


import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class OrderEntity extends BaseEntity {
    private ModelEntity model;
    private String serialNumber;
    private DamageEntity damage;
    private String note;
    private ClientEntity client;
    private LocalDate receiveDate;
    private String imageUrl;
    private List<SparePartEntity> spareParts = new ArrayList<>();
    private BigDecimal totalSparePartsPrice;
    private BigDecimal totalRepairPrice;
    private User user;
    private LocalDate leaveDate;

    public OrderEntity() {
    }

    @Column(name = "serial_number", nullable = false, length = 20)
    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Column(name = "note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Column(name = "receive_date", nullable = false)
    public LocalDate getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(LocalDate receiveDate) {
        this.receiveDate = receiveDate;
    }

    @Column(name = "leave_date")
    public LocalDate getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(LocalDate leaveDate) {
        this.leaveDate = leaveDate;
    }

    @ManyToOne
    public DamageEntity getDamage() {
        return damage;
    }

    public void setDamage(DamageEntity damage) {
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
    public List<SparePartEntity> getSpareParts() {
        return spareParts;
    }

    public void setSpareParts(List<SparePartEntity> spareParts) {
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
    public ClientEntity getClient() {
        return client;
    }

    public void setClient(ClientEntity client) {
        this.client = client;
    }

    @ManyToOne
    public ModelEntity getModel() {
        return model;
    }

    public void setModel(ModelEntity model) {
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
