package mainPackage.repositories;

import mainPackage.models.entities.RoleName;
import mainPackage.models.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole,Long> {

    Optional<UserRole> findByRole(RoleName role);
}
