package org.franza.bootstrapper.server;

public interface ClassFileReaderInterface {

	byte[] convertClassFileToByteArray(final String className) throws ClassNotFoundException;
	
}
