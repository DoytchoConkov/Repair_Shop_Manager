package мainPackage.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "clients")
public class Client extends BaseEntity {
    private String clientName;
    private String clientEmail;
    private String clientPhoneNumber;
    private String clientNote;
    private List<Order> orders;

    public Client() {
    }

    @Column(name = "full_name", length = 40)
    public String getClientName() {
        return clientName;
    }

    public void setClientName(String fullName) {
        this.clientName = fullName;
    }

    @Column(name = "email", length = 40)
    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String email) {
        this.clientEmail = email;
    }

    @Column(name = "phone_number", length = 20)
    public String getClientPhoneNumber() {
        return clientPhoneNumber;
    }

    public void setClientPhoneNumber(String phoneNumber) {
        this.clientPhoneNumber = phoneNumber;
    }

    @Column(name = "client_note")
    public String getClientNote() {
        return clientNote;
    }

    public void setClientNote(String clientNote) {
        this.clientNote = clientNote;
    }

    @OneToMany(mappedBy = "client")
    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> order) {
        this.orders = order;
    }
}
