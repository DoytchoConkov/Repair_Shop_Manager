package mainPackage.web;

import mainPackage.models.views.ClientViewModel;
import mainPackage.services.ClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/client")
public class ClientRestController {
    private final ClientService clientService;

    public ClientRestController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/find-client")
    public List<ClientViewModel> getAllModelsForBrand(@RequestParam String clientName) {
        return clientService.findByNameOrPhoneNumber(clientName);
    }
}
