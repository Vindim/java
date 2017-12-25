package students_pk.modules.data.model;

import students_pk.lib.DB;

import java.util.ArrayList;

public class ExamList {

    public static ArrayList<Object[]> getList() {
        String sql = "" +
                "SELECT " +
                "e.ID, " +
                "e.DISCIPLINE_ID, " +
                "e.ROOM_ID, " +
                "d.NAME AS DISCIPLINE_NAME, " +
                "r.NUMBER AS ROOM_NUMBER, " +
                "DATE_FORMAT(e.DATE, '%d.%m.%Y %H:%i') " +
                "FROM exam e " +
                "JOIN discipline d ON d.ID = e.DISCIPLINE_ID " +
                "JOIN room r ON r.ID = e.ROOM_ID";
        return new DB(sql).execSelect();
    }
}
