package mainPackage.web;

import mainPackage.aop.TrackLatency;
import mainPackage.models.views.IncomePerPeriodViewModel;
import mainPackage.models.views.OrderViewModel;
import mainPackage.services.OrderService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderRestController {
    private final OrderService orderService;

    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/find-order")
    @PreAuthorize("hasRole('ROLE_FRONT_OFFICE')")
    public List<OrderViewModel> getAllModelsForBrand(@RequestParam String serialNumber) {
        return orderService.findOrders(serialNumber);
    }

    @GetMapping("/find-clientById")
    @PreAuthorize("hasRole('ROLE_FRONT_OFFICE')")
    public List<OrderViewModel> getAllByClientId(@RequestParam String clientId) {
        return orderService.findOrdersByClientId(Long.valueOf(clientId));
    }

    @TrackLatency(latency = "income")
    @GetMapping("/income")
    @PreAuthorize("hasRole('ROLE_SENIOR')")
    public List<IncomePerPeriodViewModel> getOrderPerPeriod(@RequestParam String starDate, @RequestParam String endDate,
                                                            @RequestParam String technician) {
        return orderService.getByStartDateAndEndDate(starDate, endDate, technician);
    }

    @GetMapping("/orderByDate")
    @PreAuthorize("hasRole('ROLE_SENIOR')")
    public List<OrderViewModel> getOrdersPerDate(@RequestParam String date) {
        return orderService.getByDate(date);
    }

    @GetMapping("/models")
    @PreAuthorize("hasRole('ROLE_BACK_OFFICE') or hasRole('ROLE_FRONT_OFFICE')")
    public List<String> getAllModelsForBrandREST(@RequestParam String brandName) {
        List<String> models = orderService.getByBrandName(brandName);
        return models;
    }
}
