package mainPackage.services;

import mainPackage.models.entities.Client;
import mainPackage.models.services.ClientServiceModel;
import mainPackage.models.views.BrandViewModel;
import mainPackage.models.views.ClientViewModel;
import mainPackage.repositories.BrandRepository;
import mainPackage.repositories.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
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
    void findByNameOrPhoneNumber() {

    }
}