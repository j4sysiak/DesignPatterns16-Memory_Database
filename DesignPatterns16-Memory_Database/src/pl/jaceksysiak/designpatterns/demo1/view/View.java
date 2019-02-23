package pl.jaceksysiak.designpatterns.demo1.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.Border;

import pl.jaceksysiak.designpatterns.demo1.model.Model;
import pl.jaceksysiak.designpatterns.demo1.model.Person;

public class View extends JFrame implements ActionListener, PeopleChangedListener {

	private static final long serialVersionUID = 1L;
	private Model model;
	private JButton okButton;
	private JTextField nameField;
	private JPasswordField passField;
	private JPasswordField repeatPassField;
	private JList<Person> userList;
	private DefaultListModel<Person> listModel;

	private CreateUserListener createUserListener;
	private SaveListener saveListener;
	private AppListener appListener;

	public View(Model model) {
		super("MVC Demo");

		this.model = model;

		nameField = new JTextField(10);
		passField = new JPasswordField(10);
		repeatPassField = new JPasswordField(10);
		okButton = new JButton("Create user");
		listModel = new DefaultListModel<Person>();
		userList = new JList<Person>(listModel);

		int margin = 15;
		Border outerBorder = BorderFactory.createEmptyBorder(margin, margin,
				margin, margin);
		Border innerBorder = BorderFactory.createEtchedBorder();
		userList.setBorder(BorderFactory.createCompoundBorder(outerBorder,
				innerBorder));

		setLayout(new GridBagLayout());

		GridBagConstraints gc = new GridBagConstraints();
		gc.anchor = GridBagConstraints.LAST_LINE_END;
		gc.gridx = 1;
		gc.gridy = 1;
		gc.weightx = 1;
		gc.weighty = 1;
		gc.insets = new Insets(100, 0, 0, 10);
		gc.fill = GridBagConstraints.NONE;

		add(new JLabel("Name: "), gc);

		gc.anchor = GridBagConstraints.LAST_LINE_START;
		gc.gridx = 2;
		gc.gridy = 1;
		gc.weightx = 1;
		gc.weighty = 1;
		gc.insets = new Insets(100, 0, 0, 0);
		gc.fill = GridBagConstraints.NONE;

		add(nameField, gc);

		gc.anchor = GridBagConstraints.LINE_END;
		gc.gridx = 1;
		gc.gridy = 2;
		gc.weightx = 1;
		gc.weighty = 1;
		gc.insets = new Insets(0, 0, 0, 10);
		gc.fill = GridBagConstraints.NONE;

		add(new JLabel("Password: "), gc);

		gc.anchor = GridBagConstraints.LINE_START;
		gc.gridx = 2;
		gc.gridy = 2;
		gc.weightx = 1;
		gc.weighty = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.fill = GridBagConstraints.NONE;

		add(passField, gc);

		gc.anchor = GridBagConstraints.LINE_END;
		gc.gridx = 1;
		gc.gridy = 3;
		gc.weightx = 1;
		gc.weighty = 1;
		gc.insets = new Insets(0, 0, 0, 10);
		gc.fill = GridBagConstraints.NONE;

		add(new JLabel("Repeat password: "), gc);

		gc.anchor = GridBagConstraints.LINE_START;
		gc.gridx = 2;
		gc.gridy = 3;
		gc.weightx = 1;
		gc.weighty = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.fill = GridBagConstraints.NONE;

		add(repeatPassField, gc);

		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.gridx = 2;
		gc.gridy = 4;
		gc.weightx = 1;
		gc.weighty = 100;
		gc.fill = GridBagConstraints.NONE;

		add(okButton, gc);

		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.gridx = 1;
		gc.gridy = 5;
		gc.weightx = 1;
		gc.weighty = 100;
		gc.gridwidth = 2;
		gc.fill = GridBagConstraints.HORIZONTAL;

		add(new JScrollPane(userList), gc);

		okButton.addActionListener(this);

		// Database db = new Database();
		// Database db = Database.getInstance();

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(WindowEvent e) {
				fireOpenEvent();
			}

			@Override
			public void windowClosing(WindowEvent e) {
				fireCloseEvent();
			}

		});

		JMenuBar menu = createMenu();
		setJMenuBar(menu);

		setSize(600, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String password = new String(passField.getPassword());
		String repeat = new String(repeatPassField.getPassword());

		if (password.equals(repeat)) {
			String name = nameField.getText();

			fireCreateUserEvent(new CreateUserEvent(name, password));
			
			nameField.setText("");
			passField.setText("");
			repeatPassField.setText("");
		} else {
			JOptionPane.showMessageDialog(this, "Passwords do not match.",
					"Error", JOptionPane.WARNING_MESSAGE);
		}
	}

	public void showError(String error) {
		JOptionPane.showMessageDialog(this, error, "Error",
				JOptionPane.WARNING_MESSAGE);
	}

	private JMenuBar createMenu() {

		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		JMenuItem saveItem = new JMenuItem("Save");
		saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				KeyEvent.CTRL_MASK));

		fileMenu.add(saveItem);

		menuBar.add(fileMenu);

		saveItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				fireSaveEvent();
			}
		});

		return menuBar;
	}

	public void setCreateUserListener(CreateUserListener loginListener) {
		this.createUserListener = loginListener;
	}

	public void setSaveListener(SaveListener saveListener) {
		this.saveListener = saveListener;
	}

	public void setAppListener(AppListener appListener) {
		this.appListener = appListener;
	}

	private void fireCreateUserEvent(CreateUserEvent event) {
		if (createUserListener != null) {
			createUserListener.onUserCreated(event);
		}
	}

	private void fireOpenEvent() {
		if (appListener != null) {
			appListener.onOpen();
		}
	}

	private void fireCloseEvent() {
		if (appListener != null) {
			appListener.onClose();
		}
	}

	private void fireSaveEvent() {
		if (saveListener != null) {
			saveListener.onSave();
		}
	}

	@Override
	public void onPeopleChanged() {
		/* 
		 * Some interpretations of MVC would force the view
		 * to be updated only via the controller, which would
		 * contact the database, get the data and tell the view
		 * to update itself (by calling a view method directly).
		 * Others, as here, have the view listening to the model 
		 * (but never telling it what to do).
		 */
		listModel.clear();

		List<Person> people = model.getPeople();
		
		for (Person person : people) {
			listModel.addElement(person);
		}
		
	}

}
