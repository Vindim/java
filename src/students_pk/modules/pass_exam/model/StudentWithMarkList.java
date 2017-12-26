package students_pk.modules.pass_exam.model;

import students_pk.lib.DB;

import java.util.ArrayList;

public class StudentWithMarkList {

    public static ArrayList<Object[]> getList(Integer examId) {
        String sqlSelectMask = "SELECT d.MASK FROM exam e JOIN discipline d ON d.ID = e.DISCIPLINE_ID WHERE e.ID = " + examId;

        ArrayList<Object[]> maskArray = new DB(sqlSelectMask).execSelect();
        Integer mask;
        if (maskArray.size() != 0) {
            mask = (Integer) maskArray.get(0)[0];
        }
        else mask = 0;


        String sqlSelectFaculty = "SELECT ID, FULL_MASK FROM faculty";
        ArrayList<Object[]> facultyArray = new DB(sqlSelectFaculty).execSelect();
        ArrayList<String> faculties = new ArrayList<>();

        for (int i = 0; i < facultyArray.size(); i++) {
            Integer facultyId = (Integer) facultyArray.get(i)[0];
            int fullMask = (int) facultyArray.get(i)[1];

            if ((fullMask & mask) != 0) {
                faculties.add(facultyId.toString());
            }
        }

        String facultyString = String.join(", ", faculties);

        if (facultyString.equals("")) facultyString = "0";


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
                "LEFT JOIN exam_result e ON e.STUDENT_ID = s.ID AND e.EXAM_ID = " + examId +
                " WHERE s.FACULTY_ID IN (" + facultyString + ")"
                ;
        return new DB(sql).execSelect();
    }
}
