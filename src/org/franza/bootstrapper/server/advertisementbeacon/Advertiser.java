package org.franza.bootstrapper.server.advertisementbeacon;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Advertiser {

	public static final String GROUP_NUMBER = "224.50.1.1";
	private final List<Advertisement> ads = new ArrayList<Advertisement>();
	
	public void addAdvertisement(final Advertisement a) {
		ads.add(a);
	}
	
	private final Timer t = new Timer();
	
	public Advertiser(final int portNumber) {
		try {
			final InetAddress group = InetAddress.getByName(GROUP_NUMBER);
			final MulticastSocket socket = new MulticastSocket(portNumber);
				socket.setTimeToLive(16);
			
			t.scheduleAtFixedRate(new TimerTask() {

				@Override
				public void run() {
					for(final Advertisement a: ads) {
						try {
							final DatagramPacket packet = encodePacket(a);
								packet.setPort(portNumber);
								packet.setAddress(group);
								socket.send(packet);
						} catch (final IOException e) {
							e.printStackTrace();
						}
					}
				}
				
			}, 0, 5000);
			
			
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	private DatagramPacket encodePacket(final Advertisement a) throws IOException {
		final byte[] bytes = a.toByteArray();
		final DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
		return packet;
	}
	
	public void halt() {
		t.cancel();
	}
	
}
