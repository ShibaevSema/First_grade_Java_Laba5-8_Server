package semashibaev.ifmo.server.structCollection.substruct;

public class Person {
    private String name; //Поле может быть null

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name ;
    }
}