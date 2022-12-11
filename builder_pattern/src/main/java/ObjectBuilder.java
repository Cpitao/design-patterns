public class ObjectBuilder {

    private MyJSONObject jsonObject = new MyJSONObject();

    public void append(String name, String s) {
        jsonObject.addString(name, new StringObject(s));
    }

    public void append(String name, int i) {
        jsonObject.addInteger(name, new IntObject(i));
    }

    public void append(String name, float f) {
        jsonObject.addFloat(name, new FloatObject(f));
    }

    public void append(String name, MyJSONObject jsonObject) {
        this.jsonObject.addObject(name, jsonObject);
    }

    public MyJSONObject toJSONObject() {
        return this.jsonObject;
    }

}
