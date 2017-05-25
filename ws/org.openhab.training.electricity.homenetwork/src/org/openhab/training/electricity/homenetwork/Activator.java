package org.openhab.training.electricity.homenetwork;

import java.util.Hashtable;

import org.openhab.training.electricity.provider.ElectricityProvider;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {
    // homenetwork activator
    private BundleContext fContext;
    private ElectricityProvider service;

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        fContext = bundleContext;
        service = new HomeElectricityNetwork();
        Hashtable<String, Object> props = new Hashtable<>();
        bundleContext.registerService(ElectricityProvider.class.getName(), service, props);
        System.out.println("registered home electricity network service");

    }

    /*
     * (non-Javadoc)
     *
     * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
     */
    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        fContext = null;
    }

}
