package org.franza.bootstrapper.client.classloader;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ClassLoaderClientSocket implements ClassScavengerInterface {

	private final String host;
	private final int port;

	public ClassLoaderClientSocket(final String host, final int port) {
		this.host = host;
		this.port = port;
	}
	
	private byte[] aquireClassFromServer(final String className) throws Exception {
		
		final Socket socket = new Socket(host, port);
		final DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			dos.writeUTF(className);
			
		final DataInputStream dis = new DataInputStream(socket.getInputStream());
			final int length = dis.readInt();

			if(length == 0)
				return null;
			
			final byte[] b = new byte[length];
			dis.readFully(b);
		
		return b;
	}

	@Override
	public byte[] findClassByName(final String name) throws ClassNotFoundException {
		try {
			final byte[] b = aquireClassFromServer(name);
			if(b == null)
				throw new ClassNotFoundException();
			
			return b;
		} catch (final Exception e) {
			
		}
		throw new ClassNotFoundException(name);
	}
	
	
}
