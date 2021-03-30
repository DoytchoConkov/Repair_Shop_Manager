package mainPackage.aop;

import mainPackage.models.services.OrderFixServiceModel;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


import java.io.FileWriter;
import java.io.IOException;

@Aspect
@Component
public class SLOFixOrder {


    @Pointcut("execution(* mainPackage.services.OrderService.fix(..))")
    public void trackSave() {}

    @AfterReturning(pointcut = "trackSave()", returning = "orderFixModel")
    public void loggingAfterReturning(JoinPoint joinPoint, Object orderFixModel) throws IOException {
        OrderFixServiceModel orderFixServiceModel = (OrderFixServiceModel) orderFixModel;
        FileWriter myWriter = new FileWriter("src/main/java/mainPackage/logs/orderFix.log", true);
        //TODO
      //  myWriter.write(orderFixServiceModel.toString());
        myWriter.close();
    }
}
