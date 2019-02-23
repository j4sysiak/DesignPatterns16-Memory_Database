package pl.jaceksysiak.designpatterns.demo1.model;

public abstract class DAOFactory {
	
	public static final int MYSQL = 0;
	public static final int ORACLE = 1;

	public abstract PersonDAO getPersonDAO();

	public abstract LogDAO getLogDAO();
	
	public static DAOFactory getFactory(int type) {
		switch(type) {
		case MYSQL:
			return new MySQLDAOFactory();
		case ORACLE:
			return new OracleDAOFactory();
		default:
			return null;
		}
	}

}