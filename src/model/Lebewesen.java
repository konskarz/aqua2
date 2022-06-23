package model;

import java.sql.ResultSet;
import java.sql.SQLException;

import dao.AquaDaoException;
import dao.AquaDaoFactory;
import dao.PhwerteDao;
import dao.TempwerteDao;


public class Lebewesen {
	
	private int id;
	private int typ_id;
	private int tempwerte_id;
	private int phwerte_id;
	private String name;
	private String photo;
	private String info;
	private int kantenAb;

	public Lebewesen(ResultSet rs) throws SQLException {
		int columnIndex = 1;
		id = rs.getInt(columnIndex++);
		name = rs.getString(columnIndex++);
		typ_id = rs.getInt(columnIndex++);
		tempwerte_id = rs.getInt(columnIndex++);
		phwerte_id = rs.getInt(columnIndex++);
		info = rs.getString(columnIndex++);
		photo = rs.getString(columnIndex++);
		kantenAb = rs.getInt(columnIndex++);
	}

	public int columnCount() {
		return 8;
	}

	public Lebewesen(int lid, int ltyp_id, int ltempwerte_id, int lphwerte_id,
			String lname, String lphoto, String linfo, int lkantenAb) {
		id = lid;
		typ_id = ltyp_id;
		tempwerte_id = ltempwerte_id;
		phwerte_id = lphwerte_id;
		name = lname;
		photo = lphoto;
		info = linfo;
		kantenAb = lkantenAb;
	}

	public Lebewesen(Lebewesen l) {
		id = l.id;
		typ_id = l.typ_id;
		tempwerte_id = l.tempwerte_id;
		phwerte_id = l.phwerte_id;
		name = l.name;
		photo = l.photo;
		info = l.info;
		kantenAb = l.kantenAb;
	}

	public String nodeName() {
		return name;
	}

	public String toString() {
		return this.nodeName();
	}

	public LebewesenNode BuildNode(boolean isLeaf) {
		LebewesenNode n = new LebewesenNode(this, isLeaf);
		return n;
	}

	public boolean isTyp(Lebewesen l) {
		return this.typ_id == l.typ_id;
	}

	public String nodeType() {
		return "";
	}

	public int getId() {
		return id;
	}

	public void updateMenge(int menge) {}

	public String getInfo() {
		String ret = "Name: " + name + "\n";
		ret += "Photo: " + (photo != null ? photo : "<kein Photo>") + "\n";
		ret += "Kantenlänge ab: " + kantenAb + "\n";

		try {
			TempwerteDao data = AquaDaoFactory.getInstance().getTempwerteDao();
			VonBisWerte v = data.read(this.tempwerte_id);
			if (v != null)
				ret += v.toString("Temperaturwerte") + "\n";
		} catch (AquaDaoException e) {
		}
		try {
			PhwerteDao data = AquaDaoFactory.getInstance().getPhwerteDao();
			VonBisWerte v = data.read(this.phwerte_id);
			if (v != null)
				ret += v.toString("PH Werte") + "\n";
		} catch (AquaDaoException e) {
		}

		if (info != null)
			ret += info + "\n";
		return ret;
	}

}
