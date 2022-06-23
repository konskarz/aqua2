package dao;

import java.util.Properties;


/**
 * Factory Klasse für MySQL DAO's implementiert abstracte Methoden
 * aus {@link AquaDaoFactory} Klasse
 * @author Konstantin Karzanov
 * 28.08.2008
 */
public class AquaDaoFactoryMysql extends AquaDaoFactory {
	
	private String url = null;
	private StatistikDao statistikDao = null;
	private SzenarienDao szenarienDao = null;
	private SzenarioFutterzeitDao futterzeitDao;
	private DimensionenDao dimensionenDao = null;
	private LebewesenDao lebewesenDao = null;
	private PhwerteDao phwerteDao;
	private TempwerteDao tempwerteDao;
	private AquaDaoMysql aquaDao = null;

	public AquaDaoFactoryMysql(Properties p) {
		url = "jdbc:mysql://"+
			p.getProperty("db.host")+
			":"+p.getProperty("db.port")+
			"/"+p.getProperty("db.name")+
			"?user="+p.getProperty("db.user")+
			"&password="+p.getProperty("db.pass")+
			"&noAccessToProcedureBodies=true";
	}

	public StatistikDao getStatistikDao() {
		if (statistikDao == null)
			statistikDao = new StatistikDaoMysql(url);
		return statistikDao;
	}

	public SzenarienDao getSzenarienDao() {
		if (szenarienDao == null)
			szenarienDao = new SzenarienDaoMysql(url);
		return szenarienDao;
	}

	public SzenarioFutterzeitDao getSzenarioFutterzeitDao() {
		if (futterzeitDao == null)
			futterzeitDao = new SzenarioFutterzeitDaoMysql(url);
		return futterzeitDao;
	}

	public DimensionenDao getDimensionenDao() {
		if (dimensionenDao == null)
			dimensionenDao = new DimensionenDaoMysql(url);
		return dimensionenDao;
	}

	public LebewesenDao getLebewesenDao() {
		if (lebewesenDao == null)
			lebewesenDao = new LebewesenDaoMysql(url);
		return lebewesenDao;
	}

	public PhwerteDao getPhwerteDao() {
		if (phwerteDao == null)
			phwerteDao = new PhwerteDaoMysql(url);
		return phwerteDao;
	}

	public TempwerteDao getTempwerteDao() {
		if (tempwerteDao == null)
			tempwerteDao = new TempwerteDaoMysql(url);
		return tempwerteDao;
	}

	protected AquaDaoMysql getAquaDao() {
		if (aquaDao == null)
			aquaDao = new AquaDaoMysql(url);
		return aquaDao;
	}

}

