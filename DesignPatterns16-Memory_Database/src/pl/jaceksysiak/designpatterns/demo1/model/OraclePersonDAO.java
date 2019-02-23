package pl.jaceksysiak.designpatterns.demo1.model;

import java.sql.SQLException;
import java.util.List;

public class OraclePersonDAO implements PersonDAO {

	@Override
	public int addPerson(Person person) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Person getPerson(int id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Person> getPeople() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updatePerson(Person person) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deletePerson(int id) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteAll() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

}
