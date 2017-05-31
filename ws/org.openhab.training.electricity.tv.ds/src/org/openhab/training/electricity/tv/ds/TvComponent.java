package org.openhab.training.electricity.tv.ds;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.openhab.training.electricity.consumer.ElectricityConsumer;
import org.openhab.training.electricity.dynamicconsumer.DynamicConsumer;
import org.openhab.training.electricity.homenetwork.ds.HomeNetworkComponent;
import org.openhab.training.electricity.provider.ElectricityProvider;

public class TvComponent implements ElectricityConsumer, DynamicConsumer {

    private int consumption = 5;
    private ElectricityProvider currentProvider;
    public static List<ElectricityProvider> listOfProviders = new ArrayList<ElectricityProvider>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void activate() throws InterruptedException {

        System.out.println("Tv component activated");
        Thread providingAction = new Thread() {

            @Override
            public void run() {

                providingByType();
            }
        };

        scheduler.scheduleAtFixedRate(providingAction, 7, 7, TimeUnit.SECONDS);
    }

    public void deactivate() {
        scheduler.shutdown();
        System.out.println("Tv component deactivated");
    }

    public void bind(ElectricityProvider pr) {
        if (pr instanceof ElectricityProvider) {
            providerAdded(pr);
            System.out.println("Service added to the list succesfully:" + pr);
        } else {
            System.out.println("no service added ");
        }
    }

    public void unbind(ElectricityProvider e) {
        providerRemoved(e);
        if (listOfProviders.size() != 0) {
            setCurrentProvider(listOfProviders.get(0));
            System.out.println("Current provider is: " + listOfProviders.get(0));
        } else {
            System.out.println("No available providers at the moment...");
        }

    }

    public void providingByType() {
        List<ElectricityProvider> providers = getAllAvailableProviders();
        for (ElectricityProvider provider : providers) {
            // is homenetwork
            if (provider instanceof HomeNetworkComponent) {
                setCurrentProvider(provider);
                System.out.println("Provider is the electricity network...");
            }
            // is battery
            else if (listOfProviders.contains(provider instanceof HomeNetworkComponent)) {

                setCurrentProvider(provider);
                System.out.println("Provider is the battery...");

            }
            currentProvider.provide(consumption);
        }
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

    @Override
    public void startConsuming() {
        System.out.println("started consuming");

    }

    @Override
    public void stopConsuming() {
        System.out.println("Stopped consuming");
    }

    @Override
    public void setCurrentProvider(ElectricityProvider e) {
        currentProvider = e;
    }

    @Override
    public List<ElectricityProvider> getAllAvailableProviders() {
        // return the list like a get method
        return listOfProviders;
    }

}