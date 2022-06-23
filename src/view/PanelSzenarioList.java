package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerDateModel;

import main.ErrorMessage;
import main.Resources;
import model.SzenarioFutterzeit;

import dao.AquaDaoException;


/**
 * Futter-Zeiten Editor Panel
 * @author Konstantin Karzanov
 * 28.08.2008
 */
public class PanelSzenarioList extends JPanel implements ActionListener {

	private static final long serialVersionUID = 7371191512126336454L;
	private Dimension listPanePreferredSize = new Dimension(55,70);
	private JSpinner spinner = new JSpinner();
	private JList listView = new JList();
	private SzenarioFutterzeit listModel = null;

	public PanelSzenarioList() {
		super(new BorderLayout(5,0));
		setBorder(BorderFactory.createTitledBorder(Resources.getString("Szen.fzp_border")));
		add(getWestPanel(),BorderLayout.WEST);
		add(getListPane(), BorderLayout.CENTER);
	}

	private JPanel getWestPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		JPanel buttonsPanel = new JPanel(new GridLayout(2,1,0,5));
		buttonsPanel.add(getButton("Szen.fzp_addButton", "fzadd", this));
		buttonsPanel.add(getButton("Szen.fzp_deleteButton", "fzdel", this));

		panel.add(spinner, BorderLayout.NORTH);
		panel.add(buttonsPanel, BorderLayout.SOUTH);

		return panel;
	}

	private JScrollPane getListPane() {
		JScrollPane listPane = new JScrollPane(listView);
		listPane.setPreferredSize(listPanePreferredSize);
		return listPane;
	}

	private JButton getButton(String key, String cmd, ActionListener l) {
		JButton button = new JButton(Resources.getString(key));
		button.setActionCommand(cmd);
		button.addActionListener(l);
		return button;
	}

	/**
	 * Spinner einstellen: Datum-Model
	 */
	public void setSpinnerModel() {
		spinner.setModel(new SpinnerDateModel());
		spinner.setEditor(new JSpinner.DateEditor(spinner, SzenarioFutterzeit.FORMATPATTERN));
	}

	/**
	 * Zeiten-List Model({@link SzenarioFutterzeit} einstellen
	 * @param szen_id
	 */
	public void setListModel(int szen_id) {
		try {
			listModel = new SzenarioFutterzeit(szen_id);
			listView.setModel(listModel);
			listView.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		} catch (AquaDaoException e) {
			ErrorMessage.show(ErrorMessage.CONNECTION);
		}
	}

	/**
	 * Speichern Futtezeit-Liste
	 * @param szen_id
	 */
	public void saveList(int szen_id) {
		try {
			listModel.save(szen_id);
		} catch (AquaDaoException e) {
			ErrorMessage.show(ErrorMessage.CONNECTION);
		}
	}

	/**
	 * Löschen Futterzeit-Liste
	 * @param szen_id
	 */
	public void deleteList(int szen_id) {
		try {
			listModel.delete(szen_id);
		} catch (AquaDaoException e) {
			ErrorMessage.show(ErrorMessage.CONNECTION);
		}
	}

	/**
	 * Überprüfen ob Liste nicht lehr ist
	 * @return boolean
	 */
	public boolean checkList() {
		if(listModel.getSize()>0)
			return true;
		else {
			ErrorMessage.show(ErrorMessage.NO_FEED);
			return false;
		}
	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if(cmd.equalsIgnoreCase("fzadd"))
			listModel.add(spinner.getValue());
		else if(cmd.equalsIgnoreCase("fzdel") && listView.getSelectedIndex()!=-1)
			listModel.remove(listView.getSelectedIndex());
		listView.setSelectedIndex(listModel.getSize()-1);
	}

}
