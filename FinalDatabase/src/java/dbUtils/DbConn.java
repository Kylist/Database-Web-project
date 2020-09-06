package dbUtils;

import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Wrapper class for database connection. Constructor opens connection. Close
 * method closes connection.
 */
public class DbConn {

    private String errMsg = ""; // will remain "" unless error getting connection
    private java.sql.Connection conn = null;

    public DbConn() throws ClassNotFoundException {
        
        String host = "127.0.0.1:3306";
	String database = "FinalProject";
	String user = "root";
	String password = "Lycarousx2x2x2@";

        // check that the driver is installed
        try
        {
                Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
                recordError("Problem getting driver: " + e.getMessage());
        }

        try
        {
                String url = String.format("jdbc:mysql://%s/%s", host, database);

                // Set connection properties.
                Properties properties = new Properties();
                properties.setProperty("user", user);
                properties.setProperty("password", password);
                properties.setProperty("useSSL", "false");
                properties.setProperty("verifyServerCertificate", "true");
                properties.setProperty("requireSSL", "false");
                properties.setProperty("serverTimezone", "UTC");

                // get connection
                conn = DriverManager.getConnection(url, properties);
        }
        catch (SQLException e)
        {
            recordError("Problem getting connection:" + e.getMessage());
        }
        if (conn != null) 
        { 
            System.out.println("Successfully created connection to database.");	
        }
        else {
            System.out.println("Failed to create connection to database.");	
        }
        System.out.println("Execution finished.");
    } // method

    private void recordError(String errorMsg) {
        this.errMsg = errorMsg;
        System.out.println("Error in DbConn. " + errorMsg);
    }

    /* Returns database connection for use in SQL classes.  */
    public Connection getConn() {
        return this.conn;
    }

    /* Returns database connection error message or "" if there is none.  */
    public String getErr() {
        return this.errMsg;
    }

    /**
     * Close database connection.
     */
    public void close() {

        if (conn != null) {
            try {
                conn.close();
            } // try
            catch (Exception e) {
                // Don't care if connection was already closed. Do nothing.
            } // catch
        } // if
    } // method


    // This method gets run when GC (garbage collection) runs 
    // and we are never sure when this might happen, but still it's better than 
    // nothing to try to be sure that all db connections are closed when 
    // the dbConn object is no longer referenced. Maybe we can get the IT 
    // Administrator to set the GC to run more often.
    @Override
    protected void finalize() {
        try {
            super.finalize();
        } catch (Throwable ex) {
            Logger.getLogger(DbConn.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.close(); // 
    }

} // class