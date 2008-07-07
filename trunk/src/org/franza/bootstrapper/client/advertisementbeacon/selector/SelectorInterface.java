package org.franza.bootstrapper.client.advertisementbeacon.selector;

import org.franza.bootstrapper.client.advertisementbeacon.AdvertisementObserver.AdvertisementListener;

public interface SelectorInterface extends AdvertisementListener {

	void setArgs(String[] args);
	
}
