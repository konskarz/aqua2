package dao;

import java.util.Vector;

/**
 * Interface für lesen und schreiben von Futterzeiten im Szenario
 * @author Konstantin Karzanov
 * 28.08.2008
 */
public interface SzenarioFutterzeitDao {
	
	/**
	 * Speichert neu Futterzeiten für bestimmte Szenario
	 * @param szen_id
	 * @param zeit
	 * @throws AquaDaoException
	 */
	public void create(int szen_id, Vector<String> zeit) throws AquaDaoException;
	
	/**
	 * Liest Futterzeiten für bestimmte Szenario
	 * @param szen_id
	 * @return Vector<String>
	 * @throws AquaDaoException
	 */
	public Vector<String> read(int szen_id) throws AquaDaoException;
	
	/**
	 * Löscht Futterzeiten für bestimmte Szenario
	 * @param szen_id
	 * @throws AquaDaoException
	 */
	public void delete(int szen_id) throws AquaDaoException;

}
