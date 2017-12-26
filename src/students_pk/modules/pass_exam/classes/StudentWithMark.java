package students_pk.modules.pass_exam.classes;

import students_pk.lib.DB;
import students_pk.modules.data.classes.Student;

public class StudentWithMark extends Student {
    private Integer mark;
    private Integer examId;

    public StudentWithMark(int id, String lastName, String firstName, String middleName, String faculty, int facultyId, Integer examId, Integer mark) {
        super(id, lastName, firstName, middleName, faculty, facultyId);
        this.mark = mark;
        this.examId = examId;
    }

    public String getMark() {
        if (mark == null || mark == 0) {
            return "";
        }
        else return mark.toString();
    }

    public Integer getExamId() {
        return examId;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public void setExamId(Integer examId) {
        this.examId = examId;
    }

    public void saveMark() {
        String sql = "INSERT INTO exam_result (EXAM_ID, STUDENT_ID, MARK) VALUES " +
                "(" + this.examId + ", " + this.id + ", " + this.mark + ")";
        new DB(sql).execInsertOrUpdate();
    }

    public void updateMark() {
        String sql = "UPDATE exam_result SET MARK = " + this.mark + " WHERE STUDENT_ID = " + this.id + " AND EXAM_ID = " + this.examId;
        new DB(sql).execInsertOrUpdate();
    }

    public void deleteMark() {
        String sql = "DELETE FROM exam_result WHERE STUDENT_ID = " + this.id + " AND EXAM_ID = " + this.examId;
        new DB(sql).execInsertOrUpdate();
    }

}
