package мainPackage.services;

import мainPackage.models.entities.Client;
import мainPackage.models.services.ClientServiceModel;

public interface ClientService {
    Client findByNameAndPhoneNumber(ClientServiceModel clientServiceModel);
}
