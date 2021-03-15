package mainPackage.repositories;

import mainPackage.models.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Client findByClientNameAndClientPhoneNumber(String name, String phoneNumber);
}
