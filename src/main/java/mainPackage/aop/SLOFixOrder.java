package mainPackage.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;


import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Aspect
@Component
public class SLOFixOrder {

    private final SLOsConfig configs;

    public SLOFixOrder(SLOsConfig configs) {
        this.configs = configs;
    }

    @Around(value = "@annotation(TrackLatency)")
    public void trackLatency(ProceedingJoinPoint pjp, TrackLatency TrackLatency) throws Throwable {
        String latencyId = TrackLatency.latency();
//        SLOsConfig.SLOConfig config = configs.getSlos().
//                stream().
//                filter(s -> s.getId().equals(latencyId)).
//                findAny().orElseThrow(() -> new IllegalStateException("Configuration with id " + latencyId + " not found!"));

        DateTimeFormatter formatterToString = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        pjp.proceed();
        stopWatch.stop();

        long actualLatency = stopWatch.getLastTaskTimeMillis();
        FileWriter myWriter = new FileWriter("src/main/java/mainPackage/logs/orderFix.log", true);
        myWriter.write(String.format("%s The latency for %s is: %d%n", LocalDateTime.now().format(formatterToString), latencyId, actualLatency));
        myWriter.close();
    }
}
