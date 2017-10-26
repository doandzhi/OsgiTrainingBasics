package org.openhab.training.util.sender;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.openhab.training.utils.TimeUtilities;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

public class TimeSenderComponent implements TimeEventSender {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    Map<String, String> timeMap = new HashMap<String, String>();
    EventAdmin ea;
    String currentTime;

    public void bind(EventAdmin ea) {
        this.ea = ea;
    }

    // Sends the current time as event to the listeners on this topic every second

    @Override
    public void sendCurrentTime() {

        final Runnable clock = new Runnable() {
            @Override
            public void run() {

                currentTime = TimeUtilities.getCurrentTimeStamp();
                timeMap.put("timeUtil", currentTime);
                Event event = new Event("org/openhab/training/time", timeMap);
                ea.postEvent(event);
                timeMap.clear();

            }
        };
        scheduler.scheduleAtFixedRate(clock, 1, 1, TimeUnit.SECONDS);

    }

    public void activate() {

        System.out.println("Started broadcasting events! ");
        sendCurrentTime();
    }

}
