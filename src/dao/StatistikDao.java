package dao;

/**
 * Interface für schreiben Statistik
 * 
 * @author Konstantin Karzanov
 * 28.08.2008
 */
public interface StatistikDao {
	
	/**
	 * Speichert Statistik_Daten
	 * @param zeit
	 * @param phIst
	 * @param tempIst
	 * @param motoEinAus
	 * @throws AquaDaoException
	 */
	public void insertStatistik(String zeit, double phIst, double tempIst, int motoEinAus) throws AquaDaoException;

}
