package org.openhab.training.electricity.tv.events;

import java.util.Dictionary;

import org.openhab.training.electricity.tv.ds.TvComponent;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

public class EventsTV extends TvComponent implements EventHandler, ManagedService {

    String currentTime;
    String autoSleep;

    @Override
    public void handleEvent(Event event) {

        this.currentTime = (String) event.getProperty("timeUtil");

        if (!currentTime.equals(autoSleep)) {
            System.out.println("Current time is: " + event.getProperty("timeUtil"));
        } else {
            deactivate();
            return;
        }

    }

    @Override
    public void updated(Dictionary<String, ?> properties) throws ConfigurationException {
        System.out.println("Auto-sleep time:  " + properties.get("sleep"));
        this.autoSleep = (String) properties.get("sleep");
    }

}
