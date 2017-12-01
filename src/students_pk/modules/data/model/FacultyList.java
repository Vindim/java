package students_pk.modules.data.model;

import students_pk.lib.DB;

import java.util.ArrayList;

public class FacultyList {

    public static ArrayList<String[]> getList() {
        String sql = "SELECT ID, NAME FROM faculty";

        return new DB(sql).execSelect();
    }
}
