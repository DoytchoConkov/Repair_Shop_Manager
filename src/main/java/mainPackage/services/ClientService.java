package mainPackage.services;

import mainPackage.models.entities.Client;
import mainPackage.models.services.ClientServiceModel;

public interface ClientService {
    Client findByNameAndPhoneNumber(ClientServiceModel clientServiceModel);
}
