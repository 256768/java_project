import java.io.*;
import java.io.BufferedReader;

public class File extends UniversityDatabase {

    public static boolean saveStudentToFile(String fileName, int studentID) {
        java.io.File file = new java.io.File(fileName);

        try {
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("The file has been created: " + fileName);
            }
        } catch (IOException e) {
            System.out.println("Error creating file: " + e.getMessage());
            return false;
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(" ", 2);
                if (parts.length < 2) continue;

                int idFromFile = Integer.parseInt(parts[0]);
                if (idFromFile == studentID) {
                    System.out.println("Error: Student with ID " + studentID + " already exists in the file.");
                    reader.close();
                    return false;
                }
            }

            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
            return false;
        }

        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(file, true));
            Student student = findStudentById(studentID);

            if (student == null) {
                System.out.println("Student with ID " + studentID + " was not found.");
                out.close();
                return false;
            }

            out.write(student.getId() + " " + student.getFirstName() + " " + student.getLastName() + " " +
                      student.getBirthYear() + " " + student.getType() + " " + student.getAverage());
            out.newLine();
            out.close();
            return true;
        } catch (IOException e) {
            System.out.println("Error writing to the file: " + e.getMessage());
            return false;
        }
    }

    public static boolean loadStudentFromFile(String fileName, int studentID) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(" ");
                if (parts.length < 5) continue;

                int idFromFile = Integer.parseInt(parts[0]);

                if (idFromFile == studentID) {
                    System.out.println("Student found:");
                    System.out.println(line);

                    String firstName = parts[1];
                    String lastName = parts[2];
                    int birthYear = Integer.parseInt(parts[3]);
                    String average = parts[5];

                    String dataToken = parts[4];

                    Student newStudent;

                    if (dataToken.matches("telecom")) {
                        newStudent = new TelecomStudent(studentID, "telecom", firstName, lastName, birthYear);
                    } else if (dataToken.matches("cyber")) {
                        newStudent = new CyberSecurityStudent(studentID, "cyber", firstName, lastName, birthYear);
                    } else {
                        System.out.println("Unknown data format: " + dataToken);
                        return false;
                    }

                    UniversityDatabase.students.add(newStudent);
                    System.out.println("Student has been successfully added to the database.");
                    return true;
                }
            }
            System.out.println("Student with ID " + studentID + " was not found in the file.");
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
        return false;
    }
}
