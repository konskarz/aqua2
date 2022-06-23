package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.AbstractListModel;

import dao.AquaDaoException;
import dao.AquaDaoFactory;
import dao.SzenarioFutterzeitDao;


/**
 * ListModell Klasse für Futterzeiten verwaltung
 * @author Konstantin Karzanov
 * 28.08.2008
 */
public class SzenarioFutterzeit extends AbstractListModel {

	private static final long serialVersionUID = -4823111994559081489L;
	public static final String FORMATPATTERN = "HH:mm";
	public static final SimpleDateFormat TIMEFORMAT = new SimpleDateFormat(FORMATPATTERN);
	public static final long PAUSE = 30*60*1000;
	public static final String DEFAULTZEIT = "04:00";
	private static SzenarioFutterzeitDao dao = null;
	private Vector<String> times = null;

	public SzenarioFutterzeit(int szen_id) throws AquaDaoException {
		dao=AquaDaoFactory.getInstance().getSzenarioFutterzeitDao();
		if(szen_id!=0)
			times = dao.read(szen_id);
		else {
			times = new Vector<String>();
			times.add(DEFAULTZEIT);
		}
	}

	public Object getElementAt(int index) {
		return times.get(index);
	}

	public int getSize() {
		return times.size();
	}

	/**
	 * Zeit einfügen, wenn der Zeit ist noch nicht in der Liste
	 * und zwischen Zeiten 30 minuten Pause gibt
	 * @param value
	 */
	public void add(Object value) {
		if (value instanceof Date) {
			String time = TIMEFORMAT.format((Date)value);
			if (checkTime(time)) {
				times.add(time);
				fireIntervalAdded(this,times.size()-1, times.size()-1);
			}
		}
	}

	/**
	 * Zeit entfernen
	 * @param index
	 */
	public void remove(int index) {
		times.remove(index);
		fireIntervalRemoved(this, index, index);
	}

	/**
	 * Liste speichern
	 * @param szen_id
	 * @throws AquaDaoException
	 */
	public void save(int szen_id) throws AquaDaoException {
		dao.create(szen_id, times);
	}

	/**
	 * Liste löschen
	 * @param szen_id
	 * @throws AquaDaoException
	 */
	public void delete(int szen_id) throws AquaDaoException {
		dao.delete(szen_id);
	}

	private boolean checkTime(String time) {
		try {
			long newTime = TIMEFORMAT.parse(time).getTime();
			Date newDate = new Date(newTime);
			for(String s : times) {
				long oldTime = TIMEFORMAT.parse(s).getTime();
				Date oldDate = new Date(oldTime);
				if(newDate.compareTo(oldDate)>=0 && newTime-oldTime<PAUSE
						|| newDate.compareTo(oldDate)<=0 && oldTime-newTime<PAUSE) {
					return false;
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
