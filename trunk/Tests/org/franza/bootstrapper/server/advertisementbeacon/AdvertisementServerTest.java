package org.franza.bootstrapper.server.advertisementbeacon;

import junit.framework.TestCase;

import org.franza.bootstrapper.client.advertisementbeacon.AdvertisementObserver;
import org.franza.bootstrapper.client.advertisementbeacon.AdvertisementObserver.AdvertisementListener;

public class AdvertisementServerTest extends TestCase implements AdvertisementListener {

	private static final int PORT = 3000;
	private static final String TESTPACKAGE_TESTCLASS = "testpackage.testclass";
	private static final String TEST_SERVICE = "testService";
	private boolean recv = false;
	
	public void testBeacon() throws Exception {
		
		final int port = 4000;
		
		final Advertiser a = new Advertiser(port);
			a.addAdvertisement(new Advertisement(TEST_SERVICE, 
					TESTPACKAGE_TESTCLASS, PORT));
			
			final AdvertisementObserver b = new AdvertisementObserver(port);
				b.addListener(this);
			
				Thread.sleep(6000);
				
			a.halt();
			b.halt();
			
			assertTrue(recv);
	}

	@Override
	public void processAdvertisement(final byte[] ad, final int length) {
			final Advertisement a = Advertisement.fromByteArray(ad);
			recv = true;
			assertEquals(TEST_SERVICE, a.getServiceName());
			assertEquals(TESTPACKAGE_TESTCLASS, a.getClassName());
			assertEquals(PORT, a.getPort());
	}
	
	public void testNoConstruction() throws Exception {
		assertNull(Advertisement.fromByteArray(new byte[0]));
	}
	
}
