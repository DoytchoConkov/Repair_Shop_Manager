package mainPackage.services.impl;

import mainPackage.models.entities.Client;
import mainPackage.models.services.ClientServiceModel;
import mainPackage.models.views.ClientViewModel;
import mainPackage.repositories.ClientRepository;
import mainPackage.services.ClientService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class ClientServiceTest {

    @Autowired
    private ClientService clientService;
    @MockBean
    private ClientRepository mockClientRepository;

    ClientServiceTest() {
    }

    @Test
    void findByNameAndPhoneNumber() {
        Client client = new Client();
        client.setClientName("Gosho");
        client.setClientPhoneNumber("0878263591");
        ClientServiceModel clientServiceModel =new ClientServiceModel();
        clientServiceModel.setClientName("Gosho");
        clientServiceModel.setClientPhoneNumber("0878263591");
        Mockito.when(mockClientRepository.findByClientNameAndClientPhoneNumber("Gosho", "0878263591")).thenReturn(client);
        Client receivedClient = clientService.findByNameAndPhoneNumber(clientServiceModel);
        assertEquals(client,receivedClient);
    }

    @Test
    void getAllClientNames() {
        List<Client> clients = new ArrayList<>();
        clients.add(new Client());
        Mockito.when(mockClientRepository.findAll()).thenReturn(clients);
        List<ClientViewModel> allBrands = clientService.getAllClientNames();
        assertEquals(clients.size(), allBrands.size());
    }

    @Test
    void findContainingInName() {
        List<Client> clients = new ArrayList<>();
        Client client = new Client();
        client.setClientName("Gosho");
        client.setClientPhoneNumber("0878263591");
        clients.add(new Client());
        Mockito.when(mockClientRepository.findByClientNameOrPhoneNumber("%osh%")).thenReturn(clients);
        List<ClientViewModel> allClients = clientService.findByNameOrPhoneNumber("osh");
        assertEquals(clients.size(), allClients.size());
    }

    @Test
    void findContainingInPhoneNumber() {
        List<Client> clients = new ArrayList<>();
        Client client = new Client();
        client.setClientName("Gosho");
        client.setClientPhoneNumber("0878263591");
        clients.add(new Client());
        Mockito.when(mockClientRepository.findByClientNameOrPhoneNumber("%087%")).thenReturn(clients);
        List<ClientViewModel> allClients = clientService.findByNameOrPhoneNumber("087");
        assertEquals(clients.size(), allClients.size());
    }
}