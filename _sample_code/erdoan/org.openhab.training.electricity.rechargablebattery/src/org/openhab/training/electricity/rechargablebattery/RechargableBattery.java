package org.openhab.training.electricity.rechargablebattery;

import java.util.Dictionary;

import org.openhab.training.electricity.provider.ElectricityProvider;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;

public class RechargableBattery implements ElectricityProvider, ManagedService {

    private int capacity = 30;

    @Override
    public synchronized boolean provide(int value) {
        if (this.capacity > value) {
            this.capacity -= value;
            System.out.println("Battery is: " + ((double) this.capacity / 30) * 100 + "%");
            return true;
        } else {
            System.out.println("Not enough battery");
            deactivate();
            return false;
        }
    }

    @Override
    public void updated(Dictionary<String, ?> properties) throws ConfigurationException {

        if (capacity == 0) {
            activate();
        }

        System.out.println("charge updated " + properties.get("charge"));
        setCharge((int) properties.get("charge"));

    }

    public void activate() {
        System.out.println("Recharchable battery is ready to use...");
    }

    public void deactivate() {
        System.out.println("Recharchable battery dead...");
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCharge(int capacity) {
        this.capacity = capacity;
    }

}
