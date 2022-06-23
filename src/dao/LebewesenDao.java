package dao;

import javax.swing.tree.DefaultMutableTreeNode;



public interface LebewesenDao {

	public DefaultMutableTreeNode Build_LebewesenTree(String strRoot, int sid, boolean isInSelected) throws AquaDaoException;
	public boolean removeFromSzenario(int id, int szenarioId) throws AquaDaoException;
	public boolean addToSzenario(int id, int szenarioId, int menge) throws AquaDaoException;

}
