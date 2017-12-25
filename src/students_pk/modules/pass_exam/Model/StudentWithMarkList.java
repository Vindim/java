package students_pk.modules.pass_exam.Model;

import students_pk.lib.DB;

import java.util.ArrayList;

public class StudentWithMarkList {

    public static ArrayList<Object[]> getList() {
        String sql = "" +
                "SELECT " +
                "s.ID, " +
                "s.LAST_NAME, " +
                "s.FIRST_NAME, " +
                "s.MIDDLE_NAME, " +
                "f.NAME AS FACULTY_NAME, " +
                "s.FACULTY_ID, " +
                "e.EXAM_ID, " +
                "e.MARK " +
                "FROM student s " +
                "JOIN faculty f ON f.ID = s.FACULTY_ID " +
                "LEFT JOIN exam_result e ON e.STUDENT_ID = s.ID";
        /*if (selectedFaculty != 0) {
            sql = sql + " WHERE f.ID = " + selectedFaculty;
        }*/

        return new DB(sql).execSelect();
    }
}
