package mainPackage.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "damages")
public class DamageEntity extends BaseEntity{
    private String damageName;
    private List<OrderEntity> orders;

    public DamageEntity() {
    }

    public DamageEntity(String damageName) {
        this.damageName = damageName;
    }

    @Column(name = "damage", unique = true, length = 100)
    public String getDamageName() {
        return damageName;
    }

    public void setDamageName(String damage) {
        this.damageName = damage;
    }

    @OneToMany(mappedBy = "damage")
    public List<OrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderEntity> order) {
        this.orders = order;
    }
}
