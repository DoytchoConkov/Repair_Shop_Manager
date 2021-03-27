package mainPackage.services.impl;

import mainPackage.models.entities.*;
import mainPackage.models.services.IncomePerPeriodServiceModel;
import mainPackage.models.views.IncomePerPeriodViewModel;
import mainPackage.models.views.OrderNotReadyViewModel;
import mainPackage.models.views.OrderReadyViewModel;
import mainPackage.models.views.OrderViewModel;
import mainPackage.repositories.OrderRepository;
import mainPackage.services.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class OrderServiceTest {
    @Autowired
    private OrderService orderService;
    @MockBean
    private OrderRepository mockOrderRepository;

    @Test
    void add() {
        //TODO:
    }

    @Test
    void getNotReady() {
        Order order = new Order();
        Client client = new Client();
        client.setClientName("Gosho");
        client.setClientPhoneNumber("0888123456");
        Damage damage = new Damage("Broken LCD");
        Brand brand = new Brand("Huawei");
        Model model = new Model("P40 lite", brand);
        order.setClient(client);
        order.setDamage(damage);
        order.setModel(model);
        order.setSerialNumber("350101006543210");
        order.setReceiveDate(LocalDate.now());
        List<Order> orders = List.of(order);
        Mockito.when(mockOrderRepository.findNotReadyOrders()).thenReturn(orders);
        List<OrderNotReadyViewModel> actual = orderService.getNotReady();
        assertEquals(order.getClient().getClientName(), actual.get(0).getClientName());
        assertEquals(order.getModel().getModelName(), actual.get(0).getModel());
        assertEquals(order.getModel().getBrand().getBrandName(), actual.get(0).getBrand());
        assertEquals(order.getDamage().getDamageName(), actual.get(0).getDamage());
        assertEquals(order.getSerialNumber(), actual.get(0).getSerialNumber());
    }

    @Test
    void getReady() {
        Order order = new Order();
        Client client = new Client();
        client.setClientName("Gosho");
        client.setClientPhoneNumber("0888123456");
        Damage damage = new Damage("Broken LCD");
        Brand brand = new Brand("Huawei");
        Model model = new Model("P40 lite", brand);
        User user = new User();
        user.setUsername("Ivan");
        order.setClient(client);
        order.setDamage(damage);
        order.setModel(model);
        order.setSerialNumber("350101006543210");
        order.setTotalRepairPrice(BigDecimal.valueOf(100));
        order.setTotalSparePartsPrice(BigDecimal.valueOf(50));
        order.setUser(user);
        order.setReceiveDate(LocalDate.now());
        List<Order> orders = List.of(order);
        Mockito.when(mockOrderRepository.findReadyOrders()).thenReturn(orders);
        List<OrderReadyViewModel> actual = orderService.getReady();
        assertEquals(order.getClient().getClientName(), actual.get(0).getClientName());
        assertEquals(order.getModel().getModelName(), actual.get(0).getModel());
        assertEquals(order.getModel().getBrand().getBrandName(), actual.get(0).getBrand());
        assertEquals(order.getDamage().getDamageName(), actual.get(0).getDamage());
        assertEquals(order.getSerialNumber(), actual.get(0).getSerialNumber());
        assertEquals(order.getTotalSparePartsPrice(), actual.get(0).getTotalSparePartsPrice());
        assertEquals(order.getTotalRepairPrice(), actual.get(0).getTotalRepairPrice());
    }

    @Test
    void getById() {
        Order order = new Order();
        Client client = new Client();
        client.setClientName("Gosho");
        client.setClientPhoneNumber("0888123456");
        Damage damage = new Damage("Broken LCD");
        Brand brand = new Brand("Huawei");
        Model model = new Model("P40 lite", brand);
        User user = new User();
        user.setUsername("Ivan");
        order.setClient(client);
        order.setDamage(damage);
        order.setModel(model);
        order.setSerialNumber("350101006543210");
        order.setTotalRepairPrice(BigDecimal.valueOf(100));
        order.setTotalSparePartsPrice(BigDecimal.valueOf(50));
        order.setUser(user);
        order.setReceiveDate(LocalDate.now());
        Mockito.when(mockOrderRepository.findById(1L)).thenReturn(java.util.Optional.of(order));
        OrderNotReadyViewModel actual = orderService.getById(1L);
        assertEquals(order.getClient().getClientName(), actual.getClientName());
        assertEquals(order.getModel().getModelName(), actual.getModel());
        assertEquals(order.getModel().getBrand().getBrandName(), actual.getBrand());
        assertEquals(order.getDamage().getDamageName(), actual.getDamage());
        assertEquals(order.getSerialNumber(), actual.getSerialNumber());
    }

    @Test
    void deleteOrder() {
        orderService.deleteOrder(1L);
    }

    @Test
    void isContainSparePart() {
        Mockito.when(mockOrderRepository.findBySparePart(1L)).thenReturn(1L);
        assertTrue(orderService.isContainSparePart(1L));
    }

    @Test
    void getNotPayedOrders() {
        Order order = new Order();
        Client client = new Client();
        client.setClientName("Gosho");
        client.setClientPhoneNumber("0888123456");
        Damage damage = new Damage("Broken LCD");
        Brand brand = new Brand("Huawei");
        Model model = new Model("P40 lite", brand);
        User user = new User();
        user.setUsername("Ivan");
        order.setClient(client);
        order.setDamage(damage);
        order.setModel(model);
        order.setSerialNumber("350101006543210");
        order.setTotalRepairPrice(BigDecimal.valueOf(100));
        order.setTotalSparePartsPrice(BigDecimal.valueOf(50));
        order.setUser(user);
        order.setReceiveDate(LocalDate.now());
        List<Order> orders = List.of(order);
        Mockito.when(mockOrderRepository.findAllByLeaveDateIsNull()).thenReturn(orders);
        List<OrderReadyViewModel> actual = orderService.getNotPayedOrders();
        assertEquals(order.getClient().getClientName(), actual.get(0).getClientName());
        assertEquals(order.getModel().getModelName(), actual.get(0).getModel());
        assertEquals(order.getModel().getBrand().getBrandName(), actual.get(0).getBrand());
        assertEquals(order.getDamage().getDamageName(), actual.get(0).getDamage());
        assertEquals(order.getSerialNumber(), actual.get(0).getSerialNumber());
        assertEquals(order.getTotalSparePartsPrice(), actual.get(0).getTotalSparePartsPrice());
        assertEquals(order.getTotalRepairPrice(), actual.get(0).getTotalRepairPrice());
    }

    @Test
    void fix() {
//TODO
    }

    @Test
    void getReadyById() {
        Order order = new Order();
        Client client = new Client();
        client.setClientName("Gosho");
        client.setClientPhoneNumber("0888123456");
        Damage damage = new Damage("Broken LCD");
        Brand brand = new Brand("Huawei");
        Model model = new Model("P40 lite", brand);
        User user = new User();
        user.setUsername("Ivan");
        order.setClient(client);
        order.setDamage(damage);
        order.setModel(model);
        order.setSerialNumber("350101006543210");
        order.setTotalRepairPrice(BigDecimal.valueOf(100));
        order.setTotalSparePartsPrice(BigDecimal.valueOf(50));
        order.setUser(user);
        order.setReceiveDate(LocalDate.now());
        Mockito.when(mockOrderRepository.findById(1L)).thenReturn(java.util.Optional.of(order));
        OrderReadyViewModel actual = orderService.getReadyById(1L);
        assertEquals(order.getClient().getClientName(), actual.getClientName());
        assertEquals(order.getModel().getModelName(), actual.getModel());
        assertEquals(order.getModel().getBrand().getBrandName(), actual.getBrand());
        assertEquals(order.getDamage().getDamageName(), actual.getDamage());
        assertEquals(order.getSerialNumber(), actual.getSerialNumber());
    }

    @Test
    void pay() {
        Order order = new Order();
        Mockito.when(mockOrderRepository.findById(1L)).thenReturn(java.util.Optional.of(order));
        orderService.pay(1L);
    }

    @Test
    void makeNotFixed() {
        Order order = new Order();
        Mockito.when(mockOrderRepository.findById(1L)).thenReturn(java.util.Optional.of(order));
        orderService.makeNotFixed(1L);
    }

    @Test
    void findOrders() {
        Order order = new Order();
        Client client = new Client();
        client.setClientName("Gosho");
        client.setClientPhoneNumber("0888123456");
        Damage damage = new Damage("Broken LCD");
        Brand brand = new Brand("Huawei");
        Model model = new Model("P40 lite", brand);
        User user = new User();
        user.setUsername("Ivan");
        order.setClient(client);
        order.setDamage(damage);
        order.setModel(model);
        order.setSerialNumber("350101006543210");
        order.setTotalRepairPrice(BigDecimal.valueOf(100));
        order.setTotalSparePartsPrice(BigDecimal.valueOf(50));
        order.setUser(user);
        order.setReceiveDate(LocalDate.now());
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        Mockito.when(mockOrderRepository.findAllBySerialNumber("%350%")).thenReturn(orders);
        List<OrderViewModel> actual = orderService.findOrders("350");
        assertEquals(order.getClient().getClientName(), actual.get(0).getClientName());
        assertEquals(order.getModel().getModelName(), actual.get(0).getModel());
        assertEquals(order.getModel().getBrand().getBrandName(), actual.get(0).getBrandName());
        assertEquals(order.getDamage().getDamageName(), actual.get(0).getDamage());
        assertEquals(order.getSerialNumber(), actual.get(0).getSerialNumber());
        assertEquals(order.getTotalRepairPrice(), actual.get(0).getTotalRepairPrice());
    }

    @Test
    void findOrdersByClientId() {
        Order order = new Order();
        Client client = new Client();
        client.setClientName("Gosho");
        client.setClientPhoneNumber("0888123456");
        Damage damage = new Damage("Broken LCD");
        Brand brand = new Brand("Huawei");
        Model model = new Model("P40 lite", brand);
        User user = new User();
        user.setUsername("Ivan");
        order.setClient(client);
        order.setDamage(damage);
        order.setModel(model);
        order.setSerialNumber("350101006543210");
        order.setTotalRepairPrice(BigDecimal.valueOf(100));
        order.setTotalSparePartsPrice(BigDecimal.valueOf(50));
        order.setUser(user);
        order.setReceiveDate(LocalDate.now());
        List<Order> orders = List.of(order);
        Mockito.when(mockOrderRepository.findAllByClientId(1L)).thenReturn(orders);
        List<OrderViewModel> actual = orderService.findOrdersByClientId(1L);
        assertEquals(order.getClient().getClientName(), actual.get(0).getClientName());
        assertEquals(order.getModel().getModelName(), actual.get(0).getModel());
        assertEquals(order.getModel().getBrand().getBrandName(), actual.get(0).getBrandName());
        assertEquals(order.getDamage().getDamageName(), actual.get(0).getDamage());
        assertEquals(order.getSerialNumber(), actual.get(0).getSerialNumber());
        assertEquals(order.getTotalRepairPrice(), actual.get(0).getTotalRepairPrice());
    }

    @Test
    void getByStartDateAndEndDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatterToString = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        IncomePerPeriodServiceModel income = new IncomePerPeriodServiceModel(LocalDate.of(2021, 03, 01), BigDecimal.valueOf(100), BigDecimal.valueOf(50));
        List<IncomePerPeriodServiceModel> incomes = new ArrayList<>();
        incomes.add(income);
        Mockito.when(mockOrderRepository.findAllByLeaveDateBetween(LocalDate.parse("2021-03-01", formatter), LocalDate.parse("2021-03-31", formatter))).thenReturn(incomes);
        List<IncomePerPeriodViewModel> actual = orderService.getByStartDateAndEndDate("2021-03-01", "2021-03-31", "");
        assertEquals(income.getLeaveDate().format(formatterToString), actual.get(0).getLeaveDateString());
        assertEquals(income.getTotalSparePartsPrice(), actual.get(0).getTotalSparePartsPrice());
        assertEquals(income.getTotalRepairPrice(), actual.get(0).getTotalRepairPrice());
    }

    @Test
    void getByDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        Order order = new Order();
        Client client = new Client();
        client.setClientName("Gosho");
        client.setClientPhoneNumber("0888123456");
        Damage damage = new Damage("Broken LCD");
        Brand brand = new Brand("Huawei");
        Model model = new Model("P40 lite", brand);
        User user = new User();
        user.setUsername("Ivan");
        order.setClient(client);
        order.setDamage(damage);
        order.setModel(model);
        order.setSerialNumber("350101006543210");
        order.setTotalRepairPrice(BigDecimal.valueOf(100));
        order.setTotalSparePartsPrice(BigDecimal.valueOf(50));
        order.setUser(user);
        order.setReceiveDate(LocalDate.now());
        List<Order> orders = List.of(order);
        Mockito.when(mockOrderRepository.getByDate(LocalDate.parse("01-03-2021", formatter))).thenReturn(orders);
        List<OrderViewModel> actual = orderService.getByDate("01-03-2021");
        assertEquals(order.getClient().getClientName(), actual.get(0).getClientName());
        assertEquals(order.getModel().getModelName(), actual.get(0).getModel());
        assertEquals(order.getModel().getBrand().getBrandName(), actual.get(0).getBrandName());
        assertEquals(order.getDamage().getDamageName(), actual.get(0).getDamage());
        assertEquals(order.getSerialNumber(), actual.get(0).getSerialNumber());
        assertEquals(order.getTotalRepairPrice(), actual.get(0).getTotalRepairPrice());
    }

    @Test
    void findTechnicians() {

    }
}