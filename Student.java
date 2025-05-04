import java.util.ArrayList;
import java.util.List;

abstract class Student {
    protected int id;
    protected String firstName;
    protected String lastName;
    protected int birthYear;
    protected List<Integer> grades;
    protected String type;
    protected double storedAverage = 0; 

    public Student(int id, String type, String firstName, String lastName, int birthYear) {
        this.id = id;
        this.type = type;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthYear = birthYear;
        this.grades = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void addGrade(int grade) {
        grades.add(grade);
    }

    public String getType() {
        return type;
    }

    public double getAverage() {
        if (!grades.isEmpty()) {
            double sum = 0;
            for (int grade : grades) {
                sum += grade;
            }
            return sum / grades.size();
        } else {
            return storedAverage; 
        }
    }

    public void setAverage(double average) {
        this.storedAverage = average;
    }

    public abstract String getSkill();
}
