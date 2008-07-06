package org.franza.bootstrapper.client.advertisementbeacon;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AdvertisementObserver {

	public interface AdvertisementListener {
		void processAdvertisement(byte[] ad, int length);
	}
	public static final String GROUP_NUMBER = "224.50.1.1";
	
	private final List<AdvertisementListener> listeners = new ArrayList<AdvertisementListener>();

	private final Timer t = new Timer();

	public AdvertisementObserver(final int port) {
		try {
			final MulticastSocket socket = new MulticastSocket(port);
				socket.joinGroup(InetAddress.getByName(GROUP_NUMBER));
				socket.setSoTimeout(250);
				
				t.scheduleAtFixedRate(new TimerTask() {

					@Override
					public void run() {
						try {
							while(true) {
								final DatagramPacket g = new DatagramPacket(new byte[Short.MAX_VALUE], Short.MAX_VALUE);
								socket.receive(g);
								revc(g.getData(), g.getLength());
							}
						} catch (final Exception e) {

						}
					}
					
				}, 0, 10);
				
			
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addListener(final AdvertisementListener l) {
		listeners.add(l);
	}
	public void halt() {
		t.cancel();
	}

	protected void revc(final byte[] data, final int length) {
		for(final AdvertisementListener l: listeners) {
			l.processAdvertisement(data, length);
		}
	}
	
}
