package org.franza.bootstrapper.server.advertisementbeacon;

import junit.framework.TestCase;

import org.franza.bootstrapper.client.advertisementbeacon.AdvertisementObserver;
import org.franza.bootstrapper.client.advertisementbeacon.AdvertisementObserver.AdvertisementListener;

public class AdvertisementServerTest extends TestCase implements AdvertisementListener {

	private boolean recv = false;
	
	public void testBeacon() throws Exception {
		
		final Advertiser a = new Advertiser(4000);
			a.addAdvertisement(new Advertisement("testService", 
					"testpackage.testclass", 3000));
			
			final AdvertisementObserver b = new AdvertisementObserver(4000);
				b.addListener(this);
			
				Thread.sleep(6000);
				
			a.halt();
			b.halt();
			
			assertTrue(recv);
	}

	@Override
	public void processAdvertisement(final byte[] ad, final int length) {
		recv = true;	
	}
	
}
