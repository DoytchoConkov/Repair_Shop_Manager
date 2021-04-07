package mainPackage.services;

import mainPackage.models.services.OrderFixServiceModel;
import mainPackage.models.views.IncomePerPeriodViewModel;
import mainPackage.models.views.OrderNotReadyViewModel;
import mainPackage.models.services.OrderReceiveServiceModel;
import mainPackage.models.views.OrderReadyViewModel;
import mainPackage.models.views.OrderViewModel;

import java.io.IOException;
import java.util.List;

public interface OrderService {

    void add(OrderReceiveServiceModel OrderReceiveServiceModel);

    List<OrderNotReadyViewModel> getNotReady();

    List<OrderReadyViewModel> getReady();

    OrderNotReadyViewModel getById(Long id);

    void deleteOrder(Long id);

    boolean isContainSparePart(Long id);

    List<OrderReadyViewModel> getNotPayedOrders();

    void fix(OrderFixServiceModel map) throws IOException;

    OrderReadyViewModel getReadyById(Long id);

    void pay(Long id);

    void makeNotFixed(Long id);

    List<OrderViewModel> findOrders(String serialNumber);

    List<OrderViewModel> findOrdersByClientId(Long id);

    List<IncomePerPeriodViewModel> getByStartDateAndEndDate(String startDate, String endDate, String technician);

    List<OrderViewModel> getByDate(String date);

    List<String> findTechnicians();

    int countNotReadyOrders();

    int countReadyOrders();

    List<String> getByBrandName(String brandName);
}
