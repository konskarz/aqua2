package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;

import model.Dimensionen;


public class DimensionenDaoMysql extends AquaDaoMysql implements DimensionenDao {

	public static final String ID = "ID";
	public static final String L = "L";
	public static final String B = "B";
	public static final String H = "H";
	public static final String Volumen = "Volumen";
	public static final String INSERT = "INSERT INTO dimensionen (L, B, H, Volumen) VALUES (?, ?, ?, ?)";
	public static final String SELECT_ALL = "SELECT ID, L, B, H, Volumen FROM dimensionen ORDER BY 1 DESC";
	public static final String SELECT_BY_ID = "SELECT ID, L, B, H, Volumen FROM dimensionen WHERE ID = ?";
	public static final String UPDATE = "UPDATE dimensionen SET L = ?, B = ?, H = ?, Volumen = ? WHERE dimensionen.ID = ?";
	public static final String DELETE_BY_ID = "DELETE FROM dimensionen WHERE dimensionen.ID = ?";
	
	public DimensionenDaoMysql(String url) {
		super(url);
	}

	public int create(int[] values) throws AquaDaoException {
		try {
			PreparedStatement ps = getPreparedStatement(INSERT);
			for(int i=0;i<values.length;i++)
				ps.setInt(i+1, values[i]);
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

	public Vector<Dimensionen> read() throws AquaDaoException {
		try {
			ResultSet rs = getStatement().executeQuery(SELECT_ALL);
			return createDimensionenList(rs);
		} catch (SQLException e) {
			logger.log(Level.SEVERE,"SQLException: " + e.getMessage(), e);
			throw new AquaDaoException(e.getMessage(), e);
		} finally {
			close();
		}
	}
	
	public void update(int id, int[] values) throws AquaDaoException {
		try {
			PreparedStatement ps = getPreparedStatement(UPDATE);
			for(int i=0;i<values.length;i++)
				ps.setInt(i+1, values[i]);
			ps.setInt(5, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.log(Level.SEVERE,"SQLException: " + e.getMessage(), e);
			throw new AquaDaoException(e.getMessage(), e);
		} finally {
			close();
		}
	}

	public void delete(int id) throws AquaDaoException {
		try {
			PreparedStatement ps = getPreparedStatement(DELETE_BY_ID);
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.log(Level.SEVERE,"SQLException: " + e.getMessage(), e);
			throw new AquaDaoException(e.getMessage(), e);
		} finally {
			close();
		}
	}

	private Vector<Dimensionen> createDimensionenList(ResultSet rs) throws SQLException {
		Vector<Dimensionen> v = new Vector<Dimensionen>();
		while (rs.next())
			v.add(new Dimensionen(
					rs.getInt(DimensionenDaoMysql.ID),
					new int[] {
						rs.getInt(DimensionenDaoMysql.L), 
						rs.getInt(DimensionenDaoMysql.B), 
						rs.getInt(DimensionenDaoMysql.H), 
						rs.getInt(DimensionenDaoMysql.Volumen)
					}));
		rs.close();
		return v;
	}

}
