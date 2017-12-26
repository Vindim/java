package students_pk.modules.data.model;

import students_pk.lib.DB;
import java.util.ArrayList;

public class StudentList {

    public static ArrayList<Object[]> getList(int selectedFaculty) {
        String sql = "" +
                "SELECT " +
                    "s.ID, " +
                    "s.LAST_NAME, " +
                    "s.FIRST_NAME, " +
                    "s.MIDDLE_NAME, " +
                    "f.NAME AS FACULTY_NAME, " +
                    "s.FACULTY_ID " +
                "FROM student s " +
                "JOIN faculty f ON f.ID = s.FACULTY_ID";

        if (selectedFaculty != 0) {
            sql = sql + " WHERE f.ID = " + selectedFaculty;
        }
        return new DB(sql).execSelect();
    }
}

