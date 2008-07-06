package org.franza.bootstrapper.client.classloader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.franza.bootstrapper.client.advertisementbeacon.AdvertisementObserver;
import org.franza.bootstrapper.client.advertisementbeacon.ProgramSelector;

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
	
	public static void main(final String[] args) {
		final AdvertisementObserver ads = new AdvertisementObserver(Integer.valueOf(args[0]));
		final ProgramSelector program = new ProgramSelector(ads);
			ads.addListener(program);

	}
	
}
