package view;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import main.AquaEditorApplication;
import main.Resources;



public class MenuProgram extends JMenu {

	private static final long serialVersionUID = -53009622051027456L;
	private Container mainContainer = null;

	public MenuProgram(Container mainContainer) {
		super(Resources.getString("FileMenu.file_label"));
		this.mainContainer = mainContainer;
		this.setMnemonic(Resources.getMnemonic("FileMenu.file_mnemonic"));
		this.setMenu();
	}
		
	private void setMenu() {
		JMenuItem mi = this.add(new JMenuItem(Resources.getString("FileMenu.exit_label")));
		mi.setMnemonic(Resources.getMnemonic("FileMenu.exit_mnemonic"));
		mi.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(mainContainer instanceof AquaEditorApplication) {
					AquaEditorApplication application = (AquaEditorApplication)mainContainer;
					application.saveAndExit();
				}
			}
		});
	}

}
