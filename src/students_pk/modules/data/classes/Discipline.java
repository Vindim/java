package students_pk.modules.data.classes;

import students_pk.lib.DB;

import java.util.ArrayList;

public class Discipline {
    private String name;
    private int id;
    private int mask;

    public Discipline(int id, String name, int mask) {
        this.id = id;
        this.name = name;
        this.mask = mask;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getMask() {
        return mask;
    }

    public String toString() {
        return this.name;
    }

    public void save() {
        String selectMaxMask = "SELECT MAX(MASK) AS MASK FROM discipline";
        ArrayList<Object[]> maxMaskArray = new DB(selectMaxMask).execSelect();
        Object  row[] = maxMaskArray.get(0);
        Integer maxMask = (Integer) row[0];
        Integer newMask;
        if (maxMask == 0 || maxMask == null) newMask = 1;
        else newMask = maxMask * 2;
        String sql = "INSERT INTO discipline (NAME, MASK) VALUES ('" + this.name + "', " + newMask + ")";
        new DB(sql).execInsertOrUpdate();
    }

    public void update() {
        String sql = "UPDATE discipline SET " +
                "NAME = '" + this.name + "' " +
                " WHERE ID = " + this.id;
        new DB(sql).execInsertOrUpdate();
    }

    public String delete() {
        String sqlCheckUsage = "SELECT * FROM exam WHERE DISCIPLINE_ID = " + this.id;
        ArrayList<Object[]> check = new DB(sqlCheckUsage).execSelect();
        if (check.size() != 0) {
            return "Предмет используется и не может быть удалён";
        }

        String sql = "DELETE FROM discipline WHERE ID = " + this.id;
        new DB(sql).execInsertOrUpdate();
        return "ok";
    }
}
