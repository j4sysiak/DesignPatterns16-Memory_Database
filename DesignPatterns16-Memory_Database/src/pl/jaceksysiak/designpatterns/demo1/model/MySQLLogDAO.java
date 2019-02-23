package pl.jaceksysiak.designpatterns.demo1.model;

import java.util.List;

public class MySQLLogDAO implements LogDAO {
	
	/* (non-Javadoc)
	 * @see pl.jaceksysiak.designpatterns.demo1.model.LogDAO#addEntry(java.lang.String)
	 */
	@Override
	public void addEntry(String message) {
		// Not implemented.
		
		// Get current time and add log message to database.
	}
	
	/* (non-Javadoc)
	 * @see pl.jaceksysiak.designpatterns.demo1.model.LogDAO#getEntries(int)
	 */
	@Override
	public List<Log> getEntries(int number) {
		
		// Not implemented. Get latest "number" log messages.
		return null;
	}
	
	// Maybe no need for update or delete in this case.
}
