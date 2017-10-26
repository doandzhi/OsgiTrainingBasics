package org.openhab.training.electricity.battery;

import org.openhab.training.electricity.provider.ElectricityProvider;

public class Battery implements ElectricityProvider {
	private final int CAPACITY = 20;
	private int currentCharge = CAPACITY;

	@Override
	public synchronized boolean provide(int consumption) {
		System.out.println("Working on battery");
		if (CAPACITY > consumption) {
			currentCharge -= consumption;
			System.out.println("Battery is: " + ((double) currentCharge / CAPACITY) * 100 + "%");
			return true;
		} else {
			System.out.println("Not enough battery");
			return false;
		}
	}
}