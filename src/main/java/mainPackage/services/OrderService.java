package mainPackage.services;

import mainPackage.models.entities.SparePart;
import mainPackage.models.views.OrderNotReadyViewModel;
import mainPackage.models.services.OrderReceiveServiceModel;
import mainPackage.models.views.OrderReadyViewModel;

import java.util.List;

public interface OrderService {

    void add(OrderReceiveServiceModel OrderReceiveServiceModel);
    List<OrderNotReadyViewModel> getNotReady();
    List<OrderReadyViewModel> getReady();

    OrderNotReadyViewModel getById(Long id);

    void deleteOrder(Long id);

    boolean isContainSparePart(Long id);

    List<OrderReadyViewModel> getNotPayedOrders();
}
