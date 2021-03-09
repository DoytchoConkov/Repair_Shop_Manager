package mainPackage.services.impl;

import mainPackage.models.entities.*;
import mainPackage.models.services.ClientServiceModel;
import mainPackage.models.views.OrderNotReadyViewModel;
import mainPackage.models.services.OrderReceiveServiceModel;
import mainPackage.models.views.OrderReadyViewModel;
import mainPackage.repositories.OrderRepository;
import mainPackage.services.*;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ClientService clientService;
    private final ModelService modelService;
    private final DamageService damageService;
    private final ModelMapper modelMapper;
    private final UserService userService;

    public OrderServiceImpl(OrderRepository OrderRepository, ClientService clientService, ModelService modelService,
                            DamageService damageService, ModelMapper modelMapper, UserService userService) {
        this.orderRepository = OrderRepository;
        this.clientService = clientService;
        this.modelService = modelService;
        this.damageService = damageService;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @Override
    public void add(OrderReceiveServiceModel OrderReceiveServiceModel) {
        Client client = clientService.findByNameAndPhoneNumber(modelMapper.map(OrderReceiveServiceModel, ClientServiceModel.class));
        Order order = modelMapper.map(OrderReceiveServiceModel, Order.class);
        Model model = modelService.getModel(OrderReceiveServiceModel.getBrand(), OrderReceiveServiceModel.getModel());
        Damage damage = damageService.getDamage(OrderReceiveServiceModel.getDamage());
        order.setModel(model);
        order.setClient(client);
        order.setReceiveDate(LocalDateTime.now());
        order.setDamage(damage);
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        order.setUser(userService.getUserByUserName(username));
        orderRepository.save(order);
    }

    @Override
    public List<OrderNotReadyViewModel> getNotReady() {
        List<OrderNotReadyViewModel> orderNotReadyViewModels = orderRepository.findNotReadyOrders().stream().map(p -> {
            OrderNotReadyViewModel orderNotReadyViewModel = modelMapper.map(p, OrderNotReadyViewModel.class);
            orderNotReadyViewModel.setBrand(p.getModel().getBrand().getBrandName());
            return orderNotReadyViewModel;
        }).collect(Collectors.toList());
        return orderNotReadyViewModels;
    }

    @Override
    public List<OrderReadyViewModel> getReady() {
        List<OrderReadyViewModel> orderReadyViewModels = orderRepository.findReadyOrders().stream().map(p -> {
            OrderReadyViewModel OrderReadyViewModel = modelMapper.map(p, OrderReadyViewModel.class);
            OrderReadyViewModel.setBrand(p.getModel().getBrand().getBrandName());
            return OrderReadyViewModel;
        }).collect(Collectors.toList());
        return orderReadyViewModels;
    }

    @Override
    public OrderNotReadyViewModel getById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow();
        OrderNotReadyViewModel OrderNotReadyViewModel = modelMapper.map(order, OrderNotReadyViewModel.class);
        OrderNotReadyViewModel.setBrand(order.getModel().getBrand().getBrandName());
        return OrderNotReadyViewModel;
    }
}
