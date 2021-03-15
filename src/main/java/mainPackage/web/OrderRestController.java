package mainPackage.web;

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
}
