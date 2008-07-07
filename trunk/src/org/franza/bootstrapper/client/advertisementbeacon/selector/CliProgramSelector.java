package org.franza.bootstrapper.client.advertisementbeacon.selector;

import org.franza.bootstrapper.client.advertisementbeacon.AdvertisementObserver;
import org.franza.bootstrapper.client.classloader.Client;
import org.franza.bootstrapper.server.advertisementbeacon.Advertisement;

public class CliProgramSelector implements SelectorInterface {

	private final String className;
	private String[] args;
	private final AdvertisementObserver adServer;

	public CliProgramSelector(final String className, final AdvertisementObserver ads) {
		this.adServer = ads;
		this.className = className;
	}

	@Override
	public void processAdvertisement(final byte[] ad, final int length) {
		final Advertisement a = Advertisement.fromByteArray(ad);
		if(a.getClassName().equals(this.className)) {
			adServer.halt();
			final Client cli = new Client(a.getHostname(), a.getPort());
			try {
				cli.runMainMethod(a.getClassName(), args);
			} catch (final Exception e) {
				e.printStackTrace();
			} 
		}
		
	}

	public void setArgs(final String[] a) {
		this.args = a;
	}

}
