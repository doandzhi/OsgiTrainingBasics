package org.openhab.training.electricity.battery.ds;

import org.openhab.training.electricity.provider.ElectricityProvider;

public class BatteryComponent implements ElectricityProvider {

    int capacity = 20;
    int consumption;

    @Override
    public synchronized boolean provide(int consumption) {
        System.out.println("Working on battery");

        if (capacity > consumption) {
            capacity -= consumption;
            System.out.println("Battery is: " + ((double) capacity / 20) * 100 + "%");
            return true;
        } else {
            System.out.println("Not enough battery");
            return false;
        }
    }

}
