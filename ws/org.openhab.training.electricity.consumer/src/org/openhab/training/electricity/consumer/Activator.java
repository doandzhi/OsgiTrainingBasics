package org.openhab.training.electricity.consumer;

import java.util.List;

import org.openhab.training.electricity.provider.ElectricityProvider;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator, ElectricityConsumer {

    private static BundleContext context;

    static BundleContext getContext() {
        return context;
    }

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        Activator.context = bundleContext;
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        Activator.context = null;
    }

    @Override
    public void setCurrentProvider(ElectricityProvider e) {

    }

    @Override
    public List<ElectricityProvider> getAllAvailableProviders() {

        return null;
    }

    @Override
    public void startConsuming() {
        // TODO Auto-generated method stub

    }

    @Override
    public void stopConsuming() {
        // TODO Auto-generated method stub

    }

}
