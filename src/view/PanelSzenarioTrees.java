package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.tree.TreeSelectionModel;

import main.ErrorMessage;
import main.Resources;
import model.Lebewesen;
import model.LebewesenModel;

import dao.AquaDaoException;


public class PanelSzenarioTrees extends JPanel implements ActionListener {

	private static final long serialVersionUID = 871598368878726765L;
	private Dimension filterPanePreferredSize = new Dimension(130, 30);
	private Dimension treePanePreferredSize = new Dimension(130, 140);
	private Dimension infoPanePreferredSize = new Dimension(130, 140);
	private JPanel filterPanel = new JPanel();
	private JTree lebewesenTree = new JTree();
	private JTextPane infoPane = new JTextPane();
	private LebewesenModel m_lebewesenModel;

	public PanelSzenarioTrees(String keyInfoText, boolean useCheckbox) {
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createEmptyBorder(0, 3, 0, 3));

		this.add(getFilterPanel(), BorderLayout.NORTH);
		this.add(getTreePane(), BorderLayout.CENTER);
		this.add(getInfoPane(), BorderLayout.SOUTH);

		this.setInfo(Resources.getString(keyInfoText));
		if (useCheckbox)
			addCheckBox();
	}

	private JPanel getFilterPanel() {
		LayoutManager layout = new BoxLayout(filterPanel, BoxLayout.X_AXIS);
		filterPanel.setLayout(layout);
		filterPanel.setPreferredSize(filterPanePreferredSize);
		return filterPanel;
	}

	private JScrollPane getTreePane() {
		lebewesenTree.setEditable(false);
		lebewesenTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

		JScrollPane treeScrollPane = new JScrollPane(lebewesenTree);
		treeScrollPane.setPreferredSize(treePanePreferredSize);

		return treeScrollPane;
	}

	private JScrollPane getInfoPane() {
		infoPane.setEditable(false);
		JScrollPane panel = new JScrollPane(infoPane);
		panel.setPreferredSize(infoPanePreferredSize);
		return panel;
	}

	private void addCheckBox() {
		JCheckBox cBox = new JCheckBox(Resources.getString("Szen.lwp_filter"));
		cBox.setActionCommand("cbnotinaqua");
		cBox.addActionListener(this);
		filterPanel.add(cBox);
	}

	public void setInfo(String info) {
		infoPane.setText(info);
	}

	public void setModel(String keyTreeRoot, int szenarioId) throws AquaDaoException {
		m_lebewesenModel = new LebewesenModel(Resources.getString(keyTreeRoot),szenarioId);
		m_lebewesenModel.setTree(lebewesenTree);
		lebewesenTree.setModel(m_lebewesenModel);
		lebewesenTree.addTreeSelectionListener(m_lebewesenModel);
	}

	public void setRefresher(ILebewesenRefresher r) {
		m_lebewesenModel.setRefresher(r, this);
	}

	public Lebewesen selectedLebewesen() {
		return m_lebewesenModel.currentSelection().toLebewesen();
	}

	public void add(Lebewesen selectedLebewesen) throws AquaDaoException {
		m_lebewesenModel.add(selectedLebewesen);
	}

	public void removeSelectedLebewesen() throws AquaDaoException {
		m_lebewesenModel.removeSelectedLebewesen();
	}

	public void refresh(int szenarioId) throws AquaDaoException {
		m_lebewesenModel.refresh(szenarioId);
	}

	public void updateTree() {
		lebewesenTree.setVisible(false);
		lebewesenTree.setVisible(true);
	}

	public void refreshInfo(Lebewesen curSel) {
		if (curSel != null)
			this.setInfo(curSel.getInfo());
	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		try {
			if (cmd.equals("cbnotinaqua"))
				m_lebewesenModel.setInNotIn(((JCheckBox) e.getSource()).getModel().isSelected());
		} catch (AquaDaoException ade) {
			ErrorMessage.show(ErrorMessage.CONNECTION);
		}
	}

}
