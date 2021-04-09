package mainPackage.services;

import mainPackage.models.entities.ClientEntity;
import mainPackage.models.services.ClientServiceModel;
import mainPackage.models.views.ClientViewModel;
import mainPackage.models.views.OrderViewModel;

import java.util.List;

public interface ClientService {
    ClientEntity findByNameAndPhoneNumber(ClientServiceModel clientServiceModel);

    List<ClientViewModel> getAllClientNames();

    List<ClientViewModel> findByNameOrPhoneNumber(String clientName);

    List<ClientViewModel> getAll();

    List<ClientViewModel> getAllPhoneNumbers();

    ClientViewModel findClientByClientId(Long valueOf);
}
