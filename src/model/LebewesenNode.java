package model;

import javax.swing.tree.DefaultMutableTreeNode;

public class LebewesenNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = -3957025345010951854L;
	private Lebewesen m_l;
	private boolean m_isLeaf;

	public LebewesenNode(Lebewesen l, boolean isleaf) {
		super(isleaf ? l.nodeName() : l.nodeType());
		m_isLeaf = isleaf;
		m_l = l;
	}

	public boolean isTyp(Lebewesen l) {
		return m_l.isTyp(l);
	}

	public Lebewesen toLebewesen() {
		return m_l;
	}

	public boolean isSame(Lebewesen l) {
		return m_l.getId() == l.getId();
	}

	public void updateMenge(int menge) {
		m_l.updateMenge(menge);
	}

	public String toString() {
		if (m_isLeaf)
			return m_l.toString();
		return m_l.nodeType();
	}
}
