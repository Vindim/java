package students_pk.modules.data.classes;

import students_pk.lib.DB;

import java.util.ArrayList;

public class Faculty {
    private String name;
    private int id;

    public Faculty(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String toString() {
        return this.name;
    }

    public void save() {
        String sql = "INSERT INTO faculty (NAME) VALUES ('" + this.name + "')";
        new DB(sql).execInsertOrUpdate();
    }

    public void update() {
        String sql = "UPDATE faculty SET " +
                "NAME = '" + this.name + "' " +
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
