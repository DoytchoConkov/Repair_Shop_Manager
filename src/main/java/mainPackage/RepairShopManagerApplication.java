package mainPackage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RepairShopManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RepairShopManagerApplication.class, args);
    }

}
