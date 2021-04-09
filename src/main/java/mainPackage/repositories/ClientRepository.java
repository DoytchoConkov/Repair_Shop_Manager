package mainPackage.repositories;

import mainPackage.models.entities.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
    @Query("select c from ClientEntity as c order by c.clientName , c.clientPhoneNumber")
    List<ClientEntity> findAll();

    ClientEntity findByClientNameAndClientPhoneNumber(String name, String phoneNumber);

    @Query("select cl from ClientEntity as cl where cl.clientName like :name or cl.clientPhoneNumber like :name order by cl.clientName , cl.clientPhoneNumber")
    List<ClientEntity> findByClientNameOrPhoneNumber(@Param("name") String name);

    @Query("select c from ClientEntity as c order by c.clientPhoneNumber")
    List<ClientEntity> findAllPhoneNumbers();

    @Query("select c from ClientEntity as c where c.id=:id")
    ClientEntity getClientById(@Param("id") Long id);
}
