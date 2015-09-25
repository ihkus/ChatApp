package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionUtil {
	
	
	private Connection connection;
	String dbURL;
	public ConnectionUtil(String dbURL)
	{
		this.dbURL=dbURL;
	}
	public void createConnection()
	{
		try {
		 Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
         
			connection = DriverManager.getConnection(dbURL);
		} catch (SQLException e) {
			System.out.println("exception in creating connection "+e);
		} catch (InstantiationException e) {
			System.out.println("exception in creating connection "+e);
		} catch (IllegalAccessException e) {
			System.out.println("exception in creating connection "+e);
		} catch (ClassNotFoundException e) {
			System.out.println("exception in creating connection "+e);
		}
		
		
	
	
	}
	public ResultSet getResult(String query)
	{
		ResultSet resultSet=null;
		try {
			PreparedStatement ps=connection.prepareStatement(query);
			resultSet=ps.executeQuery();
		} catch (SQLException e) {
			resultSet=null;
			System.out.println("exception in fetching result "+e);
		}
		return resultSet;
	}
	public int insertQuery(String query)
	{
		
		return 0;
	}
	public int updateQuery(String query)
	{
		
		return 0;
	}
}
