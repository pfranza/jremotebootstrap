package org.franza.bootstrapper.server;

import java.io.IOException;
import java.net.InetAddress;

import org.franza.bootstrapper.server.advertisementbeacon.Advertisement;
import org.franza.bootstrapper.server.advertisementbeacon.Advertiser;

public class Server {

	private final ClassFileServerSocket server;

	public Server(final int port) {
		server = new ClassFileServerSocket(port, new ClassFileReader());
	}
	
	public void halt() {
		server.stop();
	}

	public static void main(final String[] args) throws NumberFormatException, IOException {

		new Server(Integer.valueOf(args[0]));
		final Advertiser ads = new Advertiser(Integer.valueOf(args[1]));
		addAdvertisements(ads, Integer.valueOf(args[0]));
		
		while(true) {
			try {
				Thread.sleep(500);
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

	private static void addAdvertisements(final Advertiser ads, final int port) throws IOException {

			ads.addAdvertisement(new Advertisement("Name", "org.franza.bootstrapper.ClientServerTest", InetAddress.getLocalHost().getHostAddress(), port));

		
	}
	
	
}
