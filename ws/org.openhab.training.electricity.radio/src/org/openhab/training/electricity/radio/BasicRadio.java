package org.openhab.training.electricity.radio;

import java.util.List;

import org.openhab.training.electricity.consumer.ElectricityConsumer;
import org.openhab.training.electricity.provider.ElectricityProvider;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class BasicRadio implements ElectricityConsumer, BundleActivator {

    private int consumption = 5;
    private ElectricityProvider currentProvider;
    private ElectricityProvider service;
    private BundleContext fContext;

    @SuppressWarnings("unchecked")
    @Override
    public void start(BundleContext context) {
        fContext = context;
        // gets service and set it to current provider

        ServiceReference refService = context.getServiceReference(ElectricityProvider.class);
        if (refService != null) {
            service = (ElectricityProvider) context.getService(refService);
            setCurrentProvider(service);
            startConsuming();
            System.out.println("Radio started consuming...");
        } else {
            System.out.println(
                    "There is no electricity provider... stop it and then try restarting with an active provider");

        }

    }

    @Override
    public void stop(BundleContext context) {
        service = null;
        System.out.println("Radio has been stoped");
    }

    @Override
    public void setCurrentProvider(ElectricityProvider e) {
        currentProvider = e;

    }

    @Override
    public List<ElectricityProvider> getAllAvailableProviders() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void startConsuming() {

        if (currentProvider != null) {
            System.out.println("Radio is working");
        } else {
            System.out.println("There is no set provider");
        }

    }

    @Override
    public void stopConsuming() {
        System.out.println("Radio stopped");

    }

}
