package project.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import project.main.GRS;

/**
 *
 * All database connectivity should be handled from within here.
 *
 */
public class SQL {

    private static PreparedStatement ps;

    /**
     * Queries the database and prints the results.
     *
     * @param conn
     *            a connection object
     * @param sql
     *            a SQL statement that returns rows: this query is written with
     *            the Statement class, typically used for static SQL SELECT
     *            statements.
     */
    public static void sqlQuery(Connection conn, String sql) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                String value = rsmd.getColumnName(i);
                System.out.print(value);
                if (i < columnCount) {
                    System.out.print(",  ");
                }
            }
            System.out.print("\n");
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String columnValue = rs.getString(i);
                    System.out.print(columnValue);
                    if (i < columnCount) {
                        System.out.print(",  ");
                    }
                }
                System.out.print("\n");
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Queries the database and prints the results.
     *
     * @param conn
     *            a connection object
     * @param sql
     *            a SQL statement that returns rows: this query is written with
     *            the PrepareStatement class, typically used for dynamic SQL
     *            SELECT statements.
     */
    public static void sqlQuery(Connection conn, PreparedStatement sql) {
        try {
            ResultSet rs = sql.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                String value = rsmd.getColumnName(i);
                System.out.print(value);
                if (i < columnCount) {
                    System.out.print(",  ");
                }
            }
            System.out.print("\n");
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String columnValue = rs.getString(i);
                    System.out.print(columnValue);
                    if (i < columnCount) {
                        System.out.print(",  ");
                    }
                }
                System.out.print("\n");
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * (PART 5) Create PreparedStatement to search a track by track name.
     *
     * @param sql
     *            query for prepared statement
     *
     * @param track_name
     *            track name to search by
     */
    public static void ps_SearchTracks(String sql, String track_name) {
        try {
            ps = GRS.conn.prepareStatement(sql);
            ps.setString(1, track_name);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        sqlQuery(GRS.conn, ps);
    }

    /**
     * (PART 5) Create PreparedStatement to search an artist by artist name.
     *
     * @param sql
     *            query for prepared statement
     *
     * @param track_name
     *            track name to search by
     */
    public static void ps_editMember(String sql, String first_name, String last_name, String attr,
            String new_attr) {
        try {
            ps = GRS.conn.prepareStatement(sql);
            ps.setString(1, attr);
            if (attr.equals("warehouse_id") || attr.equals("user_id")) {
                ps.setInt(2, Integer.parseInt(new_attr));
            } else if (attr.equals("warehouse_distance")) {
                ps.setDouble(2, Double.parseDouble(new_attr));
            } else {
                ps.setString(2, new_attr);
            }

            ps.setString(3, first_name);
			ps.setString(4, last_name);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Successfully edited " + first_name);
            } else {
                System.out.println("Failed to add artist.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void ps_deleteMember(String sql, String first_name,
            int user_id) {
        try {
            ps = GRS.conn.prepareStatement(sql);
            ps.setString(1, first_name);
            ps.setInt(2, user_id);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Successfully edited " + first_name);
            } else {
                System.out.println("Failed to add artist.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * (PART 6) Create PreparedStatement to search number of records in stock
     * according to album name.
     *
     * @param sql
     *            query for prepared statement
     *
     * @param album_name
     *            album name to search by
     */
    public static void ps_SearchMember(String sql, String member_name) {
        try {
            ps = GRS.conn.prepareStatement(sql);
            ps.setString(1, member_name);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        sqlQuery(GRS.conn, ps);
    }

    /**
     * (PART 7) Define your method here!
     *
     */

    public static void ps_addMember(String sql, int userID, String firstName,
            String lastName, String address, String status, String startDate,
            double warehouseDistance, int warehouseID) {

        try {
            ps = GRS.conn.prepareStatement(sql);
            ps.setInt(1, userID);
            ps.setString(2, firstName);
            ps.setString(3, lastName);
            ps.setString(4, address);
            ps.setString(5, status);
            ps.setString(6, startDate);
            ps.setDouble(7, warehouseDistance);
            ps.setInt(8, warehouseID);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Successfully Added " + firstName);
            } else {
                System.out.println("Failed to add artist.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void ps_numRented(String sql, String id) {
        try {
            ps = GRS.conn.prepareStatement(sql);
            ps.setString(1, id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        sqlQuery(GRS.conn, ps);
    }

    public static void ps_equipType(String sql, String type, int year) {
        try {
            ps = GRS.conn.prepareStatement(sql);
            ps.setString(1, "%"+type+"%");
            ps.setInt(2, year);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        sqlQuery(GRS.conn, ps);
    }

	public static void ps_rentEquipment(String sql, String sql2, int userId, int equipmentSerialNo, double value, String estDoa, int empSsn) {
		try {
			ps = GRS.conn.prepareStatement(sql);
			ps.setInt(1, equipmentSerialNo);

			PreparedStatement ps2 = GRS.conn.prepareStatement(sql2);
			ps2.setDouble(1, value);
			ps2.setString(2, estDoa);
			ps2.setInt(3, empSsn);
			ps2.setInt(4, userId);
			ps2.setInt(5, equipmentSerialNo);

			int rowsAffected2 = ps2.executeUpdate();

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0 && rowsAffected2 > 0) {
				System.out.println("Successfully rented equipment.");
			} else {
				System.out.println("Failed to rent equipment.");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void ps_returnEquipment(String sql, String sql2, int rentalNo) {
		try {
			ps = GRS.conn.prepareStatement(sql);
			ps.setInt(1, rentalNo);

			PreparedStatement ps2 = GRS.conn.prepareStatement(sql2);
			ps2.setInt(1, rentalNo);

			int rowsAffected = ps.executeUpdate();
			int rowsAffected2 = ps2.executeUpdate();

			System.out.println("Successfully returned equipment.");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

}
