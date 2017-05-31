package org.openhab.training.electricity.radio.ds;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.openhab.training.electricity.consumer.ElectricityConsumer;
import org.openhab.training.electricity.provider.ElectricityProvider;
import org.osgi.service.component.annotations.Component;

@Component
public class BasicRadioComponent implements ElectricityConsumer {

    final int consumption = 2;
    private ElectricityProvider currentProvider;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void bind(ElectricityProvider pr) {
        System.out.println("started radio");

        currentProvider = pr;
        System.out.println("Service set succesfully and started using it:" + pr);

    }

    public void activate() {
        System.out.println("Radio component activated");
        startConsuming();
    }

    public void unbind(ElectricityProvider e) {
        currentProvider = null;
        System.out.println("Radio has been stoped");
    }

    @Override
    public List<ElectricityProvider> getAllAvailableProviders() {
        // no need to override at this phase
        return null;
    }

    @Override
    public void stopConsuming() {
        System.out.println("Radio stopped");
        currentProvider = null;
    }

    @Override
    public void startConsuming() {

        Thread providingAction = new Thread() {

            @Override
            public void run() {

                currentProvider.provide(consumption);
            }
        };

        scheduler.scheduleAtFixedRate(providingAction, 7, 7, TimeUnit.SECONDS);
    }

    @Override
    public void setCurrentProvider(ElectricityProvider e) {
        currentProvider = e;

    }

}