import java.io.FileInputStream;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.Properties;
import java.util.Scanner;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



public class HotelReservationSystem {
    private static String url;
    private static String username;
    private static String password;

    private static void loadConfig() {
        Properties props = new Properties();
        try (FileInputStream input = new FileInputStream("db.properties")) {
            props.load(input);
            url = props.getProperty("db.url");
            username = props.getProperty("db.username");
            password = props.getProperty("db.password");
        } catch (IOException e) {
            System.out.println("Could not load db.properties: " + e.getMessage());
            System.out.println("Make sure db.properties exists in the project root (see db.properties.example).");
            System.exit(1);
        }
    }


    public static void main(String[]args) throws ClassNotFoundException{
        loadConfig();

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage());
        }

        try{
            Connection con  = DriverManager.getConnection(url, username, password);
            Scanner sc = new Scanner(System.in);

            while(true){
                System.out.println();
                System.out.println("HOTEL MANAGEMENT SYSTEM");
                System.out.println("1. Reserve a room");
                System.out.println("2. View Reservations");
                System.out.println("3. Get Room Number");
                System.out.println("4. Update Reservations");
                System.out.println("5. Delete Reservations");
                System.out.println("0. Exit");
                System.out.println("Choose an option: ");
                int choice = sc.nextInt();
                sc.nextLine(); // consume leftover newline

                switch(choice){
                    case 1:
                        reserveRoom(con, sc);
                        break;
                    case 2:
                        viewReservations(con);
                        break;
                    case 3:
                        getRoomNumber(con, sc);
                        break;
                    case 4:
                        updateReservation(con,sc);
                        break;
                    case 5:
                        deleteReservation(con, sc);
                        break;
                    case 0:
                        exit();
                        sc.close();
                        con.close();
                        return;
                    default:
                        System.out.println("Invalid choice.Try again.");
                }
            }

        }catch(SQLException e) {
            System.out.println("Database error: "+e.getMessage());
        }catch(InterruptedException e) {
            throw new RuntimeException(e);
        }

    }


    private static void reserveRoom(Connection con, Scanner sc){
        try{
            System.out.println("Enter guest name: ");
            String guestName = sc.nextLine();
            System.out.println("Enter room number: ");
            int roomNumber = sc.nextInt();
            sc.nextLine(); // consume leftover newline
            System.out.println("Enter contact number: ");
            String contactNumber = sc.nextLine();

            String sql = "INSERT INTO reservations (guest_name, room_number, contact_number) VALUES (?, ?, ?)";

            try(PreparedStatement ps = con.prepareStatement(sql)){
                ps.setString(1, guestName);
                ps.setInt(2, roomNumber);
                ps.setString(3, contactNumber);

                int affectedRows = ps.executeUpdate();

                if(affectedRows > 0){
                    System.out.println("Reservation successful!");
                }else{
                    System.out.println("Reservation failed.");
                }
            }
        }catch(SQLException e){
            System.out.println("Database error: " + e.getMessage());
        }

    }


    private static void viewReservations(Connection con) throws SQLException{
        String sql = "SELECT reservation_id, guest_name, room_number, contact_number, reservation_date FROM reservations";

        try(PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){

            System.out.println("Current Reservation: ");
            System.out.println("+----------------+----------------+---------------+-----------------+------------------+");
            System.out.println("| Reservation ID | Guest          | Room Number   | Contact Number  | Reservation Date |");
            System.out.println("+----------------+----------------+---------------+-----------------+------------------+");

            while(rs.next()){
                int reservationId = rs.getInt("reservation_id");
                String guestName = rs.getString("guest_name");
                int roomNumber = rs.getInt("room_number");
                String contactNumber = rs.getString("contact_number");
                String reservationDate = rs.getTimestamp("reservation_date").toString();

                //formate and display the reservation date in the table-like formate
                System.out.printf("| %-14d | %-15s | %-13d | %-20s | %-19s  |\n",
                        reservationId, guestName, roomNumber, contactNumber, reservationDate);
            }

            System.out.println("+----------------+----------------+---------------+-----------------+------------------+");
        }
    }

    private static void getRoomNumber(Connection con, Scanner sc) {

        try {
            System.out.println("Enter reservation ID: ");
            int reservationId = sc.nextInt();
            sc.nextLine(); // consume leftover newline
            System.out.println("Enter guest name: ");
            String guestName = sc.nextLine();

            String sql = "SELECT room_number FROM reservations " +
                    "WHERE reservation_id = ? AND guest_name = ?";

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, reservationId);
                ps.setString(2, guestName);

                try(ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        int roomNumber = rs.getInt("room_number");
                        System.out.println("Room Number for Reservation ID " + reservationId + " and Guest " + guestName + " is: " + roomNumber);
                    } else {
                        System.out.println("No reservation found for that ID and guest name.");
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }

    }

    private static void updateReservation(Connection con, Scanner sc) {
        try {
            System.out.println("Enter reservation ID to update: ");
            int reservationId = sc.nextInt();
            sc.nextLine();  // consume the new line character.

            if (!reservationExists(con, reservationId)) {
                System.out.println("No reservation found for the given ID.");
                return;
            }
            System.out.println("Enter new guest name: ");

            String newGuestName = sc.nextLine();

            System.out.println("Enter new room number: ");
            int newRoomNumber = sc.nextInt();
            sc.nextLine(); // consume leftover newline
            System.out.println("Enter new contact number: ");
            String newContactNumber = sc.nextLine();

            String sql = "UPDATE reservations SET guest_name = ?, room_number = ?, contact_number = ? " +
                    "WHERE reservation_id = ?";

            try(PreparedStatement ps = con.prepareStatement(sql)){
                ps.setString(1, newGuestName);
                ps.setInt(2, newRoomNumber);
                ps.setString(3, newContactNumber);
                ps.setInt(4, reservationId);

                int affectedRows = ps.executeUpdate();

                if(affectedRows > 0){
                    System.out.println("Reservation updated successfully!");
                }else{
                    System.out.println("Reservation update failed.");
                }

            }
        }catch(SQLException e){
            System.out.println("Database Error: "+ e.getMessage());
        }

    }

    private static void deleteReservation(Connection con, Scanner sc ){
        try{
            System.out.println("Enter reservation ID to delete: ");
            int reservationId = sc.nextInt();
            sc.nextLine(); // consume leftover newline

            if(!reservationExists(con, reservationId)){
                System.out.println("No reservation found for the given ID.");
                return;
            }

            String sql = "DELETE FROM reservations WHERE reservation_id = ?";

            try(PreparedStatement ps = con.prepareStatement(sql)){
                ps.setInt(1, reservationId);

                int affectedRows = ps.executeUpdate();

                if(affectedRows >0){
                    System.out.println("Reservation deleted successfully!");
                }else{
                    System.out.println("Reservation deletion failed.");
                }
            }
        }catch(SQLException e){
            System.out.println("Database Error: " +e.getMessage());
        }
    }

    private static boolean reservationExists(Connection con, int reservationId){
        try{
            String sql = "SELECT reservation_id FROM reservations WHERE reservation_id = ?";

            try(PreparedStatement ps = con.prepareStatement(sql)){
                ps.setInt(1, reservationId);

                try(ResultSet rs = ps.executeQuery()){
                    return rs.next(); //if there is a result, the reservation exists.
                }
            }
        }catch(SQLException e){
            System.out.println("Database Error: " +e.getMessage());
            return false; // handle database errors as needed.

        }

    }

    public static void exit() throws InterruptedException {
        System.out.println("Exiting System");
        int i = 5;
        while(i!=0){
            System.out.println(".");
            Thread.sleep(450);
            i--;
        }
        System.out.println();
        System.out.println("ThankYou for Using Hotel Reservation System!!!");
    }
}