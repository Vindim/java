package students_pk.modules.data.classes;

public class Faculty {
    private String name;
    private String id;

    public Faculty(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return this.name;
    }
}
