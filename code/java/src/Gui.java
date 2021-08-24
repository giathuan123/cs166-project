package src;

import java.util.Date;
import java.util.Enumeration;
import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.MaskFormatter;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.awt.*;
import java.util.List;

public class Gui{

	private static JFrame mainFrame = null;
	private static JPanel childFrame = null;
	private static Color background = new Color(0xeeeeee);
	private static MechanicShop esql = null;
	static final JButton[] buttons = new JButton[10];
	static final JButton backButton = new JButton("Go Back");
	static final Color orButtonColor = backButton.getBackground();
	static final String[] promptLine = {
		"Add a customer",
		"Add a mechanic",
		"Add a car",
		"Insert a service request",
		"Close a service request",
		"<html><center>List date, comment, and bill for all <br>"
			+ "closed request with bill lower than 100</center><html>",
		"List customers with more than 20 cars",
		"<html><center>List Make, Model, and Year of all cars build before <br>1995 having less than 50000 miles</center></html>",
		"<html><center>List Make, model and number of service requests for the first " + 
			"<br>k cars with the highest number of service orders</center></html>",
		"<html><center>List the first name, last name and total bill of customers in descending " + 
			"<br>order of their total bill for all cars brought to the mechanic</center></html>"
	};
	private static void setUIFont(FontUIResource f){
		Enumeration<Object> keys = UIManager.getDefaults().keys();
		while(keys.hasMoreElements()){
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if( value instanceof FontUIResource)
				UIManager.put(key, f);
		}
	}
	private static void createMainFrame(){
		mainFrame  = new JFrame("Vroom Dealer's DBMS");
		mainFrame.setResizable(false);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(1000,700);
	}
	private static void addTableToDisplayPane(JPanel internalPanel, JScrollPane table){
		JPanel displayPanel =  (JPanel) internalPanel.getComponents()[1];
		displayPanel.removeAll();
		displayPanel.setLayout(new GridLayout(1,1));
		displayPanel.add(table); 
	}
	private static MaskFormatter createMaskFormatter(String fmt){
		MaskFormatter mFmt = null;
		try{
			mFmt = new MaskFormatter(fmt);
		}catch(Exception e){
			System.out.println(e.getMessage());
			System.out.println("Can't create format: " + fmt);
		}
		return mFmt;
	}
	// Option 1
	private static JPanel addCustomerPane(){
		JLabel mainLabel = new JLabel(promptLine[0]);
		mainLabel.setHorizontalAlignment(JLabel.CENTER);
		mainLabel.setVerticalAlignment(JLabel.CENTER);
		mainLabel.setFont(new Font("Courier", Font.PLAIN, 20));
		JPanel addCustomerPane = new JPanel();
		addCustomerPane.setLayout(new GridLayout(7, 1, 0, 15));
		addCustomerPane.setBorder(new EmptyBorder(25, 25, 25, 25));
		addCustomerPane.add(mainLabel);
		// First Name,
		JTextField firstName = new JTextField(32);
		firstName.setBorder(new TitledBorder("Enter first name"));
		firstName.setBackground(background);
		addCustomerPane.add(firstName);
		// Last Name
		JTextField lastName = new JTextField(32);
		lastName.setBorder(new TitledBorder("Enter last name"));
		lastName.setBackground(background);
		addCustomerPane.add(lastName);
		MaskFormatter phoneFmt = createMaskFormatter("(###)###-####");
		// Phone Number
		JFormattedTextField phoneNumber = new JFormattedTextField(phoneFmt);
		phoneNumber.setBorder(new TitledBorder("Enter phone number"));
		phoneNumber.setBackground(background);
		addCustomerPane.add(phoneNumber);
		// Address
		JTextArea address = new JTextArea();
		JScrollPane scroll = new JScrollPane(address);
		scroll.setBorder(BorderFactory.createEmptyBorder());
		address.setBorder(new TitledBorder("Enter address"));
		address.setBackground(background);
		addCustomerPane.add(scroll);

		// Submission Button
		JButton submissionButton = new JButton("Add customer");
		submissionButton.addActionListener((e)->{
				try{
				String firstNameStr = firstName.getText();
				String lastNameStr = lastName.getText();
				String phoneNumberStr = phoneNumber.getText();
				String addressStr = address.getText();
				if(firstNameStr.length() == 0 || firstNameStr.length() > 32) 
				throw new Exception("First name can't be empty and must have no more than 32 characters");
				if(lastNameStr.length() == 0 || lastNameStr.length() > 32) 
				throw new Exception("Last name can't be empty and must have no more than 32 characters");
				if(addressStr.length() == 0 || addressStr.length() > 256) 
				throw new Exception("Address can't be empty and must have no more than 256t words");
				String query = "INSERT INTO customer(fname, lname, phone, address) VALUES ('" 
				+ firstNameStr + "','" + lastNameStr + 
				"','" + phoneNumberStr + "','" + addressStr + "');";
				esql.executeUpdate(query);
				submissionButton.setBackground(Color.green);
				// NEED DBMS LOGIC
				Timer timer = new Timer(500, event->{
						firstName.setText("");
						lastName.setText("");
						phoneNumber.setText("");
						address.setText("");
						submissionButton.setBackground(orButtonColor);
						});
				timer.setRepeats(false);
				timer.start();
				System.out.println("[INFO] customer inserted: " + firstNameStr + "," + lastNameStr + ", " + phoneNumberStr + ", " + addressStr);
				}catch(Exception exc){
					submissionButton.setText(exc.getMessage());
					Timer timer2 = new Timer(500, event->{
							submissionButton.setText("Add mechanic");
							submissionButton.setBackground(orButtonColor);
							});
					timer2.setRepeats(false);
					timer2.start();
					System.out.println("[ERROR] customer not inserted: " + exc.getMessage());
					submissionButton.setBackground(Color.RED);
				}
		});
		addCustomerPane.add(submissionButton);

		return addCustomerPane;
	}
	// Option 2
	private static JPanel addMechanicPane(){
		JLabel mainLabel = new JLabel(promptLine[1]);
		mainLabel.setHorizontalAlignment(JLabel.CENTER);
		mainLabel.setVerticalAlignment(JLabel.CENTER);
		mainLabel.setFont(new Font("Courier", Font.PLAIN, 20));
		JPanel addMechanicPane = new JPanel();
		GridLayout gridLayout = new GridLayout(6, 1, 0 , 15);
		addMechanicPane.setLayout(gridLayout);
		addMechanicPane.setBorder(new EmptyBorder(25, 25, 25, 25));
		addMechanicPane.add(mainLabel);

		// First Name,
		JTextField firstName = new JTextField(32);
		firstName.setBorder(new TitledBorder("Enter first name"));
		firstName.setBackground(background);
		addMechanicPane.add(firstName);
		// Last Name
		JTextField lastName = new JTextField(32);
		lastName.setBorder(new TitledBorder("Enter last name"));
		lastName.setBackground(background);
		addMechanicPane.add(lastName);
		// Experience
		JTextField experience = new JTextField(5);
		experience.setBorder(new TitledBorder("Enter years of experience"));
		addMechanicPane.add(experience);
		// Submission Button
		JButton submissionButton = new JButton("Add mechanic");
		experience.setBackground(background);
		addMechanicPane.add(submissionButton);

		submissionButton.addActionListener((e)->{
				try{
				String firstNameStr = firstName.getText();
				String lastNameStr = lastName.getText();
				int experienceInt = Integer.parseInt((experience.getText().equals("") ? "0": experience.getText()));
				if(firstNameStr.length() == 0 || firstNameStr.length() > 32) 
				throw new Exception("First name can't be empty and must have no more than 32 characters");
				if(lastNameStr.length() == 0 || lastNameStr.length() > 32) 
				throw new Exception("Last name can't be empty and must have no more than 32 characters");
				if(experience.getText().length() == 0 || experience.getText().length() > 3 || experienceInt > 100 || experienceInt < 0) 
				throw new Exception("Mechanic experience must be within 0 to 100 years");
				submissionButton.setBackground(Color.green);
				// NEED DBMS LOGIC
				String query = "INSERT INTO mechanic(fname, lname, experience) VALUES ('" + 
				firstNameStr + "','" + lastNameStr + "'," + experienceInt + ");";
				esql.executeUpdate(query);
				Timer timer = new Timer(500, event->{
						firstName.setText("");
						lastName.setText("");
						experience.setText("");
						submissionButton.setBackground(orButtonColor);
						});
				timer.setRepeats(false);
				timer.start();
				System.out.println("[INFO] mechanic inserted: " + firstNameStr + "," + lastNameStr + ", " + experienceInt);
				}catch(Exception exc){
					System.out.println("[ERROR] mechanic not inserted: " + exc.getMessage());
					submissionButton.setText(exc.getMessage());
					submissionButton.setBackground(Color.RED);
					Timer timer2 = new Timer(900, event->{
							submissionButton.setText("Add mechanic");
							submissionButton.setBackground(orButtonColor);
							});
					timer2.setRepeats(false);
					timer2.start();
				}
		});

		return addMechanicPane;
	}
	// Option 3
	private static JPanel addCarPane(){
		JLabel mainLabel = new JLabel(promptLine[2]);
		mainLabel.setHorizontalAlignment(JLabel.CENTER);
		mainLabel.setVerticalAlignment(JLabel.CENTER);
		mainLabel.setFont(new Font("Courier", Font.PLAIN, 20));
		JPanel addCarPane = new JPanel();
		addCarPane.setLayout(new GridLayout(7, 1, 0, 15));
		addCarPane.setBorder(new EmptyBorder(25, 25, 25, 25));
		addCarPane.add(mainLabel);

		// VIN NUMBER,
		JTextField vinNumber = new JTextField(16);
		vinNumber.setBorder(new TitledBorder("Enter VIN number"));
		vinNumber.setBackground(background);
		addCarPane.add(vinNumber);
		// MAKE
		JTextField carMake = new JTextField(32);
		carMake.setBorder(new TitledBorder("Enter car make"));
		carMake.setBackground(background);
		addCarPane.add(carMake);
		// MODEL 
		JTextField carModel = new JTextField(32);
		carModel.setBorder(new TitledBorder("Enter car model"));
		carModel.setBackground(background);
		addCarPane.add(carModel);
		// YEAR
		JTextField carYear = new JTextField(4);
		carYear.setBorder(new TitledBorder("Enter car year"));
		carYear.setBackground(background);
		addCarPane.add(carYear);
		// SUBMISSION BUTTON
		JButton submissionButton = new JButton("Add car");
		submissionButton.addActionListener((e)->{
				try{
				String vinNumberStr = vinNumber.getText();
				String carMakeStr = carMake.getText();
				String carModelStr = carModel.getText();
				int carYearInt = Integer.parseInt((carYear.getText().equals("")?"0":carYear.getText()));
				if(vinNumberStr.length() != 16) 
				throw new Exception("VIN number must have 16 characters");
				if(carMakeStr.length() == 0 || carMakeStr.length() > 32) 
				throw new Exception("Car make can't be empty and no more than 32 characters");
				if(carModelStr.length() == 0 || carModelStr.length() > 32) 
				throw new Exception("Car model can't be empty and no more than 32 characters");
				if(carYear.getText().length() != 4 || carYearInt < 1970) 
				throw new Exception("Car year must be after 1970");
				// DBMS LOGIC
				String query = "INSERT INTO car(vin, make, model, year) VALUES ('" + 
				vinNumberStr + "','" + carMakeStr + "','" + 
				carModelStr + "'," + carYearInt + ");";
				esql.executeUpdate(query);
				// SUCCESS
				Timer timer = new Timer(500, event->{
						submissionButton.setBackground(orButtonColor);
						vinNumber.setText("");
						carMake.setText("");
						carModel.setText("");
						carYear.setText("");
						});
				timer.setRepeats(false);
				timer.start();
				System.out.println("[INFO] car inserted: " + vinNumberStr + "," + carMakeStr + ", " + carModelStr);
				}catch(Exception exc){
					System.out.println("[ERROR] car not inserted: " + exc.getMessage());
					submissionButton.setText(exc.getMessage());
					Timer timer2 = new Timer(500, event->{
							submissionButton.setText("Add car");
							submissionButton.setBackground(orButtonColor);
							});
					timer2.setRepeats(false);
					timer2.start();
					submissionButton.setBackground(Color.RED);
				}
		});

		addCarPane.add(submissionButton);

		return addCarPane;
	}
	// Option 4
	private static JPanel insertServiceRequestPane(){
		// main label
		JLabel mainLabel = new JLabel(promptLine[3]);
		mainLabel.setHorizontalAlignment(JLabel.CENTER);
		mainLabel.setVerticalAlignment(JLabel.CENTER);
		mainLabel.setFont(new Font("Courier", Font.PLAIN, 20));

		// main panel
		JPanel insertServiceRequestPane = new JPanel();
		insertServiceRequestPane.setLayout(new GridLayout(6, 1, 0, 15));
		insertServiceRequestPane.setBorder(new EmptyBorder(25, 25, 25, 25));
		insertServiceRequestPane.add(mainLabel);

		// Customer Last Name
		JTextField lastName = new JTextField(32);
		lastName.setBorder(BorderFactory.createTitledBorder("Enter customer's lastname"));
		lastName.setBackground(background);
		// Submission Button
		JButton addCustomerButton = new JButton("Add customer");
		addCustomerButton.addActionListener(new ButtonActionListener(0));
		JButton submissionButton = new JButton("Get customers");
		submissionButton.addActionListener((e)->{
				try{
				String lastNameStr = lastName.getText();
				if(esql.executeQueryAndReturnResult("SELECT id, fname, lname from customer where lname='"+lastNameStr+"';").size() > 0){
				String[] col = {"ID", "First Name", "Last Name"};
				JPanel customerListPane = displayResultPane("Select your customer");
				JScrollPane customerScrollPane = addTableQuery("SELECT id, fname, lname from customer where lname='"+lastNameStr+"';", col );
				JTable customerTable = (JTable) customerScrollPane.getViewport().getComponents()[0];
				customerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				JButton custButton = new JButton("Select customer");
				addTableToDisplayPane(customerListPane, customerScrollPane);
				custButton.addActionListener((event)->{
						try{
						String id = customerTable.getValueAt(customerTable.getSelectedRow(), 0).toString();
						JPanel carListPane = displayResultPane("Select your car");
						String[] carCol = {"VIN", "Make", "Model", "Year"};
						JScrollPane carScrollPane = addTableQuery("select vin, make, model, year from owns, car where customer_id="+id+"and car_vin=vin", carCol);
						JButton carButton = new JButton("Select car");
						JTable carTable = (JTable) carScrollPane.getViewport().getComponents()[0];
						carTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						addTableToDisplayPane(carListPane, carScrollPane);
						carListPane.add(backButton);
						carListPane.add(carButton);
						mainFrame.setContentPane(carListPane);
						mainFrame.revalidate();
						mainFrame.repaint();
						carButton.addActionListener((e2)->{
								String vin = (String) carTable.getValueAt(carTable.getSelectedRow(),0);
								JPanel servicePane = servicePane(vin, id);
								servicePane.add(backButton);
								mainFrame.setContentPane(servicePane);
								mainFrame.revalidate();
								mainFrame.repaint();
								});
						}catch(Exception exc){}
				});
				customerListPane.add(backButton);
				customerListPane.add(custButton);
				mainFrame.setContentPane(customerListPane);
				mainFrame.revalidate();
				mainFrame.repaint();
				}else{
					throw new Exception("Please create a new customer");
					} 
				}catch(Exception exc){
					System.out.println("[ERROR] no customer: " + exc.getMessage());
					submissionButton.setText(exc.getMessage());
					Timer timer2 = new Timer(500, e2->{
							submissionButton.setText("Select customer");
							submissionButton.setBackground(orButtonColor);
							});
					timer2.setRepeats(false);
					timer2.start();
					submissionButton.setBackground(Color.RED);
				}});
		// adding childrens
		insertServiceRequestPane.add(lastName);
		insertServiceRequestPane.add(addCustomerButton);
		insertServiceRequestPane.add(submissionButton);
		return insertServiceRequestPane;
	}
	private static JPanel servicePane(String vin, String cid){
		// main label
		JLabel innerLabel = new JLabel("Service Info");
		innerLabel.setHorizontalAlignment(JLabel.CENTER);
		innerLabel.setVerticalAlignment(JLabel.CENTER);
		innerLabel.setFont(new Font("Courier", Font.PLAIN, 20));

		// main panel
		JPanel servicePane = new JPanel();
		servicePane.setLayout(new GridLayout(8, 15));
		servicePane.setBorder(new EmptyBorder(25, 25, 25, 25));
		servicePane.add(innerLabel);

		// Date
		MaskFormatter dateFmt = createMaskFormatter("####-##-##");
		SimpleDateFormat dFormatter = new SimpleDateFormat("yyyy-MM-dd");
		Date dateNow = new Date(System.currentTimeMillis());
		dateFmt.setPlaceholder(dFormatter.format(dateNow));
		JFormattedTextField date = new JFormattedTextField(dateFmt);
		date.setBorder(BorderFactory.createTitledBorder("Enter date"));
		date.setBackground(background);
		// Mileage
		JTextField mileage = new JTextField(10);
		mileage.setBorder(BorderFactory.createTitledBorder("Enter mileage"));
		mileage.setBackground(background);
		// Complain 
		JTextArea complains = new JTextArea();
		JScrollPane scroll = new JScrollPane(complains);
		scroll.setBorder(BorderFactory.createEmptyBorder());
		complains.setBorder(new TitledBorder("Enter complains"));
		complains.setBackground(background);
		servicePane.add(scroll);
		// Submission Button
		JButton insertButton = new JButton("Add service request");
		insertButton.addActionListener((e)->{
				try{
				int mileageInt = Integer.parseInt(mileage.getText());
				String dateStr = date.getText();
				String complainStr = complains.getText();
				if(mileageInt < 0) 
				throw new Exception("Mileage can't be negative");
				String query = "INSERT INTO service_request(customer_id, car_vin, odometer, date, complain) VALUES (" 
				+ cid + ",'" + vin + "'," + mileageInt + ",'" + dateStr + "','" + complainStr + "');";
				esql.executeUpdate(query);
				insertButton.setBackground(Color.green);
				// NEED DBMS LOGIC
				Timer timer = new Timer(500, event->{
						date.setText("");
						mileage.setText("");
						complains.setText("");
						insertButton.setBackground(orButtonColor);
						});
				timer.setRepeats(false);
				timer.start();
				System.out.println("[INFO] service_request inserted: " + cid + "," + vin + ", " + mileageInt + ", " + dateStr + ", " + complainStr);
				}catch(Exception exc){
					System.out.println("[ERROR] service_request not inserted: " + exc.getMessage());
					insertButton.setText(exc.getMessage());
					Timer timer2 = new Timer(500, event->{
							insertButton.setText("Add service request");
							insertButton.setBackground(orButtonColor);
							});
					timer2.setRepeats(false);
					timer2.start();
					insertButton.setBackground(Color.RED);
				}
		});
		servicePane.add(date);
		servicePane.add(mileage);
		servicePane.add(complains);
		servicePane.add(insertButton);
		return servicePane;
	}
	// Option 5
	private static JPanel closeServiceRequestPane(){
		JLabel mainLabel = new JLabel(promptLine[4]);
		mainLabel.setHorizontalAlignment(JLabel.CENTER);
		mainLabel.setVerticalAlignment(JLabel.CENTER);
		mainLabel.setBackground(Color.RED);
		mainLabel.setFont(new Font("Courier", Font.PLAIN, 20));

		JPanel closeServiceRequestPane = new JPanel();
		closeServiceRequestPane.setLayout(new GridLayout(8, 1, 0, 25));
		closeServiceRequestPane.setBorder(new EmptyBorder(25, 25, 25, 25));
		closeServiceRequestPane.add(mainLabel);

		// request rid
		JTextField requestId = new JTextField(13);
		requestId.setBorder(BorderFactory.createTitledBorder("Enter request id"));
		requestId.setBackground(background);

		// mechanic sid
		JTextField mechanicId = new JTextField(13);
		mechanicId.setBorder(BorderFactory.createTitledBorder("Enter mechanic's employee id"));
		mechanicId.setBackground(background);
		// date
		MaskFormatter dateFmt = createMaskFormatter("####-##-##");
		SimpleDateFormat dFormatter = new SimpleDateFormat("yyyy-MM-dd");
		Date dateNow = new Date(System.currentTimeMillis());
		dateFmt.setPlaceholder(dFormatter.format(dateNow));
		JFormattedTextField date = new JFormattedTextField(dateFmt);
		date.setBorder(BorderFactory.createTitledBorder("Enter closing date"));
		date.setBackground(background);
		// comment
		JTextArea comment = new JTextArea();
		JScrollPane scroll = new JScrollPane(comment);
		scroll.setBorder(BorderFactory.createEmptyBorder());
		comment.setBorder(new TitledBorder("Enter comment"));
		comment.setBackground(background);
		// bill
		JTextField billing = new JTextField(10);
		billing.setBorder(BorderFactory.createTitledBorder("Enter billing"));
		billing.setBackground(background);
		// submission button
		JButton submissionButton = new JButton("Close service request");
		submissionButton.addActionListener(
				(e)->{
				try{
				String requestIdStr = requestId.getText();
				String mechanicIdStr = mechanicId.getText();
				String dateStr = date.getText();
				String commentStr = comment.getText();
				int billingInt = Integer.parseInt(billing.getText());
				String insertQuery = "INSERT INTO closed_request(rid, mid, date, bill, comment) VALUES (" + requestIdStr + "," + mechanicIdStr + ",'" + dateStr + "'," + billingInt + ",'" + commentStr + "');";
				esql.executeUpdate(insertQuery);
				submissionButton.setBackground(Color.green);
				// NEED DBMS LOGIC
				Timer timer = new Timer(800, event->{
						requestId.setText("");
						mechanicId.setText("");
						billing.setText("");
						comment.setText("");
						submissionButton.setBackground(orButtonColor);
						});
				timer.setRepeats(false);
				timer.start();
				System.out.println("[INFO] service_request close: " + requestIdStr + "," + mechanicIdStr + ",'" + dateStr + "'," + billingInt + ",'" + commentStr + "';");
				}catch(Exception exc){
					System.out.println("[ERROR] service_request not closed: " + exc.getMessage());
					submissionButton.setText(exc.getMessage());
					submissionButton.setBackground(Color.RED);
					Timer timer2 = new Timer(500, event->{
							submissionButton.setText("Close service request");
							submissionButton.setBackground(orButtonColor);
							});
					timer2.setRepeats(false);
					timer2.start();
				}
				});
		closeServiceRequestPane.add(requestId);
		closeServiceRequestPane.add(mechanicId);
		closeServiceRequestPane.add(date);
		closeServiceRequestPane.add(billing);
		closeServiceRequestPane.add(scroll);
		closeServiceRequestPane.add(submissionButton);
		return closeServiceRequestPane;
	}
	// Option 6,7
	private static JPanel displayResultPane(String title){
		// main label
		JLabel mainLabel = new JLabel(title);
		mainLabel.setHorizontalAlignment(JLabel.CENTER);
		mainLabel.setBackground(Color.RED);
		mainLabel.setVerticalAlignment(JLabel.CENTER);
		mainLabel.setFont(new Font("Courier", Font.PLAIN, 15));
		// parent display panel  
		JPanel displayResultPane = new JPanel();
		displayResultPane.setBorder(new EmptyBorder(25, 25, 25, 25));
		displayResultPane.setSize(450,400);
		displayResultPane.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		// middle display panel
		JPanel displayPanel = new JPanel();
		displayPanel.setBackground(Color.red);
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.weightx = 1;
		displayResultPane.add(mainLabel, constraints);
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weighty=1;
		constraints.weightx=1;
		constraints.fill = GridBagConstraints.BOTH;
		displayResultPane.add(displayPanel, constraints);
		return displayResultPane;
	}
	private static class ButtonActionListener implements ActionListener{
		private int page;
		public static JPanel[] internalPanel = null;
		public ButtonActionListener(int page){
			this.page = page;
		}

