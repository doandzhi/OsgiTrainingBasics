package org.openhab.training.electricity.homenetwork;

import org.openhab.training.electricity.provider.ElectricityProvider;

public class HomeElectricityNetwork implements ElectricityProvider {

    @Override
    public boolean provide(int value) {
        // must always return true
        System.out.println("Working on Home network");
        return true;
    }

}
