import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertQueries {

  public InsertQueries() {}

  public void performInsertQuery(String insertQuery) {
    if (insertQuery == null) {
      throw new NullPointerException("query must not be null!");
    } else if (insertQuery.isEmpty()) {
      throw new IllegalArgumentException("query must not be empty!");
    }
    Connection conn = DBConnection.getDBConnection();
    try (PreparedStatement prStmt = conn.prepareStatement(insertQuery);) {
      prStmt.executeUpdate();
      System.out.println("New user was inserted.");
    } catch (SQLException e) {
      System.out.println("Error inserting user.");
      e.printStackTrace();
    }
  }

  public void insertNewUser(String firstname, String lastname, int birthyear, String type, int average) {
    if (firstname == null || lastname == null || type == null)
      throw new NullPointerException("First name, last name, and type must be set.");

    Connection conn = DBConnection.getDBConnection();

    String insertUser = "INSERT INTO user (firstname, lastname, birthyear, type, average) VALUES(?,?,?,?,?)";

    try (PreparedStatement prStmt = conn.prepareStatement(insertUser)) {
      prStmt.setString(1, firstname);
      prStmt.setString(2, lastname);
      prStmt.setInt(3, birthyear);
      prStmt.setString(4, type);
      prStmt.setInt(5, average);

      prStmt.executeUpdate();
      System.out.println("New user was added to the database!");
    } catch (SQLException e) {
      System.out.println("The user already exists or incorrect input.");
      e.printStackTrace();
    }
  }
}
