package org.openhab.training.electricity.radio;

import java.util.List;
import org.openhab.training.electricity.consumer.ElectricityConsumer;
import org.openhab.training.electricity.provider.ElectricityProvider;

public abstract class BasicRadio implements ElectricityConsumer {

	private final int CONSUMPTION = 5;
	private ElectricityProvider currentProvider;

	@Override
	public void setCurrentProvider(ElectricityProvider e) {
		currentProvider = e;
	}

	public abstract List<ElectricityProvider> getAllAvailableProviders();

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
