package dao;

public class AquaDaoMysqlTest {

	public static void main(String[] args) throws AquaDaoException {
		AquaDaoMysql dao = AquaDaoFactory.getInstance().getAquaDao();
		dao.test();
	}

}
