/*******************************************************
	CSCD 327 RELATIONAL DATABASE SYSTEMS
					Project
			Student Name: Jake Mowatt
 *******************************************************/
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.CallableStatement;
import java.util.*;
import java.lang.String;
import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Query {

	private Connection conn;
	private PreparedStatement stmt;
	private ResultSet result;
	private Scanner scanner = new Scanner(System.in);

	public Query(Connection c) throws SQLException
	{
		conn = c;
	}

	public void query1() throws IOException, SQLException {
		// Take user input
		System.out.println("\nEnter the author id:");
		String authorID = scanner.nextLine();

		// Prepare the SQL statement to check if authorID exists
		String query  = "select * from author where authorID =?";
		stmt = conn.prepareStatement(query);

		// Replace the '?' in the above statement with the input author id
		stmt.setString(1, authorID);

		// Retrieve data with the query
		result = stmt.executeQuery();

		if(!result.next()) {
			System.out.println("authorID does not exist");
			return;
		}

		//User Input for first and last names
		System.out.println("\nEnter the author's first name:");
		String fName = scanner.nextLine();

		System.out.println("\nEnter the author's last name:");
		String lName = scanner.nextLine();

		// Prepare the SQL statement
		query  = "update author set firstName = ?, lastName = ? where authorID = ?";
		stmt = conn.prepareStatement(query);

		// Replace the '?'s in the above statement with the inputs
		stmt.setString(1, fName);
		stmt.setString(2, lName);
		stmt.setString(3, authorID);

		// Insert data with the query and print status
		if (stmt.executeUpdate() == 1) {
			System.out.println("\nData added successfully.");
		}
	}

	public void query2() throws IOException, SQLException {
		// Take user input
		System.out.println("\nEnter the author id:");
		String authorID = scanner.nextLine();

		// Prepare the SQL statement
		String query  = "delete from author where authorID = ?";
		stmt = conn.prepareStatement(query);

		// Replace the '?' in the above statement with the input book id
		stmt.setString(1, authorID);

		// Delete data with the query and print status
		if (stmt.executeUpdate() == 1) {
			System.out.println("\nData deleted successfully.");
		}
	}

	public void query3() throws IOException, SQLException {
		//Prepare the SQL statement
		String query = "select firstName, lastName from (select firstName, lastName, count(*) as bookCount from author natural join book_author group by authorID) as temp where bookCount > 1";
		stmt = conn.prepareStatement(query);

		// Retrieve data with the query
		result = stmt.executeQuery();

		// Print the retrieved data
		System.out.println("\nQuery output:");
		System.out.println("-------------");

		if(!result.next()) {
			System.out.println("No results exist for this input");
			return;
		}

		else
			do {
				String row = result.getString(1) + " " + result.getString(2);
				System.out.println(row);
			} while (result.next());
	}

	public void query4() throws IOException, SQLException {
		//Prepare the SQL statement
		String query = "select firstName, lastName from customer natural join orders group by customerID having avg(shipCost) = (select max(avg_cost) from (select avg(shipCost) as avg_cost from orders group by customerID) as CustomerAverages)";
		stmt = conn.prepareStatement(query);

		// Retrieve data with the query
		result = stmt.executeQuery();

		// Print the retrieved data
		System.out.println("\nQuery output:");
		System.out.println("-------------");

		if(!result.next()) {
			System.out.println("No results exist for this input");
			return;
		}

		else
			do {
				String row = result.getString(1) + " " + result.getString(2);
				System.out.println(row);
			} while (result.next());
	}

	public void query5() throws IOException, SQLException {
		// Take user input
		System.out.println("\nEnter the price bounds (i.e. 20 and 30)");
		String prices = scanner.nextLine();

		String cleanedPrices = prices.replaceAll("[^0-9 ]", "");
		String[] parts = cleanedPrices.trim().split("\\s+");
		int price1, price2;
		if (parts.length >= 2) {
			price1 = Integer.parseInt(parts[0]);
			price2 = Integer.parseInt(parts[1]);
		} else {
			System.out.println("\nInvalid price bounds");
			return;
		}

		// Prepare the SQL statement
		String query  = "select title, name from book natural join publisher where price between ? and ?";
		stmt = conn.prepareStatement(query);

		// Replace the '?' in the above statement with the input book id
		stmt.setInt(1, price1);
		stmt.setInt(2, price2);

		// Retrieve data with the query
		result = stmt.executeQuery();

		// Print the retrieved data
		System.out.println("\nQuery output:");
		System.out.println("-------------");

		if(!result.next()) {
			System.out.println("No results exist for this input");
			return;
		}

		else
			do {
				String row = result.getString(1) + "\t\t" + result.getString(2);
				System.out.println(row);
			} while (result.next());
	}

	public void query6() throws IOException, SQLException {
		// Take user input
		System.out.println("\nEnter an order id");
		int orderID = scanner.nextInt();

		// Prepare the SQL statement
		String query  = "select sum(price * quantity) + ifnull(shipCost, 0) as order_price from items natural join book natural join orders where orderID = ? group by orderID";
		stmt = conn.prepareStatement(query);

		// Replace the '?' in the above statement with the input book id
		stmt.setInt(1, orderID);

		// Retrieve data with the query
		result = stmt.executeQuery();

		// Print the retrieved data
		System.out.println("\nQuery output:");
		System.out.println("-------------");

		if(!result.next()) {
			System.out.println("No results exist for this input");
			return;
		}

		else
			do {
				String row = result.getString(1);
				System.out.println(row);
			} while (result.next());
	}

	public void query7() throws IOException, SQLException {
		// Prepare the SQL statement
		String query = "select region, count(*) as reg_orders from customer natural join orders group by region order by reg_orders desc limit 1";
		stmt = conn.prepareStatement(query);

		// Retrieve data with the query
		result = stmt.executeQuery();

		// Print the retrieved data
		System.out.println("\nQuery output:");
		System.out.println("-------------");

		if(!result.next()) {
			System.out.println("No results exist for this input");
			return;
		}

		else
			do {
				String row = result.getString(1);
				System.out.println(row);
			} while (result.next());
	}

	public void query8() throws IOException, SQLException {
		// Take user input
		System.out.println("\nEnter a customer's name (i.e. JOHN DOE)");
		String names = scanner.nextLine();
		String[] parts = names.trim().split("\\s+");
		String fName, lName;
		if (parts.length >= 2) {
			fName = parts[0];
			lName = parts[1];
		} else {
			System.out.println("\nInvalid Name Format");
			return;
		}

		// Prepare the SQL statement
		String query  = "select customerID, firstName, lastName, sum(price * quantity) as total from orders natural join items natural join book natural join customer where firstName = ? and lastName = ? group by customerID";
		stmt = conn.prepareStatement(query);

		// Replace the '?' in the above statement with the input book id
		stmt.setString(1, fName);
		stmt.setString(2, lName);

		// Retrieve data with the query
		result = stmt.executeQuery();

		// Print the retrieved data
		System.out.println("\nQuery output:");
		System.out.println("-------------");

		if(!result.next()) {
			System.out.println("No results exist for this input");
			return;
		}

		else
			do {
				String row = result.getString(4);
				System.out.println(row);
			} while (result.next());
	}

	public void query9() throws IOException, SQLException {
		// Prepare the SQL statement
		String query = "select distinct category, getAverage(category) as Avg_Price from book order by category asc";
		stmt = conn.prepareStatement(query);

		// Retrieve data with the query
		result = stmt.executeQuery();

		// Print the retrieved data
		System.out.println("\nQuery output:");
		System.out.println("-------------");

		if(!result.next()) {
			System.out.println("No results exist for this input");
			return;
		}

		else
			do {
				String row = result.getString(1) + "\t\t" + result.getString(2);
				System.out.println(row);
			} while (result.next());
	}

	public void query10() throws IOException, SQLException {
		// Prepare the SQL statement
		String query = "call listCoauthors()";
		stmt = conn.prepareStatement(query);

		// Retrieve data with the query
		result = stmt.executeQuery();

		// Print the retrieved data
		System.out.println("\nQuery output:");
		System.out.println("-------------");

		if(!result.next()) {
			System.out.println("No results exist for this input");
			return;
		}

		else
			do {
				String row = result.getString(3);
				System.out.println(row);
			} while (result.next());
	}
}