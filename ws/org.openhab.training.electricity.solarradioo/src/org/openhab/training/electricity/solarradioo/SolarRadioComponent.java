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
    public void bind(ElectricityProvider pr) {
        System.out.println("started Solar radio");
        if (pr != null) {
            currentProvider = pr;
            System.out.println("Service set succesfully and started using it:" + pr);
            startConsuming();
        } else {
            System.out.println("Radio is working without electricity...");

        }

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
    public void activate() {

        System.out.println("Radio component activated");

    }

    @Override
    public void unbind(ElectricityProvider e) {
        scheduler.shutdown();
        currentProvider = null;
        System.out.println("Radio working without provider");

    }

}