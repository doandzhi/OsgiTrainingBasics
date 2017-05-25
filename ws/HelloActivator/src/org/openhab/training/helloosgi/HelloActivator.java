package org.openhab.training.helloosgi;

import org.openhab.training.utils.TimeUtilities;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class HelloActivator implements BundleActivator {
    @Override
    public void start(BundleContext context) {
        System.out.println("Hello mein bundle!");
        System.out.println(TimeUtilities.getCurrentTimeStamp());
    }

    @Override
    public void stop(BundleContext context) {
        System.out.println("Goodbye bundle!");
    }
}
