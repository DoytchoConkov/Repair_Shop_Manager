package mainPackage.web;

import mainPackage.aop.TrackLatency;
import mainPackage.models.bindings.OrderFixBindingModel;
import mainPackage.models.bindings.SparePartBindingModel;
import mainPackage.models.services.OrderFixServiceModel;
import mainPackage.models.services.SparePartServiceModel;
import mainPackage.models.views.BrandViewModel;
import mainPackage.models.views.OrderNotReadyViewModel;
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
import java.io.IOException;
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
    @PreAuthorize("hasRole('ROLE_BACK_OFFICE')")
    public String OrdersToRepairAll(Model model) {
        List<OrderNotReadyViewModel> orderNotReadyViewModels = orderService.getNotReady();
        model.addAttribute("orderNotReadyViewModels", orderNotReadyViewModels);
        return "/orders/orders-not-ready";
    }
    @GetMapping("/fix/{id}")
    @PreAuthorize("hasRole('ROLE_BACK_OFFICE')")
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
    @TrackLatency(latency = "fix order")
    @PostMapping("/fix/{id}")
    @PreAuthorize("hasRole('ROLE_BACK_OFFICE')")
    public String fixOrderConfirm(@PathVariable Long id, @Valid @ModelAttribute("orderFixBindingModel") OrderFixBindingModel orderFixBindingModel,
                                  BindingResult bindingResult, RedirectAttributes redirectAttributes) throws IOException {
        boolean compareResult = orderFixBindingModel.getSparePartPrice().compareTo(orderFixBindingModel.getTotalPrice()) > 0;
        if (bindingResult.hasErrors() || compareResult) {
            redirectAttributes.addFlashAttribute("orderFixBindingModel", orderFixBindingModel);
            if (compareResult) {
                redirectAttributes.addFlashAttribute("totalPriceError", true);
            }
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.orderFixBindingModel", bindingResult);
            return String.format("redirect:/back-office/fix/{%d}", id);
        }
        OrderFixServiceModel orderFixServiceModel = modelMapper.map(orderFixBindingModel, OrderFixServiceModel.class);
        orderFixServiceModel.setId(id);
        orderService.fix(orderFixServiceModel);

        return "redirect:/back-office/not-fixed";
    }

    @GetMapping("/add-spare-part")
    @PreAuthorize("hasRole('ROLE_BACK_OFFICE')")
    public String addSparePart(Model model) {
        List<BrandViewModel> brands = brandService.getAll();
        model.addAttribute("brands", brands);
        List<SparePartViewModel> spareParts = sparePartsService.getAll();
        model.addAttribute("spareParts", spareParts);
        if (!model.containsAttribute("sparePartsReceiveBindingModel")) {
            model.addAttribute("sparePartsReceiveBindingModel", new SparePartBindingModel());
        }
        return "/spare-parts/add-spare-part";
    }

    @PostMapping("/add-spare-part")
    @PreAuthorize("hasRole('ROLE_BACK_OFFICE')")
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
        return "redirect:/home";
    }
}