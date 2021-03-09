package мainPackage.repositories;

import мainPackage.models.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findById(String id);
    Client findByClientNameAndClientPhoneNumber(String name,String phoneNumber);
}
