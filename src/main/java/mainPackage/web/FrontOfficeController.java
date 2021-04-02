package mainPackage.web;

import mainPackage.models.bindings.OrderReceiveBindingModel;
import mainPackage.models.services.ClientServiceModel;
import mainPackage.models.services.OrderReceiveServiceModel;
import mainPackage.models.views.BrandViewModel;
import mainPackage.models.views.DamageViewModel;
import mainPackage.models.views.ModelViewModel;
import mainPackage.models.views.OrderReadyViewModel;
import mainPackage.services.*;
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
    private final ModelService modelService;

    public FrontOfficeController(ModelMapper modelMapper, OrderService orderService, DamageService damageService, BrandService brandService, ClientService clientService, ModelService modelService) {
        this.modelMapper = modelMapper;
        this.orderService = orderService;
        this.damageService = damageService;
        this.brandService = brandService;
        this.clientService = clientService;
        this.modelService = modelService;
    }

    @GetMapping("/fixed-orders")
    @PreAuthorize("hasRole('ROLE_FRONT_OFFICE')")
    public String payOrder(Model model) {
        List<OrderReadyViewModel> orderReadyViewModels = orderService.getReady();
        model.addAttribute("orderReadyViewModels", orderReadyViewModels);
        return "/orders/orders-ready";
    }

    @GetMapping("/pay-order/{id}")
    @PreAuthorize("hasRole('ROLE_FRONT_OFFICE')")
    public String payOrderRedirect(@PathVariable Long id, Model model) {
        OrderReadyViewModel orderReadyViewModel = orderService.getReadyById(id);
        model.addAttribute("orderReadyViewModel", orderReadyViewModel);
        return "/orders/order-details";
    }

    @PostMapping("/pay-order/{id}")
    @PreAuthorize("hasRole('ROLE_FRONT_OFFICE')")
    public String payOrderNow(@PathVariable Long id) {
        orderService.pay(id);
        return "redirect:/home";
    }

    @GetMapping("/receive")
    @PreAuthorize("hasRole('ROLE_FRONT_OFFICE')")
    public String OrderReceive(Model model) {
        List<DamageViewModel> damages = damageService.getAll();
        model.addAttribute("damages", damages);
        List<BrandViewModel> brands = brandService.getAll();
        model.addAttribute("brands", brands);
        List<ModelViewModel> models = modelService.getAll();
        model.addAttribute("models", models);
        if (!model.containsAttribute("OrderReceiveBindingModel")) {
            model.addAttribute("OrderReceiveBindingModel", new OrderReceiveBindingModel());
        }
        return "/orders/orders-receive";
    }

    @PostMapping("/receive")
    @PreAuthorize("hasRole('ROLE_FRONT_OFFICE')")
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
    @PreAuthorize("hasRole('ROLE_FRONT_OFFICE')")
    public String clientInfo(Model model) {
        model.addAttribute("clients", clientService.getAllClientNames());
        return "/info/client-info";
    }

    @GetMapping("/order-info")
    @PreAuthorize("hasRole('ROLE_FRONT_OFFICE')")
    public String OrderCheck() {
        return "/info/orders-info";
    }
}
