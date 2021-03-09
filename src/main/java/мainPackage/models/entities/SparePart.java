package мainPackage.models.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "spare_parts")
public class SparePart extends BaseEntity{
    private Model model;
    private String sparePartName;
    private BigDecimal price;
    private int pieces;
    private List<Order> orders;

    public SparePart() {
    }

    public SparePart(Model model, String sparePartName) {
        this.model = model;
        this.sparePartName = sparePartName;
        this.price = BigDecimal.valueOf(0);
        this.pieces = 0;
        this.orders = new ArrayList<>();
    }


    @ManyToOne
    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

      @Column(name = "price", nullable = false)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Column(name = "spare_part_name", nullable = false, length = 20)
    public String getSparePartName() {
        return sparePartName;
    }

    public void setSparePartName(String sparePartName) {
        this.sparePartName = sparePartName;
    }


    @Column(name = "quantity", nullable = false)
    public int getPieces() {
        return pieces;
    }

    public void setPieces(int count) {
        this.pieces = count;
    }

    @ManyToMany(mappedBy = "spareParts")
    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
