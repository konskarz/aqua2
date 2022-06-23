package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import javax.swing.tree.DefaultMutableTreeNode;

import model.Lebewesen;
import model.LebewesenModel;
import model.LebewesenNode;
import model.LebewesenTyp;
import model.LebewesenTypSzenario;



public class LebewesenDaoMysql extends AquaDaoMysql implements LebewesenDao {

	public static final String SELECT_IN = "select l.id, l.name, t.id, l.tempwerte_id, l.phwerte_id, l.info, l.photo, l.kantenab, t.bezeichnung, sl.LebewMenge from lebewesen l join typen t on l.typ_id = t.id join szenarien_lebewesen sl where sl.lebew_id = l.id and sl.szen_id = ?";
	public static final String SELECT_NOTIN = "select l.id, l.name, t.id, l.tempwerte_id, l.phwerte_id, l.info, l.photo, l.kantenab, t.bezeichnung from lebewesen l join typen t on l.typ_id = t.id";
	public static final String FILTER_NOTIN_SZENARIO = " where not exists(select 1 from szenarien_lebewesen sl where sl.lebew_id = l.id and sl.szen_id = ?)";
	public static final String ORDERBY = " order by t.id, l.name";
	public static final String DELETE = "delete from szenarien_lebewesen where lebew_id = ? and szen_id = ?";
	public static final String INSERT = "insert into szenarien_lebewesen (lebew_id, szen_id,LebewMenge) values (?,?,?)";
	public static final String EXISTS = "select lebew_id from szenarien_lebewesen where lebew_id = ? and szen_id = ?";
	public static final String UPDATE = "update szenarien_lebewesen set LebewMenge = LebewMenge + ? where lebew_id = ? and szen_id = ?";

	public LebewesenDaoMysql(String url) {
		super(url);
	}

	public DefaultMutableTreeNode Build_LebewesenTree(String strRoot, int sid,
			boolean isInSelected) throws AquaDaoException {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(strRoot);
		try {
			PreparedStatement ps;
			if (sid >= 0) {
				ps = getPreparedStatement(SELECT_IN + ORDERBY);
			} else {
				ps = getPreparedStatement(SELECT_NOTIN + FILTER_NOTIN_SZENARIO + ORDERBY);
			}
			ps.setInt(1, Math.abs(sid));
			ResultSet rs = ps.executeQuery();
			Lebewesen l;
			LebewesenNode parent;
			int cnt = 0;
			while (rs.next()) {
				cnt++;
				if (sid > 0)
					l = new LebewesenTypSzenario(sid, rs);
				else
					l = new LebewesenTyp(rs);
				parent = LebewesenModel.findOrCreateParentTyp(root, l, null);
				parent.add(l.BuildNode(true));
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "SQLException: " + e.getMessage(), e);
			throw new AquaDaoException(e.getMessage(), e);
		} finally {
			close();
		}
		return root;
	}

	public boolean removeFromSzenario(int id, int szenarioId) throws AquaDaoException {
		boolean ret = false;
		try {
			PreparedStatement ps;
			ps = getPreparedStatement(DELETE);
			ps.setInt(1, id);
			ps.setInt(2, szenarioId);
			ps.executeUpdate();
			ret = true;
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "SQLException: " + e.getMessage(), e);
			throw new AquaDaoException(e.getMessage(), e);
		} finally {
			close();
		}
		return ret;
	}

	public boolean addToSzenario(int id, int szenarioId, int menge)
			throws AquaDaoException {
		boolean ret = false;
		try {
			PreparedStatement ps;
			ps = getPreparedStatement(EXISTS);
			ps.setInt(1, id);
			ps.setInt(2, szenarioId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				ps = getPreparedStatement(UPDATE);
				ps.setInt(1, menge);
				ps.setInt(2, id);
				ps.setInt(3, szenarioId);
			}
			else {
				ps = getPreparedStatement(INSERT);
				ps.setInt(1, id);
				ps.setInt(2, szenarioId);
				ps.setInt(3, menge);
			}
			rs = null;
			ps.executeUpdate();
			ret = true;
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "SQLException: " + e.getMessage(), e);
			throw new AquaDaoException(e.getMessage(), e);
		} finally {
			close();
		}
		return ret;
	}

}
