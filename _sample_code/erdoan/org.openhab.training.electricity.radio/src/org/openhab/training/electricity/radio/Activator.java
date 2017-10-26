package org.openhab.training.electricity.radio;

import org.openhab.training.electricity.provider.ElectricityProvider;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.BundleActivator;

public class Activator implements BundleActivator {

	BasicRadio radio;
	private ElectricityProvider service;

@Override
	public void start(BundleContext context) {
		// gets service and set it to current provider
		ServiceReference<ElectricityProvider> refService = context.getServiceReference(ElectricityProvider.class);
		if (refService != null) {
			service = (ElectricityProvider) context.getService(refService);
			radio.setCurrentProvider(service);
			radio.startConsuming();
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
}