package org.franza.bootstrapper.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;


public class ClassFileReader implements ClassFileReaderInterface{

	public byte[] convertClassFileToByteArray(final String className) throws ClassNotFoundException {

		try {
			final String classPath = System.getProperty("java.class.path",".");
//			final String libPath = System.getProperty("java.library.path",".");
			
			final String[] cp = classPath.split(File.pathSeparator);
//			final String[] lp = libPath.split(File.pathSeparator);
			
//			final String[] l = concat(cp, lp);
			
			for (final String string : cp) {
//				System.out.println(string);
				final File f = new File(string);
				if(f.isDirectory()) {
					final File file = searchDirectory(f, className);
					if(file != null) {
						return convertFileToBytes(file);
					}

				} else if(f.isFile() && f.getName().toLowerCase().endsWith(".jar")) {
					final byte[] file = searchJar(f, className);
					if(file != null) {
						return file;
					}
				}

			}
		} catch(final Exception e) {
			e.printStackTrace();
		}

		throw new ClassNotFoundException();
	}

//	String[] concat(final String[] A, final String[] B) {
//		final String[] C= new String[A.length+B.length];
//		   System.arraycopy(A, 0, C, 0, A.length);
//		   System.arraycopy(B, 0, C, A.length, B.length);
//		 
//		   return C;
//		} 
	
	private byte[] searchJar(final File f, final String className) {
		
		final StringBuffer buf = new StringBuffer(className.replaceAll("\\.", "/"));
			buf.append(".class");
		
		try {
			final JarFile jar = new JarFile(f);

			final ZipEntry ze = jar.getEntry(buf.toString());
			if(ze == null) //jar does not contain entry;
				return null;

			final int length=(int)ze.getSize();
			final byte fileContent[]=new byte[length];
			final InputStream fin = jar.getInputStream(ze);
			fin.read(fileContent,0,length);
			return fileContent;
			
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private File searchDirectory(final File f, final String className) {
		 final String[] dirs = className.split("\\.");
		 final File[] files = f.listFiles();
		 for (final File file : files) {
			 final File b = search(dirs, 0, file);
			 if(b != null)
				 return b;
		 }
		 return null;
	}
	
	private File search(final String[] dirs, final int i, final File file) {
		
		if(file.isFile()) {
			if(file.getName().equals(dirs[dirs.length-1] + ".class"))
				return file;
			
		} else { //isDirectory

			if(dirs[i].equals(file.getName())) { //decend into directory
				for(final File f: file.listFiles()) {
					final File b = search(dirs, i+1, f);
					if(b != null)
						return b;
				}
			} else {
				return null;
			}
		}
			
		return null;
	}

	private byte[] convertFileToBytes(final File f) throws Exception{		
		final FileInputStream fis = new FileInputStream(f);
		final byte[] b = new byte[fis.available()];
		fis.read(b);		
		return b;
	}
	
}
