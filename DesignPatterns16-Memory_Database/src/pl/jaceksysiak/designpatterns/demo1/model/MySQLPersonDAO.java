package pl.jaceksysiak.designpatterns.demo1.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/*
 * One DAO class person table or view
 * CRUD - Create, retrieve, update, delete
 */

public class MySQLPersonDAO implements PersonDAO {

	/* (non-Javadoc)
	 * @see pl.jaceksysiak.designpatterns.demo1.model.PersonDAO#addPerson(pl.jaceksysiak.designpatterns.demo1.model.Person)
	 */
	@Override
	public int addPerson(Person person) throws SQLException {

		Connection conn = Database.getInstance().getConnection();

		PreparedStatement p = conn
				.prepareStatement("insert into people (name, password) values (?,?)");

		p.setString(1, person.getName());
		p.setString(2, person.getPassword());

		int updated = p.executeUpdate();

		p.close();

		return updated;
	}

	/* (non-Javadoc)
	 * @see pl.jaceksysiak.designpatterns.demo1.model.PersonDAO#getPerson(int)
	 */
	@Override
	public Person getPerson(int id) throws SQLException {
		Connection conn = Database.getInstance().getConnection();

		String sql = "select id, name, password from people where id=? order by id";
		PreparedStatement selectStatement = conn.prepareStatement(sql);
		
		selectStatement.setInt(1, id);

		ResultSet results = selectStatement.executeQuery();

		Person person = null;

		if (results.next()) {
			String name = results.getString("name");
			String password = results.getString("password");

			person = new Person(id, name, password);
		}

		results.close();
		selectStatement.close();

		return person;
	}

	/* (non-Javadoc)
	 * @see pl.jaceksysiak.designpatterns.demo1.model.PersonDAO#getPeople()
	 */
	@Override
	public List<Person> getPeople() throws SQLException {

		List<Person> people = new ArrayList<Person>();

		Connection conn = Database.getInstance().getConnection();

		String sql = "select id, name, password from people order by id";
		Statement selectStatement = conn.createStatement();

		ResultSet results = selectStatement.executeQuery(sql);

		while (results.next()) {
			int id = results.getInt("id");
			String name = results.getString("name");
			String password = results.getString("password");

			Person person = new Person(id, name, password);
			people.add(person);
		}

		results.close();
		selectStatement.close();

		return people;
	}

	/* (non-Javadoc)
	 * @see pl.jaceksysiak.designpatterns.demo1.model.PersonDAO#updatePerson(pl.jaceksysiak.designpatterns.demo1.model.Person)
	 */
	@Override
	public int updatePerson(Person person) throws SQLException {
		Connection conn = Database.getInstance().getConnection();

		PreparedStatement p = conn
				.prepareStatement("update people set name=?, password=? where id=?");

		p.setString(1, person.getName());
		p.setString(2, person.getPassword());
		p.setInt(3, person.getId());

		int updated = p.executeUpdate();

		p.close();
		
		return updated;
	}

	/* (non-Javadoc)
	 * @see pl.jaceksysiak.designpatterns.demo1.model.PersonDAO#deletePerson(int)
	 */
	@Override
	public int deletePerson(int id) throws SQLException {
		Connection conn = Database.getInstance().getConnection();

		PreparedStatement p = conn
				.prepareStatement("delete from people where id=?");

		p.setInt(1, id);

		int deleted = p.executeUpdate();

		p.close();
		
		return deleted;
	}
	
	/* (non-Javadoc)
	 * @see pl.jaceksysiak.designpatterns.demo1.model.PersonDAO#deleteAll()
	 */
	@Override
	public int deleteAll() throws SQLException {
		Connection conn = Database.getInstance().getConnection();

		PreparedStatement p = conn
				.prepareStatement("delete from people");

		int deleted = p.executeUpdate();

		p.close();
		
		return deleted;
	}
}
