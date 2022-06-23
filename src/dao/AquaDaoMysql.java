package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Super Klasse für DAO's die MySQL-Datenbank abfragen führen
 * @author Konstantin Karzanov
 * 28.08.2008
 */
public class AquaDaoMysql {

	protected static final Logger logger = Logger.getLogger("at.wifiwien.aqua.dao");
	private String url = null;
	private Connection c = null;
	private Statement s = null;
	private PreparedStatement ps = null;
	private CallableStatement cs = null;

	public AquaDaoMysql(String url) {
		this.url = url;
	}

	/**
	 * Schließt Statement, PreparedStatement, CallableStatement und Connection
	 * @throws AquaDaoException
	 */
	public void close() throws AquaDaoException {
		try {
			if (s != null) {s.close();s = null;}
			if (ps != null) {ps.close();ps = null;}
			if (cs != null) {cs.close();cs = null;}
			if (c != null) {c.close();c = null;}
		} catch (SQLException e) {
			logger.log(Level.SEVERE,"SQLException: " + e.getMessage(), e);
			throw new AquaDaoException(e.getMessage(), e);
		}
	}

	/**
	 * Macht Connection
	 * @return Connection
	 * @throws AquaDaoException
	 */
	public Connection getConnection() throws AquaDaoException {
		try {
			c = DriverManager.getConnection(url);
		} catch (SQLException e) {
			logger.log(Level.SEVERE,"SQLException: " + e.getMessage(), e);
			throw new AquaDaoException(e.getMessage(), e);
		}
		logger.log(Level.CONFIG,"Connection: " + url);
		return c;
	}

	/**
	 * Macht Statement mit vorhandene oder mit neu Connection
	 * @return Statement
	 * @throws AquaDaoException
	 */
	public Statement getStatement() throws AquaDaoException {
		if (c == null)
			getConnection();
		try {
			s = c.createStatement();
		} catch (SQLException e) {
			logger.log(Level.SEVERE,"SQLException: " + e.getMessage(), e);
			throw new AquaDaoException(e.getMessage(), e);
		}
		return s;
	}

	/**
	 * Macht PreparedStatement mit vorhandene oder mit neu Connection
	 * @return PreparedStatement
	 * @throws AquaDaoException
	 */
	public PreparedStatement getPreparedStatement(String sql) throws AquaDaoException {
		if (c == null)
			getConnection();
		try {
			ps = c.prepareStatement(sql);
		} catch (SQLException e) {
			logger.log(Level.SEVERE,"SQLException: " + e.getMessage(), e);
			throw new AquaDaoException(e.getMessage(), e);
		}
		logger.log(Level.CONFIG,"PreparedStatement: " + sql);
		return ps;
	}

	/**
	 * Macht CallableStatement mit vorhandene oder mit neu Connection
	 * @return CallableStatement
	 * @throws AquaDaoException
	 */
	public CallableStatement getCallableStatement(String sql) throws AquaDaoException {
		if (c == null)
			getConnection();
		try {
			cs = c.prepareCall(sql);
		} catch (SQLException e) {
			logger.log(Level.SEVERE,"SQLException: " + e.getMessage(), e);
			throw new AquaDaoException(e.getMessage(), e);
		}
		logger.log(Level.CONFIG,"CallableStatement: " + sql);
		return cs;
	}

	/**
	 * Geschützte Methode die hello-Prozeduere:
	 * <pre>CREATE TABLE Greetings (Message CHAR(20));
	 * INSERT INTO Greetings VALUES ('Hello, MySQL!');
	 * SELECT * FROM Greetings;<pre>
	 * aus Datenbank aufruft
	 * und danach Tabele Greetings Löscht.
	 * Wird von {@link AquaDaoMysqlTest} aufgerufen.
	 * @throws AquaDaoException
	 */
	protected void test() throws AquaDaoException {
		try {
			ResultSet rs = getCallableStatement("{call hello}").executeQuery();
			rs.next();
			System.out.println(rs.getString(1));
			rs.close();
			getStatement().execute("DROP TABLE Greetings");
		} catch (SQLException e) {
			logger.log(Level.SEVERE,"SQLException: " + e.getMessage(), e);
			throw new AquaDaoException(e.getMessage(), e);
		} finally {
			close();
		}
	}

}
