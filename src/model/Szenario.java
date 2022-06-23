package model;

import java.util.Date;

import dao.AquaDaoException;
import dao.SzenarienDao;


/**
 * Entitätsklasse für Szenario
 * @author Konstantin Karzanov
 * 28.08.2008
 */
public class Szenario {
	
	private int id;
	private String name;
	private String sname;
	private double tempMin;
	private double tempMax;
	private double phMin;
	private double phMax;
	private Date lichtEin;
	private Date lichtAus;
	private int dim_id;
	
	public Szenario(int id, String szenName, String shortName, double tempMin, double tempMax, 
			double phMin, double phMax, Date lichtEin, Date lichtAus, int dim_id) {
		this.id = id;
		this.name = szenName;
		this.sname = shortName;
		this.tempMin = tempMin;
		this.tempMax = tempMax;
		this.phMin = phMin;
		this.phMax = phMax;
		this.lichtEin = lichtEin;
		this.lichtAus = lichtAus;
		this.dim_id = dim_id;
	}

	public String toString() {
		return getName();
	}

	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getShortName() {
		return sname;
	}

	public double getTempMin() {
		return tempMin;
	}

	public double getTempMax() {
		return tempMax;
	}

	public double getPhMin() {
		return phMin;
	}

	public double getPhMax() {
		return phMax;
	}

	public Date getLichtEin() {
		return lichtEin;
	}

	public Date getLichtAus() {
		return lichtAus;
	}

	public int getDim_id() {
		return dim_id;
	}

	public void set(String szen_name, int aqua_id, double tempMin,
			double tempMax, double phMin, double phMax, Date lichtEin,
			Date lichtAus) {
		this.name = szen_name;
		this.sname = name.length()>6 ? name.substring(0,6) : name;
		this.tempMin = tempMin;
		this.tempMax = tempMax;
		this.phMin = phMin;
		this.phMax = phMax;
		this.lichtEin = lichtEin;
		this.lichtAus = lichtAus;
		this.dim_id = aqua_id;
	}

	/**
	 * Szenario speichern
	 * @param dao
	 * @throws AquaDaoException
	 */
	public void save(SzenarienDao dao) throws AquaDaoException {
		if(sname==null)
			sname = name.length()>6 ? name.substring(0,6) : name;
		if (id==0)
			this.id = dao.create(name, sname, tempMin, tempMax, phMin, phMax, lichtEin, lichtAus, dim_id);
		else
			dao.update(id, name, sname, tempMin, tempMax, phMin, phMax, lichtEin, lichtAus, dim_id);
	}

	/**
	 * Szenario entfernen
	 * @param dao
	 * @throws AquaDaoException
	 */
	public void delete(SzenarienDao dao) throws AquaDaoException {
		dao.delete(id);
	}

	/**
	 * �berpr�ft ob Dimensionen im Szenario gespeichert sind
	 * und wenn "ja" liefert Szenario Name zurück
	 * @param dim_id
	 * @return name
	 */
	public String dim_idCheck(int dim_id) {
		if(this.dim_id==dim_id)
			return this.name;
		return null;
	}

}
