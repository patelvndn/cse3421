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

        System.out.println("What to Update?");
        String attributeToUpdate = cin.nextLine();

        System.out.println("What to Update?");
        String newValue = cin.nextLine();

        String sql = "UPDATE Community_Member SET ? LIKE ? WHERE Fname LIKE ?";

        SQL.ps_editMember(sql, firstName, attributeToUpdate, newValue);
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
        String firstName = cin.nextLine();

        System.out.println("What to Rent?");
        String attributeToUpdate = cin.nextLine();

        String sql = "";

        Utilities.placeholder();
    }

    private static void returnEquipment(Scanner cin) {
        System.out.println("Enter USER ID:");
        String firstName = cin.nextLine();

        System.out.println("What to return?");
        String attributeToUpdate = cin.nextLine();

        String sql = "";

        System.out.println("Enter Date:");
        String startDate = cin.nextLine();

        Utilities.placeholder();
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

        String sql = "Select COUNT(*) FROM Rental WHERE user_id LIKE ?";

        SQL.ps_numRented(sql, id);
    }

    private static void findMostPopularItem(Scanner cin) {
        String sql = "Select COUNT(*) FROM Deliver ORDER Desc GROUP BY equipment_id LIMIT 1";

        SQL.sqlQuery(conn, sql);
    }

    private static void findMostFrequentManufacturer(Scanner cin) {

        String sql = "Select COUNT(*) FROM Deliver, Manufacturer JOIN Deliver ON Manufacturer BY (Equipment_id = Equipment_id) ORDER Desc GROUP BY Manufacturer LIMIT 1";

        SQL.sqlQuery(conn, sql);
    }

    private static void findMostUsedDrone(Scanner cin) {
        String sql = "Select COUNT(*) FROM Deliver ORDER Desc GROUP BY drone_id LIMIT 1";

        SQL.sqlQuery(conn, sql);
    }

    private static void findMemberWithMostItemsRented(Scanner cin) {

        /**
         * SELECT member_id, COUNT(*) AS total_items_rented FROM renting GROUP
         * BY member_id ORDER BY total_items_rented DESC LIMIT 1;
         */
        String sql = "Select User_id, count(*) FROM Rental GROUP BY user_id ORDER BY Desc LIMIT 1";

        SQL.sqlQuery(conn, sql);
    }

    private static void findEquipmentByTypeReleasedBeforeYear(Scanner cin) {

        System.out.println("Enter type:");
        String type = cin.nextLine();

        System.out.println("Enter year:");
        int id = cin.nextInt();

        /**
         * SELECT description FROM equipment WHERE type_of_equipment =
         * 'specified_type' AND release_year < 'specified_year';
         */
        String sql = "Select description FROM equipment WHERE description LIKE ? AND year < ?";

        SQL.ps_equipType(sql, type, id);
    }

    private static void generateReports(Scanner cin) {
        System.out.print("1. Add New Record\n" + "2. Edit Record\n"
                + "3. Search Records\n" + "4. Delete Records\n"
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
