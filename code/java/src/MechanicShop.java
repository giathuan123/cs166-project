/*
 * Template JAVA User Interface
 * =============================
 *
 * Database Management Systems
 * Department of Computer Science &amp; Engineering
 * University of California - Riverside
 *
 * Target DBMS: 'Postgres'
 *
 */

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;

/**
 * This class defines a simple embedded SQL utility class that is designed to
 * work with PostgreSQL JDBC drivers.
 *
 */

public class MechanicShop{
	//reference to physical database connection
	private Connection _connection = null;
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	
	public MechanicShop(String dbname, String dbport, String user, String passwd) throws SQLException {
		System.out.print("Connecting to database...");
		try{
			// constructs the connection URL
			String url = "jdbc:postgresql://localhost:" + dbport + "/" + dbname;
			System.out.println ("Connection URL: " + url + "\n");
			
			// obtain a physical connection
	        this._connection = DriverManager.getConnection(url, user, passwd);
	        System.out.println("Done");
		}catch(Exception e){
			System.err.println("Error - Unable to Connect to Database: " + e.getMessage());
	        System.out.println("Make sure you started postgres on this machine");
	        System.exit(-1);
		}
	}
	
	/**
	 * Method to execute an update SQL statement.  Update SQL instructions
	 * includes CREATE, INSERT, UPDATE, DELETE, and DROP.
	 * 
	 * @param sql the input SQL string
	 * @throws java.sql.SQLException when update failed
	 * */
	public void executeUpdate (String sql) throws SQLException { 
		// creates a statement object
		Statement stmt = this._connection.createStatement ();

		// issues the update instruction
		stmt.executeUpdate (sql);

		// close the instruction
	    stmt.close ();
	}//end executeUpdate

	/**
	 * Method to execute an input query SQL instruction (i.e. SELECT).  This
	 * method issues the query to the DBMS and outputs the results to
	 * standard out.
	 * 
	 * @param query the input query string
	 * @return the number of rows returned
	 * @throws java.sql.SQLException when failed to execute the query
	 */
	public int executeQueryAndPrintResult (String query) throws SQLException {
		//creates a statement object
		Statement stmt = this._connection.createStatement ();

		//issues the query instruction
		ResultSet rs = stmt.executeQuery (query);

		/*
		 *  obtains the metadata object for the returned result set.  The metadata
		 *  contains row and column info.
		 */
		ResultSetMetaData rsmd = rs.getMetaData ();
		int numCol = rsmd.getColumnCount ();
		int rowCount = 0;
		
		//iterates through the result set and output them to standard out.
		boolean outputHeader = true;
		while (rs.next()){
			if(outputHeader){
				for(int i = 1; i <= numCol; i++){
					System.out.print(rsmd.getColumnName(i) + "\t\t");
			    }
			    System.out.println();
			    outputHeader = false;
			}
			for (int i=1; i<=numCol; ++i){
				System.out.print (rs.getString (i).trim() + "\t\t");
      }
			System.out.println ();
			++rowCount;
		}//end while
		stmt.close ();
		return rowCount;
	}
	
	/**
	 * Method to execute an input query SQL instruction (i.e. SELECT).  This
	 * method issues the query to the DBMS and returns the results as
	 * a list of records. Each record in turn is a list of attribute values
	 * 
	 * @param query the input query string
	 * @return the query result as a list of records
	 * @throws java.sql.SQLException when failed to execute the query
	 */
	public List<List<String>> executeQueryAndReturnResult (String query) throws SQLException { 
		//creates a statement object 
		Statement stmt = this._connection.createStatement (); 
		
		//issues the query instruction 
		ResultSet rs = stmt.executeQuery (query); 
	 
		/*
		 * obtains the metadata object for the returned result set.  The metadata 
		 * contains row and column info. 
		*/ 
		ResultSetMetaData rsmd = rs.getMetaData (); 
		int numCol = rsmd.getColumnCount (); 
	 
		//iterates through the result set and saves the data returned by the query. 
		List<List<String>> result  = new ArrayList<List<String>>(); 
		while (rs.next()){
			List<String> record = new ArrayList<String>(); 
			for (int i=1; i<=numCol; ++i) 
				record.add(rs.getString (i)); 
			result.add(record); 
		}//end while 
		stmt.close (); 
		return result; 
	}//end executeQueryAndReturnResult
	
	/**
	 * Method to execute an input query SQL instruction (i.e. SELECT).  This
	 * method issues the query to the DBMS and returns the number of results
	 * 
	 * @param query the input query string
	 * @return the number of rows returned
	 * @throws java.sql.SQLException when failed to execute the query
	 */
	public int executeQuery (String query) throws SQLException {
		//creates a statement object
		Statement stmt = this._connection.createStatement ();

		//issues the query instruction
		ResultSet rs = stmt.executeQuery (query);

		int rowCount = 0;

		//iterates through the result set and count nuber of results.
		while(rs.next()){
			rowCount++;
		}//end while
		stmt.close ();
		return rowCount;
	}
	
