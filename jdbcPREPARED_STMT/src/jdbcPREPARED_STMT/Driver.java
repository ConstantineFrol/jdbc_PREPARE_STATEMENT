/**
 * 
 */
package jdbcPREPARED_STMT;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



/**
 * @author 		Konstantin Frolov
 * Student No.	R00144177
 * email		konstantin.frolov@mycit.ie
 */
public class Driver {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws SQLException{
		 
		String url = "jdbc:mysql://localhost:3306/demo1?autoReconnect=true&useSSL=false";
		String user = "root";
		String password = "root";
		
		Connection myConnection = null;
		PreparedStatement myStatement = null;
		ResultSet myRs = null;
		
		
		try {
			
			// 1. Get Connection to database
			myConnection = DriverManager.getConnection(url, user, password);
			
			// 2. Prepare Statement
			myStatement = myConnection.prepareStatement("SELECT * FROM employees WHERE salary > ? AND department = ?");
			
			// 3. Set the Parameters
			myStatement.setDouble(1, 80000);
			myStatement.setString(2, "Legal");
			
			// 4. Execute SQL query
			myRs = myStatement.executeQuery();
			
			// 5. Display the result set		
			 while (myRs.next()) {
		            String lName = myRs.getString("last_name");
		            String fName = myRs.getString("first_name");
		            String mail = myRs.getString("email");
		            String depart = myRs.getString("department");
		            double salary = myRs.getDouble("salary");
		            System.out.println(fName + "\t" + lName +
		                               "\t" + mail + "\t" + depart +
		                               "\t" + salary);
		        }

			// 6. Reuse the prepare statement: salary > 25000, department = HR
				myStatement.setDouble(1, 25000);
				myStatement.setString(2, "HR");
			
			// 7. Execute SQL query
				myRs = myStatement.executeQuery();	
			// 8. Display the reused result set
				
				System.out.println("Reused Statement:\n");
				 while (myRs.next()) {
			            String lName = myRs.getString("last_name");
			            String fName = myRs.getString("first_name");
			            String depart = myRs.getString("department");
			            double salary = myRs.getDouble("salary");
			            System.out.println(fName + "\t" + lName +
			                               "\t" + depart +
			                               "\t" + salary);
			        }	
					
		}catch(SQLException e) {
			System.err.print("SQLException: ");
			System.out.println("Oops! " + e.getMessage());
		}finally {
			if (myStatement != null && myConnection != null) {
				myStatement.close();
				myConnection.close();
//				System.out.println("end of connection");
			}
		}
		
	}

}
