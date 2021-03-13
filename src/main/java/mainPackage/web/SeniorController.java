package mainPackage.web;

import mainPackage.models.bindings.SparePartBindingModel;
import mainPackage.models.entities.SparePart;
import mainPackage.models.views.BrandViewModel;
import mainPackage.services.BrandService;
import mainPackage.services.OrderService;
import mainPackage.services.SparePartsService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/senior")
public class SeniorController {
    private final ModelMapper modelMapper;
    private final BrandService brandService;
    private final SparePartsService sparePartsService;
    private final OrderService orderService;

    public SeniorController(ModelMapper modelMapper, BrandService brandService, SparePartsService sparePartsService,
                            OrderService orderService) {
        this.modelMapper = modelMapper;
        this.brandService = brandService;
        this.sparePartsService = sparePartsService;
        this.orderService = orderService;
    }

    @GetMapping("/edit-spare-parts")
    @PreAuthorize("isAuthenticated()")
    public String viewSpareParts(Model model) {
        if (!model.containsAttribute("sparePartsReceiveBindingModel")) {
            model.addAttribute("sparePartsReceiveBindingModel", new SparePartBindingModel());
        }
        List<BrandViewModel> brands = brandService.getAll();
        model.addAttribute("brands", brands);
        return "/extended/edit-spare-parts";
    }

    @GetMapping("/edit-orders")
    @PreAuthorize("isAuthenticated()")
    public String editOrder(Model model) {
        model.addAttribute("orderReadyViewModels", orderService.getNotPayedOrders());
        return "/extended/edit-orders";
    }

    @GetMapping("/income-info")
    @PreAuthorize("isAuthenticated()")
    public String incomeInfo() {
        return "/extended/income-info";
    }

    @DeleteMapping("/spare_part/delete/{id}")
    public String deleteSparePart(@PathVariable Long id,
                                  Model model) {
        SparePart sparePart = sparePartsService.findById(id);
        if (orderService.isContainSparePart(sparePart.getId())) {
            sparePart.setPieces(0);
            sparePartsService.update(sparePart);
        } else {
            sparePartsService.deleteSparePart(id);
        }

        return "redirect:/home";
    }

    @DeleteMapping("/order/delete/{id}")
    public String deleteOrder(@PathVariable Long id, Model model) {
        orderService.deleteOrder(id);
        return "redirect:/home";
    }

}
