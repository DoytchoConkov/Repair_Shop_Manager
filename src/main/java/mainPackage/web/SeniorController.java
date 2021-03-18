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

import java.time.LocalDate;
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
    public String incomeInfo(Model model) {
        if (!model.containsAttribute("startDate")) {
            model.addAttribute("startDate", LocalDate.now());
        }
        if (!model.containsAttribute("endDate")) {
            model.addAttribute("endDate", LocalDate.now());
        }
        if (!model.containsAttribute("income")) {
            model.addAttribute("income", orderService.getByStartDateAndEndDate(LocalDate.now().toString(), LocalDate.now().toString()));
        }
        return "/info/income-info";
    }

    @PutMapping("/spare_part/edit/{id}")
    public String editSparePart(@PathVariable Long id, @ModelAttribute SparePartBindingModel sparePartBindingModel) {
        sparePartsService.edit(id, sparePartBindingModel);
        return "redirect:/home";
    }

    @DeleteMapping("/spare_part/delete/{id}")
    public String deleteSparePart(@PathVariable Long id) {
        if (orderService.isContainSparePart(id)) {
            sparePartsService.update(id,0);
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

    @PostMapping("/order/make-not-fixed/{id}")
    public String makeOrderNotFixed(@PathVariable Long id, Model model) {
        orderService.makeNotFixed(id);
        return "redirect:/home";
    }

}
