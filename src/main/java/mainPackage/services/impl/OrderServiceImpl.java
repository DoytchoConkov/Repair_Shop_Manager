package mainPackage.services.impl;

import mainPackage.models.entities.*;
import mainPackage.models.services.ClientServiceModel;
import mainPackage.models.services.OrderFixServiceModel;
import mainPackage.models.views.OrderNotReadyViewModel;
import mainPackage.models.services.OrderReceiveServiceModel;
import mainPackage.models.views.OrderReadyViewModel;
import mainPackage.models.views.OrderViewModel;
import mainPackage.repositories.OrderRepository;
import mainPackage.services.*;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    private final SparePartsService sparePartsService;

    public OrderServiceImpl(OrderRepository orderRepository, ClientService clientService, ModelService modelService,
                            DamageService damageService, ModelMapper modelMapper, UserService userService,
                            SparePartsService sparePartsService) {
        this.orderRepository = orderRepository;
        this.clientService = clientService;
        this.modelService = modelService;
        this.damageService = damageService;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.sparePartsService = sparePartsService;
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
        return orderRepository.findNotReadyOrders().stream().map(p -> {
            OrderNotReadyViewModel orderNotReadyViewModel = modelMapper.map(p, OrderNotReadyViewModel.class);
            orderNotReadyViewModel.setBrand(p.getModel().getBrand().getBrandName());
            return orderNotReadyViewModel;
        }).collect(Collectors.toList());
    }

    @Override
    public List<OrderReadyViewModel> getReady() {
        return orderRepository.findReadyOrders().stream().map(p -> {
            OrderReadyViewModel OrderReadyViewModel = modelMapper.map(p, OrderReadyViewModel.class);
            OrderReadyViewModel.setBrand(p.getModel().getBrand().getBrandName());
            return OrderReadyViewModel;
        }).collect(Collectors.toList());
    }

    @Override
    public OrderNotReadyViewModel getById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow();
        OrderNotReadyViewModel OrderNotReadyViewModel = modelMapper.map(order, OrderNotReadyViewModel.class);
        OrderNotReadyViewModel.setBrand(order.getModel().getBrand().getBrandName());
        return OrderNotReadyViewModel;
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public boolean isContainSparePart(Long id) {
        Long count = orderRepository.findBySparePart(id);
        return count > 0;
    }

    @Override
    public List<OrderReadyViewModel> getNotPayedOrders() {
        return orderRepository.findAllByLeaveDateIsNull().stream().map(or -> {
            OrderReadyViewModel orderReadyViewModel = modelMapper.map(or, OrderReadyViewModel.class);
            orderReadyViewModel.setBrand(or.getModel().getBrand().getBrandName());
            return orderReadyViewModel;
        }).collect(Collectors.toList());

    }

    @Override
    public void fix(OrderFixServiceModel orderServiceModel) {
        Order order = orderRepository.findById(orderServiceModel.getId()).orElseThrow();
        List<Long> spareParts = orderServiceModel.getSpId();
        if (spareParts == null) {
            spareParts = new ArrayList<>();
        }
        List<SparePart> sparePartsList = spareParts.stream().map(sp -> sparePartsService.findById(sp)).collect(Collectors.toList());
        order.setTotalSparePartsPrice(orderServiceModel.getSpPrice());
        order.setTotalRepairPrice(orderServiceModel.getTotalPrice());
        order.setSpareParts(sparePartsList);
        orderRepository.save(order);
    }

    @Override
    public OrderReadyViewModel getReadyById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow();
        OrderReadyViewModel orderReadyViewModel = modelMapper.map(order, OrderReadyViewModel.class);
        orderReadyViewModel.setBrand(order.getModel().getBrand().getBrandName());
        return orderReadyViewModel;
    }

    @Override
    public void pay(Long id) {
        Order order = orderRepository.findById(id).orElseThrow();
        order.setLeaveDate(LocalDateTime.now());
        orderRepository.save(order);
    }

    @Override
    public void makeNotFixed(Long id) {
        Order order = orderRepository.findById(id).orElseThrow();
        order.setTotalRepairPrice(null);
        order.setTotalSparePartsPrice(null);
        order.getSpareParts().clear();
        orderRepository.save(order);
    }

    @Override
    public List<OrderViewModel> findOrders(String serialNumber) {
        List<Order> ords = orderRepository.findAllBySerialNumber("%" + serialNumber + "%");
        return ords.stream()
                .map(ord -> {
                    OrderViewModel orderViewModel = modelMapper.map(ord, OrderViewModel.class);
                    orderViewModel.setBrandName(ord.getModel().getBrand().getBrandName());
                    return orderViewModel;
                }).collect(Collectors.toList());
    }

    @Override
    public List<OrderViewModel> findOrdersByClientId(Long id) {
        List<Order> ords = orderRepository.findAllByClientId(id);
        return ords.stream()
                .map(ord -> {
                    OrderViewModel orderViewModel = modelMapper.map(ord, OrderViewModel.class);
                    orderViewModel.setBrandName(ord.getModel().getBrand().getBrandName());
                    return orderViewModel;
                }).collect(Collectors.toList());
    }

    @Override
    public List<OrderViewModel> getByStartDateAndEndDate(String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter formatterToString = DateTimeFormatter.ofPattern("dd-MM-YYYY");
        //TODO do for other places where have a LocalDateTime !!!
        //   LocalDateTime formatDateTime = LocalDateTime.parse(now, formatter);
        //    String formatDateTime = now.format(formatter);


        List<Order> incomeList=orderRepository.findAllByLeaveDateBetween(LocalDateTime.parse(startDate + " 00:00:00",formatter),
                LocalDateTime.parse(endDate + " 23:59:59",formatter));
        return incomeList.stream().map(o->{
            OrderViewModel viewModel= modelMapper.map(o,OrderViewModel.class);
            return viewModel;
        }).collect(Collectors.toList());
    }
}