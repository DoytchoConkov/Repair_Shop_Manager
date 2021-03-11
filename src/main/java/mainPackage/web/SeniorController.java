package mainPackage.web;

import mainPackage.models.entities.SparePart;
import mainPackage.services.BrandService;
import mainPackage.services.OrderService;
import mainPackage.services.SparePartsService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String editSparePart() {
        return "edit-spare-part";
    }

    @GetMapping("/view-spare-parts")
    @PreAuthorize("isAuthenticated()")
    public String viewSpareParts(Model model) {
        model.addAttribute("spareParts", sparePartsService.getAll());
        return "/extended/view-spare-parts";
    }

    @GetMapping("/spare_parts/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public String editSparePart(@PathVariable Long id, Model model) {
        model.addAttribute("sparePart", sparePartsService.getById(id));
        return "/extended/edit-spare-part";
    }

    @GetMapping("/edit-orders")
    @PreAuthorize("isAuthenticated()")
    public String editOrder(Model model) {
        model.addAttribute("orderReadyViewModels", orderService.getReady());
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

        return "redirect:/";
    }

    @DeleteMapping("/order/delete/{id}")
    public String deleteOrder(@PathVariable Long id,
                              Model model) {

        orderService.deleteOrder(id);

        return "redirect:/senior/view-spare-parts";
    }

}
