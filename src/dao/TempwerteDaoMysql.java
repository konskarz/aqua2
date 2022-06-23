package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import model.VonBisWerte;



public class TempwerteDaoMysql extends AquaDaoMysql implements TempwerteDao {

	public final String SELECT = "select Von, Bis from tempwerte where id = ?";

	public TempwerteDaoMysql(String url) {
		super(url);
	}

	public VonBisWerte read(int id) throws AquaDaoException {
		VonBisWerte value = null;
		try {
			PreparedStatement ps = getPreparedStatement(SELECT);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				value = new VonBisWerte(rs.getDouble(1), rs.getDouble(2));
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "SQLException: " + e.getMessage(), e);
			throw new AquaDaoException(e.getMessage(), e);
		} finally {
			close();
		}
		return value;
	}

}
