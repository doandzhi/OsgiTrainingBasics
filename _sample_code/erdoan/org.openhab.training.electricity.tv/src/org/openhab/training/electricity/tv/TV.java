package org.openhab.training.electricity.tv;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.openhab.training.electricity.battery.Battery;
import org.openhab.training.electricity.consumer.ElectricityConsumer;
import org.openhab.training.electricity.dynamicconsumer.DynamicConsumer;
import org.openhab.training.electricity.homenetwork.HomeElectricityNetwork;
import org.openhab.training.electricity.provider.ElectricityProvider;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

public class TV implements ElectricityConsumer, DynamicConsumer,
        ServiceTrackerCustomizer<ElectricityProvider, ElectricityProvider> {

    private int consumption = 5;
    private ElectricityProvider currentProvider;
    public static List<ElectricityProvider> listOfProviders = new ArrayList<ElectricityProvider>();
    private BundleContext context;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public TV(BundleContext context) {
        this.context = context;
    }

    void launcher() throws InterruptedException {
        // System.out.println("launcher started");
        List<ElectricityProvider> providers = getAllAvailableProviders();
        // vikame fora v nishka koqto izbira provider

        Thread providingAction = new Thread() {

            @Override
            public void run() {
                for (ElectricityProvider provider : providers) {
                    if (provider instanceof HomeElectricityNetwork) {
                        setCurrentProvider(provider);
                        System.out.println("Provider is the electricity network...");

                    }

                    else if (listOfProviders.contains(provider instanceof HomeElectricityNetwork)) {

                        setCurrentProvider(provider);
                        System.out.println("Provider is the battery...");

                    }
                }
                currentProvider.provide(consumption);
            }
        };
        // puskame nishka prez opredelen period

        scheduler.scheduleAtFixedRate(providingAction, 10, 5, TimeUnit.SECONDS);

    }

    @Override
    public ElectricityProvider addingService(ServiceReference<ElectricityProvider> reference) {

        ElectricityProvider service = context.getService(reference);
        if (service instanceof HomeElectricityNetwork || service instanceof Battery) {
            providerAdded(service);
            System.out.println("Service added to the list succesfully:" + service);
        } else {
            System.out.println("no service added ");
        }
        System.out.println("Current provider is " + currentProvider);
        return service;
    }

    @Override
    public void modifiedService(ServiceReference<ElectricityProvider> reference, ElectricityProvider service) {
        // we don't need this yet

    }

    @Override
    public void removedService(ServiceReference<ElectricityProvider> reference, ElectricityProvider service) {

        ElectricityProvider serviceElProvider = context.getService(reference);
        providerRemoved(serviceElProvider);

        if (listOfProviders.size() != 0) {
            setCurrentProvider(listOfProviders.get(0));
            System.out.println("current provider is " + listOfProviders.get(0));
        } else {
            System.out.println("No available providers at the moment...");
        }

    }

    @Override
    public void providerAdded(ElectricityProvider e) {
        // adds the provider to the list
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
