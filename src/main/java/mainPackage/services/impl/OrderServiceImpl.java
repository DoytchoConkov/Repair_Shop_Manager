package mainPackage.services.impl;

import mainPackage.errors.OrderIdNotFoundException;
import mainPackage.models.entities.*;
import mainPackage.models.services.ClientServiceModel;
import mainPackage.models.services.IncomePerPeriodServiceModel;
import mainPackage.models.services.OrderFixServiceModel;
import mainPackage.models.views.IncomePerPeriodViewModel;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
        order.setReceiveDate(LocalDate.now());
        order.setDamage(damage);
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
        Order order = orderRepository.findById(orderServiceModel.getId())
                .orElseThrow(() -> new OrderIdNotFoundException(String.format("No order with this %d", orderServiceModel.getId())));
        Set<Long> spareParts = orderServiceModel.getSparePartIds();
        List<SparePart> sparePartsList = new ArrayList<>();
        if (spareParts != null) {
            spareParts.remove("");
            if (!spareParts.isEmpty()) {
                sparePartsList = spareParts.stream().map(sp -> sparePartsService.findById(sp)).collect(Collectors.toList());
                sparePartsService.reduceSpareParts(sparePartsList.stream().map(s -> s.getId()).collect(Collectors.toList()));
            }
        }
        order.setTotalSparePartsPrice(orderServiceModel.getSparePartPrice());
        order.setTotalRepairPrice(orderServiceModel.getTotalPrice());
        order.setSpareParts(sparePartsList);
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        order.setUser(userService.getUserByUserName(username));
        orderRepository.save(order);
    }

    @Override
    public OrderReadyViewModel getReadyById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderIdNotFoundException(String.format("No order with this %d", id)));
        OrderReadyViewModel orderReadyViewModel = modelMapper.map(order, OrderReadyViewModel.class);
        orderReadyViewModel.setBrand(order.getModel().getBrand().getBrandName());
        return orderReadyViewModel;
    }

    @Override
    public void pay(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderIdNotFoundException(String.format("No order with this %d", id)));
        order.setLeaveDate(LocalDate.now());
        orderRepository.save(order);
    }

    @Override
    public void makeNotFixed(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderIdNotFoundException(String.format("No order with this %d", id)));
        order.setTotalRepairPrice(null);
        order.setTotalSparePartsPrice(null);
        order.getSpareParts().clear();
        orderRepository.save(order);
    }

    @Override
    public List<OrderViewModel> findOrders(String serialNumber) {
        List<Order> ords = orderRepository.findAllBySerialNumber("%" + serialNumber + "%");
        return getOrderViewModels(ords);
    }


    @Override
    public List<OrderViewModel> findOrdersByClientId(Long id) {
        List<Order> ords = orderRepository.findAllByClientId(id);
        return getOrderViewModels(ords);
    }

    @Override
    public List<IncomePerPeriodViewModel> getByStartDateAndEndDate(String startDate, String endDate, String technician) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatterToString = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        if (technician.isEmpty()) {
            List<IncomePerPeriodServiceModel> incomeList = orderRepository.findAllByLeaveDateBetween(LocalDate.parse(startDate, formatter), LocalDate.parse(endDate, formatter));

            return incomeList.stream().map(o -> {
                IncomePerPeriodViewModel viewModel = modelMapper.map(o, IncomePerPeriodViewModel.class);
                viewModel.setLeaveDateString(o.getLeaveDate().format(formatterToString));
                return viewModel;
            }).collect(Collectors.toList());
        }
        List<IncomePerPeriodServiceModel> incomeList = orderRepository.findAllByLeaveDateBetweenAndTechnician(LocalDate.parse(startDate, formatter), LocalDate.parse(endDate, formatter), technician);

        return incomeList.stream().map(o -> {
            IncomePerPeriodViewModel viewModel = modelMapper.map(o, IncomePerPeriodViewModel.class);
            viewModel.setLeaveDateString(o.getLeaveDate().format(formatterToString));
            return viewModel;
        }).collect(Collectors.toList());
    }

    @Override
    public List<OrderViewModel> getByDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter formatterToString = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        List<Order> orders = orderRepository.getByDate(LocalDate.parse(date, formatter));
        List<OrderViewModel> ordersView = orders.stream().map(o -> {
            OrderViewModel order = modelMapper.map(o, OrderViewModel.class);
            order.setBrandName(o.getModel().getBrand().getBrandName());
            return order;
        }).collect(Collectors.toList());
        return ordersView;
    }

    @Override
    public List<String> findTechnicians() {
        return orderRepository.findTechnicians();
    }

    @Override
    public int countNotReadyOrders() {
        return orderRepository.countNotReadyOrders();
    }

    @Override
    public int countReadyOrders() {
        return orderRepository.countReadyOrders();
    }

    private List<OrderViewModel> getOrderViewModels(List<Order> ords) {
        return ords.stream()
                .map(ord -> {
                    OrderViewModel orderViewModel = modelMapper.map(ord, OrderViewModel.class);
                    orderViewModel.setBrandName(ord.getModel().getBrand().getBrandName());
                    DateTimeFormatter formatterToString = DateTimeFormatter.ofPattern("dd-MM-YYYY");
                    if (ord.getLeaveDate() != null) {
                        orderViewModel.setLeaveDate(ord.getLeaveDate().format(formatterToString));
                    } else {
                        orderViewModel.setLeaveDate("");
                    }
                    if (ord.getReceiveDate() != null) {
                        orderViewModel.setReceiveDate(ord.getReceiveDate().format(formatterToString));
                    } else {
                        orderViewModel.setReceiveDate("");
                    }
                    return orderViewModel;
                }).collect(Collectors.toList());
    }
}