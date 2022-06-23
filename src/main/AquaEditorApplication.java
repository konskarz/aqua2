package main;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import view.MenuBarAqua;
import view.PanelSzenario;


public class AquaEditorApplication extends JFrame implements WindowStateListener {
	
	private static final long serialVersionUID = 3584800399190190155L;
	private AquaPreferences prefs = null;

	public static void main(String[] args) {
		new AquaEditorApplication();
	}

	public AquaEditorApplication() {
		prefs = new AquaPreferences(this);
		Resources.setResourceBundle(prefs.getLocale());
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				init();
			};
		});
	}
	
	private void init() {
		setTitle(Resources.getString("Frame.title"));
		setLookAndFeel(prefs.getLafClassName());
		setContent();
		setBoundsAndState();
		setVisible(true);
		addWindowStateListener(this);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				saveAndExit();
			}
		});
	}
	
	private void setLookAndFeel(String lafClassName) {
		try {
			UIManager.setLookAndFeel(lafClassName);
		} catch (Exception ex) {}
		SwingUtilities.updateComponentTreeUI(this);
	}

	private void setContent() {
		setJMenuBar(new MenuBarAqua(this));
		getContentPane().add(new PanelSzenario(), BorderLayout.CENTER);
	}
	
	private void setBoundsAndState() {
		this.setSize(prefs.getWindowSize());
		this.setLocation(prefs.getWindowPoint());
		this.setExtendedState(prefs.getWindowState());
	}
	
	private void saveBounds() {
		if(this.getExtendedState()==JFrame.NORMAL) {
			prefs.setWindowPoint(this.getLocation());
			prefs.setWindowSize(this.getSize());
		}
	}

	private void saveLookAndFeel() {
		prefs.setLafClassName(UIManager.getLookAndFeel().getClass().getName());
	}

	public void saveAndExit() {
		saveBounds();
		saveLookAndFeel();
		System.exit(0);
	}
	
	public void windowStateChanged(WindowEvent e) {
		prefs.setWindowState(this.getExtendedState());
	}

}
