package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;

import model.Szenario;


public class SzenarienDaoMysql extends AquaDaoMysql implements SzenarienDao {

	public static final String ID = "ID";
	public static final String NAME = "Name";
	public static final String SHORTNAME = "ShortName";
	public static final String TEMPMIN = "TempMin";
	public static final String TEMPMAX = "TempMax";
	public static final String PHMIN = "PHMin";
	public static final String PHMAX = "PHMax";
	public static final String LICHTEIN = "LichtEin";
	public static final String LICHTAUS = "LichtAus";
	public static final String DIM_ID = "Dim_ID";
	public static final String INSERT = "INSERT INTO szenarien (Name, ShortName, TempMin, TempMax, PHMin, PHMax, LichtEin, LichtAus, Dim_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	public static final String SELECT_ALL = "SELECT s.ID, s.Name, s.ShortName, s.TempMin, s.TempMax, s.PHMin, s.PHMax, s.LichtEin, s.LichtAus, s.Dim_ID FROM szenarien s ORDER BY 1 DESC";
	public static final String SELECT_BY_ID = "SELECT s.ID, s.Name, s.ShortName, s.TempMin, s.TempMax, s.PHMin, s.PHMax, s.LichtEin, s.LichtAus, s.Dim_ID FROM szenarien s WHERE ID = ?";
	public static final String UPDATE = "UPDATE szenarien SET Name = ?, ShortName = ?, TempMin = ?, TempMax = ?, PHMin = ?, PHMax = ?, LichtEin = ?, LichtAus = ?, Dim_ID = ? WHERE szenarien.ID = ?";
	public static final String DELETE_BY_ID = "DELETE FROM szenarien WHERE szenarien.ID = ?";

	public SzenarienDaoMysql(String url) {
		super(url);
	}

	public int create(String name, String shortname, double tempMin, double tempMax,
			double phMin, double phMax, Date lichtEin, Date lichtAus,
			int dim_ID) throws AquaDaoException {
		try {
			PreparedStatement ps = getPreparedStatement(INSERT);
			ps.setString(1, name);
			ps.setString(2, shortname);
			ps.setDouble(3, tempMin);
			ps.setDouble(4, tempMax);
			ps.setDouble(5, phMin);
			ps.setDouble(6, phMax);
			ps.setString(7, getString(lichtEin));
			ps.setString(8, getString(lichtAus));
			ps.setInt(9, dim_ID);
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

	public Vector<Szenario> read() throws AquaDaoException {
		try {
			ResultSet rs = getStatement().executeQuery(SELECT_ALL);
			return createSzenarien(rs);
		} catch (SQLException e) {
			logger.log(Level.SEVERE,"SQLException: " + e.getMessage(), e);
			throw new AquaDaoException(e.getMessage(), e);
		} finally {
			close();
		}
	}

	public void update(int id, String name, String shortname, double tempMin, double tempMax,
			double phMin, double phMax, Date lichtEin, Date lichtAus,
			int dim_ID) throws AquaDaoException {
		try {
			PreparedStatement ps = getPreparedStatement(UPDATE);
			ps.setString(1, name);
			ps.setString(2, shortname);
			ps.setDouble(3, tempMin);
			ps.setDouble(4, tempMax);
			ps.setDouble(5, phMin);
			ps.setDouble(6, phMax);
			ps.setString(7, getString(lichtEin));
			ps.setString(8, getString(lichtAus));
			ps.setInt(9, dim_ID);
			ps.setInt(10, id);
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

	private Vector<Szenario> createSzenarien(ResultSet rs) throws SQLException, AquaDaoException {
		Vector<Szenario> v = new Vector<Szenario>();
		while (rs.next())
			v.add(new Szenario(
					rs.getInt(SzenarienDaoMysql.ID),
					rs.getString(SzenarienDaoMysql.NAME),
					rs.getString(SzenarienDaoMysql.SHORTNAME),
					rs.getDouble(SzenarienDaoMysql.TEMPMIN),
					rs.getDouble(SzenarienDaoMysql.TEMPMAX),
					rs.getDouble(SzenarienDaoMysql.PHMIN),
					rs.getDouble(SzenarienDaoMysql.PHMAX),
					getDate(rs.getString(SzenarienDaoMysql.LICHTEIN)),
					getDate(rs.getString(SzenarienDaoMysql.LICHTAUS)),
					rs.getInt(SzenarienDaoMysql.DIM_ID)
					));
		rs.close();
		return v;
	}
	
	private Date getDate(String text) throws AquaDaoException {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		Date date;
		try {
			date = sdf.parse(text);
		} catch (ParseException e) {
			logger.log(Level.SEVERE,"ParseException: " + e.getMessage(), e);
			throw new AquaDaoException(e.getMessage(), e);
		}
		return date;
	}

	private String getString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		return sdf.format(date);
	}

}
