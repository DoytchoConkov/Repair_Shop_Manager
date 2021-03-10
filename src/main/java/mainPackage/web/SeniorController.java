package mainPackage.web;

import mainPackage.models.bindings.SparePartBindingModel;
import mainPackage.models.services.SparePartServiceModel;
import mainPackage.models.views.BrandViewModel;
import mainPackage.models.views.SparePartViewModel;
import mainPackage.services.BrandService;
import mainPackage.services.OrderService;
import mainPackage.services.SparePartsService;
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

    @GetMapping("/add-spare-parts")
    @PreAuthorize("isAuthenticated()")
    public String addSparePart(Model model) {
        List<BrandViewModel> brands = brandService.getAll();
        model.addAttribute("brands", brands);
        List<SparePartViewModel> spareParts = sparePartsService.getAll();
        model.addAttribute("spareParts", spareParts);
        if (!model.containsAttribute("sparePartsReceiveBindingModel")) {
            model.addAttribute("sparePartsReceiveBindingModel", new SparePartBindingModel());
        }
        return "/extended/add-spare-part";
    }

    @PostMapping("/add-spare-parts")
    @PreAuthorize("isAuthenticated()")
    public String addSparePartConfirm(@Valid @ModelAttribute("sparePartsReceiveBindingModel")
                                              SparePartBindingModel sparePartsReceiveBindingModel,
                                      BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("sparePartsReceiveBindingModel", sparePartsReceiveBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.sparePartsReceiveBindingModel",
                    bindingResult);
            return "redirect:/add-spare-parts";
        }
        sparePartsService.save(modelMapper.map(sparePartsReceiveBindingModel, SparePartServiceModel.class));


        return "redirect:/index";
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
}
