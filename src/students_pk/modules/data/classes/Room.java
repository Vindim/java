package students_pk.modules.data.classes;

import students_pk.lib.DB;

import java.util.ArrayList;

public class Room {

    private String number;
    private int id;

    public Room(int id, String number) {
        this.id = id;
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public int getId() {
        return id;
    }

    public String toString() {
        return this.number;
    }

    public void save() {
        String sql = "INSERT INTO room (NUMBER) VALUES ('" + this.number + "')";
        new DB(sql).execInsertOrUpdate();
    }

    public void update() {
        String sql = "UPDATE room SET " +
                "NUMBER = '" + this.number + "' " +
                " WHERE ID = " + this.id;
        new DB(sql).execInsertOrUpdate();
    }

    public String delete() {
        String sqlCheckUsage = "SELECT * FROM exam WHERE ROOM_ID = " + this.id;
        ArrayList<Object[]> check = new DB(sqlCheckUsage).execSelect();
        if (check.size() != 0) {
            return "Аудитория используется и не может быть удалена";
        }

        String sql = "DELETE FROM room WHERE ID = " + this.id;
        new DB(sql).execInsertOrUpdate();
        return "ok";
    }
}
