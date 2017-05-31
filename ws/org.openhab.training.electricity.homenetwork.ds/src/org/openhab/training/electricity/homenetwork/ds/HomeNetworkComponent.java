package org.openhab.training.electricity.homenetwork.ds;

import org.openhab.training.electricity.provider.ElectricityProvider;

public class HomeNetworkComponent implements ElectricityProvider {

    @Override
    public boolean provide(int value) {
        System.out.println("Working on Home network");
        return true;
    }

}