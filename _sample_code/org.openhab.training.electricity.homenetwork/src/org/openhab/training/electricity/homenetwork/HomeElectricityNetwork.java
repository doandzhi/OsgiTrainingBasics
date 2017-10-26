package org.openhab.training.electricity.homenetwork;

import org.openhab.training.electricity.provider.ElectricityProvider;

public class HomeElectricityNetwork implements ElectricityProvider {

	@Override
	public boolean provide(int value) {
		// since it is accepted that the network has infinite charge it always returns true
		return true;
	}
}
