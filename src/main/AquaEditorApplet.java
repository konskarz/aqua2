package main;

import java.awt.BorderLayout;
import java.net.URL;

import javax.swing.JApplet;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import view.MenuBarAqua;
import view.PanelSzenario;


public class AquaEditorApplet extends JApplet {
	
	private static final long serialVersionUID = 1003016146497959856L;

	public void init() {
		Resources.setResourceBundle(getLocale());
		setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		setContent();
	}

	private void setContent() {
		setJMenuBar(new MenuBarAqua(this));
		getContentPane().add(new PanelSzenario(), BorderLayout.CENTER);
	}
	
	public void setLookAndFeel(String lafClassName) {
		try {
			UIManager.setLookAndFeel(lafClassName);
		} catch (Exception ex) {}
		SwingUtilities.updateComponentTreeUI(this);
	}

	public URL getURL(String filename) {
		URL codeBase = this.getCodeBase();
		URL url = null;
		try {
			url = new URL(codeBase, filename);
			System.out.println(url);
		} catch (java.net.MalformedURLException e) {
			System.err.println(e.getMessage());
			return null;
		}
		return url;
	}

}
