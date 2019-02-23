package pl.jaceksysiak.designpatterns.demo1.model;

import java.sql.SQLException;
import java.util.List;

public interface PersonDAO {

	public int addPerson(Person person) throws SQLException;

	public Person getPerson(int id) throws SQLException;

	public List<Person> getPeople() throws SQLException;

	public int updatePerson(Person person) throws SQLException;

	public int deletePerson(int id) throws SQLException;

	public int deleteAll() throws SQLException;

}