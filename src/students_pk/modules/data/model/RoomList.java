package students_pk.modules.data.model;

import students_pk.lib.DB;

import java.util.ArrayList;

public class RoomList {

    public static ArrayList<Object[]> getList() {
        String sql = "SELECT ID, NUMBER FROM room";

        return new DB(sql).execSelect();
    }
}
