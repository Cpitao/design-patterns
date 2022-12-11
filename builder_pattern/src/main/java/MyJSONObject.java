import java.util.HashMap;

public class MyJSONObject {

    private HashMap<String, StringObject> strings = new HashMap<>();
    private HashMap<String, IntObject> ints = new HashMap<>();
    private HashMap<String, FloatObject> floats = new HashMap<>();
    private HashMap<String, MyJSONObject> objects = new HashMap<>();

    public void addString(String name, StringObject string) {
        strings.put(name, string);
    }

    public void addInteger(String name, IntObject intObject) {
        ints.put(name, intObject);
    }

    public void addFloat(String name, FloatObject floatObject) {
        floats.put(name, floatObject);
    }

    public void addObject(String name, MyJSONObject jsonObject) {
        objects.put(name, jsonObject);
    }

    public HashMap<String, StringObject> getStrings() {
        return strings;
    }

    public HashMap<String, FloatObject> getFloats() {
        return floats;
    }

    public HashMap<String, IntObject> getInts() {
        return ints;
    }

    public HashMap<String, MyJSONObject> getObjects() {
        return objects;
    }

    public String toString() {
        return "{\n\tStrings: " + strings + '\n' +
                "\tIntegers: " + ints + '\n' +
                "\tFloats: " + floats + '\n' +
                "\tObjects:" + objects + "}\n";
    }
}
