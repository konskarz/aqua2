package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;

public class SzenarioFutterzeitDaoMysql extends AquaDaoMysql implements SzenarioFutterzeitDao {

	public static final String UHRZEIT = "UhrZeit";
	public static final String INSERTFZ = "INSERT INTO futterzeit (UhrZeit) VALUES (?)";
	public static final String INSERTSFZ = "INSERT INTO futterzeit_szenarien (FutterZeit_ID, Szenarien_ID) VALUES (?, ?)";
	public static final String SELECT_BY_ID = "SELECT fz.UhrZeit FROM futterzeit_szenarien fzs JOIN futterzeit fz ON fzs.FutterZeit_ID = fz.ID WHERE fzs.Szenarien_ID = ? ORDER BY fz.UhrZeit ASC";
	public static final String DELETE_BY_ID = "DELETE fzs.*,fz.* FROM futterzeit_szenarien fzs INNER JOIN futterzeit fz ON fzs.FutterZeit_ID = fz.ID WHERE fzs.Szenarien_ID=?";

	public SzenarioFutterzeitDaoMysql(String url) {
		super(url);
	}

	public void create(int szen_id, Vector<String> zeit) throws AquaDaoException {
		delete(szen_id);
		for(String s : zeit) {
			insertfzs(szen_id, insertfz(s));
		}
	}
	
	public Vector<String> read(int szen_id) throws AquaDaoException {
		Vector<String> v = new Vector<String>();
		try {
			PreparedStatement ps = getPreparedStatement(SELECT_BY_ID);
			ps.setInt(1, szen_id);
			ResultSet rs = ps.executeQuery();
			while (rs.next())
				v.add(rs.getString(SzenarioFutterzeitDaoMysql.UHRZEIT));
			rs.close();
			return v;
		} catch (SQLException e) {
			logger.log(Level.SEVERE,"SQLException: " + e.getMessage(), e);
			throw new AquaDaoException(e.getMessage(), e);
		} finally {
			close();
		}
	}

	public void delete(int szen_id) throws AquaDaoException {
		try {
			PreparedStatement ps = getPreparedStatement(DELETE_BY_ID);
			ps.setInt(1, szen_id);
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.log(Level.SEVERE,"SQLException: " + e.getMessage(), e);
			throw new AquaDaoException(e.getMessage(), e);
		} finally {
			close();
		}
	}

	private void insertfzs(int szen_id, int fz_id) throws AquaDaoException {
		try {
			PreparedStatement ps = getPreparedStatement(INSERTSFZ);
			ps.setInt(1, fz_id);
			ps.setInt(2, szen_id);
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.log(Level.SEVERE,"SQLException: " + e.getMessage(), e);
			throw new AquaDaoException(e.getMessage(), e);
		} finally {
			close();
		}
	}

	private int insertfz(String s) throws AquaDaoException {
		try {
			PreparedStatement ps = getPreparedStatement(INSERTFZ);
			ps.setString(1, s);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			logger.log(Level.SEVERE,"SQLException: " + e.getMessage(), e);
			throw new AquaDaoException(e.getMessage(), e);
		} finally {
			close();
		}
	}

}
