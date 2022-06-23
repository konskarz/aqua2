package model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LebewesenTypSzenario extends LebewesenTyp {

	@SuppressWarnings("unused")
	private int SzenarioId;
	private int Menge;

	public LebewesenTypSzenario(int sid, String typName, int lid, int ltyp_id,
			int ltempwerte_id, int lphwerte_id, String lname, String lphoto, String linfo, int lkantenAb) {
		super(typName, lid, ltyp_id, ltempwerte_id, lphwerte_id, lname, lphoto, linfo, lkantenAb);
		SzenarioId = sid;
		Menge = 1;
	}

	public LebewesenTypSzenario(int sid, ResultSet rs) throws SQLException {
		super(rs);
		Menge = rs.getInt(this.columnCount() + 1);
		SzenarioId = sid;
	}

	public LebewesenTypSzenario(LebewesenTyp l, int szenarioId, int menge) {
		super(l);
		Menge = menge;
		SzenarioId = szenarioId;
	}

	public String nodeName() {
		return super.nodeName() + " (" + Menge + ")";
	}

	public void updateMenge(int menge) {
		Menge += menge;
	}

	public String getInfo() {
		String ret = super.getInfo();
		ret += "Menge: " + Menge + "\n";
		return ret;
	}

	public String toString() {
		return this.nodeName();
	}

}
