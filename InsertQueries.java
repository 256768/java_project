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
      System.out.println("New user was inserted.");
    } catch (SQLException e) {
      System.out.println("This user is already in database.");
    }
  }

  public void insertNewUser(String major, String name, String surname) {
    if (major == null || name == null || surname == null)
      throw new NullPointerException("Email, name and surname must be set.");

    Connection conn = DBConnection.getDBConnection();

    String insertUser = "INSERT INTO user " + "(email,name,surname)" + "VALUES(?,?,?)";

    try (PreparedStatement prStmt = conn.prepareStatement(insertUser)) {
      prStmt.setString(1, major);

      prStmt.executeUpdate();

      System.out.println("New user was added to the database!");
    } catch (SQLException e) {
      System.out.println("The user already exists or worng input.");
      	e.printStackTrace();
    }
  }


}
