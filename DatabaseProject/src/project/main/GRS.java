package project.main;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import project.sql.SQL;
import project.util.Utilities;

public class GRS {

    private static String DATABASE = "project.db";

    public static Connection conn = null;

    /**
     * Connects to the database if it exists, creates it if it does not, and
     * returns the connection object.
     *
     * @param databaseFileName
     *            the database file name
     * @return a connection object to the designated database
     */
    public static Connection initializeDB(String databaseFileName) {
        /**
         * The "Connection String" or "Connection URL".
         *
         * "jdbc:sqlite:" is the "subprotocol". (If this were a SQL Server
         * database it would be "jdbc:sqlserver:".)
         */
        String url = "jdbc:sqlite:" + databaseFileName;
        Connection conn = null; // If you create this variable inside the Try block it will be out of scope
        try {
            conn = DriverManager.getConnection(url);
            if (conn != null) {
                // Provides some positive assurance the connection and/or creation was successful.
                DatabaseMetaData meta = conn.getMetaData();
                System.out
                        .println("The driver name is " + meta.getDriverName());
                System.out.println(
                        "The connection to the database was successful.");
            } else {
                // Provides some feedback in case the connection failed but did not throw an exception.
                System.out.println("Null Connection");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out
                    .println("There was a problem connecting to the database.");
        }
        return conn;
    }

    private static final Set<Character> MENU_OPTIONS = new HashSet<>(
            Arrays.asList('1', '2', '3', '4', '5', '6', 'x'));

    private static void manageMembers(Scanner cin) {
        System.out.print("1. Add New Record\n" + "2. Edit Record\n"
                + "3. Search Records\n" + "4. Delete Records\n"
                + "Input numerical selection (or 'x' to return to main menu): ");
        String input = cin.nextLine();

        switch (input) {
            case "1":
                addMember(cin);
                break;
            case "2":
                editMember(cin);
                break;
            case "3":
                searchMember(cin);
                break;
            case "4":
                deleteMember(cin);
            case "x":
                // Return to the main menu
                break;
            default:
                System.out.println(
                        "Invalid input. Please enter a valid numerical selection.");
        }
    }

    private static void addMember(Scanner cin) {
        String sql = "INSERT INTO Community_Member (User_ID, Fname, Lname, address, status, start_date, warehouse_distance, warehouse_id) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        System.out.println("Enter User ID:");
        int userID = cin.nextInt();

        System.out.println("Enter First Name:");
        String firstName = cin.nextLine();

        System.out.println("Enter Last Name:");
        String lastName = cin.nextLine();

        System.out.println("Enter Address:");
        String address = cin.nextLine();

        System.out.println("Enter Status:");
        String status = cin.nextLine();

        System.out.println("Enter Start Date:");
        String startDate = cin.nextLine();

        System.out.println("Enter Warehouse Distance:");
        double warehouseDistance = Double.parseDouble(cin.nextLine());

        System.out.println("Enter Warehouse ID:");
        int warehouseID = cin.nextInt();

        SQL.ps_addMember(sql, userID, firstName, lastName, address, status,
                startDate, warehouseDistance, warehouseID);

    }

    private static void editMember(Scanner cin) {
        System.out.println("Enter first name to search:");
        String firstName = cin.nextLine();

		System.out.println("Enter last name to search:");
        String lastName = cin.nextLine();

        System.out.println("What to Update?");
        String attributeToUpdate = cin.nextLine();

        System.out.println("What to Update?");
        String newValue = cin.nextLine();

        String sql = "UPDATE Community_Member SET ? = ? WHERE Fname = ? AND Lname = ?";

        SQL.ps_editMember(sql, firstName, lastName, attributeToUpdate, newValue);
    }

    private static void deleteMember(Scanner cin) {
        System.out.println("Enter first name to delete:");
        String firstName = cin.nextLine();

        System.out.println("Enter User ID:");
        int userID = cin.nextInt();

        String sql = "DELETE FROM Community_Member WHERE Fname LIKE ? AND User_ID LIKE ?";

        SQL.ps_deleteMember(sql, firstName, userID);
    }

    private static void searchMember(Scanner cin) {
        System.out.println("Enter first name to search:");
        String firstName = cin.nextLine();
        String sql = "SELECT * FROM Community_Member WHERE Fname LIKE ?";

        SQL.ps_SearchMember(sql, firstName);

    }

    private static void rentEquipment(Scanner cin) {
        System.out.println("Enter USER ID:");
        int userId = Integer.parseInt(cin.nextLine());

		System.out.println("Enter employee SSN:");
		int empSsn = Integer.parseInt(cin.nextLine());

        System.out.println("What to Rent?");
        int equipmentSerialNo = Integer.parseInt(cin.nextLine());

		System.out.println("Enter value of equipment:");
		double value = Double.parseDouble(cin.nextLine());

		System.out.println("Enter estimated date of arrival:");
		String estDoa = cin.nextLine();

		String sql2 = "INSERT INTO Rental (rental_no, num_items, value, est_doa, emp_ssn, user_id, equipment_ID) VALUES ((SELECT MAX(rental_no) + 1 FROM Rental), 1, ?, ?, ?, ?, ?)";
		String sql = "UPDATE Equipment SET rental_id = (SELECT MAX(rental_no) FROM Rental) WHERE equipment_serial_no = ?";

        SQL.ps_rentEquipment(sql, sql2, userId, equipmentSerialNo, value, estDoa, empSsn);
    }

    private static void returnEquipment(Scanner cin) {
        System.out.println("Enter rental number:");
		int rentalNo = Integer.parseInt(cin.nextLine());

		String sql = "UPDATE Equipment SET rental_id = NULL WHERE rental_id = ?";
		String sql2 = "DELETE FROM Rental WHERE rental_no = ?";

		SQL.ps_returnEquipment(sql, sql2, rentalNo);
    }

    private static void scheduleDelivery(Scanner cin) {
        System.out.println("Enter USER ID:");
        String firstName = cin.nextLine();

        System.out.println("What to deliver?");
        String attributeToUpdate = cin.nextLine();

        System.out.println("Enter Drone ID...?:");
        String startDate = cin.nextLine();

        String sql = "";

        Utilities.placeholder();
    }

    private static void schedulePickup(Scanner cin) {
        System.out.println("Enter USER ID:");
        String firstName = cin.nextLine();

        System.out.println("What to deliver?");
        String attributeToUpdate = cin.nextLine();

        System.out.println("Enter Warehouse Address...?:");
        String startDate = cin.nextLine();

        String sql = "";

        Utilities.placeholder();
    }

    private static void findTotalEquipmentRentedByMember(Scanner cin) {
        System.out.println("Enter USER ID:");
        String id = cin.nextLine();

        String sql = "Select COUNT(*) AS Equipment_Rented FROM Rental WHERE user_id = ?";

        SQL.ps_numRented(sql, id);
    }

    private static void findMostPopularItem(Scanner cin) {
        String sql = "SELECT equipment_ID, COUNT(*) AS Number_Out FROM Rental GROUP BY equipment_ID ORDER BY Number_Out DESC LIMIT 1;";

        SQL.sqlQuery(conn, sql);
    }

    private static void findMostFrequentManufacturer(Scanner cin) {

        String sql = "SELECT E.manufacturer_name, E.manufacturer_address FROM Equipment AS E, (SELECT equipment_ID, COUNT(*) AS Number_Out FROM Rental GROUP BY equipment_ID ORDER BY Number_Out DESC LIMIT 1) AS Q WHERE E.equipment_serial_no = Q.equipment_ID;";

        SQL.sqlQuery(conn, sql);
    }

    private static void findMostUsedDrone(Scanner cin) {
        String sql = "SELECT drone_ID, COUNT(*) AS Deliveries FROM Rental GROUP BY drone_ID ORDER BY drone_ID DESC LIMIT 1;";

        SQL.sqlQuery(conn, sql);
    }

    private static void findMemberWithMostItemsRented(Scanner cin) {
        String sql = "SELECT user_id, COUNT(*) AS Rentals FROM Rental GROUP BY user_id ORDER BY user_id DESC LIMIT 1;";

        SQL.sqlQuery(conn, sql);
    }

    private static void findEquipmentByTypeReleasedBeforeYear(Scanner cin) {

        System.out.println("Enter type:");
        String type = cin.nextLine();

        System.out.println("Enter year:");
        int id = cin.nextInt();

        String sql = "SELECT equipment_serial_no, equipment_model, description FROM Equipment WHERE description LIKE ? AND year < ?;";

        SQL.ps_equipType(sql, type, id);
    }

    private static void generateReports(Scanner cin) {
        System.out.print("1. Find Total Equipment Rented By Member\n" + "2. Find Most Popular Item\n"
                + "3. Find Most Frequent Manufacturer\n" + "4. Find Most Used Drone\n"
				+ "5. Find Member With Most Items Rented\n" + "6. Find Equipment By Type Released Before Year\n"
                + "Input numerical selection (or 'x' to return to main menu): ");
        String input = cin.nextLine();

        switch (input) {
            case "1":
                findTotalEquipmentRentedByMember(cin);
                break;
            case "2":
                findMostPopularItem(cin);
                break;
            case "3":
                findMostFrequentManufacturer(cin);
                break;
            case "4":
                findMostUsedDrone(cin);
                break;
            case "5":
                findMemberWithMostItemsRented(cin);
                break;
            case "6":
                findEquipmentByTypeReleasedBeforeYear(cin);
                break;
            case "x":
                // Return to the main menu
                break;
            default:
                System.out.println(
                        "Invalid input. Please enter a valid numerical selection.");
        }

    }

    private static void mainMenu(Scanner cin) {

        char selection;
        do {
            System.out.print("~Equipment Renting~\n" + "1. Manage Members\n"
                    + "2. Rent Equipment\n" + "3. Return Equipment\n"
                    + "4. Schedule Delivery\n" + "5. Schedule Pickup\n"
                    + "6. Useful Reports\n"
                    + "Input numerical selection (or 'x' to quit): ");
            String input = cin.nextLine();
            selection = !input.isEmpty() ? input.charAt(0) : ' ';

            while (!MENU_OPTIONS.contains(selection)) {
                System.out.print("Incorrect option specified! Try again: ");
                input = cin.nextLine();
                selection = !input.isEmpty() ? input.charAt(0) : ' ';
            }

            switch (input) {
                case "1":
                    manageMembers(cin);
                    break;
                case "2":
                    rentEquipment(cin);
                    break;
                case "3":
                    returnEquipment(cin);
                    break;
                case "4":
                    scheduleDelivery(cin);
                    break;
                case "5":
                    schedulePickup(cin);
                    break;
                case "6":
                    generateReports(cin);
                    break;
                case "x":
                    // Exit or return to the main program loop
                    break;
                default:
            }
            Utilities.printDivider();
        } while (selection != 'x');
    }

    public static void main(String[] args) {
        conn = initializeDB(DATABASE);

        Scanner cin = new Scanner(System.in);
        mainMenu(cin);

        cin.close();
        try {
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        System.out.println("Bye");
    }

}
