package view;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

public class MenuBarAqua extends JMenuBar {

	private static final long serialVersionUID = 6499947801152315618L;
	private Container mainContainer = null;

	public MenuBarAqua(Container mainContainer) {
		this.mainContainer = mainContainer;
		setMenuBar();
	}

	private void setMenuBar() {
		if(mainContainer instanceof JFrame)
			this.add(new MenuProgram(mainContainer));
		this.add(new MenuLaf(mainContainer));
	}

}
