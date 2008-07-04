package org.franza.bootstrapper.client.classloader;

import java.util.HashMap;
import java.util.Map;

public class ByteArrayClassLoader extends ClassLoader {

	private ClassScavengerInterface scavenger;
	private Map<String, Class<?>> classCache = new HashMap<String, Class<?>>();
	
	public ByteArrayClassLoader(ClassScavengerInterface scavenger) {
		super(ClassLoader.getSystemClassLoader());
		this.scavenger = scavenger;
	}
	
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		
		Class<?> val = classCache.get(name);
		if(val != null)
			return val;
		
		byte[] bytes = scavenger.findClassByName(name);		
		Class<?> cls = defineClass(name, bytes, 0, bytes.length);
		classCache.put(name, cls);
		return cls;
	}

	
	
	
}
