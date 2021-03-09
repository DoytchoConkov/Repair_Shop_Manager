package мainPackage.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "damages")
public class Damage extends BaseEntity{
    private String damageName;
    private List<Order> orders;

    public Damage() {
    }

    public Damage(String damageName) {
        this.damageName = damageName;
    }

    @Column(name = "damage", unique = true, length = 100)
    public String getDamageName() {
        return damageName;
    }

    public void setDamageName(String damagename) {
        this.damageName = damagename;
    }

    @OneToMany(mappedBy = "damage")
    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> order) {
        this.orders = order;
    }
}
