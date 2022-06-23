package dao;

import java.util.Vector;

import model.Dimensionen;


/**
 * Interface für lesen und schreiben Dimensionen
 * 
 * @author Konstantin Karzanov
 * 28.08.2008
 */
public interface DimensionenDao {

	/**
	 * Speichert neu Dimensionen
	 * 
	 * @param values
	 * @return int
	 * @throws AquaDaoException
	 */
	public int create(int[] values) throws AquaDaoException;
	
	/**
	 * Liest Dimensionen
	 * @return Vector<Dimensionen>
	 * @throws AquaDaoException
	 */
	public Vector<Dimensionen> read() throws AquaDaoException;
	
	/**
	 * Speichert neu Werte für bestimte Dimensionen
	 * @param id
	 * @param values
	 * @throws AquaDaoException
	 */
	public void update(int id, int[] values) throws AquaDaoException;
	
	/**
	 * Löscht bestimte Dimensionen.
	 * @param id
	 * @throws AquaDaoException
	 */
	public void delete(int id) throws AquaDaoException;

}
