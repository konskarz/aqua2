package model;

import java.util.Date;

import javax.swing.DefaultComboBoxModel;

import dao.AquaDaoException;
import dao.AquaDaoFactory;
import dao.SzenarienDao;


/**
 * ComboBoxModel Klasse für Szenarien-Verwaltung
 * @author Konstantin Karzanov
 * 28.08.2008
 */
public class Szenarien extends DefaultComboBoxModel {
	
	private static final long serialVersionUID = -7888833300976707376L;
	private static SzenarienDao dao = null;

	public Szenarien() throws AquaDaoException {
		super((dao = AquaDaoFactory.getInstance().getSzenarienDao()).read());
	}

	/**
	 * Neu und bestehende Szenario speichern
	 * @param selectedIndex
	 * @param szen_name
	 * @param aqua_id
	 * @param tempMin
	 * @param tempMax
	 * @param phMin
	 * @param phMax
	 * @param lichtEin
	 * @param lichtAus
	 * @return id
	 * @throws AquaDaoException
	 */
	public int save(int selectedIndex, String szen_name, int aqua_id, double tempMin,
			double tempMax, double phMin, double phMax, Date lichtEin, Date lichtAus) 
			throws AquaDaoException {
		Szenario s;
		if (selectedIndex>=0 && selectedIndex<getSize()) {
			s = (Szenario) getElementAt(selectedIndex);
			s.set(szen_name,aqua_id,tempMin,tempMax,phMin,phMax,lichtEin,lichtAus);
			s.save(dao);
		}
		else {
			s = new Szenario(0,szen_name,null,tempMin,tempMax,phMin,phMax,lichtEin,lichtAus,aqua_id);
			s.save(dao);
			addElement(s);
		}
		return s.getId();
	}

	/**
	 * Szenario löschen
	 * @param index
	 * @return id
	 * @throws AquaDaoException
	 */
	public int delete(int index) throws AquaDaoException {
		Szenario s = (Szenario)getElementAt(index);
		int id = s.getId();
		s.delete(dao);
		removeElementAt(index);
		return id;
	}

	/**
	 * �berpr�ft ob Dimensionen in irgendwelche Szenario gespeichert sind
	 * und liefert Szenario-Name zurück
	 * @param dim_id
	 * @return name
	 */
	public String isInUse(int dim_id) {
		for(int i=0;i<this.getSize();i++) {
			Szenario s = (Szenario) this.getElementAt(i);
			String szenName = s.dim_idCheck(dim_id);
			if(szenName!=null)
				return szenName;
		}
		return null;
	}
	
}
