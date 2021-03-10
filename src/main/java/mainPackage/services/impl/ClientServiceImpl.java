package mainPackage.services.impl;

import mainPackage.models.entities.Client;
import mainPackage.models.services.ClientServiceModel;
import mainPackage.models.views.ClientViewModel;
import mainPackage.repositories.ClientRepository;
import mainPackage.services.ClientService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<ClientViewModel> findAll() {
        return clientRepository.findAll().stream().map(cl->modelMapper.map(cl, ClientViewModel.class))
                .collect(Collectors.toList());
    }
}
