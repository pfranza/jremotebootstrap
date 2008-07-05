package org.franza.bootstrapper;

import junit.framework.TestCase;

import org.franza.bootstrapper.client.classloader.ByteArrayClassLoader;
import org.franza.bootstrapper.client.classloader.ClassScavengerInterface;
import org.franza.bootstrapper.server.ClassFileReader;
import org.franza.bootstrapper.server.ClassFileReaderInterface;

public class ClassLoaderBridgeTest extends TestCase implements ClassScavengerInterface {

	private final ByteArrayClassLoader loader = new ByteArrayClassLoader(this);
	private final ClassFileReaderInterface reader = new ClassFileReader();

	public void testLoadClassFromFolder() throws Exception {
		final Class<?> cls = loader.findClass(ClassLoaderBridgeTest.class.getName());
		assertNotNull(cls);
		assertEquals(ClassLoaderBridgeTest.class.getName(), cls.getName());
		
	}
	
	
	@Override
	public byte[] findClassByName(final String name) throws ClassNotFoundException {
		return reader.convertClassFileToByteArray(name);

	}
	
}
