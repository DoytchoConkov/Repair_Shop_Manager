package mainPackage.web;

import mainPackage.models.views.ClientViewModel;
import mainPackage.models.views.OrderViewModel;
import mainPackage.services.ClientService;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ROLE_FRONT_OFFICE')")
    public List<ClientViewModel> getAllModelsForBrand(@RequestParam String clientName) {
        return clientService.findByNameOrPhoneNumber(clientName);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_FRONT_OFFICE')")
    public List<ClientViewModel> getAll() {
        return clientService.getAll();
    }

    @GetMapping("/allPhones")
    @PreAuthorize("hasRole('ROLE_FRONT_OFFICE')")
    public List<ClientViewModel> getAllPhones() {
        return clientService.getAllPhoneNumbers();
    }

    @GetMapping("/find-clientById")
    @PreAuthorize("hasRole('ROLE_FRONT_OFFICE')")
    public ClientViewModel getAllByClientId(@RequestParam String clientId) {
        return clientService.findClientByClientId(Long.valueOf(clientId));
    }
}
