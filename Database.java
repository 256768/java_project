import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public void saveToDatabase() {
    String url = "jdbc:mysql://localhost:3306/university";
    String user = "username"; 
    String password = "password"; 

    try (Connection conn = DriverManager.getConnection(url, user, password)) {
        String sql = "INSERT INTO students (id, first_name, last_name, birth_year, type) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        for (Student student : students) {
            pstmt.setInt(1, student.getId());
            pstmt.setString(2, student.getFirstName());
            pstmt.setString(3, student.getLastName());
            pstmt.setInt(4, student.getBirthYear());
            pstmt.setString(5, student instanceof TelecomStudent ? "telecom" : "cyber");
            pstmt.addBatch();
        }
        pstmt.executeBatch();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

public void loadFromDatabase() {
    String url = "jdbc:mysql://localhost:3306/university"; 
    String user = "username"; 
    String password = "password"; 

    try (Connection conn = DriverManager.getConnection(url, user, password)) {
        String sql = "SELECT * FROM students";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            int birthYear = rs.getInt("birth_year");
            String type = rs.getString("type");

            if (type.equals("telecom")) {
                students.add(new TelecomStudent(id, firstName, lastName, birthYear));
            } else {
                students.add(new CyberSecurityStudent(id, firstName, lastName, birthYear));
            }
            nextId = Math.max(nextId, id + 1);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
