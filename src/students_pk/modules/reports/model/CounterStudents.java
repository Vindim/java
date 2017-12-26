package students_pk.modules.reports.model;

import students_pk.lib.DB;

import java.util.ArrayList;

public class CounterStudents {

    public static Double getPercent(Integer examId, Integer part) {
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

        String sqlCounterAllStudents = "SELECT COUNT(ID) FROM student WHERE FACULTY_ID IN (" + facultyString + ")";
        ArrayList<Object[]> allStudents = new DB(sqlCounterAllStudents).execSelect();

        Integer all;
        if (allStudents.size() != 0) {
            all = (Integer) allStudents.get(0)[0];
        }
        else all = 0;

        String beginPart = "";
        String endPart = "";

        switch (part) {
            case 0:
                beginPart = "0";
                endPart = "25";
                break;
            case 1:
                beginPart = "26";
                endPart = "50";
                break;
            case 2:
                beginPart = "51";
                endPart = "75";
                break;
            case 3:
                beginPart = "76";
                endPart = "100";
                break;
        }

        String sqlSelectPassedStudent = "SELECT COUNT(STUDENT_ID) AS COUNT FROM exam_result WHERE MARK <= " + endPart + " AND MARK >= "+ beginPart + " AND EXAM_ID = " + examId;
        ArrayList<Object[]> passedStudents = new DB(sqlSelectPassedStudent).execSelect();

        Integer passed;
        if (passedStudents.size() != 0) {
            passed = (Integer) passedStudents.get(0)[0];
        }
        else passed = 0;

        return (double) (passed * 100) / all;
    }

}
