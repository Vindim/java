package students_pk.models;

import students_pk.lib.DB;

import java.util.ArrayList;

public class Students {
    private String lastName;
    private String firstName;
    private String middleName;
    private String faculty;

    public Students(String lastName, String firstName, String middleName, String faculty) {
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

    public static ArrayList<String[]> getTable() {
        String sql = "SELECT s.ID, s.LAST_NAME, s.FIRST_NAME, s.MIDDLE_NAME, f.NAME AS FACULT_NAME  FROM student s JOIN faculty f ON f.ID = s.FACULTY_ID";

        String fields[] = {"id", "ln", "fn", "mn", "fan"};
        return new DB(fields, sql).execSelect();
        /*
        for (int i = 0; i < rs.size(); i ++) {
            String row[] = rs.get(i);

            String id = row[0];
            String lastName = row[1];
            String firstName = row[2];
            String middleName = row[3];
            String faculty = row[4];
            System.out.printf("id: %s, name: %s %s %s, faculty: %s\n", id, lastName, firstName, middleName, faculty);

        }*/
        //return rs;
    }
}