		@Override
			public void actionPerformed(ActionEvent e){
				if(internalPanel == null){
					System.out.println("[ButtonActionListener] internalPanel not initialized");
					return;
				}else{
					internalPanel[page].add(backButton);
					mainFrame.setContentPane(internalPanel[page]);
					mainFrame.revalidate();
					mainFrame.repaint();
				}
				return;
			}
	}
	private static void addChildFrame(){
		childFrame = new JPanel();
		childFrame.setBorder(new EmptyBorder(25, 25, 25, 25));
		childFrame.setLayout(new GridLayout(11, 1, 0, 15));
		JLabel label = new JLabel("Choose your option!");
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.CENTER);
		label.setFont(new Font("Courier", Font.PLAIN, 20));
		childFrame.add(label);

		for(int i = 0; i < buttons.length; i++) {
			buttons[i] = new JButton(promptLine[i]);
			buttons[i].setBounds(5, 5, 90, 200);
			childFrame.add(buttons[i]);
		}
		mainFrame.add(childFrame);
	}
	public static void initBackButton(){
		backButton.addActionListener(e->{
				mainFrame.setContentPane(childFrame);
				mainFrame.revalidate();
				mainFrame.repaint();
				});
		return;
	}

	public static void initPages(){
		for(int i = 0; i < buttons.length; i++){
			buttons[i].addActionListener(new ButtonActionListener(i));
		}
		return;
	}
	public static void initInternalPanes(){
		ButtonActionListener.internalPanel = new JPanel[]{
			addCustomerPane(), // option 1
				addMechanicPane(), // option 2
				addCarPane(),      // option 3
				insertServiceRequestPane(), // option 4
				closeServiceRequestPane(),  // option 5
				displayResultPane(promptLine[5]), // option 6
				displayResultPane(promptLine[6]), // option 7
				displayResultPane(promptLine[7]), // option 8
				displayResultPane(promptLine[8]), // option 9
				displayResultPane(promptLine[9]), // option 10
		};
	}
	public static JScrollPane addTableQuery(String query, String[] col) throws SQLException{
		List<List<String>> data = esql.executeQueryAndReturnResult(query);
		String[][] stringData = data.stream().map(l->l.toArray(new String[l.size()])).toArray(String[][]::new);
		JTable dataTable = new JTable(stringData, col);
		JScrollPane tablePane = new JScrollPane(dataTable);
		tablePane.setBorder(BorderFactory.createEmptyBorder());
		return tablePane;
	}
	public static void main(String[] args) {
		//Create and set up the window.
		try{
			String dbname = args[0];
			String dbport = args[1];
			String dbuser = args[2];
			esql = new MechanicShop(dbname, dbport, dbuser, "");
		}catch(SQLException e){

		}
		createMainFrame();
		initBackButton();
		addChildFrame();
		initInternalPanes();
		initKPane();
		initPages();
		// LISTING require injecting database response into gui
		buttons[5].addActionListener((e)->{
				try{
				addTableToDisplayPane(ButtonActionListener.internalPanel[5], addTableQuery("select date, comment, bill from closed_request where bill<100;", new String[] {"Date", "Comment", "Bill"}));
				}catch(SQLException exc){
				System.out.println(exc.getMessage());
				}
				});
		buttons[6].addActionListener((e)->{
				try{
				addTableToDisplayPane(ButtonActionListener.internalPanel[6], addTableQuery("select fname, lname, noCars from customer, (select customer_id, count(*) as noCars from owns group by customer_id having count(*) > 20) as o  where o.customer_id = id;", new String[] {"First name", "Last name","number of cars"}));
				}catch(SQLException exc){
				System.out.println(exc.getMessage());
				}
				});
		buttons[7].addActionListener((e)->{
				try{
				addTableToDisplayPane(ButtonActionListener.internalPanel[7], addTableQuery("select make, model, year, cur_miles from car C, (select car_vin, max(odometer) as cur_miles  from service_request group by car_vin) as cm where C.year < 1995 and C.vin = cm.car_vin and cur_miles < 50000;", new String[] {"Make", "Model", "Year", "Mileage"}));
				}catch(SQLException exc){
				System.out.println(exc.getMessage());
				}
				});
		buttons[8].addActionListener((e)->{
				try{
				addTableToDisplayPane(ButtonActionListener.internalPanel[8], addTableQuery("select distinct make, model, noService from car, (select car_vin, count(*) as noService from service_request group by car_vin) as cm where car_vin = vin order by noService desc limit 10;", new String[] {"Make", "Model","number of services"}));
				}catch(SQLException exc){
				System.out.println(exc.getMessage());
				}
				});
		buttons[9].addActionListener((e)->{
				try{
				addTableToDisplayPane(ButtonActionListener.internalPanel[9], addTableQuery("select fname, lname, totalBills from customer,(select sum(bill) as totalBills, customer_id from closed_request C, service_request S where C.rid = S.rid group by customer_id) as bills where customer_id = id order by totalBills desc;", new String[] {"First name", "Last Name","Total Bills"}));
				}catch(SQLException exc){
				System.out.println(exc.getMessage());
				}
				});
		setUIFont(new FontUIResource("Serif", Font.ITALIC, 12));
		mainFrame.setVisible(true);
	}
	private static void initKPane(){
		JTextField inputK = new JTextField("10");
		inputK.setBorder(BorderFactory.createTitledBorder("Enter k value"));
		inputK.setBackground(background);
		JButton inputButton = new JButton("Get Data");
		inputButton.addActionListener(event->{
				try{
				int k = Integer.parseInt(inputK.getText());
				addTableToDisplayPane(ButtonActionListener.internalPanel[8], addTableQuery("select distinct make, model, noService from car, (select car_vin, count(*) as noService from service_request group by car_vin) as cm where car_vin = vin order by noService desc limit "+k+";", new String[] {"Make", "Model","number of services"}));
				mainFrame.revalidate();
				mainFrame.repaint();
				Timer timer = new Timer(500, event2->{
						inputButton.setBackground(orButtonColor);
						});
				timer.setRepeats(false);
				timer.start();
				System.out.println("[INFO] k value query: " + k);
				}catch(Exception exc){
				System.out.println("[ERROR] k value error" + exc.getMessage());
				inputButton.setText(exc.getMessage());
				inputButton.setBackground(Color.RED);
				Timer timer2 = new Timer(500, event3->{
						inputButton.setText("Get Data");
						inputButton.setBackground(orButtonColor);
						});
				timer2.setRepeats(false);
				timer2.start();
				}
		});
		GridBagConstraints gc = new GridBagConstraints();
		gc.weightx = 0.1;
		gc.fill = GridBagConstraints.BOTH;
		ButtonActionListener.internalPanel[8].add(inputK, gc);
		gc.weightx = 0.1;
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.WEST;
		ButtonActionListener.internalPanel[8].add(inputButton, gc);
	}
}

