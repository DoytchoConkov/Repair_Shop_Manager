package mainPackage.repositories;

import mainPackage.models.entities.Client;
import mainPackage.models.views.ClientViewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query("select c from Client  as c order by c.clientName , c.clientPhoneNumber")
    List<Client> findAll();

    Client findByClientNameAndClientPhoneNumber(String name, String phoneNumber);

    @Query("select cl from Client as cl where cl.clientName like :name or cl.clientPhoneNumber like :name order by cl.clientName , cl.clientPhoneNumber")
    List<Client> findByClientNameOrPhoneNumber(@Param("name") String name);
}
