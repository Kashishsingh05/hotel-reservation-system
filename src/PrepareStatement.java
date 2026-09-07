import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.xml.transform.Result;

public class PrepareStatement {
    public static void main(String[] args)throws ClassNotFoundException{

        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String username = "root";
        String pass = "Aman@2006";
        String query = "SELECT * from employees where name = ?";
        //String query = "SELECT * from employees(id, name, job_title, salary) VALUES(?,?,?,?)";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Drivers loaded successfully!");

        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage());
        }

        try{
            Connection con = DriverManager.getConnection(url, username, pass);
            System.out.println("Connection Established Successfully!");
           // Statement st = con.createStatement();

            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1,"kashish");
            //preparedStatement.setString(2,"Full Stack Developer");
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String job_title = rs.getString("job_title");
                double salary = rs.getDouble("salary");
                System.out.println("ID: " +id);
                System.out.println("NAME: "+name);
                System.out.println("JOB TITLE: "+job_title);
                System.out.println("SALARY: "+salary);

            }

            rs.close();
            preparedStatement.close();
            con.close();
            System.out.println();
            System.out.println("Connection closed successfullt!");

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

    }
}
