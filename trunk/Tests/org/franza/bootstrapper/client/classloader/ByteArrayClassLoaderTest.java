package org.franza.bootstrapper.client.classloader;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import junit.framework.TestCase;

public class ByteArrayClassLoaderTest extends TestCase implements ClassScavengerInterface{

	private static String CLASS = "-13570 -17730 0 50 16 1792 513 21 29797 29556 28769 25451 24935 25903 18017 27493 17260 24947 29447 4 256 4202 24950 24879 27745 28263 12111 25194 25955 29697 6 15465 28265 29758 256 808 10582 256 1091 28516 25866 3 9 3072 1280 1537 15 19561 28261 20085 28002 25970 21601 25196 25857 18 19567 25441 27734 24946 26977 25196 25940 24930 27749 256 1140 26729 29441 23 19572 25971 29808 24931 27489 26469 12102 24939 25923 27745 29555 15105 10 21359 30066 25445 18025 27749 256 3654 24939 25923 27745 29555 11882 24950 24832 8448 256 768 0 0 256 256 1280 1536 256 1792 0 12032 256 256 0 1322 -18688 2225 0 2 10 0 6 1 0 3 11 0 12 1 0 5 12 13 0 1 14 0 2 15";
	
	private ByteArrayClassLoader loader = new ByteArrayClassLoader(this);
	
	@Override
	public byte[] findClassByName(String name) throws ClassNotFoundException {
		
		if(name.equals("testpackage.FakeClass")) {
			return getByteArrayFromString(CLASS);			
		}
		
		throw new ClassNotFoundException();
	}

	public void testNoClassDef() {
		boolean thrown = false;
		try {
			loader.findClass("testpackage.nonexistantClass");
		} catch (ClassNotFoundException e) {
			thrown = true;
		}
		
		assertTrue("Exception not thrown.", thrown);
	}
	
	public void testLoadClassFromByteArray() throws Exception {
		Class<?> cls = loader.findClass("testpackage.FakeClass");
		assertNotNull(cls);
		assertEquals("testpackage.FakeClass", cls.getName());
	}

	private byte[] getByteArrayFromString(String c) {
		String[] ints = c.split(" ");
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream bao = new DataOutputStream(b);
		for (String string : ints) {
			int val = Integer.valueOf(string);
			try {
				bao.writeShort(val);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
		return b.toByteArray();
	}
	
}
