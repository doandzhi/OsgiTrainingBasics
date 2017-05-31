package org.openhab.training.electricity.combinedradio;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.openhab.training.electricity.dynamicconsumer.DynamicConsumer;
import org.openhab.training.electricity.provider.ElectricityProvider;
import org.openhab.training.electricity.solarradioo.SolarRadioComponent;

public class CombinedRadio extends SolarRadioComponent implements DynamicConsumer {

    int consumption = 2;
    ElectricityProvider currentProvider;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    public static List<ElectricityProvider> listOfProviders = new ArrayList<ElectricityProvider>();

    @Override
    public void bind(ElectricityProvider pr) {
        System.out.println("Started combined radio");
        if (pr instanceof ElectricityProvider) {
            providerAdded(pr);
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
        System.out.println("CombinedRadio component activated");
    }

    @Override
    public void unbind(ElectricityProvider e) {
        scheduler.shutdown();
        currentProvider = null;
        System.out.println("Radio working without provider");

    }

    @Override
    public void providerAdded(ElectricityProvider e) {
        listOfProviders.add(e);
    }

    @Override
    public void providerRemoved(ElectricityProvider e) {
        listOfProviders.remove(e);
        if (currentProvider == e) {
            currentProvider = null;
        }
        System.out.println("provider has been removed " + " current provider is" + currentProvider);

    }

}
