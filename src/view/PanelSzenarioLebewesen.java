package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import main.ErrorMessage;
import main.Resources;
import model.Lebewesen;

import dao.AquaDaoException;


public class PanelSzenarioLebewesen extends JPanel implements ActionListener, ILebewesenRefresher {

	private static final long serialVersionUID = 6368093942707988148L;
	private PanelSzenarioTrees dblwPanel = null;
	private PanelSzenarioTrees szlwPanel = null;

	private JButton lwAddButton;
	private JButton lwRemButton;
	private int SzenarioId;
	
	public PanelSzenarioLebewesen() {
		BoxLayout layout = new BoxLayout(this, BoxLayout.X_AXIS);
		this.setLayout(layout);
		this.setBorder(BorderFactory.createTitledBorder(Resources.getString("Szen.lw_border")));

		dblwPanel = new PanelSzenarioTrees("Szen.lwinfp_text", true);
		szlwPanel = new PanelSzenarioTrees("Szen.lwszinfp_text", false);
		
		this.add(szlwPanel);
		this.add(getLebewesenEditPanel());
		this.add(dblwPanel);
	}

	private JPanel getLebewesenEditPanel() {
		JPanel panel = new JPanel();
		BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.setLayout(layout);
		
		lwAddButton=getButton("Szen.lwp_addButton","lwadd",this);
		lwRemButton=getButton("Szen.lwp_deleteButton","lwrem",this);
		lwAddButton.setEnabled(false);
		lwRemButton.setEnabled(false);

		panel.add(lwAddButton);
		panel.add(Box.createRigidArea(new Dimension(0,5)));
		panel.add(lwRemButton);

		return panel;
	}

	private JButton getButton(String key, String actionCommand, ActionListener l) {
		JButton button = new JButton(Resources.getString(key));
		button.setActionCommand(actionCommand);
		button.addActionListener(l);
		return button;
	}

	public void setModels() throws AquaDaoException {
		dblwPanel.setModel("Szen.lwp_treeRoot",SzenarioId * -1);
		szlwPanel.setModel("Szen.lwszp_treeRoot",SzenarioId);
		dblwPanel.setRefresher(this);
		szlwPanel.setRefresher(this);
	}
	
	public void setValues(int szen_id) throws AquaDaoException {
		this.SzenarioId = szen_id;
		refreshLebewesenPanles();
	}
	
	public void setDefaults() throws AquaDaoException {
		this.SzenarioId = 0;
		refreshLebewesenPanles();
	}
	
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		try {
			if( cmd.equalsIgnoreCase("lwadd"))
				szlwPanel.add( dblwPanel.selectedLebewesen() );
			else if( cmd.equalsIgnoreCase("lwrem"))
				szlwPanel.removeSelectedLebewesen();
		} catch (AquaDaoException ade) {
			ErrorMessage.show(ErrorMessage.CONNECTION);
		}
	}

	private void refreshLebewesenPanles() throws AquaDaoException {
		dblwPanel.refresh( SzenarioId*-1 );
		szlwPanel.refresh( SzenarioId );
	}

	public void LebenwesenDeselected(Lebewesen curSel, PanelSzenarioTrees parent) {
		if( szlwPanel == parent )
			lwRemButton.setEnabled(false);
		parent.refreshInfo( null );
	}

	public void LebenwesenSelected(Lebewesen curSel, PanelSzenarioTrees parent) {
		if( szlwPanel == parent )
			lwRemButton.setEnabled(true);
		else
			lwAddButton.setEnabled(true);
		parent.refreshInfo( curSel );
	}

	public void refreshBothSides() {
		dblwPanel.updateTree();
		szlwPanel.updateTree();
	}

	public void deleteButtonPressed(PanelSzenarioTrees parent) {
		try {
			if( szlwPanel == parent && lwRemButton.isEnabled() )
				szlwPanel.removeSelectedLebewesen();
		} catch (AquaDaoException ade) {
			ErrorMessage.show(ErrorMessage.CONNECTION);
		}
	}

	public void addButtonPressed(PanelSzenarioTrees parent) {
		try {
			if( dblwPanel == parent && lwAddButton.isEnabled() )
				szlwPanel.add( dblwPanel.selectedLebewesen() );
		} catch (AquaDaoException ade) {
			ErrorMessage.show(ErrorMessage.CONNECTION);
		}
	}

}
