package org.franza.bootstrapper.server;

import junit.framework.TestCase;

public class ClassFileReaderTest extends TestCase {

	private final ClassFileReader fileReader = new ClassFileReader();
	
	public void testFindClassInFolder() throws Exception {
		assertNotNull(fileReader.convertClassFileToByteArray(ClassFileReaderTest.class.getName()));
	}
	
	public void testFindClassInJar() throws Exception {
		assertNotNull(fileReader.convertClassFileToByteArray("com.vladium.jcd.cls.ClassDef"));
	}
	
}
