package org.franza.bootstrapper.client.classloader;

public interface ClassScavengerInterface {

	byte[] findClassByName(String name) throws ClassNotFoundException;
	
}
