package мainPackage.repositories;

import мainPackage.models.entities.RoleName;
import мainPackage.models.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole,Long> {

    Optional<UserRole> findByRole(RoleName role);
}
