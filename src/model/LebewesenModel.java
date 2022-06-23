package model;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import view.ILebewesenRefresher;
import view.PanelSzenarioTrees;

import dao.AquaDaoException;
import dao.AquaDaoFactory;
import dao.LebewesenDao;


public class LebewesenModel extends DefaultTreeModel implements TreeSelectionListener, KeyListener, MouseListener {

	private static final long serialVersionUID = 3967724499036108108L;
	private final int INSERTED = 0;
	private final int UPDATED = 2;

	private DefaultMutableTreeNode m_root;
	private LebewesenDao m_sqlData;
	private int m_SzenarioId;
	private LebewesenNode m_curSelection;
	private ILebewesenRefresher m_refresher;
	private PanelSzenarioTrees m_Parent;
	private JTree m_tree;
	private boolean isInSelected = false;

	public LebewesenModel(String strRoot, int inSzenario) throws AquaDaoException {
		super(null);
		m_curSelection = null;
		m_SzenarioId = inSzenario;
		m_sqlData = AquaDaoFactory.getInstance().getLebewesenDao();
		m_root = m_sqlData.Build_LebewesenTree(strRoot, m_SzenarioId,isInSelected);
		this.setRoot(m_root);
	}

	public static LebewesenNode findOrCreateParentTyp(DefaultMutableTreeNode root, Lebewesen l, boolean[] created) {
		LebewesenNode n;
		for (int i = 0; i < root.getChildCount(); i++) {
			n = (LebewesenNode) root.getChildAt(i);
			if (n.isTyp(l))
				return n;
		}
		n = l.BuildNode(false);
		root.add(n);
		if (created != null)
			created[0] = true;

		return n;
	}

	public void valueChanged(TreeSelectionEvent arg0) {
		TreePath t = arg0.getOldLeadSelectionPath();
		if (t != null && t.getParentPath() != null)
			m_refresher.LebenwesenDeselected(((LebewesenNode) t.getLastPathComponent()).toLebewesen(), m_Parent);
		m_curSelection = null;
		t = arg0.getNewLeadSelectionPath();
		if (t != null && t.getParentPath() != null)
			m_curSelection = (LebewesenNode) t.getLastPathComponent();
		if (m_curSelection != null && m_curSelection.getChildCount() == 0)
			m_refresher.LebenwesenSelected(m_curSelection.toLebewesen(),m_Parent);
	}

	public LebewesenNode currentSelection() {
		return m_curSelection;
	}

	private LebewesenNode findLebewesen(LebewesenNode parent, Lebewesen l) {
		LebewesenNode n;
		for (int i = 0; i < parent.getChildCount(); i++) {
			n = (LebewesenNode) parent.getChildAt(i);
			if (n.isSame(l))
				return n;
		}
		return null;
	}

	public void add(Lebewesen l) throws AquaDaoException {
		int menge = 0;
		if (m_SzenarioId > 0) {
			String ret = JOptionPane.showInputDialog("Wie viele " + l.nodeName() + "?", 1);
			if (ret == null || ret.length() == 0)
				return;
			menge = Integer.parseInt(ret);
			m_sqlData.addToSzenario(l.getId(), m_SzenarioId, menge);
			l = new LebewesenTypSzenario((LebewesenTyp) l, m_SzenarioId, menge);
		}
		boolean[] created = new boolean[1];
		created[0] = false;
		LebewesenNode parent = LebewesenModel.findOrCreateParentTyp(m_root, l, created);
		if (created[0]) {
			fireChanges(parent, INSERTED);
			TreePath tp = new TreePath(parent.getPath());
			m_tree.scrollPathToVisible(tp);
		}
		LebewesenNode nl;
		if ((nl = findLebewesen(parent, l)) != null)
			nl.updateMenge(menge);
		else {
			nl = l.BuildNode(true);
			parent.add(nl);
		}
		fireChanges(nl, INSERTED);
	}

	public void removeSelectedLebewesen() throws AquaDaoException {
		if (m_curSelection == null)
			return;
		if (m_SzenarioId > 0) {
			Lebewesen l = m_curSelection.toLebewesen();
			m_sqlData.removeFromSzenario(l.getId(), m_SzenarioId);
		}
		DefaultMutableTreeNode parent = (DefaultMutableTreeNode) m_curSelection.getParent();
		this.removeNodeFromParent(m_curSelection);
		if (parent.getChildCount() == 0) {
			// DefaultMutableTreeNode grandPa =
			// (DefaultMutableTreeNode)parent.getParent();
			this.removeNodeFromParent(parent);
		}
	}

	public void setRefresher(ILebewesenRefresher r, PanelSzenarioTrees treesPanel) {
		m_refresher = r;
		m_Parent = treesPanel;
	}

	public void refresh(int szenarioId) throws AquaDaoException {
		m_curSelection = null;
		m_SzenarioId = szenarioId;
		m_root = m_sqlData.Build_LebewesenTree(m_root.toString(), m_SzenarioId,isInSelected);
		this.setRoot(m_root);
	}

	private void fireChanges(DefaultMutableTreeNode changed, int what,DefaultMutableTreeNode parent) {
		Object[] path;
		int[] childIndices;
		Object[] children;

		path = parent.getPath();
		childIndices = new int[1];
		childIndices[0] = parent.getIndex(changed);
		children = new Object[1];
		children[0] = changed;

		switch (what) {
		case INSERTED:
			this.fireTreeNodesInserted(changed, path, childIndices, children);
			break;
		case UPDATED:
			this.fireTreeNodesChanged(changed, path, childIndices, children);
			break;
		}
		TreePath tp = new TreePath(changed.getPath());
		m_tree.scrollPathToVisible(tp);
		m_tree.setSelectionPath(tp);
		m_tree.setRequestFocusEnabled(true);
		m_tree.setFocusable(true);
	}

	private void fireChanges(DefaultMutableTreeNode changed, int what) {
		DefaultMutableTreeNode parent = (DefaultMutableTreeNode) changed.getParent();
		this.fireChanges(changed, what, parent);
	}

	public void setTree(JTree lebewesenTree) {
		m_tree = lebewesenTree;
		m_tree.addKeyListener(this);
		m_tree.addMouseListener(this);
	}

	public void deleteButtonPressed() {
		m_refresher.deleteButtonPressed(m_Parent);
	}

	public void keyPressed(KeyEvent arg0) {}

	public void keyReleased(KeyEvent arg0) {
		if (arg0.getKeyCode() == KeyEvent.VK_DELETE)
			this.deleteButtonPressed();
	}

	public void keyTyped(KeyEvent arg0) {}

	public void mouseClicked(MouseEvent arg0) {
		if (arg0.getClickCount() == 2)
			m_refresher.addButtonPressed(m_Parent);
	}

	public void mouseEntered(MouseEvent arg0) {}

	public void mouseExited(MouseEvent arg0) {}

	public void mousePressed(MouseEvent arg0) {}

	public void mouseReleased(MouseEvent arg0) {}

	public void setInNotIn(boolean selected) throws AquaDaoException {
		isInSelected = selected;
		this.refresh(m_SzenarioId);
	}

}
