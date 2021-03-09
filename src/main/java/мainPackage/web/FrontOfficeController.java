package мainPackage.web;

import мainPackage.models.bindings.OrderReceiveBindingModel;
import мainPackage.models.bindings.SparePartBindingModel;
import мainPackage.models.services.ClientServiceModel;
import мainPackage.models.services.OrderReceiveServiceModel;
import мainPackage.models.views.BrandViewModel;
import мainPackage.models.views.DamageViewModel;
import мainPackage.models.views.OrderReadyViewModel;
import мainPackage.services.BrandService;
import мainPackage.services.DamageService;
import мainPackage.services.OrderService;
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

    public FrontOfficeController(ModelMapper modelMapper, OrderService orderService, DamageService damageService, BrandService brandService) {
        this.modelMapper = modelMapper;
        this.orderService = orderService;
        this.damageService = damageService;
        this.brandService = brandService;
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

        return "redirect:/index";
    }

    @GetMapping("/client-info")
    @PreAuthorize("isAuthenticated()")
    public String clientInfo() {
        return "/info/client-info";
    }

    @GetMapping("/orders-info")
    @PreAuthorize("isAuthenticated()")
    public String OrderCheck() {
        return "/info/orders-info";
    }
}
