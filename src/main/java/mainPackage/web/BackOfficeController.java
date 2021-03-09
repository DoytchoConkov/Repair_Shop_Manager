package mainPackage.web;

import mainPackage.models.bindings.SparePartBindingModel;
import mainPackage.models.views.OrderNotReadyViewModel;
import mainPackage.services.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/back-office")
public class BackOfficeController {
    private final OrderService orderService;
    private final ModelMapper modelMapper;

    public BackOfficeController(OrderService orderService, ModelMapper modelMapper) {
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    @PreAuthorize("isAuthenticated()")
    public String OrdersToRepairAll(Model model) {
        List<OrderNotReadyViewModel> orderNotReadyViewModels = orderService.getNotReady();
        model.addAttribute("orderNotReadyViewModels", orderNotReadyViewModels);
        if (!model.containsAttribute("orderFixBindingModel")) {
            model.addAttribute("orderFixBindingModel", new SparePartBindingModel());
        }

        return "/orders/orders-not-ready";
    }

    @GetMapping("/order-fix/{id}")
    @PreAuthorize("isAuthenticated()")
    public String OrdersToRepair(@PathVariable Long id, Model model) {
        model.addAttribute("order", orderService.getById(id));
        return "/orders/orders-details";
    }
}
