package org.franza.bootstrapper.client.classloader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.franza.bootstrapper.client.advertisementbeacon.AdvertisementObserver;
import org.franza.bootstrapper.client.advertisementbeacon.selector.CliProgramSelector;
import org.franza.bootstrapper.client.advertisementbeacon.selector.SelectorInterface;
import org.franza.bootstrapper.client.advertisementbeacon.selector.UiProgramSelector;

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
		final AdvertisementObserver ads = new AdvertisementObserver(Integer.valueOf(System.getProperty("port", "3000")));
		final String c = System.getProperty("class");
		final SelectorInterface iface = (c == null)?new UiProgramSelector(ads):new CliProgramSelector(c, ads);
			iface.setArgs(args);
			ads.addListener(iface);
	}
	
}
