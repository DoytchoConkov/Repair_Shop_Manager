package mainPackage.services.impl;

import mainPackage.models.entities.ClientEntity;
import mainPackage.models.services.ClientServiceModel;
import mainPackage.models.views.ClientViewModel;
import mainPackage.models.views.OrderViewModel;
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
    public ClientEntity findByNameAndPhoneNumber(ClientServiceModel clientServiceModel) {
        ClientEntity client = clientRepository.findByClientNameAndClientPhoneNumber(clientServiceModel.getClientName(),
                clientServiceModel.getClientPhoneNumber());
        if (client == null) {
            client = modelMapper.map(clientServiceModel, ClientEntity.class);
            client = clientRepository.save(client);
        }
        return client;
    }

    @Override
    public List<ClientViewModel> getAllClientNames() {
        return clientRepository.findAll().stream().map(cl -> modelMapper.map(cl, ClientViewModel.class)).collect(Collectors.toList());
    }

    @Override
    public List<ClientViewModel> findByNameOrPhoneNumber(String clientName) {
        List<ClientEntity> clients = clientRepository.findByClientNameOrPhoneNumber("%"+clientName+"%");
        return clients.stream().map(cl -> modelMapper.map(cl, ClientViewModel.class)).collect(Collectors.toList());
    }
    @Override
    public List<ClientViewModel> getAll() {
        return clientRepository.findAll().stream().map(cl->modelMapper.map(cl,ClientViewModel.class)).collect(Collectors.toList());
    }

    @Override
    public List<ClientViewModel> getAllPhoneNumbers() {
        return clientRepository.findAllPhoneNumbers().stream().map(cl->modelMapper.map(cl,ClientViewModel.class)).collect(Collectors.toList());
    }

    @Override
    public ClientViewModel findClientByClientId(Long id) {
        return modelMapper.map(clientRepository.getClientById(id),ClientViewModel.class);
    }
}
