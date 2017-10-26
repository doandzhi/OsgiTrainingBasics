package org.openhab.training.electricity.consumer;

import java.util.List;

import org.openhab.training.electricity.provider.ElectricityProvider;

public interface ElectricityConsumer {

    void startConsuming();

    void stopConsuming();

    void setCurrentProvider(ElectricityProvider e);

    List<ElectricityProvider> getAllAvailableProviders();
}
