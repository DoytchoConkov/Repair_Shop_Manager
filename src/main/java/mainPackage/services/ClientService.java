package mainPackage.services;

import mainPackage.models.entities.Client;
import mainPackage.models.services.ClientServiceModel;
import mainPackage.models.views.ClientViewModel;

import java.util.List;

public interface ClientService {
    Client findByNameAndPhoneNumber(ClientServiceModel clientServiceModel);

    List<ClientViewModel> getAllClientNames();

    List<ClientViewModel> findByNameOrPhoneNumber(String clientName);
}
