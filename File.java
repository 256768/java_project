import java.io.*;

public void saveStudentToFile(int id, String filename) {
    Student student = findStudentById(id);
    if (student != null) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(student.getId() + "," + student.getFirstName() + "," + student.getLastName() + "," + student.getBirthYear());
            writer.newLine();
            for (int grade : student.grades) {
                writer.write(grade + " ");
            }
        } catch (IOException e) {
            e.printStackTrace();

    public void loadStudentFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = reader.readLine();
            if (line != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String firstName = parts[1];
                String lastName = parts[2];
                int birthYear = Integer.parseInt(parts[3]);

                String type = parts[4]; 
                Student student;
                if (type.equals("telecom")) {
                    student = new TelecomStudent(id, firstName, lastName, birthYear);
                } else {
                    student = new CyberSecurityStudent(id, firstName, lastName, birthYear);
                }

                line = reader.readLine();
                if (line != null) {
                    String[] grades = line.trim().split(" ");
                    for (String gradeStr : grades) {
                        if (!gradeStr.isEmpty()) {
                            int grade = Integer.parseInt(gradeStr);
                            student.addGrade(grade);
                        }
                    }
                }

                students.add(student);
                nextId = Math.max(nextId, id + 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Chyba při načítání dat: " + e.getMessage());
        }
    }
}