	/**
	 * Method to fetch the last value from sequence. This
	 * method issues the query to the DBMS and returns the current 
	 * value of sequence used for autogenerated keys
	 * 
	 * @param sequence name of the DB sequence
	 * @return current value of a sequence
	 * @throws java.sql.SQLException when failed to execute the query
	 */
	
	public int getCurrSeqVal(String sequence) throws SQLException {
		Statement stmt = this._connection.createStatement ();
		
		ResultSet rs = stmt.executeQuery (String.format("Select currval('%s')", sequence));
		if (rs.next()) return rs.getInt(1);
		return -1;
	}

	/**
	 * Method to close the physical connection if it is open.
	 */
	public void cleanup(){
		try{
			if (this._connection != null){
				this._connection.close ();
			}//end if
		}catch (SQLException e){
	         // ignored.
		}//end try
	}//end cleanup

	/**
	 * The main execution method
	 * 
	 * @param args the command line arguments this inclues the <mysql|pgsql> <login file>
	 */
	public static void main (String[] args) {
		if (args.length != 3) {
			System.err.println (
				"Usage: " + "java [-classpath <classpath>] " + MechanicShop.class.getName () +
		            " <dbname> <port> <user>");
			return;
		}//end if
		
		MechanicShop esql = null;
		
		try{
			System.out.println("(1)");
			
			try {
				Class.forName("org.postgresql.Driver");
			}catch(Exception e){

				System.out.println("Where is your PostgreSQL JDBC Driver? " + "Include in your library path!");
				e.printStackTrace();
				return;
			}
			
			System.out.println("(2)");
			String dbname = args[0];
			String dbport = args[1];
			String user = args[2];
			
			esql = new MechanicShop (dbname, dbport, user, "");
			
			boolean keepon = true;
			while(keepon){
				System.out.println("MAIN MENU");
				System.out.println("---------");
				System.out.println("1. AddCustomer");
				System.out.println("2. AddMechanic");
				System.out.println("3. AddCar");
				System.out.println("4. InsertServiceRequest");
				System.out.println("5. CloseServiceRequest");
				System.out.println("6. ListCustomersWithBillLessThan100");
				System.out.println("7. ListCustomersWithMoreThan20Cars");
				System.out.println("8. ListCarsBefore1995With50000Milles");
				System.out.println("9. ListKCarsWithTheMostServices");
				System.out.println("10. ListCustomersInDescendingOrderOfTheirTotalBill");
				System.out.println("11. < EXIT");
				
				/*
				 * FOLLOW THE SPECIFICATION IN THE PROJECT DESCRIPTION
				 */
				switch (readChoice()){
					case 1: AddCustomer(esql); break;
					case 2: AddMechanic(esql); break;
					case 3: AddCar(esql); break;
					case 4: InsertServiceRequest(esql); break;
					case 5: CloseServiceRequest(esql); break;
					case 6: ListCustomersWithBillLessThan100(esql); break;
					case 7: ListCustomersWithMoreThan20Cars(esql); break;
					case 8: ListCarsBefore1995With50000Milles(esql); break;
					case 9: ListKCarsWithTheMostServices(esql); break;
					case 10: ListCustomersInDescendingOrderOfTheirTotalBill(esql); break;
					case 11: keepon = false; break;
				}
			}
		}catch(Exception e){
			System.err.println (e.getMessage ());
		}finally{
			try{
				if(esql != null) {
					System.out.print("Disconnecting from database...");
					esql.cleanup ();
					System.out.println("Done\n\nBye !");
				}//end if				
			}catch(Exception e){
				// ignored.
			}
		}
	}

	public static int readChoice() {
		int input;
		// returns only if a correct value is given.
		do {
			System.out.print("Please make your choice: ");
			try { // read the integer, parse it and break.
				input = Integer.parseInt(in.readLine());
				break;
			}catch (Exception e) {
				System.out.println("Your input is invalid!");
				continue;
			}//end try
		}while (true);
		return input;
	}//end readChoice
	
  private static String getStringInput(String prompt, int bounds, boolean checkDigits) throws Exception{
    System.out.print(prompt); 
    String answer = in.readLine();
    if(checkDigits){
      try{
        Integer.parseInt(answer);
      }catch(NumberFormatException e){
        System.out.println(e);
        throw new Exception("String not numeric");
      }
    } 
    if(answer.length() > bounds) throw new Exception("Exceed length");
    return answer;
  }
	public static void AddCustomer(MechanicShop esql){//1
      try{
        int insertRow = esql.executeQuery("SELECT * FROM customer;");
        String firstName = getStringInput("Please enter your first name: ", 32, false);
        String lastName = getStringInput("Please enter your last name: ", 32, false);
        String phoneNumber = getStringInput("Please enter your phone: ", 13, true);
        String address = getStringInput("Please enter your address: ", 256, false);
        String query = "INSERT INTO customer(id, fname, lname, phone, address) VALUES (" + insertRow + ",'" + firstName + "','" + lastName + "','" + phoneNumber + "','" + address + "');";
        esql.executeUpdate(query);
      }catch(Exception e){
        System.out.println(e.getMessage());
        //ignored
      }
	}
	
