package org.franza.bootstrapper.server.advertisementbeacon;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Advertisement {

	private final String serviceName;
	private final String className;
	private final int port;

	public Advertisement(final String serviceName, final String className,
			final int portNumber) {
		this.serviceName = serviceName;
		this.className = className;
		this.port = portNumber;
	}
	
	protected byte[] toByteArray() throws IOException {

			final ByteArrayOutputStream baos = new ByteArrayOutputStream();
			final DataOutputStream dos = new DataOutputStream(baos);

			dos.writeUTF(serviceName);
			dos.writeUTF(className);
			dos.writeInt(port);

			return baos.toByteArray();

	}

}
