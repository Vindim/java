package students_pk.modules.data.model;

public class Student {
    private String lastName;
    private String firstName;
    private String middleName;
    private String faculty;

    public Student(String lastName, String firstName, String middleName, String faculty) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.faculty = faculty;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getFaculty() {
        return faculty;
    }


}
