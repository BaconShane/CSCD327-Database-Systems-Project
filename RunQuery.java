/*******************************************************
	CSCD 327 RELATIONAL DATABASE SYSTEMS
					Project
			Student Name: Jake Mowatt
 *******************************************************/
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.IOException;
import java.util.Scanner;

public class RunQuery {

	public static void main(String[] args) {

		int run = 1, queryNumber;
		Scanner scanner = new Scanner(System.in);

		try {

			Connection conn = getConnection();
			Query myquery = new Query(conn);

			System.out.println("Welcome!!!");

			while (run == 1) {

				System.out.println("\nChoose an option from the following Queries:");
				System.out.println("1. Update an author's name");
				System.out.println("2. Delete an author");
				System.out.println("3. List the names of the authors who have written multiple books");
				System.out.println("4. List the name(s) of the customer(s) whose orders incurred the highest average shipping cost");
				System.out.println("5. List the book titles in a specific price range with their publishers’ names");
				System.out.println("6. Find the total order value for an input order ID (including shipping cost)");
				System.out.println("7. Find the region(s) from which the maximum number of orders have been placed");
				System.out.println("8. Find the total price of books across all orders placed by a given customer");
				System.out.println("9. List the average price of books in each category in ascending order of category");
				System.out.println("10. Find all pairs of author names who have coauthored a book");
				System.out.println();
				
				queryNumber = scanner.nextInt();
				
				if (queryNumber == 1)
					// Run Query 1
					myquery.query1();

				else if (queryNumber == 2)
					// Run Query 2
					myquery.query2();

				else if (queryNumber == 3)
					// Run Query 3
					myquery.query3();

				else if (queryNumber == 4)
					// Run Query 4
					myquery.query4();

				else if (queryNumber == 5)
					// Run Query 5
					myquery.query5();

				else if (queryNumber == 6)
					// Run Query 6
					myquery.query6();

				else if (queryNumber == 7)
					// Run Query 7
					myquery.query7();

				else if (queryNumber == 8)
					// Run Query 8
					myquery.query8();

				else if (queryNumber == 9)
					// Run Query 9
					myquery.query9();

				else if (queryNumber == 10)
					// Run Query 10
					myquery.query10();

				else
					System.out.println("\nInvalid choice...");

				System.out.println("\nEnter 1 to CONTINUE, enter any other number to QUIT");
				run = scanner.nextInt();
			}
			
			System.out.println("\nExiting...Bye!");
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		scanner.close();
	}

	public static Connection getConnection() throws SQLException {
		
		// Create connection to the database
		Connection connection;
		String serverName = "10.219.0.50:3306";
		String database = "w26jmowatt1_projectDB"; 	// CHANGE THE USERNAME HERE TO TEST, LATER CHANGE THE DATABASE NAME FOR THE PROJECT
		String url = "jdbc:mysql://" + serverName + "/" + database;
		String username = "w26jmowatt1"; 		// CHANGE THE USERNAME HERE
		String password = ""; 		// CHANGE THE PASSWORD HERE
		connection = DriverManager.getConnection(url, username, password);
		return connection;
	}
}