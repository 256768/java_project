import java.io.*;
import java.io.BufferedReader;



public class File extends UniversityDatabase  {
public static boolean saveStudentToFile(String fileName, int studentID) {
try {
	FileWriter fw = new FileWriter(fileName, true);
	BufferedWriter out = new BufferedWriter(fw);
	Student student = findStudentById(studentID);
	
	out.write(student.getId() + " " + student.getFirstName() + " " + student.getLastName() + " " + student.getBirthYear() + " " + student.getSkill()  +"\n") ;
	out.newLine();
	out.close();
	fw.close();
	
	return true;
}

catch (IOException e) {
            System.out.println("File cannot be created.");
            return false;
        }


}


public static boolean loadStudentFromFile(String fileName, int studentID) {
    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split(" ", 2);
            if (parts.length < 2) continue;

            int idFromFile = Integer.parseInt(parts[0]);
            if (idFromFile == studentID) {
                System.out.println("Student nalezen:");
                System.out.println(line);
                return true;
            }
        }
        System.out.println("Student s ID " + studentID + " nebyl nalezen v souboru.");
    } catch (IOException | NumberFormatException e) {
        System.out.println("Chyba při čtení souboru: " + e.getMessage());
    }
    return false;
}


}        