package dao;

import java.util.Date;
import java.util.Vector;

import model.Szenario;


/**
 * Interface für lesen und schreiben Szenarien
 * 
 * @author Konstantin Karzanov
 * 28.08.2008
 */

public interface SzenarienDao {

	/**
	 * Speichert neu Szenario
	 * @param name
	 * @param shortname
	 * @param tempMin
	 * @param tempMax
	 * @param phMin
	 * @param phMax
	 * @param lichtEin
	 * @param lichtAus
	 * @param dim_ID
	 * @return id
	 * @throws AquaDaoException
	 */
	public int create(String name, String shortname, double tempMin, double tempMax, double phMin, double phMax, Date lichtEin, Date lichtAus, int dim_ID) throws AquaDaoException;
	
	/**
	 * Liest Szenarien.
	 * @return Vector<Szenario>
	 * @throws AquaDaoException
	 */
	public Vector<Szenario> read() throws AquaDaoException;
	
	/**
	 * Speichert neu Werte für bestimte Szenario
	 * @param id
	 * @param name
	 * @param shortname
	 * @param tempMin
	 * @param tempMax
	 * @param phMin
	 * @param phMax
	 * @param lichtEin
	 * @param lichtAus
	 * @param dim_ID
	 * @throws AquaDaoException
	 */
	public void update(int id, String name, String shortname, double tempMin, double tempMax, double phMin, double phMax, Date lichtEin, Date lichtAus, int dim_ID) throws AquaDaoException;
	
	/**
	 * Löscht bestimte Szenario
	 * @param id
	 * @throws AquaDaoException
	 */
	public void delete(int id) throws AquaDaoException;

}
