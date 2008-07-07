package org.franza.bootstrapper.client.advertisementbeacon.selector;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JList;

import org.franza.bootstrapper.client.advertisementbeacon.AdvertisementObserver;
import org.franza.bootstrapper.client.classloader.Client;
import org.franza.bootstrapper.server.advertisementbeacon.Advertisement;

public class UiProgramSelector extends JFrame implements SelectorInterface, MouseListener {

	private static final long serialVersionUID = 5411807871327808945L;
	
	private final JList list = new JList();
	private final Map<String, Advertisement> ads = new HashMap<String, Advertisement>();

	private String[] args;

	private final AdvertisementObserver adServer;
	
	public UiProgramSelector(final AdvertisementObserver ads) {
		this.adServer = ads;
		list.addMouseListener(this);
		
		list.setSize(300, 200);
		this.setSize(300, 200);
		this.pack();
		this.getContentPane().add(list);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public void setArgs(final String[] arg) {
		this.args = arg;
	}
	
	@Override
	public void processAdvertisement(final byte[] ad, final int length) {
		final Advertisement a = Advertisement.fromByteArray(ad);
		ads.put(a.getClassName(), a);
		list.setListData(ads.values().toArray());
		
	}

	@Override
	public void mouseClicked(final MouseEvent e) {
		if (e.getClickCount() == 2) {
            System.out.println("Connecting to " + list.getSelectedValue());
            
            final Advertisement a = (Advertisement) list.getSelectedValue();
            final Client cli = new Client(a.getHostname(), a.getPort());
            	try {
            		this.setVisible(false);
            		this.dispose();
            		adServer.halt();
					cli.runMainMethod(a.getClassName(), args);
				} catch (final Exception e1) {
					e1.printStackTrace();
				}
        }
		
	}

	@Override
	public void mouseEntered(final MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(final MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(final MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(final MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
