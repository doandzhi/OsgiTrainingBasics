package org.openhab.training.electricity.dynamicconsumer;

import org.openhab.training.electricity.provider.ElectricityProvider;

public interface DynamicConsumer {

    void providerAdded(ElectricityProvider e);

    void providerRemoved(ElectricityProvider e);
}
