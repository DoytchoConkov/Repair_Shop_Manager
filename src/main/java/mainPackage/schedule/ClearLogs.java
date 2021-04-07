package mainPackage.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class ClearLogs {
    @Scheduled(cron = "0 0 0 1 */1 *")
    public void clearFixesInThreeMounts() {
        new File("scr/main/java/logs/mainPackage/logFile.log").delete();
    }
}
