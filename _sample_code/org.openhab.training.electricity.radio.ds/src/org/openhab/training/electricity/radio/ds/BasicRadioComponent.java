package org.openhab.training.electricity.radio.ds;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.openhab.training.electricity.consumer.ElectricityConsumer;
import org.openhab.training.electricity.provider.ElectricityProvider;

public class BasicRadioComponent implements ElectricityConsumer {

	private final int CONSUMPTION = 2;
	private ElectricityProvider currentProvider;
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	public void bindElProvider(ElectricityProvider eProvider) {
		currentProvider = eProvider;
	}

	public void activate() {
		startConsuming();
	}

	public void unbindElProvider(ElectricityProvider eProvider) {
		stopConsuming();
	}

	@Override
	public List<ElectricityProvider> getAllAvailableProviders() {
		return new ArrayList<>();
	}

	@Override
	public void stopConsuming() {
		System.out.println("Radio stopped");
		currentProvider = null;
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
	public void startConsuming() {
		Thread providingAction = new Thread() {
			@Override
			public void run() {
				currentProvider.provide(CONSUMPTION);
			}
		};
		scheduler.scheduleAtFixedRate(providingAction, 1, 20, TimeUnit.SECONDS);
	}

	@Override
	public void setCurrentProvider(ElectricityProvider e) {
		currentProvider = e;
	}
}