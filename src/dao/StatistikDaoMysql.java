package dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;


public class StatistikDaoMysql extends AquaDaoMysql implements StatistikDao {
	
	private static final String INSERT = "INSERT INTO statistik (Zeit, TempIst, PHIst, motoEinAus) VALUES (?, ?, ?, ?)";

	public StatistikDaoMysql(String url) {
		super(url);
	}

	public void insertStatistik(String zeit, double phIst, double tempIst, int motoEinAus) throws AquaDaoException {
		try {
			PreparedStatement ps = getPreparedStatement(INSERT);
			ps.setString(1, zeit);
			ps.setDouble(2, phIst);
			ps.setDouble(3, tempIst);
			ps.setInt(4, motoEinAus);
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.log(Level.SEVERE,"SQLException: " + e.getMessage(), e);
			throw new AquaDaoException(e.getMessage(), e);
		} finally {
			close();
		}
	}

}
