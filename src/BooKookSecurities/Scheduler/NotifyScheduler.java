package BooKookSecurities.Scheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NotifyScheduler {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void setScheduler(Runnable task, int delay){
        //delay in hours
        scheduler.scheduleAtFixedRate(task, 0, delay, TimeUnit.HOURS);
    }
}
