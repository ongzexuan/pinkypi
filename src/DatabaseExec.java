import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;


public class DatabaseExec {
	//Connection variables
	private String url = "jdbc:mysql://localhost:3306/";
	private String dbName;
	private String dbUserName = "root";
	private String dbPassword = "";
	
	public DatabaseExec(String databaseName) {
		this.dbName = databaseName;
	}
	
	public void updateToDatabase(FormData f) {
		String SSEFCode = f.getSSEFCode();
		
		try {
			//Load drivers
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			
			//Connect to database
			Connection conn = DriverManager.getConnection(url+dbName, dbUserName, dbPassword);
			
			//Execute queries
			Statement stmt = conn.createStatement();
			
			String updateCommand = "INSERT INTO PDFDATA ";
			String attributeCommand = "(SSEFCode, ";
			String valueCommand = "(\"" + SSEFCode + "\", ";
			for (int i = 0; i < f.dataList.size(); i++) {
				DataSet d = f.dataList.remove();
				boolean requiresQuotes = d.getRequiresQuotes();
				attributeCommand += d.getFieldName();
				valueCommand += ((requiresQuotes)?"\"":"") + d.getData() + ((requiresQuotes)?"\"":"");
				if (i != f.dataList.size()) {
					attributeCommand += ", ";
					valueCommand += ",";
				}
				f.dataList.add(d);
			}
			attributeCommand += ")";
			valueCommand += ")";
			updateCommand +=  attributeCommand + " VALUES " + valueCommand + ";";
            System.out.println(updateCommand);
            stmt.executeUpdate(updateCommand);
			
			//Close connection
			conn.close();
		} catch (SQLException e) {
			while (e != null) {
				System.out.println("Message: " + e.getMessage());
				System.out.println("SQLState: " + e.getSQLState());
				System.out.println("Vendor Error: " + e.getErrorCode());
				e.printStackTrace(System.out);
				e = e.getNextException();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	private void executeCommand(String command) {
		try {
			//Load drivers
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			
			//Connect to database
			Connection conn = DriverManager.getConnection(url+dbName, dbUserName, dbPassword);
			
			//Execute queries
			Statement stmt = conn.createStatement();
			stmt.execute(command);
			
			//Close connection
			conn.close();
		} catch (SQLException e) {
			while (e != null) {
				System.out.println("Message: " + e.getMessage());
				System.out.println("SQLState: " + e.getSQLState());
				System.out.println("Vendor Error: " + e.getErrorCode());
				e.printStackTrace(System.out);
				e = e.getNextException();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void executeUpdate(String command) {
		try {
			//Load drivers
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			
			//Connect to database
			Connection conn = DriverManager.getConnection(url+dbName, dbUserName, dbPassword);
			
			//Execute queries
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(command);
			
			//Close connection
			conn.close();
		} catch (SQLException e) {
			while (e != null) {
				System.out.println("Message: " + e.getMessage());
				System.out.println("SQLState: " + e.getSQLState());
				System.out.println("Vendor Error: " + e.getErrorCode());
				e.printStackTrace(System.out);
				e = e.getNextException();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/
}
