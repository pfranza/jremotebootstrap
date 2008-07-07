package org.franza.bootstrapper.server;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

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
		final Integer port = getPort();
		System.out.println("Server started on port: " + port);
		new Server(port);
		final Advertiser ads = new Advertiser(port);
		addAdvertisements(ads, port);
		
		while(true) {
			try {
				Thread.sleep(500);
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

	private static void addAdvertisements(final Advertiser ads, final int port) throws IOException {
		for(final Class<?> c: findAllMainMethods()) {
			ads.addAdvertisement(new Advertisement(c.getSimpleName(), c.getName(), InetAddress.getLocalHost().getHostAddress(), port));
		}		
	}

	private static List<Class<?>> findAllMainMethods() {
		final List<Class<?>> l = new ArrayList<Class<?>>();
			
		final String classPath = System.getProperty("java.class.path",".");	
		final String[] cp = classPath.split(File.pathSeparator);

		for (final String string : cp) {

			final File f = new File(string);
			if(f.isDirectory()) {
				try {
					for(final File e: f.listFiles()) {
						searchDirectory(e, l, "");
					}
				} catch (final Exception e) {
					e.printStackTrace();
				}				
			} else if(f.isFile() && f.getName().toLowerCase().endsWith(".jar")) {
				try {
					searchJar(f, l);
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}

		}
		
		return l;
	}

	private static void searchJar(final File f, final List<Class<?>> l) throws Exception {
		final JarFile jar = new JarFile(f);
		final Enumeration<JarEntry> en = jar.entries();
		while(en.hasMoreElements()) {
			final JarEntry e = en.nextElement();
			if(e.getName().endsWith(".class") && !e.getName().contains("$")) {
				try {
				final Class<?> c = Class.forName(e.getName().substring(0, e.getName().length()-6).replace('/', '.'));
				addItem(l, c);
				} catch (final NoClassDefFoundError n) {}
			}
		}
		
	}

	private static void addItem(final List<Class<?>> l, final Class<?> c) {
		
		if(c.getName().startsWith("junit."))
			return;
		
		try {
			c.getMethod("main", new Class[] {String[].class});
			l.add(c);
			System.out.println("   Advertising " + c);
		} catch(final NoSuchMethodException nsme) {

		}
	}

	private static void searchDirectory(final File f, final List<Class<?>> l, final String pack) throws Exception {
		if(f.isFile() && f.getName().endsWith(".class") && !f.getName().contains("$")) {
			final Class<?> c = Class.forName(pack + f.getName().substring(0, f.getName().length()-6));
			addItem(l, c);
		} else if(f.isDirectory()) {
			for(final File e: f.listFiles()) {
				searchDirectory(e, l, pack + f.getName() + ".");
			}
		}
		
	}
	
	public static int getPort() {
		return Integer.valueOf(System.getProperty("port", "3000"));
	}
	
}
