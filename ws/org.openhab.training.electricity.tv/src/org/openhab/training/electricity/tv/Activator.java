package org.openhab.training.electricity.tv;

import org.openhab.training.electricity.provider.ElectricityProvider;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

public class Activator implements BundleActivator {

    TV tv;
    // Activator of the TV

    @Override
    public void start(BundleContext bundleContext) throws Exception {

        tv = new TV(bundleContext);
        ServiceTracker tracker = new ServiceTracker<>(bundleContext, ElectricityProvider.class, tv);
        tracker.open();
        System.out.println("Tracking started...");
        tv.launcher();

    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {

    }

}
