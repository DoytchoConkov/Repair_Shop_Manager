package мainPackage.services;

import мainPackage.models.views.OrderNotReadyViewModel;
import мainPackage.models.services.OrderReceiveServiceModel;
import мainPackage.models.views.OrderReadyViewModel;

import java.util.List;

public interface OrderService {

    void add(OrderReceiveServiceModel OrderReceiveServiceModel);
    List<OrderNotReadyViewModel> getNotReady();
    List<OrderReadyViewModel> getReady();

    OrderNotReadyViewModel getById(Long id);
}
