package mainPackage.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;


import java.io.FileWriter;

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
        SLOsConfig.SLOConfig config = configs.getSlos().
                stream().
                filter(s -> s.getId().equals(latencyId)).
                findAny().orElseThrow(() -> new IllegalStateException(latencyId + " not found!"));

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        pjp.proceed();
        stopWatch.stop();

        long actualLatency = stopWatch.getLastTaskTimeMillis();
        FileWriter myWriter = new FileWriter("src/main/java/mainPackage/logs/orderFix.log", true);
        myWriter.write(String.format("The latency is: %d", actualLatency));
        myWriter.close();
    }
}
