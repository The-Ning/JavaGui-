package Íø°É;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBconnection {
   private static String Driver = "com.mysql.jdbc.Driver";
   private static String URL="jdbc:mysql://localhost:3306/Íø°É";
   private static String User="root";
   private static String password="0302243708";          	
      public static Connection getconn(){
		Connection con=null;
		try {
				Class.forName(Driver);
			con=DriverManager.getConnection(URL, User, password);
		} 
		 catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	    return con;
    	    }
      public static void Close(Connection con,Statement statement,ResultSet rs){
    		  try {
    			  if(con!=null){
				con.close();}
    			  if(statement!=null){
    				  statement.close();
    			  }
    			  if(rs!=null){
    				  rs.close();
    			  }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	  
      }
}
