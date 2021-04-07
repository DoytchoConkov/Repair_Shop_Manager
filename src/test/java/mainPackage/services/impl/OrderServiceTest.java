package mainPackage.services.impl;

import mainPackage.errors.OrderIdNotFoundException;
import mainPackage.models.entities.*;
import mainPackage.models.services.ClientServiceModel;
import mainPackage.models.services.IncomePerPeriodServiceModel;
import mainPackage.models.services.OrderFixServiceModel;
import mainPackage.models.services.OrderReceiveServiceModel;
import mainPackage.models.views.IncomePerPeriodViewModel;
import mainPackage.models.views.OrderNotReadyViewModel;
import mainPackage.models.views.OrderReadyViewModel;
import mainPackage.models.views.OrderViewModel;
import mainPackage.repositories.ModelRepository;
import mainPackage.repositories.OrderRepository;
import mainPackage.repositories.UserRepository;
import mainPackage.services.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class OrderServiceTest {
    @Autowired
    private OrderService orderService;
    @MockBean
    private OrderRepository mockOrderRepository;
    @MockBean
    private ModelRepository mockModelRepository;
    @MockBean
    private UserRepository mockUserRepository;

    @Test
    void add() {
        ClientServiceModel clientServiceModel = new ClientServiceModel();
        clientServiceModel.setClientName("Gosho");
        clientServiceModel.setClientPhoneNumber("0888123456");
        OrderReceiveServiceModel orderReceiveServiceModel = new OrderReceiveServiceModel();
        orderReceiveServiceModel.setModel("Iphone 7");
        orderReceiveServiceModel.setBrand("Apple");
        orderReceiveServiceModel.setDamage("Broken LCD");
        orderReceiveServiceModel.setSerialNumber("350101002635912");
        orderReceiveServiceModel.setClient(clientServiceModel);
        orderService.add(orderReceiveServiceModel);
        verify(mockOrderRepository,times(1)).save(any());

    }

    @Test
    void getNotReady() {
        OrderEntity order = new OrderEntity();
        ClientEntity client = new ClientEntity();
        client.setClientName("Gosho");
        client.setClientPhoneNumber("0888123456");
        DamageEntity damage = new DamageEntity("Broken LCD");
        BrandEntity brand = new BrandEntity("Huawei");
        ModelEntity model = new ModelEntity("P40 lite", brand);
        order.setClient(client);
        order.setDamage(damage);
        order.setModel(model);
        order.setSerialNumber("350101006543210");
        order.setReceiveDate(LocalDate.now());
        List<OrderEntity> orders = List.of(order);
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
        OrderEntity order = new OrderEntity();
        ClientEntity client = new ClientEntity();
        client.setClientName("Gosho");
        client.setClientPhoneNumber("0888123456");
        DamageEntity damage = new DamageEntity("Broken LCD");
        BrandEntity brand = new BrandEntity("Huawei");
        ModelEntity model = new ModelEntity("P40 lite", brand);
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
        List<OrderEntity> orders = List.of(order);
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
        OrderEntity order = new OrderEntity();
        ClientEntity client = new ClientEntity();
        client.setClientName("Gosho");
        client.setClientPhoneNumber("0888123456");
        DamageEntity damage = new DamageEntity("Broken LCD");
        BrandEntity brand = new BrandEntity("Huawei");
        ModelEntity model = new ModelEntity("P40 lite", brand);
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
    void getByIdWithWrongId() {
        Assertions.assertThrows(OrderIdNotFoundException.class, () -> orderService.getById(23L));
    }

    @Test
    void deleteOrder() {
        orderService.deleteOrder(1L);
        verify(mockOrderRepository, times(1)).deleteById(1L);
    }

    @Test
    void isContainSparePart() {
        Mockito.when(mockOrderRepository.findBySparePart(1L)).thenReturn(1L);
        assertTrue(orderService.isContainSparePart(1L));
    }

    @Test
    void getNotPayedOrders() {
        OrderEntity order = new OrderEntity();
        ClientEntity client = new ClientEntity();
        client.setClientName("Gosho");
        client.setClientPhoneNumber("0888123456");
        DamageEntity damage = new DamageEntity("Broken LCD");
        BrandEntity brand = new BrandEntity("Huawei");
        ModelEntity model = new ModelEntity("P40 lite", brand);
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
        List<OrderEntity> orders = List.of(order);
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
    @WithMockUser(username = "Ivan")
    void fix() throws IOException {
        OrderFixServiceModel orderFixServiceModel = new OrderFixServiceModel();
        orderFixServiceModel.setId(1L);
        orderFixServiceModel.setSparePartIds(null);
        orderFixServiceModel.setSparePartPrice(BigDecimal.valueOf(10));
        orderFixServiceModel.setTotalPrice(BigDecimal.valueOf(30));
        OrderEntity order = new OrderEntity();
        ClientEntity client = new ClientEntity();
        client.setClientName("Gosho");
        client.setClientPhoneNumber("0888123456");
        DamageEntity damage = new DamageEntity("Broken LCD");
        BrandEntity brand = new BrandEntity("Huawei");
        ModelEntity model = new ModelEntity("P40 lite", brand);
        User user = new User();
        user.setUsername("Ivan");
        order.setClient(client);
        order.setDamage(damage);
        order.setModel(model);
        order.setSerialNumber("350101006543210");
        order.setReceiveDate(LocalDate.now());
        Mockito.when(mockOrderRepository.findById(1L)).thenReturn(java.util.Optional.of(order));
        Mockito.when(mockUserRepository.findByUsername("Ivan")).thenReturn(java.util.Optional.of(user));
        orderService.fix(orderFixServiceModel);
        verify(mockOrderRepository, times(1)).save(order);
    }

    @Test
    void getReadyById() {
        OrderEntity order = new OrderEntity();
        ClientEntity client = new ClientEntity();
        client.setClientName("Gosho");
        client.setClientPhoneNumber("0888123456");
        DamageEntity damage = new DamageEntity("Broken LCD");
        BrandEntity brand = new BrandEntity("Huawei");
        ModelEntity model = new ModelEntity("P40 lite", brand);
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
        OrderEntity order = new OrderEntity();
        Mockito.when(mockOrderRepository.findById(1L)).thenReturn(java.util.Optional.of(order));
        orderService.pay(1L);
        verify(mockOrderRepository,times(1)).save(any());
    }

    @Test
    void makeNotFixed() {
        OrderEntity order = new OrderEntity();
        Mockito.when(mockOrderRepository.findById(1L)).thenReturn(java.util.Optional.of(order));
        orderService.makeNotFixed(1L);
        verify(mockOrderRepository,times(1)).save(any());
    }

    @Test
    void findOrders() {
        OrderEntity order = new OrderEntity();
        ClientEntity client = new ClientEntity();
        client.setClientName("Gosho");
        client.setClientPhoneNumber("0888123456");
        DamageEntity damage = new DamageEntity("Broken LCD");
        BrandEntity brand = new BrandEntity("Huawei");
        ModelEntity model = new ModelEntity("P40 lite", brand);
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
        List<OrderEntity> orders = new ArrayList<>();
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
        OrderEntity order = new OrderEntity();
        ClientEntity client = new ClientEntity();
        client.setClientName("Gosho");
        client.setClientPhoneNumber("0888123456");
        DamageEntity damage = new DamageEntity("Broken LCD");
        BrandEntity brand = new BrandEntity("Huawei");
        ModelEntity model = new ModelEntity("P40 lite", brand);
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
        List<OrderEntity> orders = List.of(order);
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
        OrderEntity order = new OrderEntity();
        ClientEntity client = new ClientEntity();
        client.setClientName("Gosho");
        client.setClientPhoneNumber("0888123456");
        DamageEntity damage = new DamageEntity("Broken LCD");
        BrandEntity brand = new BrandEntity("Huawei");
        ModelEntity model = new ModelEntity("P40 lite", brand);
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
        List<OrderEntity> orders = List.of(order);
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
        Mockito.when(mockOrderRepository.findTechnicians()).thenReturn(List.of("Gosho", "Mitko"));
        List<String> technicians = orderService.findTechnicians();
        assertEquals("Gosho", technicians.get(0));
        assertEquals("Mitko", technicians.get(1));
    }

    @Test
    void countNotReadyOrders() {
        Mockito.when(mockOrderRepository.countNotReadyOrders()).thenReturn(1);
        int notReadyOrders = orderService.countNotReadyOrders();
        assertEquals(1, notReadyOrders);
    }

    @Test
    void countReadyOrders() {
        Mockito.when(mockOrderRepository.countReadyOrders()).thenReturn(1);
        int readyOrders = orderService.countReadyOrders();
        assertEquals(1, readyOrders);
    }
    @Test
    void getByBrandNameWithValidBrandName() {
        List<ModelEntity> models = new ArrayList<>();
        ModelEntity model = new ModelEntity();
        BrandEntity brand = new BrandEntity("Apple");
        model.setBrand(brand);
        model.setModelName("Iphone 6s");
        models.add(model);
        Mockito.when(mockModelRepository.getByBrandName("Apple")).thenReturn(List.of("Apple"));
        List<String> allModels = orderService.getByBrandName("Apple");
        assertEquals(models.size(), allModels.size());
    }

    @Test
    void getByBrandNameWithNotValidBrandName() {
        Mockito.when(mockModelRepository.getByBrandName("Apple")).thenReturn(new ArrayList<>());
        List<String> allModels = orderService.getByBrandName("Apple");
        assertTrue(allModels.isEmpty());
    }

}