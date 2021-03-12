package mainPackage.web;

import mainPackage.models.bindings.OrderFixBindingModel;
import mainPackage.models.bindings.SparePartBindingModel;
import mainPackage.models.bindings.UserRegisterBindingModel;
import mainPackage.models.services.SparePartServiceModel;
import mainPackage.models.views.BrandViewModel;
import mainPackage.models.views.OrderNotReadyViewModel;
import mainPackage.models.views.SparePartViewModel;
import mainPackage.services.BrandService;
import mainPackage.services.ModelService;
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
@RequestMapping("/back-office")
public class BackOfficeController {
    private final OrderService orderService;
    private final ModelMapper modelMapper;
    private final BrandService brandService;
    private final SparePartsService sparePartsService;

    public BackOfficeController(OrderService orderService, ModelMapper modelMapper, BrandService brandService, SparePartsService sparePartsService) {
        this.orderService = orderService;
        this.modelMapper = modelMapper;
        this.brandService = brandService;
        this.sparePartsService = sparePartsService;
    }

    @GetMapping("/not-fixed")
    @PreAuthorize("isAuthenticated()")
    public String OrdersToRepairAll(Model model) {
        List<OrderNotReadyViewModel> orderNotReadyViewModels = orderService.getNotReady();
        model.addAttribute("orderNotReadyViewModels", orderNotReadyViewModels);
        if (!model.containsAttribute("orderFixBindingModel")) {
            model.addAttribute("orderFixBindingModel", new SparePartBindingModel());
        }

        return "/orders/orders-not-ready";
    }

    @GetMapping("/fix/{id}")
    @PreAuthorize("isAuthenticated()")
    public String fixOrder(@PathVariable Long id, Model model) {
        OrderNotReadyViewModel orderToFixViewModel = orderService.getById(id);
        List<SparePartViewModel> sparePartViewModels = sparePartsService.getByBrandAndModel(orderToFixViewModel.getBrand(), orderToFixViewModel.getModel());
        if (!model.containsAttribute("orderFixedBindingModel")) {
            model.addAttribute("orderFixedBindingModel", new OrderFixBindingModel());
        }
        model.addAttribute("order", orderToFixViewModel);
        model.addAttribute("spareParts", sparePartViewModels);
        return "/orders/order-fixing";
    }

    @PostMapping("/fix/{id}")
    @PreAuthorize("isAuthenticated()")
    public String fixOrderConfirm(@PathVariable Long id, @Valid @ModelAttribute OrderFixBindingModel orderFixBindingModel,
                                  BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors() || orderFixBindingModel.getSpPrice().compareTo(orderFixBindingModel.getTotalPrice()) < 0) {
            redirectAttributes.addFlashAttribute("orderFixBindingModel", orderFixBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.orderFixBindingModel", bindingResult);
            return "redirect:/back-office/fix/" + id;
        }
        return "redirect:/back-office/not-fixed";
    }

    @GetMapping("/add-spare-part")
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

    @PostMapping("/add-spare-part")
    @PreAuthorize("isAuthenticated()")
    public String addSparePartConfirm(@Valid @ModelAttribute("sparePartsReceiveBindingModel")
                                              SparePartBindingModel sparePartsReceiveBindingModel,
                                      BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("sparePartsReceiveBindingModel", sparePartsReceiveBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.sparePartsReceiveBindingModel",
                    bindingResult);
            return "redirect:/back-office/add-spare-part";
        }
        sparePartsService.save(modelMapper.map(sparePartsReceiveBindingModel, SparePartServiceModel.class));


        return "redirect:/index";
    }
}
