import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by hohankitivan on 1/22/14.
 */
public class SSEFInterface {

    private String url = "jdbc:mysql://localhost:3306/";
    private String dbUsername = "root";
    private String dbPassword = "";

    private String weather;

    private boolean loginworks;

    public SSEFInterface(){
        loginworks = false;
    }

    // poll() will update fields in the future when SSEFInterface communicates with the Models
    public void poll(){

    }

    public void setLogin(String username, String password){
        dbUsername = username;
        dbPassword = password;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);
            loginworks = true;
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean isLoggedIn(){
        return loginworks;
    }

    public boolean databaseExists(){
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection conn = DriverManager.getConnection(url+"SSEF", dbUsername, dbPassword);
            ResultSet resultSet = conn.getMetaData().getCatalogs();
            String databaseName = resultSet.getString(1);
            if (databaseName.equals("SSEF"))
                return true;
            resultSet.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public void constructDatabase(){
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);
            Statement s = conn.createStatement();
            int Result = s.executeUpdate("CREATE DATABASE SSEF");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void uploadFile(String code, String filename){
        pdfParserDevice parser = new pdfParserDevice(filename, "temp.txt");
        parser.getFields();
        FormData fd = new FormData(code, "temp.txt");
        DatabaseExec de = new DatabaseExec("SSEF");
        de.updateToDatabase(fd);
        File temp = new File("temp.txt");
        if (temp.delete()) System.out.println("File uploaded successfully!");
    }
}
