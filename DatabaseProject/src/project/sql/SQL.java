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

        public static void ps_scheduleDelivery(int droneID, int rentalNo,
            String sql) {
        try {
            ps = GRS.conn.prepareStatement(sql);
            ps.setInt(1, droneID);

            ps.setInt(2, rentalNo);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Successfully scheduled delivery.");
            } else {
                System.out.println("Unable to schedule delivery.");

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void ps_schedulePickup(int rentalNo, String sql) {
        try {
            ps = GRS.conn.prepareStatement(sql);

            ps.setInt(1, rentalNo);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Successfully scheduled pickup.");
            } else {
                System.out.println("Unable to schedule pickup.");

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

	public static void ps_addDrone(String sql, int droneSerialNo, String droneName, String droneModel, String warrantyExpDate, double weightCapacity, double maxSpeed, String location, double distanceAutonomy, String status, int year, int warehouseID, String manufacturerAddress, String manufacturerName) {
		try {
			ps = GRS.conn.prepareStatement(sql);
			ps.setInt(1, droneSerialNo);
			ps.setString(2, droneName);
			ps.setString(3, droneModel);
			ps.setString(4, warrantyExpDate);
			ps.setDouble(5, weightCapacity);
			ps.setDouble(6, maxSpeed);
			ps.setString(7, location);
			ps.setDouble(8, distanceAutonomy);
			ps.setString(9, status);
			ps.setInt(10, year);
			ps.setInt(11, warehouseID);
			ps.setString(12, manufacturerAddress);
			ps.setString(13, manufacturerName);

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Successfully added drone.");
			} else {
				System.out.println("Failed to add drone.");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void ps_editDrone(String sql, int droneSerialNo, String attr, String new_attr) {
		try {
			ps = GRS.conn.prepareStatement(sql);
			ps.setString(1, attr);
			if (attr.equals("weight_capacity") || attr.equals("max_speed") || attr.equals("distance_autonomy")) {
				ps.setDouble(2, Double.parseDouble(new_attr));
			} else if (attr.equals("year") || attr.equals("warehouse_id")) {
				ps.setInt(2, Integer.parseInt(new_attr));
			} else {
				ps.setString(2, new_attr);
			}

			ps.setInt(3, droneSerialNo);

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Successfully edited drone.");
			} else {
				System.out.println("Failed to edit drone.");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void ps_deleteDrone(String sql, int droneSerialNo) {
		try {
			ps = GRS.conn.prepareStatement(sql);
			ps.setInt(1, droneSerialNo);

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Successfully deleted drone.");
			} else {
				System.out.println("Failed to delete drone.");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void ps_searchDrone(String sql, int droneSerialNo) {
		try {
			ps = GRS.conn.prepareStatement(sql);
			ps.setInt(1, droneSerialNo);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		sqlQuery(GRS.conn, ps);
	}

	public static void ps_addEquipment(String sql, int equipmentSerialNo, String description, String equipmentModel, int year, String status, String warrantyExpDate, double weight, double width, double length, double height, int warehouseID, String manufacturerAddress, String manufacturerName) {
		try {
			ps = GRS.conn.prepareStatement(sql);
			ps.setInt(1, equipmentSerialNo);
			ps.setString(2, description);
			ps.setString(3, equipmentModel);
			ps.setInt(4, year);
			ps.setString(5, status);
			ps.setString(6, warrantyExpDate);
			ps.setDouble(7, weight);
			ps.setDouble(8, width);
			ps.setDouble(9, length);
			ps.setDouble(10, height);
			ps.setInt(11, warehouseID);
			ps.setString(12, manufacturerAddress);
			ps.setString(13, manufacturerName);

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Successfully added equipment.");
			} else {
				System.out.println("Failed to add equipment.");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void ps_editEquipment(String sql, int equipmentSerialNo, String attr, String new_attr) {
		try {
			ps = GRS.conn.prepareStatement(sql);
			ps.setString(1, attr);
			if (attr.equals("weight") || attr.equals("width") || attr.equals("length") || attr.equals("height")) {
				ps.setDouble(2, Double.parseDouble(new_attr));
			} else if (attr.equals("year") || attr.equals("warehouse_id")) {
				ps.setInt(2, Integer.parseInt(new_attr));
			} else {
				ps.setString(2, new_attr);
			}

			ps.setInt(3, equipmentSerialNo);

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Successfully edited equipment.");
			} else {
				System.out.println("Failed to edit equipment.");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void ps_deleteEquipment(String sql, int equipmentSerialNo) {
		try {
			ps = GRS.conn.prepareStatement(sql);
			ps.setInt(1, equipmentSerialNo);

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Successfully deleted equipment.");
			} else {
				System.out.println("Failed to delete equipment.");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void ps_searchEquipment(String sql, int equipmentSerialNo) {
		try {
			ps = GRS.conn.prepareStatement(sql);
			ps.setInt(1, equipmentSerialNo);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		sqlQuery(GRS.conn, ps);
	}

	public static void ps_addWarehouse(String sql, int warehouseID, String address, String city, int droneCapacity, int equipmentCapacity, int managerSSN) {
		try {
			ps = GRS.conn.prepareStatement(sql);
			ps.setInt(1, warehouseID);
			ps.setString(2, address);
			ps.setString(3, city);
			ps.setInt(4, droneCapacity);
			ps.setInt(5, equipmentCapacity);
			ps.setInt(6, managerSSN);

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Successfully added warehouse.");
			} else {
				System.out.println("Failed to add warehouse.");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void ps_editWarehouse(String sql, int warehouseID, String attr, String new_attr) {
		try {
			ps = GRS.conn.prepareStatement(sql);
			ps.setString(1, attr);
			if (attr.equals("drone_capacity") || attr.equals("equipment_capacity")) {
				ps.setInt(2, Integer.parseInt(new_attr));
			} else {
				ps.setString(2, new_attr);
			}

			ps.setInt(3, warehouseID);

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Successfully edited warehouse.");
			} else {
				System.out.println("Failed to edit warehouse.");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void ps_deleteWarehouse(String sql, int warehouseID) {
		try {
			ps = GRS.conn.prepareStatement(sql);
			ps.setInt(1, warehouseID);

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Successfully deleted warehouse.");
			} else {
				System.out.println("Failed to delete warehouse.");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void ps_searchWarehouse(String sql, int warehouseID) {
		try {
			ps = GRS.conn.prepareStatement(sql);
			ps.setInt(1, warehouseID);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		sqlQuery(GRS.conn, ps);
	}

	public static void ps_addManufacturer(String sql, String name, String address, String city, String phone) {
		try {
			ps = GRS.conn.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, address);
			ps.setString(3, city);
			ps.setString(4, phone);

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Successfully added manufacturer.");
			} else {
				System.out.println("Failed to add manufacturer.");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void ps_editManufacturer(String sql, String name, String address, String attributeToUpdate, String newValue) {
		try {
			ps = GRS.conn.prepareStatement(sql);
			ps.setString(1, attributeToUpdate);
			ps.setString(2, newValue);
			ps.setString(3, name);
			ps.setString(4, address);

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Successfully edited manufacturer.");
			} else {
				System.out.println("Failed to edit manufacturer.");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void ps_deleteManufacturer(String sql, String name, String address) {
		try {
			ps = GRS.conn.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, address);

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Successfully deleted manufacturer.");
			} else {
				System.out.println("Failed to delete manufacturer.");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void ps_searchManufacturer(String sql, String name, String address) {
		try {
			ps = GRS.conn.prepareStatement(sql);
			ps.setString(1, "%"+name+"%");
			ps.setString(2, "%"+address+"%");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		sqlQuery(GRS.conn, ps);
	}

	public static void ps_addRating(String sql, String reviewDesc, int rating, int userID, int equipmentID) {
		try {
			ps = GRS.conn.prepareStatement(sql);
			ps.setString(1, reviewDesc);
			ps.setInt(2, rating);
			ps.setInt(3, userID);
			ps.setInt(4, equipmentID);

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Successfully added rating.");
			} else {
				System.out.println("Failed to add rating.");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void ps_editRating(String sql, String attributeToUpdate, String newValue, int reviewID) {
		try {
			ps = GRS.conn.prepareStatement(sql);
			ps.setString(1, attributeToUpdate);
			ps.setString(2, newValue);
			ps.setInt(3, reviewID);

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Successfully edited rating.");
			} else {
				System.out.println("Failed to edit rating.");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void ps_deleteRating(String sql, int reviewID) {
		try {
			ps = GRS.conn.prepareStatement(sql);
			ps.setInt(1, reviewID);

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Successfully deleted rating.");
			} else {
				System.out.println("Failed to delete rating.");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void ps_searchRating(String sql, int equipmentID) {
		try {
			ps = GRS.conn.prepareStatement(sql);
			ps.setInt(1, equipmentID);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		sqlQuery(GRS.conn, ps);
	}

}
