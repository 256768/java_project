import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UniversityDatabase {
    protected static List<Student> students;
    private static int nextId;

    public UniversityDatabase() {
        students = new ArrayList<>();
        nextId = 1; 
    }

    public void addStudent( int id, String type,  String firstName, String lastName, int birthYear) {
        Student student;
        if (type.equals("telecom")) {
            student = new TelecomStudent(id, type,  firstName, lastName, birthYear);
        } else {
            student = new CyberSecurityStudent(id, type, firstName, lastName, birthYear);
        }
        students.add(student);
    }

    public void addGrade(int id, int grade) {
        for (Student student : students) {
            if (student.getId() == id) {
                student.addGrade(grade);
                return;
            }
        }
        System.out.println("Student not found.");
    }

    public void removeStudent(int id) {
        students.removeIf(student -> student.getId() == id);
    }

    public static Student findStudentById(int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    public void printAllStudents() {
        students.sort(Comparator.comparing(Student::getLastName));
        for (Student student : students) {
            System.out.printf("ID: %d, Name: %s %s, Year: %d, Average: %.2f\n",
                    student.getId(), student.getFirstName(), student.getLastName(),
                    student.getBirthYear(), student.getAverage());
        }
    }

    public double getGeneralAverage(String type) {
        double sum = 0;
        int count = 0;
        for (Student student : students) {
            if ((type.equals("telecom") && student instanceof TelecomStudent) ||
                (type.equals("cyber") && student instanceof CyberSecurityStudent)) {
                sum += student.getAverage();
                count++;
            }
        }
        return count > 0 ? sum / count : 0;
    }

    public int getStudentCount(String type) {
        int count = 0;
        for (Student student : students) {
            if ((type.equals("telecom") && student instanceof TelecomStudent) ||
                (type.equals("cyber") && student instanceof CyberSecurityStudent)) {
                count++;
            }
        }
        return count;
    }

   
    
    public void saveToDatabase() {
    	  Connection conn = DBConnection.getDBConnection();
    	    InsertQueries insertQueries = new InsertQueries();

    	    for (Student student : students) {
    	        String firstName = student.getFirstName();
    	        String lastName = student.getLastName();
    	        int birthYear = student.getBirthYear();
    	        String type = (student instanceof TelecomStudent) ? "telecom" : "cyber"; 
    	        double average = student.getAverage();


    	        insertQueries.insertNewUser(firstName, lastName, birthYear, type, (int) average); 
    	    }
    }
     
    public void loadFromDatabase() {
        Connection conn = DBConnection.getDBConnection();
        String query = "SELECT id, type, firstname, lastname, birthyear, average FROM user";

        try (PreparedStatement prStmt = conn.prepareStatement(query);
             ResultSet rs = prStmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String type = rs.getString("type");
                String firstName = rs.getString("firstname");
                String lastName = rs.getString("lastname");
                int birthYear = rs.getInt("birthyear");
                double average = rs.getDouble("average");

                addStudent(id, type, firstName, lastName, birthYear);

                Student s = findStudentById(id);
                if (s != null) {
                    s.setAverage(average);
                }
            }

            System.out.println("All students were loaded from the database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        UniversityDatabase db = new UniversityDatabase();
        Scanner scanner = new Scanner(System.in);
        DBConnection.setupUserTable();
        String command;

        while (true) {
            System.out.println("Input command (add, grade, remove, find, list, skill, average, count, save, load from file, load from database, exit):");
            command = scanner.nextLine();

            switch (command) {
                case "add":
                    System.out.println("Input student type (telecom/cyber): ");
                    String type = scanner.nextLine();
                    System.out.println("Input first name:");
                    String firstName = scanner.nextLine();
                    System.out.println("Input last name:");
                    String lastName = scanner.nextLine();
                    System.out.println("Input birth year:");
                    int birthYear = Integer.parseInt(scanner.nextLine());
                    db.addStudent(nextId++, type, firstName, lastName, birthYear);
                    break;

                case "grade":
                    System.out.println("Input students ID: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    System.out.println("Input grade:");
                    int grade = Integer.parseInt(scanner.nextLine());
                    db.addGrade(id, grade);
                    break;
                    
                case "skill":
                    System.out.println("Input student type (telecom/cyber): ");
                    String major = scanner.nextLine();
                    System.out.println("Input student's ID: ");
                    int skillID = Integer.parseInt(scanner.nextLine());

                    Student studentSkill = db.findStudentById(skillID);
                    if (studentSkill != null) {
                        if (major.equals("telecom") && studentSkill instanceof TelecomStudent) {
                            System.out.printf("Skill of %s %s (Telecom): %s\n",
                                    studentSkill.getFirstName(), studentSkill.getLastName(),
                                    studentSkill.getSkill());
                        } else if (major.equals("cyber") && studentSkill instanceof CyberSecurityStudent) {
                            System.out.printf("Skill of %s %s (Cyber Security): %s\n",
                                    studentSkill.getFirstName(), studentSkill.getLastName(),
                                    studentSkill.getSkill());
                        } else {
                            System.out.println("Student type does not match the provided ID.");
                        }
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;

                case "remove":
                    System.out.println("Input students ID to remove from database:");
                    int removeId = Integer.parseInt(scanner.nextLine());
                    db.removeStudent(removeId);
                    break;

                case "find":
                    System.out.println("Input students ID:");
                    int findId = Integer.parseInt(scanner.nextLine());
                    Student student = db.findStudentById(findId);
                    if (student != null) {
                        System.out.printf("ID: %d, Name: %s %s, Year: %d, Average: %.2f\n",
                                student.getId(), student.getFirstName(), student.getLastName(),
                                student.getBirthYear(), student.getAverage());
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;

                case "list":
                    db.printAllStudents();
                    break;

                case "average":
                    System.out.println("Input type (telecom/cyber):");
                    String avgType = scanner.nextLine();
                    double average = db.getGeneralAverage(avgType);
                    System.out.printf("Average grades of %s: %.2f\n", avgType, average);
                    break;

                case "count":
                    System.out.println("Input type (telecom/cyber):");
                    String countType = scanner.nextLine();
                    int count = db.getStudentCount(countType);
                    System.out.printf("Number of students of %s: %d\n", countType, count);
                    break;

                case "save":
                	System.out.println("Input file name:");
                    String saveFilename = scanner.nextLine();
                    System.out.println("Input students ID to save to file:");
                    int saveIdSave = Integer.parseInt(scanner.nextLine());
                    File.saveStudentToFile(saveFilename, saveIdSave);
                    db.saveToDatabase();
                    System.out.println("Saved to database and file.");
                    break;

                case "load from file":
                    System.out.println("Input file name to load student from file: ");
                    String loadFilename = scanner.nextLine();
                    System.out.println("Input students ID to save to file:");
                    int saveIdLoad = Integer.parseInt(scanner.nextLine());
                    File.loadStudentFromFile(loadFilename, saveIdLoad );
                    break;
                    
                case "load from database":
                    db.loadFromDatabase();
                    break;

                case "exit":
                    scanner.close();
                    DBConnection.closeConnection();
                    return;

                default:
                    System.out.println("Unknown command, please, try again: ");
                    break;
            }
        }
    }
}

                   

