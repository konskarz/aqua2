package model;

import javax.swing.DefaultComboBoxModel;

import dao.AquaDaoException;
import dao.AquaDaoFactory;
import dao.DimensionenDao;


/**
 * ComboBoxModel Klasse für Dimensionen-Verwaltung 
 * @author Konstantin Karzanov
 * 28.08.2008
 */
public class DimensionenList extends DefaultComboBoxModel {
	
	private static final long serialVersionUID = -7888833300976707376L;
	private static DimensionenDao dao = null;


	public DimensionenList() throws AquaDaoException {
		super((dao = AquaDaoFactory.getInstance().getDimensionenDao()).read());
	}

	/**
	 * Dimensionen erstellen
	 * @return Dimensionen
	 */
	public Dimensionen create() {
		Dimensionen d = new Dimensionen();
		addElement(d);
		return d;
	}

	/**
	 * Dimensionen speichern
	 * @param object
	 * @throws AquaDaoException
	 */
	public void save(Object object) throws AquaDaoException {
		Dimensionen d = (Dimensionen)object;
		d.save(dao);
		setSelectedItem(object);
		fireContentsChanged(this, -1, -1);
	}

	/**
	 * Dimensionen entfernen
	 * @param index
	 * @throws AquaDaoException
	 * @throws IndexOutOfBoundsException
	 */
	public void delete(int index) throws AquaDaoException, IndexOutOfBoundsException {
		Dimensionen d = (Dimensionen) getElementAt(index);
		d.delete(dao);
		removeElementAt(index);
		if(getSize()==0)
			create();
	}

	/**
	 * Liefert Dimensionen id zum zeigen im Szenario
	 * @param dim_id
	 * @return index
	 */
	public int findId(int dim_id) {
		for (int i=0;i<getSize();i++) {
			Dimensionen d = (Dimensionen)getElementAt(i);
			if(d.checkID(dim_id))
				return i;
		}
		return 0;
	}

	/**
	 * Liefert Dimensionen id für speichern im Szenario
	 * @param selectedIndex
	 * @return int
	 * @throws AquaDaoException
	 */
	public int getId(int selectedIndex) throws AquaDaoException {
		Dimensionen d = (Dimensionen)getElementAt(selectedIndex);
		int dim_id = d.getId();
		if(dim_id==0)
			dim_id=d.save(dao);
		return dim_id;
	}
	
}
