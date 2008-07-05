package org.franza.bootstrapper.client.classloader;

import java.util.HashMap;
import java.util.Map;

public class ByteArrayClassLoader extends ClassLoader {

	private final ClassScavengerInterface scavenger;
	private final Map<String, Class<?>> classCache = new HashMap<String, Class<?>>();
	
	public ByteArrayClassLoader(final ClassScavengerInterface scavenger) {
		super(ClassLoader.getSystemClassLoader());
		this.scavenger = scavenger;
	}
	
	@Override
	public Class<?> findClass(final String name) throws ClassNotFoundException {
		
		final Class<?> val = classCache.get(name);
		if(val != null)
			return val;
		
		final byte[] bytes = scavenger.findClassByName(name);		
		final Class<?> cls = defineClass(name, bytes, 0, bytes.length);
		classCache.put(name, cls);
		return cls;
	}

	
	
	
}
