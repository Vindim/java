package students_pk.modules.data.classes;

import students_pk.lib.DB;

import java.util.ArrayList;

public class Faculty {
    private String name;
    private int id;
    private int fullMask;

    public Faculty(int id, String name, int fullMask) {
        this.id = id;
        this.name = name;
        this.fullMask = fullMask;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getFullMask() {
        return fullMask;
    }

    public String toString() {
        return this.name;
    }

    public void save() {
        String sql = "INSERT INTO faculty (NAME, FULL_MASK) VALUES ('" + this.name + "', "+ this.fullMask +")";
        new DB(sql).execInsertOrUpdate();
    }

    public void update() {
        String sql = "UPDATE faculty SET " +
                "NAME = '" + this.name + "', " +
                "FULL_MASK = " + this.fullMask +
                " WHERE ID = " + this.id;
        new DB(sql).execInsertOrUpdate();
    }

    public String delete() {
        String sqlCheckUsage = "SELECT * FROM student WHERE FACULTY_ID = " + this.id;
        ArrayList<Object[]> check = new DB(sqlCheckUsage).execSelect();
        if (check.size() != 0) {
            return "Факультет используется и не может быть удалён";
        }

        String sql = "DELETE FROM faculty WHERE ID = " + this.id;
        new DB(sql).execInsertOrUpdate();
        return "ok";
    }
}
