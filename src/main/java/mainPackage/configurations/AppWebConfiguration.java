package mainPackage.configurations;

import mainPackage.interceptors.OrdersInterceptor;
import mainPackage.interceptors.UserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppWebConfiguration  implements WebMvcConfigurer {
    private final OrdersInterceptor ordersInterceptor;
    private final UserInterceptor userInterceptor;

    @Autowired
    public AppWebConfiguration(OrdersInterceptor ordersInterceptor, UserInterceptor userInterceptor) {
        this.ordersInterceptor = ordersInterceptor;
        this.userInterceptor = userInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(ordersInterceptor);
        registry.addInterceptor(userInterceptor);
    }
}
