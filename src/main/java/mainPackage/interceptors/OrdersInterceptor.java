package mainPackage.interceptors;

import mainPackage.services.OrderService;
import org.springframework.stereotype.Component;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class OrdersInterceptor implements HandlerInterceptor {
    private final OrderService orderService;

    public OrdersInterceptor(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView){
        int notReadyOrders = orderService.countNotReadyOrders();
        int readyOrders = orderService.countReadyOrders();
        if (modelAndView == null) {
            modelAndView = new ModelAndView();
        }
            modelAndView.addObject("notReadyOrders", notReadyOrders);
            modelAndView.addObject("readyOrders", readyOrders);
    }
}
