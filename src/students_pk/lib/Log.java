package students_pk.lib;

import java.io.*;

public class Log {
    private String str;

    public Log(String str) {
        this.str = str;
    }

    public void writeLog() {
        File file = new File("src/students_pk/log.txt");
        try {
            FileWriter writer = new FileWriter(file, true);
            String log = str + "\r\n";
            writer.write(log);
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
