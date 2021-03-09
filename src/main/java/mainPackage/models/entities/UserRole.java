package mainPackage.models.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class UserRole extends BaseEntity{
    public UserRole() {
    }

    public UserRole(RoleName role) {
        this.role = role;
    }

    @Enumerated(EnumType.STRING)
    private RoleName role;

    public RoleName getRole() {
        return role;
    }

    public void setRole(RoleName role) {
        this.role = role;
    }
}
