package pl.jaceksysiak.designpatterns.demo1.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pl.jaceksysiak.designpatterns.demo1.view.PeopleChangedListener;

public class Model {
	// Could use a sorted set here -- LinkedHashSet or TreeSet
	private Set<Person> people = new HashSet<Person>();
	private PeopleChangedListener peopleChangedListener;

	public List<Person> getPeople() {
		return new ArrayList<Person>(people);
	}

	public void addPerson(Person person) {
		people.add(person);
		fireDataChanged();
	}

	public void deletePerson(Person person) {
		people.remove(person);
		fireDataChanged();
	}

	public void save() throws Exception {

		/*
		 * Note: this very simple implementation takes no account of what would
		 * happen if multiple users were using this application. In this case,
		 * we'd probably want to think of stuff like stored procedures,
		 * transactions and so on, and how to handle the case where multiple
		 * users try to modify the same person record at the same time.
		 */

		DAOFactory factory = DAOFactory.getFactory(DAOFactory.MYSQL);
		PersonDAO personDAO = factory.getPersonDAO();

		for (Person person : people) {

			if (person.getId() != 0) {
				// If the person has an ID, the record must
				// already exist in the database, because we
				// get the IDs from the database autoincrement
				// ID column.
				personDAO.updatePerson(person);
			} else {
				personDAO.addPerson(new Person(person.getName(), person
						.getPassword()));
			}
		}
		
		// Load to get the latest IDs for any new people added.
		load();
		
		fireDataChanged();
	}

	public void load() throws Exception {
		DAOFactory factory = DAOFactory.getFactory(DAOFactory.MYSQL);
		PersonDAO personDAO = factory.getPersonDAO();
		
		people.clear();
		people.addAll(personDAO.getPeople());
		
		fireDataChanged();
	}

	public void setPeopleChangedListener(PeopleChangedListener peopleChangedListener) {
		this.peopleChangedListener = peopleChangedListener;
	}
	
	private void fireDataChanged() {
		if(peopleChangedListener != null) {
			peopleChangedListener.onPeopleChanged();
		}
	}

}
