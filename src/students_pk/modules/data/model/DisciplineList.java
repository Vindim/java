package students_pk.modules.data.model;

import students_pk.lib.DB;

import java.util.ArrayList;

public class DisciplineList {
    public static ArrayList<Object[]> getList() {
        String sql = "SELECT ID, NAME, MASK FROM discipline";
        return new DB(sql).execSelect();
    }
}
