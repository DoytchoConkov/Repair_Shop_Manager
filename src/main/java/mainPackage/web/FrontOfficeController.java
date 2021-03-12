package mainPackage.web;

import mainPackage.models.bindings.OrderReceiveBindingModel;
import mainPackage.models.bindings.SparePartBindingModel;
import mainPackage.models.services.ClientServiceModel;
import mainPackage.models.services.OrderReceiveServiceModel;
import mainPackage.models.views.BrandViewModel;
import mainPackage.models.views.DamageViewModel;
import mainPackage.models.views.OrderReadyViewModel;
import mainPackage.services.BrandService;
import mainPackage.services.ClientService;
import mainPackage.services.DamageService;
import mainPackage.services.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/front-office")
public class FrontOfficeController {

    private final ModelMapper modelMapper;
    private final OrderService orderService;
    private final DamageService damageService;
    private final BrandService brandService;
    private final ClientService clientService;

    public FrontOfficeController(ModelMapper modelMapper, OrderService orderService, DamageService damageService, BrandService brandService, ClientService clientService) {
        this.modelMapper = modelMapper;
        this.orderService = orderService;
        this.damageService = damageService;
        this.brandService = brandService;
        this.clientService = clientService;
    }

    @GetMapping("/export")
    @PreAuthorize("isAuthenticated()")
    public String exportOrder(Model model) {
        List<OrderReadyViewModel> orderReadyViewModels = orderService.getReady();
        model.addAttribute("orderReadyViewModels", orderReadyViewModels);
        if (!model.containsAttribute("orderExport")) {
            model.addAttribute("orderExport", new SparePartBindingModel());
        }
        return "/orders/orders-ready";
    }

    @GetMapping("/receive")
    @PreAuthorize("isAuthenticated()")
    public String OrderReceive(Model model) {
        List<DamageViewModel> damages = damageService.getAll();
        model.addAttribute("damages", damages);
        List<BrandViewModel> brands = brandService.getAll();
        model.addAttribute("brands", brands);
        if (!model.containsAttribute("OrderReceiveBindingModel")) {
            model.addAttribute("OrderReceiveBindingModel", new OrderReceiveBindingModel());
        }
        return "/orders/orders-receive";
    }

    @PostMapping("/receive")
    @PreAuthorize("isAuthenticated()")
    public String OrderReceiveConfirm(@Valid @ModelAttribute("orderReceiveBindingModel") OrderReceiveBindingModel orderReceiveBindingModel,
                                        BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("orderReceiveBindingModel", orderReceiveBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.orderReceiveBindingModel", bindingResult);
            return "redirect:/front-office/receive";
        }

        OrderReceiveServiceModel orderReceiveServiceModel = modelMapper.map(orderReceiveBindingModel, OrderReceiveServiceModel.class);
        orderReceiveServiceModel.setClient(modelMapper.map(orderReceiveBindingModel, ClientServiceModel.class));
        orderService.add(orderReceiveServiceModel);

        return "redirect:/home";
    }

    @GetMapping("/client-info")
    @PreAuthorize("isAuthenticated()")
    public String clientInfo(Model model) {
        model.addAttribute("clients", clientService.getAllClientNames());
//        if(!model.containsAttribute("clientName")){
//            model.addAttribute("clientName", new ClientBindingModel());
//        }
        return "/info/client-info";
    }

    @GetMapping("/order-info")
    @PreAuthorize("isAuthenticated()")
    public String OrderCheck() {
        return "/info/orders-info";
    }
}
