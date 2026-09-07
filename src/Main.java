import java.sql.*;

public class Main{
    public static void main(String[] args) throws ClassNotFoundException{
        /*
        import package
        load and register
        create connection
        create statement
        execute statement
        process the result
        close
         */
        String url ="jdbc:mysql://localhost:3306/mydatabase";
        String username="root";
        String pass = "Aman@2006";
        //String query = "select * from employees;";
        //String query = "INSERT INTO EMPLOYEES(id, name, job_title, salary) VALUES (4,'Harshit', 'AWS Developer', 80000.0);";
        //String query = "DELETE FROM employees where id = 4; ";
        String query = "UPDATE employees\n" + "SET job_title = 'FrontEnd Developer', salary = 50000.0\n" + "WHERE id = 2;";

        //driver loading
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Drivers loaded successfully!!");
        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage());
        }

        //connection establishment
        try {
            Connection con = DriverManager.getConnection(url, username, pass);
            System.out.println("Connection Established Successfully!!" );
            Statement st = con.createStatement();

            //insert database
            /*int rowsaffected = st.executeUpdate(query);
            if(rowsaffected >0){
                System.out.println("Insert successfully!!."+ rowsaffected + " row(s) affected.");
            }else{
                System.out.println("Insert failed!");
            }*/

            //delete database
            /*int rowsaffected = st.executeUpdate(query);
            if(rowsaffected >0){
                System.out.println("Deletion successfull!!."+ rowsaffected + " row(s) affected.");
            }else{
                System.out.println("Deletion failed!");
            } */

            //update database
            int rowsaffected = st.executeUpdate(query);
            if(rowsaffected >0){
                System.out.println("Updation successfull!!."+ rowsaffected + " row(s) affected.");
            }else {
                System.out.println("Updation failed!");
            }


            //retrive database
            /*ResultSet rs= st.executeQuery(query);

            while(rs.next()){
                int id  = rs.getInt("id");
                String name = rs.getString("name");
                String job_title = rs.getString("job_title");
                double salary = rs.getDouble("salary");
                System.out.println();
                System.out.println("=================");
                System.out.println("ID: "+id);
                System.out.println("Name: "+ name);
                System.out.println("Job Title: "+job_title);
                System.out.println("Salary: "+salary);
            }
            rs.close();*/
            st.close();
            con.close();
            System.out.println();
            System.out.println("Connection closed successfully!!");

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }


    }
}