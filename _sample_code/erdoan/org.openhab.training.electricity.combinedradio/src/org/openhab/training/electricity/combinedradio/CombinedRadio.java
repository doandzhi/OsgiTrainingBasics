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

	private final int CONSUMPTION = 2;
	ElectricityProvider currentProvider;
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	public List<ElectricityProvider> listOfProviders = new ArrayList<ElectricityProvider>();

	@Override
	public void bindElProvider(ElectricityProvider electricityProvider) {
		if (electricityProvider instanceof ElectricityProvider) {
			providerAdded(electricityProvider);
			currentProvider = electricityProvider;
			return;
		}
	}

	@Override
	public void startConsuming() {

		Thread providingAction = new Thread() {
			@Override
			public void run() {
				if (currentProvider != null) {
					currentProvider.provide(CONSUMPTION);
				} else {
					System.out.println("CombinedRadio working without provider");
				}
			}
		};
		scheduler.scheduleAtFixedRate(providingAction, 5, 5, TimeUnit.SECONDS);
	}

	@Override
	public void activate() {
		startConsuming();
	}

	@Override
	public void deactivate() {
		scheduler.shutdown();
		try {
		scheduler.awaitTermination(2, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
	        System.out.println("Termination interrupted" + e);
	    } finally {
	        if (!scheduler.isTerminated()) {
	            System.out.println("Killing non-finished tasks");
	        }
	        scheduler.shutdownNow();
	    }
	}

	@Override
	public void unbindElProvider(ElectricityProvider electricityProvider) {
		providerRemoved(electricityProvider);
		if (!getAllAvailableProviders().isEmpty()) {
			currentProvider = getAllAvailableProviders().get(0);
		} else {
			System.out.println("Radio is working without providers...");
		}
	}

	@Override
	public void providerAdded(ElectricityProvider e) {
		listOfProviders.add(e);
	}

	@Override
	public void providerRemoved(ElectricityProvider e) {
		if (listOfProviders.isEmpty()) {
			System.out.println("No providers available");
		} else {
			listOfProviders.remove(e);
			if (currentProvider == e) {
				currentProvider = null;
			}
			System.out.println("provider has been removed... current provider is" + currentProvider);
		}
	}

	@Override
	public List<ElectricityProvider> getAllAvailableProviders() {
		return listOfProviders;
	}
}