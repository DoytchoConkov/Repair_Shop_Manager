package мainPackage.services.impl;

import мainPackage.models.entities.Client;
import мainPackage.models.services.ClientServiceModel;
import мainPackage.repositories.ClientRepository;
import мainPackage.services.ClientService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final ModelMapper modelMapper;

    public ClientServiceImpl(ClientRepository clientRepository, ModelMapper modelMapper) {
        this.clientRepository = clientRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Client findByNameAndPhoneNumber(ClientServiceModel clientServiceModel) {
        Client client = clientRepository.findByClientNameAndClientPhoneNumber(clientServiceModel.getClientName(),
                clientServiceModel.getClientPhoneNumber());
        if (client == null) {
            client = modelMapper.map(clientServiceModel, Client.class);
            client = clientRepository.save(client);
        }
        return client;
    }
}
