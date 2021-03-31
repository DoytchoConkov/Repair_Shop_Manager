package mainPackage.web;

import mainPackage.models.views.IncomePerPeriodViewModel;
import mainPackage.models.views.OrderViewModel;
import mainPackage.services.OrderService;
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
    public List<OrderViewModel> getAllModelsForBrand(@RequestParam String serialNumber) {
        return orderService.findOrders(serialNumber);
    }

    @GetMapping("/find-clientById")
    public List<OrderViewModel> getAllByClientId(@RequestParam String clientId) {
        return orderService.findOrdersByClientId(Long.valueOf(clientId));
    }

    @GetMapping("/income")
    public List<IncomePerPeriodViewModel> getOrderPerPeriod(@RequestParam String starDate, @RequestParam String endDate,
                                                            @RequestParam String technician) {
        return orderService.getByStartDateAndEndDate(starDate, endDate, technician);
    }

    @GetMapping("/orderByDate")
    public List<OrderViewModel> getOrdersPerDate(@RequestParam String date) {
        return orderService.getByDate(date);
    }
}
