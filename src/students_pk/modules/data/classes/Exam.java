package students_pk.modules.data.classes;

import students_pk.lib.DB;

import java.util.ArrayList;

public class Exam {

    private int id;
    private int disciplineId;
    private int roomId;

    private String discipline;
    private String room;
    private String date;

    public Exam(int id, int disciplineId, int roomId, String discipline, String room, String date) {
        this.id = id;
        this.disciplineId = disciplineId;
        this.roomId = roomId;
        this.discipline = discipline;
        this.room = room;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public int getDisciplineId() {
        return disciplineId;
    }

    public int getRoomId() {
        return roomId;
    }

    public String getDiscipline() {
        return discipline;
    }

    public String getRoom() {
        return room;
    }

    public String getDate() {
        return date;
    }

    public String toString() {
        return discipline;
    }

    public void save() {
        String sql = "INSERT INTO exam (DISCIPLINE_ID, ROOM_ID, DATE) VALUES ("
                + this.disciplineId + ", "
                + this.roomId + ", '"
                + this.date + "')";
        new DB(sql).execInsertOrUpdate();
    }

    public void update() {
        String sql = "UPDATE exam SET " +
                "DISCIPLINE_ID = " + this.disciplineId + ", " +
                "ROOM_ID = " + this.roomId + ", " +
                "DATE = '" + this.date + "' " +
                " WHERE ID = " + this.id;
        new DB(sql).execInsertOrUpdate();
    }

    public String delete() {
        String sqlCheckUsage = "SELECT * FROM exam_result WHERE EXAM_ID = " + this.id;
        ArrayList<Object[]> check = new DB(sqlCheckUsage).execSelect();
        if (check.size() != 0) {
            return "Некоторые студенты уже сдали этот экзамен, поэтому его удалить нельзя";
        }

        String sql = "DELETE FROM exam WHERE ID = " + this.id;
        new DB(sql).execInsertOrUpdate();
        return "ok";
    }
}
