package org.franza.bootstrapper.server.advertisementbeacon;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Advertisement {

	private final String serviceName;
	private final String className;
	private final int port;
	private final String hostname;

	public Advertisement(final String serviceName, final String className,
			final String hostname, final int portNumber) {
		this.serviceName = serviceName;
		this.className = className;
		this.hostname = hostname;
		this.port = portNumber;
	}
	
	protected byte[] toByteArray() throws IOException {

			final ByteArrayOutputStream baos = new ByteArrayOutputStream();
			final DataOutputStream dos = new DataOutputStream(baos);

			dos.writeUTF(serviceName);
			dos.writeUTF(className);
			dos.writeUTF(hostname);
			dos.writeInt(port);

			return baos.toByteArray();

	}
	
	public static Advertisement fromByteArray(final byte[] data) {
		try {
			final DataInputStream dis = new DataInputStream(new ByteArrayInputStream(data));
			final Advertisement a = new Advertisement(dis.readUTF(), dis.readUTF(), dis.readUTF(), dis.readInt());
			return a;
		} catch (final Exception e) {
			return null;
		}
	}

	public String getServiceName() {
		return serviceName;
	}

	public String getClassName() {
		return className;
	}

	public int getPort() {
		return port;
	}
	
	@Override
	public String toString() {
		final StringBuffer buf = new StringBuffer();
			buf.append(serviceName)
			   .append(": (")
			   .append(className)
			   .append(") ")
			   .append(hostname)
			   .append(":")
			   .append(port);
		
		return buf.toString();
	}

	public String getHostname() {
		return hostname;
	}

}