	public static void AddMechanic(MechanicShop esql){//2
		try{
        int insertRow = esql.executeQuery("SELECT * FROM mechanic;");
        String firstName = getStringInput("Please enter mechanic's first name: ", 32, false);
        String lastName = getStringInput("Please enter mechanic's last name: ", 32, false);
        int experience = Integer.parseInt(getStringInput("Please enter years of experience: ", 13, true));
        String query = "INSERT INTO mechanic(id, fname, lname, experience) VALUES ('" + insertRow + "','" + firstName + "','" + lastName + "'," + experience + ";";
        esql.executeUpdate(query);
      }catch(Exception e){
        System.out.println(e.getMessage());
        //ignored
      }
	}
	
	public static void AddCar(MechanicShop esql){//3
		try{
        String carVin = getStringInput("Please enter car VIN: ", 32, false);
        String carMake = getStringInput("Please enter car make: ", 32, false);
        String carModel = getStringInput("Please enter car model", 32, false);
        int carYear = Integer.parseInt(getStringInput("Please enter years: ", 13, true));
        String query = "INSERT INTO car(vin, make, model, year) VALUES ('" + carVin + "','" + carMake + "','" + carModel + "'," + carYear + ";";
        esql.executeUpdate(query);
      }catch(Exception e){
        System.out.println(e.getMessage());
        //ignored
      }
	}
	
	public static void InsertServiceRequest(MechanicShop esql){//4
	   try{
       String customerLastName = getStringInput("Please enter customer last name: ", 32, false);
       String query = "SELECT id, fname, lname from customer where lname='"+customerLastName + "';";
       List<List<String>> result = esql.executeQueryAndReturnResult(query);
       System.out.printf("%20s%5s%25s%25s\n", "Selection", "ID", "First Name", "Last Name");
       for(int rows = 0; rows < result.size(); rows++){
         List<String> r = result.get(rows);
         String customerId = r.get(0);
         String firstName = r.get(1);
         String lastName = r.get(2);
         System.out.printf("%20s%5s%25s%25s\n", Integer.toString(rows), customerId, firstName.trim(), lastName.trim());
       }
       System.out.println();
       int selectionNo = Integer.parseInt(getStringInput("Please enter selection number: ", 10, true));
       String customer_id = result.get(selectionNo).get(0);

       String queryCustomerCar = "select make, model, year, vin from owns, car where customer_id="+ customer_id + "and car_vin = vin";
       List<List<String>> resultCar = esql.executeQueryAndReturnResult(queryCustomerCar);
       System.out.printf("%20s%15s%15s%10s%25s\n", "Selection", "Make", "Model", "Year", "VIN");
       for(int rows = 0; rows < resultCar.size(); rows++){
         List<String> r = resultCar.get(rows);
         String carMake = r.get(0);
         String carModel = r.get(1);
         String carYear = r.get(2);
         String carVin = r.get(3);
         System.out.printf("%20s%15s%15s%10s%25s\n", Integer.toString(rows), carMake, carModel, carYear, carVin);
       }
       System.out.println();
       int carSelectionNo = Integer.parseInt(getStringInput("Please enter car selection number: ", 10, true));

       String carVin = resultCar.get(carSelectionNo).get(3);
       String mileage = getStringInput("Please enter mileage: ", 10, true);
       String complain = getStringInput("Please enter problem: ", 256, false);
       System.out.println("Inserting Service Request");
       System.out.println("Car Vin: " + carVin);
       System.out.println("Customer ID: " + customer_id);
       System.out.println("Mileage: " + mileage);
       System.out.println("Complain: " + complain);
     }catch(Exception e){
        System.out.println(e.getMessage());
     }	
	}
	
	public static void CloseServiceRequest(MechanicShop esql) throws Exception{//5
		
	}
	
	public static void ListCustomersWithBillLessThan100(MechanicShop esql){//6
		
	}
	
	public static void ListCustomersWithMoreThan20Cars(MechanicShop esql){//7
		
	}
	
	public static void ListCarsBefore1995With50000Milles(MechanicShop esql){//8
		
	}
	
	public static void ListKCarsWithTheMostServices(MechanicShop esql){//9
		//
		
	}
	
	public static void ListCustomersInDescendingOrderOfTheirTotalBill(MechanicShop esql){//9
		//
		
	}
	
}
