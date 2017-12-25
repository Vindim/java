package students_pk.modules.pass_exam.Classes;

import students_pk.modules.data.classes.Student;

public class StudentWithMark extends Student {
    public Integer mark;
    public Integer examId;

    public StudentWithMark(int id, String lastName, String firstName, String middleName, String faculty, int facultyId, Integer examId, Integer mark) {
        super(id, lastName, firstName, middleName, faculty, facultyId);
        this.mark = mark;
        this.examId = examId;
    }

    public Integer getMark() {
        return mark;
    }

    public Integer getExamId() {
        return examId;
    }


}
