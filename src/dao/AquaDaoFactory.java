package dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Dimensionen;
import model.DimensionenList;
import model.Szenarien;
import model.SzenarioFutterzeit;
import model.VonBisWerte;

/**
 * Abstract Factory Klasse, die statische Methode: "getInstance" hat.
 * Wird normaleweise mit "AquaDaoFactory.getInstance()" aufgerufen.
 * @author Konstantin Karzanov
 * 28.08.2008
 */
public abstract class AquaDaoFactory {
	
	private static final Logger logger = Logger.getLogger("at.wifiwien.aqua.dao");
	private static final String PROPERTIES_FILE = "aquadb.properties";
	private static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
	private static AquaDaoFactory instance = null;
	
	/** 
	 * Liest Datenbanlverbindung-Einstellungen aus aquadb.properties-Datei
	 * und instatziert konkrete Factory für bestimte DB umgebung, 
	 * derzeit nur für MySQL Dtenbank ({@link AquaDaoFactoryMysql}).
	 * 
	 * @return AquaDaoFactory
	 * @throws AquaDaoException
	 */
	public static AquaDaoFactory getInstance() throws AquaDaoException {
		if (instance == null) {
			Properties p = loadProperties();
			if (p.getProperty("db.typ").toLowerCase().equals("mysql")) {
				loadDriver(MYSQL_DRIVER);
				instance = new AquaDaoFactoryMysql(p);
			}
			else {
				logger.log(Level.SEVERE,"Unknown Datebase Type");
 				throw new AquaDaoException("Unknown Datebase Type");
			}
		}
		return instance;
	}
	
	/**
	 * Liefert {@link StatistikDao} zurück, die Statistik von UploadTool
	 * in Datenbank schreiben kann.
	 * 
	 * @return StatistikDao
	 */
	public abstract StatistikDao getStatistikDao();
	
	/**
	 * Liefert {@link SzenarienDao}, die liest und schreibt {@link Szenarien} 
	 * aus/in der Datenbank
	 * 
	 * @return SzenarienDao
	 */
	public abstract SzenarienDao getSzenarienDao();
	
	/**
	 *  Liefert {@link SzenarioFutterzeitDao}, die liest und schreibt 
	 *  {@link SzenarioFutterzeit} aus/in der Datenbank
	 * 
	 * @return SzenarioFutterzeitDao
	 */
	public abstract SzenarioFutterzeitDao getSzenarioFutterzeitDao();
	
	/**
	 *  Liefert {@link DimensionenDao}, die liest {@link DimensionenList} 
	 *  und schreibt {@link Dimensionen} aus/in der Datenbank
	 * 
	 * @return DimensionenDao
	 */
	public abstract DimensionenDao getDimensionenDao();
	
	/**
	 * Liefert {@link LebewesenDao}, die liest Lebewesen Daten aus der Datenbank
	 * und schreibt Daten über das Lebewesen im Szenario.
	 * 
	 * @return LebewesenDao
	 */
	public abstract LebewesenDao getLebewesenDao();

	/**
	 * Liefert {@link PhwerteDao}, die liest PH-Werte ({@link VonBisWerte}) 
	 * aus der Datenbank.
	 * 
	 * @return PhwerteDao
	 */
	public abstract PhwerteDao getPhwerteDao();
	
	/**
	 * Liefert {@link PhwerteDao}, die liest Temperatur-Werte ({@link VonBisWerte}) 
	 * aus der Datenbank.
	 * 
	 * @return TempwerteDao
	 */
	public abstract TempwerteDao getTempwerteDao();
	
	/**
	 * Geschützte methode für testen. Wird von {@link AquaDaoMysqlTest} aufgerufen.
	 * 
	 * @return AquaDaoMysql
	 */
	protected abstract AquaDaoMysql getAquaDao();

	// Instatziert DB Driver für bestimte DB umgebung
	private static void loadDriver(String driver) throws AquaDaoException {
			try {
				Class.forName(driver).newInstance();
			} catch (InstantiationException e) {
				logger.log(Level.SEVERE,"InstantiationException: " + e.getMessage(), e);
				throw new AquaDaoException("InstantiationException: " + e.getMessage(), e);
			} catch (IllegalAccessException e) {
				logger.log(Level.SEVERE,"IllegalAccessException: " + e.getMessage(), e);
				throw new AquaDaoException("IllegalAccessException: " + e.getMessage(), e);
			} catch (ClassNotFoundException e) {
				logger.log(Level.SEVERE,"ClassNotFoundException: " + e.getMessage(), e);
				throw new AquaDaoException("ClassNotFoundException: " + e.getMessage(), e);
			}
	}
	
	// Liest Properties
	private static Properties loadProperties() throws AquaDaoException {
		Properties properties = new Properties();
		try {
			InputStream is = AquaDaoFactory.class.getResourceAsStream(PROPERTIES_FILE);
			properties.load(is);
			is.close();
			return properties;
		} catch (IOException e) {
			logger.log(Level.SEVERE,"IOException: " + e.getMessage(), e);
			throw new AquaDaoException("IOException: " + e.getMessage(), e);
		}
	}

}
