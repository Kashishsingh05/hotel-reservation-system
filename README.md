Hotel Reservation System

A simple console-based Hotel Reservation System built in Java using JDBC to connect to a MySQL database. This project allows users to reserve rooms, view reservations, look up room numbers, and update or delete existing bookings — all through a menu-driven command-line interface.

Features
Reserve a room (guest name, room number, contact number)
View all current reservations in a formatted table
Look up a room number using a reservation ID and guest name
Update an existing reservation
Delete a reservation
Uses PreparedStatement throughout to prevent SQL injection
Tech Stack
Java (JDK 21+)
JDBC (MySQL Connector/J)
MySQL
Project Structure
Test JDBC/
├── src/
│   └── HotelReservationSystem.java
├── schema.sql
├── db.properties.example
├── .gitignore
└── README.md
Setup Instructions
1. Clone the repository
   bash
   git clone https://github.com/Kashishsingh05/hotel-reservation-system.git
   cd hotel-reservation-system
2. Set up the database

Run the provided schema file in MySQL to create the database and table:

bash
mysql -u root -p < schema.sql
3. Configure your database credentials

Copy the example config file and fill in your own MySQL credentials:

bash
cp db.properties.example db.properties

Then edit db.properties:

properties
db.url=jdbc:mysql://localhost:3306/hotel_db
db.username=your_mysql_username
db.password=your_mysql_password

Note: db.properties is excluded from version control via .gitignore to keep your credentials private.

4. Add the MySQL Connector/J driver

Download MySQL Connector/J and add it to your project's classpath/dependencies.

5. Compile and run
   bash
   javac -d out src/HotelReservationSystem.java
   java -cp "out;path/to/mysql-connector-j.jar" HotelReservationSystem

(On Mac/Linux, use : instead of ; in the classpath.)

Sample Output
HOTEL MANAGEMENT SYSTEM
1. Reserve a room
2. View Reservations
3. Get Room Number
4. Update Reservations
5. Delete Reservations
0. Exit
   Choose an option:
   Database Schema
   java
   CREATE TABLE reservations (
   reservation_id   INT AUTO_INCREMENT PRIMARY KEY,
   guest_name       VARCHAR(255) NOT NULL,
   room_number      INT NOT NULL,
   contact_number   VARCHAR(10) NOT NULL,
   reservation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
   );