package org.openhab.training.util.console;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.eclipse.osgi.framework.console.CommandInterpreter;
import org.eclipse.osgi.framework.console.CommandProvider;
import org.openhab.training.electricity.consumer.ElectricityConsumer;
import org.openhab.training.electricity.dynamicconsumer.DynamicConsumer;
import org.openhab.training.electricity.provider.ElectricityProvider;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

public class CustomComands implements CommandProvider {

    private static final String tvPID = "org.openhab.training.electricity.tv.events";
    private static final String batteryPID = "org.openhab.training.electricity.rechargablebattery";
    private String autoSleep;
    private int charge = 30;
    List<ElectricityProvider> providers = new ArrayList<ElectricityProvider>();
    List<Object> consumers = new ArrayList<>();
    ConfigurationAdmin ca;

    @Override
    public String getHelp() {
        String s = "custom commands";
        return s;
    }

    // shows all registered ElectricityProvider services

    public void _providers(CommandInterpreter intp) {
        for (ElectricityProvider provider : providers) {
            System.out.println("Available electricity provider: " + provider);
        }
    }

    // shows all registered ElectricityConsumer and DynamicConsumer services

    public void _consumers(CommandInterpreter intp) {
        for (Object consumer : consumers) {
            System.out.println("Available electricity consumer: " + consumer);
        }
    }

    // a command that sets the charge of the battery to a given value

    public Object _setcharge(CommandInterpreter intp) throws IOException {
	if(intp!=null){
        Integer input = Integer.parseInt(intp.nextArgument());
		if (input < 30 && input > 0) {
			setCharge(input);
			setConfBattery();
			return "Charging battery... " + input;
		}
        return "Unable to charge the battery with the given charge!";
	}else{
		System.out.println("You must provide a charging value.");
	}
		return intp;

    }

    // a command that sets the autosleep property of the tv
    // receives a String value in forma HH:MM:SS

    public Object _setsleep(CommandInterpreter intp) throws IOException {

        setAutoSleep(intp.nextArgument());
        setConfTV();
        return "Sleeping time updated... ";
    }

    // config admin bind
    public void bind(ConfigurationAdmin ca) {
        this.ca = ca;
    }

    // sets TV configuration and updates the TV with it

    public void setConfTV() throws IOException {

        Hashtable<String, String> dict = new Hashtable<>();
        Configuration conf = ca.getConfiguration(tvPID);
        dict.put("sleep", getAutoSleep());
        conf.setBundleLocation(null);
        conf.update(dict);

    }

    // sets the Recharchable Battery configuration and updates it with it

    public void setConfBattery() throws IOException {
        Hashtable<String, Integer> dictt = new Hashtable<>();
        Configuration conff = ca.getConfiguration(batteryPID);
        dictt.put("charge", getCharge());
        conff.setBundleLocation(null);
        conff.update(dictt);

    }

    // getters and setters above

    public void setCharge(int charge) {
        this.charge = charge;
    }

    public int getCharge() {
        return charge;
    }

    public void setAutoSleep(String s) {
        this.autoSleep = s;
    }

    public String getAutoSleep() {
        return this.autoSleep;
    }

    public void setConsumer(ElectricityConsumer c) {
        consumers.add(c);
    }

    public void setDynamicConsumer(DynamicConsumer c) {
        consumers.add(c);
    }

    public void showAllProviders(ElectricityProvider e) {
        providers.add(e);
    }

}
