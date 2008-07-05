package org.franza.bootstrapper.server;

public class Server {

	private final ClassFileServerSocket server;

	public Server(final int port) {
		server = new ClassFileServerSocket(port, new ClassFileReader());
	}
	
	public void halt() {
		server.stop();
	}


}
