package org.openhab.training.electricity.solarradioo;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.openhab.training.electricity.provider.ElectricityProvider;
import org.openhab.training.electricity.radio.ds.BasicRadioComponent;

public class SolarRadioComponent extends BasicRadioComponent {

    int consumption = 2;
    ElectricityProvider currentProvider;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Override
    public void bindElProvider(ElectricityProvider pr) {

        if (pr != null) {
            currentProvider = pr;

        } else {
            System.out.println("Radio is working without electricity...");

        }

    }

    @Override
    public void activate() {
        startConsuming();
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
    public void unbindElProvider(ElectricityProvider e) {

        currentProvider = null;
        System.out.println("Radio working without provider");

    }

    public void deactivate() {
        scheduler.shutdown();
        System.out.println("Radio is stopped...");
    }
}