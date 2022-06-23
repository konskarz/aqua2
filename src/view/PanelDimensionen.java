package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import main.ErrorMessage;
import main.Resources;
import model.Dimensionen;


/**
 * Dimensionen-Editor Dialog
 * @author Konstantin Karzanov
 * 28.08.2008
 */
public class PanelDimensionen extends JPanel implements ActionListener {

	private static final long serialVersionUID = -4858837190922870641L;
	private boolean save;
	private JDialog dialog;
	private JSpinner lSpinner = new JSpinner();
	private JSpinner bSpinner = new JSpinner();
	private JSpinner hSpinner = new JSpinner();
	private JSpinner[] spinners = {lSpinner,bSpinner,hSpinner};
	private Dimensionen dimensionen = null;
	
	public PanelDimensionen() {
		super(new BorderLayout());
		add(getCenterPanel(),BorderLayout.CENTER);
		add(getSouthPanel(),BorderLayout.SOUTH);
	}
	
	private JPanel getCenterPanel() {
		JPanel innerPanel = new JPanel(new GridLayout(3,2,5,5));
		innerPanel.add(new JLabel(Resources.getString("DimPanel.length_label")));
		innerPanel.add(lSpinner);
		innerPanel.add(new JLabel(Resources.getString("DimPanel.width_label")));
		innerPanel.add(bSpinner);
		innerPanel.add(new JLabel(Resources.getString("DimPanel.height_label")));
		innerPanel.add(hSpinner);

		JPanel outerPanel = new JPanel();
		outerPanel.add(innerPanel);
		return outerPanel;
	}
	
	private JPanel getSouthPanel() {
		JPanel panel = new JPanel();
		panel.add(getButton("DimPanel.saveButton", "dimsave", this));
		panel.add(getButton("DimPanel.cancelButton", "dimcanc", this));
		return panel;
	}

	private void setModel(Object model) {
		this.dimensionen=(Dimensionen)model;
		for(int i=0;i<spinners.length;i++)
			spinners[i].setModel(new SpinnerNumberModel(dimensionen.getValues()[i],20,6485,5));
	}

	private JButton getButton(String key, String actionCommand, ActionListener l) {
		JButton button = new JButton(Resources.getString(key));
		button.setActionCommand(actionCommand);
		button.addActionListener(l);
		return button;
	}

	private boolean save() {
		try {
			dimensionen.setValues(lSpinner.getValue(),bSpinner.getValue(),hSpinner.getValue());
		} catch (NumberFormatException nfe) {
			ErrorMessage.show(nfe.getMessage().substring(17), ErrorMessage.NOT_VALID);
			return false;
		}
		return true;
	}

	/**
	 * Zeigt Dimensionen-Editor Dialog
	 * Liefert true, wenn Benutzer auf Spechern-Knopf druckt.
	 * Liefert false, wenn Benutzer auf Abbrechen-Knopf druckt
	 * @param parent
	 * @param model
	 * @return boolean
	 */
	public boolean showDialog(Component parent, Object model) {
		save = false;
		setModel(model);
		Frame owner = parent instanceof Frame?(Frame)parent:(Frame)SwingUtilities.getAncestorOfClass(Frame.class, parent);
		if (dialog==null || dialog.getOwner()!=owner) {
			dialog = new JDialog(owner, true);
			dialog.add(this);
			dialog.pack();
		}
		dialog.setLocationRelativeTo(owner);
		dialog.setTitle(Resources.getString("DimDialog.title"));
		dialog.setResizable(false);
		dialog.setVisible(true);
		return save;
	}
	
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if(cmd.equals("dimsave") && save()) {
			dialog.setVisible(false);
			save=true;
		}
		else if (cmd.equals("dimcanc"))
			dialog.setVisible(false);
	}

}
