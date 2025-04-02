import java.util.*;

public class UniversityDatabase {
    private List<Student> students;
    private int nextId;

    public UniversityDatabase() {
        students = new ArrayList<>();
        nextId = 1; // ID od 1
    }

    public void addStudent(String type, String firstName, String lastName, int birthYear) {
        Student student;
        if (type.equals("telecom")) {
            student = new TelecomStudent(nextId++, firstName, lastName, birthYear);
        } else {
            student = new CyberSecurityStudent(nextId++, firstName, lastName, birthYear);
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

    public Student findStudentById(int id) {
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

    public void saveStudentToFile(int id, String filename) {
        Student student = findStudentById(id);
        if (student != null) {
            // ulozeni do souboru
        } else {
            System.out.println("Student not found.");
        }
    }

    public void loadStudentFromFile(String filename) {
        // nacteni ze souboru
    }

    public void saveToDatabase() {
        // SQL
    }

    public void loadFromDatabase() {
        // SQL
    }

    public static void main(String[] args) {
        UniversityDatabase db = new UniversityDatabase();
        Scanner scanner = new Scanner(System.in);
        String command;

        while (true) {
            System.out.println("Zadejte příkaz (add, grade, remove, find, list, average, count, save, load, exit):");
            command = scanner.nextLine();

            switch (command) {
                case "add":
                    System.out.println("Zadejte typ studenta (telecom/cyber):");
                    String type = scanner.nextLine();
                    System.out.println("Zadejte jméno:");
                    String firstName = scanner.nextLine();
                    System.out.println("Zadejte příjmení:");
                    String lastName = scanner.nextLine();
                    System.out.println("Zadejte rok narození:");
                    int birthYear = Integer.parseInt(scanner.nextLine());
                    db.addStudent(type, firstName, lastName, birthYear);
                    break;

                case "grade":
                    System.out.println("Zadejte ID studenta:");
                    int id = Integer.parseInt(scanner.nextLine());
                    System.out.println("Zadejte známku:");
                    int grade = Integer.parseInt(scanner.nextLine());
                    db.addGrade(id, grade);
                    break;

                case "remove":
                    System.out.println("Zadejte ID studenta k odstranění:");
                    int removeId = Integer.parseInt(scanner.nextLine());
                    db.removeStudent(removeId);
                    break;

                case "find":
                    System.out.println("Zadejte ID studenta:");
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
                    System.out.println("Zadejte typ (telecom/cyber):");
                    String avgType = scanner.nextLine();
                    double average = db.getGeneralAverage(avgType);
                    System.out.printf("Průměr pro %s: %.2f\n", avgType, average);
                    break;

                case "count":
                    System.out.println("Zadejte typ (telecom/cyber):");
                    String countType = scanner.nextLine();
                    int count = db.getStudentCount(countType);
                    System.out.printf("Počet studentů pro %s: %d\n", countType, count);
                    break;

                case "save":
                    System.out.println("Zadejte ID studenta k uložení do souboru:");
                    int saveId = Integer.parseInt(scanner.nextLine());
                    System.out.println("Zadejte název souboru:");
                    String saveFilename = scanner.nextLine();
                    db.saveStudentToFile(saveId, saveFilename);
                    break;

                case "load":
                    System.out.println("Zadejte název souboru pro načtení studenta:");
                    String loadFilename = scanner.nextLine();
                    db.loadStudentFromFile(loadFilename);
                    break;

                case "exit":
                    db.saveToDatabase();
                    System.out.println("Uložení do databáze a ukončení programu.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Neznámý příkaz. Zkuste to znovu.");
                    break;
            }
        }
    }
}

                   

