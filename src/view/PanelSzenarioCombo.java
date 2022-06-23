package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import main.Resources;


/**
 * Panel mit ComboBox und Buttons
 * @author Konstantin Karzanov
 * 28.08.2008
 */
public class PanelSzenarioCombo extends JPanel {

	private static final long serialVersionUID = -3934254051975504889L;
	private Dimension comboBoxSize = new Dimension(222,23);
	private Dimension innerPanelMinimumSize = new Dimension(250,23);
	private Dimension innerPanelMaximumSize = new Dimension(400,40);
	private Dimension horizontalRigidArea = new Dimension(5,0);
	private JComboBox comboBox = new JComboBox();
	private JPanel innerPanel = new JPanel(new GridLayout(1,3,5,5));

	
	public PanelSzenarioCombo() {
		BoxLayout szenarioPanelLayout = new BoxLayout(this, BoxLayout.X_AXIS);
		setLayout(szenarioPanelLayout);
		
		innerPanel.setMinimumSize(innerPanelMinimumSize);
		innerPanel.setMaximumSize(innerPanelMaximumSize);

		add(comboBox);
		add(Box.createRigidArea(horizontalRigidArea));
		add(innerPanel);
	}
	
	public PanelSzenarioCombo(String titleKey) {
		this();
		setBorder(BorderFactory.createTitledBorder(Resources.getString(titleKey)));
	}

	/**
	 * ComboBox einstellen
	 * @param isEditable
	 * @param aCommand
	 * @param l
	 */
	public void setComboBox(boolean isEditable,String aCommand,ActionListener l) {
		comboBox.setPreferredSize(comboBoxSize);
		comboBox.setMinimumSize(comboBoxSize);
		comboBox.setMaximumSize(comboBoxSize);
		comboBox.setEditable(isEditable);
		comboBox.setActionCommand(aCommand);
		comboBox.addActionListener(l);
	}
	
	/**
	 * Einfügen Buttons rechts von ComboBox
	 * @param key
	 * @param actionCommand
	 * @param l
	 */
	public void addButton(String key, String actionCommand, ActionListener l) {
		JButton button = new JButton(Resources.getString(key));
		button.setActionCommand(actionCommand);
		button.addActionListener(l);
		innerPanel.add(button);
	}

	/**
	 * ComboBox Model einstellen
	 * @param aModel
	 */
	public void setModel(ComboBoxModel aModel) {
		comboBox.setModel(aModel);
	}

	/**
	 * ComboBox-Eintragsindex auswählen
	 * @param anIndex
	 */
	public void setSelectedIndex(int anIndex) {
		comboBox.setSelectedIndex(anIndex);
	}

	/**
	 * ComboBox-Eintrag auswählen
	 * @param anObject
	 */
	public void setSelectedItem(Object anObject) {
		comboBox.setSelectedItem(anObject);
	}

	/**
	 * Liefert index von ausgew�hlte ComboBox-Eintrag
	 * @return int
	 */
	public int getSelectedIndex() {
		return comboBox.getSelectedIndex();
	}

	/**
	 * Liefert ausgew�hlte ComboBox-Eintrag
	 * @return Object
	 */
	public Object getSelectedItem() {
		return comboBox.getSelectedItem();
	}

}
