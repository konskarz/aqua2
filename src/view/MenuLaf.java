package view;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import main.Resources;



public class MenuLaf extends JMenu {

	private static final long serialVersionUID = -7224147425212104092L;
	private Container mainContainer = null;

	public MenuLaf(Container mainContainer) {
		super(Resources.getString("LafMenu.laf_label"));
		this.mainContainer = mainContainer;
		this.setMnemonic(Resources.getMnemonic("LafMenu.laf_mnemonic"));
		this.setMenu();
	}

	private void setMenu() {
		ButtonGroup rg = new ButtonGroup();
		UIManager.LookAndFeelInfo[] installedLookAndFeels = UIManager.getInstalledLookAndFeels();
 		for (int i = 0; i < installedLookAndFeels.length; i++) {
			String lafName = installedLookAndFeels[i].getName();
			String lafClassName = installedLookAndFeels[i].getClassName();
			if (isAvailableLaf(lafClassName)) {
				JMenuItem mi = createLafMenuItem(lafName, lafClassName);
				this.add(mi);
				rg.add(mi);
			}
		}
	}
	
	private JRadioButtonMenuItem createLafMenuItem(String lafName, String lafClassName) {
		JRadioButtonMenuItem rbmi;
		rbmi = new JRadioButtonMenuItem(lafName);
		rbmi.setMnemonic(lafName.charAt(0));
		setLafMenuItem(rbmi,lafClassName);
		return rbmi;
	}

	private JRadioButtonMenuItem setLafMenuItem(JRadioButtonMenuItem rbmi, final String lafClassName) {
		String curlaf = UIManager.getLookAndFeel().getClass().getName();
		rbmi.setSelected(lafClassName.equals(curlaf));
		rbmi.setActionCommand("lafChanged");
		rbmi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setLaf(lafClassName);
			}
		});
		return rbmi;
	}

	private boolean isAvailableLaf(String lafClassName) {
		try { 
			LookAndFeel laf = (LookAndFeel)(Class.forName(lafClassName).newInstance());
			return laf.isSupportedLookAndFeel();
		} catch (Exception e) {
			return false;
		}
	}
	
	private void setLaf(String lafClassName) {
		try {
			UIManager.setLookAndFeel(lafClassName);
		} catch (Exception ex) {}
		SwingUtilities.updateComponentTreeUI(mainContainer);
	}
	
}
