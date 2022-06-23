package dao;

import model.VonBisWerte;

public interface TempwerteDao {

	public VonBisWerte read( int id ) throws AquaDaoException;

}
