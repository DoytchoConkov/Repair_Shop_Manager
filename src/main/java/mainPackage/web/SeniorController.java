package mainPackage.web;

import mainPackage.models.bindings.SparePartBindingModel;
import mainPackage.models.views.BrandViewModel;
import mainPackage.services.BrandService;
import mainPackage.services.OrderService;
import mainPackage.services.SparePartsService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/senior")
public class SeniorController {
    private final BrandService brandService;
    private final SparePartsService sparePartsService;
    private final OrderService orderService;

    public SeniorController(BrandService brandService, SparePartsService sparePartsService, OrderService orderService) {
        this.brandService = brandService;
        this.sparePartsService = sparePartsService;
        this.orderService = orderService;
    }

    @GetMapping("/edit-spare-parts")
    @PreAuthorize("hasRole('ROLE_SENIOR')")
    public String viewSpareParts(Model model) {
        if (!model.containsAttribute("sparePartsReceiveBindingModel")) {
            model.addAttribute("sparePartsReceiveBindingModel", new SparePartBindingModel());
        }
        List<BrandViewModel> brands = brandService.getAll();
        model.addAttribute("brands", brands);
        return "/spare-parts/edit-spare-parts";
    }

    @GetMapping("/edit-orders")
    @PreAuthorize("hasRole('ROLE_SENIOR')")
    public String editOrder(Model model) {
        model.addAttribute("orderReadyViewModels", orderService.getNotPayedOrders());
        return "/orders/edit-orders";
    }

    @GetMapping("/income-info")
    @PreAuthorize("hasRole('ROLE_SENIOR')")
    public String incomeInfo(Model model) {
        if (!model.containsAttribute("startDate")) {
            model.addAttribute("startDate", LocalDate.now());
        }
        if (!model.containsAttribute("endDate")) {
            model.addAttribute("endDate", LocalDate.now());
        }
        if (!model.containsAttribute("technicians")) {
            model.addAttribute("technicians", orderService.findTechnicians());
        }
        return "/info/income-info";
    }

    @PutMapping("/spare_part/edit/{id}")
    @PreAuthorize("hasRole('ROLE_SENIOR')")
    public String editSparePart(@PathVariable Long id, @ModelAttribute SparePartBindingModel sparePartBindingModel) {
        sparePartsService.edit(id, sparePartBindingModel);
        return "redirect:/home";
    }

    @DeleteMapping("/spare_part/delete/{id}")
    @PreAuthorize("hasRole('ROLE_SENIOR')")
    public String deleteSparePart(@PathVariable Long id) {
        if (orderService.isContainSparePart(id)) {
            sparePartsService.update(id, 0);
        } else {
            sparePartsService.deleteSparePart(id);
        }
        return "redirect:/home";
    }

    @DeleteMapping("/order/delete/{id}")
    @PreAuthorize("hasRole('ROLE_SENIOR')")
    public String deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return "redirect:/home";
    }

    @PostMapping("/order/make-not-fixed/{id}")
    @PreAuthorize("hasRole('ROLE_SENIOR')")
    public String makeOrderNotFixed(@PathVariable Long id) {
        orderService.makeNotFixed(id);
        return "redirect:/home";
    }

}
