package students_pk.modules.data.model;

import students_pk.lib.DB;
import java.util.ArrayList;

public class StudentList {

    public static ArrayList<String[]> getTable() {
        String sql = "SELECT s.ID, s.LAST_NAME, s.FIRST_NAME, s.MIDDLE_NAME, f.NAME AS FACULT_NAME  FROM student s JOIN faculty f ON f.ID = s.FACULTY_ID";

        return new DB(sql).execSelect();
    }
}

