package students_pk.modules.data.classes;

import students_pk.lib.DB;

public class Student {
    private String lastName;
    private String firstName;
    private String middleName;
    private String faculty;
    private int facultyId;
    private int id;

    public Student(Integer id, String lastName, String firstName, String middleName, String faculty, Integer facultyId) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.faculty = faculty;
        this.facultyId = facultyId;
    }

    public int getId() {
        return this.id;
    }

    public String getLastName() {
        return this.lastName;
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

    public int getFacultyId() {
        return facultyId;
    }


    public void save() {
        String sql = "INSERT INTO student (LAST_NAME, FIRST_NAME, MIDDLE_NAME, FACULTY_ID) VALUES ('"
                + this.lastName + "', '"
                + this.firstName + "', '"
                + this.middleName + "', "
                + this.facultyId + ")";
        new DB(sql).execInsertOrUpdate();
    }

    public void update() {
        String sql = "UPDATE student SET " +
                "LAST_NAME = '" + this.lastName + "', " +
                "FIRST_NAME = '" + this.firstName + "', " +
                "MIDDLE_NAME = '" + this.middleName + "', " +
                "FACULTY_ID = " + this.facultyId +
                " WHERE ID = " + this.id;
        System.out.print(sql);
        new DB(sql).execInsertOrUpdate();
    }

    public void delete() {
        String sql = "DELETE FROM student WHERE ID = " + this.id;
        new DB(sql).execInsertOrUpdate();
    }


}
