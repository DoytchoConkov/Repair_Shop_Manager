package mainPackage.repositories;

import mainPackage.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User as u where u.username=:username")
    Optional<User> findByUsername(String username);

    @Query("select count(u.id) from User as u JOIN u.roles as r WHERE r.role=1")
    int findAdminUsers();

    @Query("select u from User as u order by u.username")
    List<User> findAll();
}
