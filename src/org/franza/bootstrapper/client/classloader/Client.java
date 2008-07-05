package org.franza.bootstrapper.client.classloader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Client {

	private final ByteArrayClassLoader classLoader;
	
	public Client(final String hostname, final int port) {
		classLoader = new ByteArrayClassLoader(new ClassLoaderClientSocket(hostname, port));
	}
	
	public void runMainMethod(final String className, final String[] args) throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		final Class<?> cls = classLoader.findClass(className);
		final Method mtd = cls.getMethod("main", new Class[] {String[].class});
		mtd.invoke(null, new Object[]{args}); 

	}
	
}
