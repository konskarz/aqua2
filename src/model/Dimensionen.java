package model;

import dao.AquaDaoException;
import dao.DimensionenDao;

/**
 * Entitätsklasse für Dimensionen
 * @author Konstantin Karzanov
 * 28.08.2008
 */
public class Dimensionen {
	
	private int id = 0;
	private int[] values;
	private int[] defaults = {100,50,75,100*50*75/1000};
	
	public Dimensionen() {
		this.values = defaults.clone();
	}

	public Dimensionen(int id, int[] values) {
		this.id=id;
		this.values=values;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(values[0]);
		sb.append("x"+values[1]);
		sb.append("x"+values[2]);
		sb.append(" ("+values[3]+" Liter)");
		return sb.toString();
	}

	public int getId() {
		return id;
	}

	public int[] getValues() {
		return values;
	}

	public void setValues(Object l, Object b, Object h) throws NumberFormatException {
		values[0] = Integer.parseInt(l.toString());
		values[1] = Integer.parseInt(b.toString());
		values[2] = Integer.parseInt(h.toString());
		values[3] = values[0]*values[1]*values[2]/1000;
	}

	/**
	 * Dimensionen speichern. Bei neu Dimensionen id einstellen
	 * @param dao
	 * @return id
	 * @throws AquaDaoException
	 */
	public int save(DimensionenDao dao) throws AquaDaoException {
		if (id==0)
			this.id = dao.create(values);
		else
			dao.update(id, values);
		return id;
	}

	/**
	 * Dimensionen entfernen
	 * @param dao
	 * @throws AquaDaoException
	 */
	public void delete(DimensionenDao dao) throws AquaDaoException {
		dao.delete(id);
	}

	/**
	 * Überprüfen Dimensionen id zum zeigen im Szenario
	 * @param id
	 * @return boolean
	 */
	public boolean checkID(int id) {
		return this.id==id;
	}

}
