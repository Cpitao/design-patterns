public class StringObject {

    private String string;

     public StringObject(String string) {
         this.string = string;
     }

    public String getString() {
        return string;
    }

    public String toString() {
         return '"' + string + '"';
    }
}
