package model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LebewesenTyp extends Lebewesen {

	private String typ;

	public LebewesenTyp(String typName, int lid, int ltyp_id, int ltempwerte_id, 
			int lphwerte_id, String lname, String lphoto,String linfo, int lkantenAb) {
		super(lid, ltyp_id, ltempwerte_id, lphwerte_id, lname, lphoto, linfo,lkantenAb);
		typ = typName;
	}

	public LebewesenTyp(ResultSet rs) throws SQLException {
		super(rs);
		// select l.id, l.name, t.id, l.tempwerte_id, l.phwerte_id, l.info,
		// l.photo, l.kantenab, t.bezeichnung from lebewesen l join typen t on
		// l.typ_id = t.id";
		typ = rs.getString(super.columnCount() + 1);
	}

	public LebewesenTyp(LebewesenTyp l) {
		super(l);
		typ = l.typ;
	}

	public String nodeType() {
		return typ;
	}

	public boolean isTyp(Lebewesen l) {
		if (super.isTyp(l))
			return true;
		LebewesenTyp lt = (LebewesenTyp) l;
		return this.typ.equals(lt.typ);
	}

	public int columnCount() {
		return 9;
	}

	public String getInfo() {
		String ret = "Typ: " + typ + "\n";
		ret += super.getInfo();
		return ret;
	}
}
