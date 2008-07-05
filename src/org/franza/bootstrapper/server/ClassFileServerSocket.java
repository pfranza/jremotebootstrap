package org.franza.bootstrapper.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class ClassFileServerSocket {

	private final Timer t = new Timer();
	private ServerSocket s;
	private boolean running = false;
	private ClassFileReaderInterface fileReader;
	
	
	public ClassFileServerSocket(final int port, final ClassFileReaderInterface fileReader) {

		try {
			s = new ServerSocket(port);
			this.fileReader = fileReader;


			t.schedule(new TimerTask() {

				@Override
				public void run() {
					running = true;

					while(running) {
						try {
							final Socket a = s.accept();
							processSocket(a);
						} catch (final IOException e) {
							
						} 				
					}

				}

			}, 5);

		} catch (final IOException e) {
			e.printStackTrace();
		}

	}


	private void processSocket(final Socket a) throws IOException {
		final DataInputStream dis = new DataInputStream(a.getInputStream());
		final DataOutputStream dos = new DataOutputStream(a.getOutputStream());

		final String className = dis.readUTF();
		try {
			final byte[] bytes = fileReader.convertClassFileToByteArray(className);
			dos.writeInt(bytes.length);
			dos.write(bytes);
		} catch (final ClassNotFoundException e) {
			dos.writeInt(0);
		}

		a.close();
	}


	public void stop() {
		running = false;
		try {
			s.close();
		} catch (final IOException e) {
			
		}
		
	}

	
	
}
