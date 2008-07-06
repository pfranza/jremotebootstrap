package org.franza.bootstrapper;

import junit.framework.TestCase;

import org.franza.bootstrapper.client.classloader.ClassLoaderClientSocket;
import org.franza.bootstrapper.client.classloader.Client;
import org.franza.bootstrapper.server.ClassFileReader;
import org.franza.bootstrapper.server.ClassFileServerSocket;
import org.franza.bootstrapper.server.Server;

public class ClientServerTest extends TestCase {

	public void testClientServer() throws Exception {
		final ClassFileServerSocket server = new ClassFileServerSocket(3000, new ClassFileReader());		
		final ClassLoaderClientSocket client = new ClassLoaderClientSocket("127.0.0.1", 3000);
		final byte[] cls1 = client.findClassByName(ClientServerTest.class.getName());
		final byte[] cls2 = client.findClassByName(ClassFileServerSocket.class.getName());
		server.stop();
		assertNotNull(cls1);
		assertNotNull(cls2);
	}
	
	public void testClientServerNotFound() throws Exception {
		final ClassFileServerSocket server = new ClassFileServerSocket(3001, new ClassFileReader());		
		final ClassLoaderClientSocket client = new ClassLoaderClientSocket("127.0.0.1", 3001);
		boolean thrown = false;
		try {
			client.findClassByName("FakePackage.FakeClass");
		} catch (final ClassNotFoundException cnf) {
			thrown = true;
		}
		server.stop();		
		assertTrue(thrown);
	}

	public void testMainRunner() throws Exception{
		final Server s = new Server(3000);
		final Client c = new Client("127.0.0.1", 3000);
			final String[] args = {"arg1", "arg2"};
			c.runMainMethod(ClientServerTest.class.getName(), args );	
			s.halt();
	}
	
	public static void main(final String[] args) {
		System.out.println("Method Run!");
	}
}
